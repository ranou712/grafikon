package net.parostroj.timetable.output2.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import net.parostroj.timetable.actions.TrainComparator;
import net.parostroj.timetable.actions.TrainSort;
import net.parostroj.timetable.actions.TrainsHelper;
import net.parostroj.timetable.model.*;
import net.parostroj.timetable.utils.TimeConverter;

/**
 * Extracts information for train timetables.
 *
 * @author jub
 */
public class TrainTimetablesExtractor {

    private TrainDiagram diagram;
    private List<Train> trains;

    public TrainTimetablesExtractor(TrainDiagram diagram, List<Train> trains) {
        this.diagram = diagram;
        this.trains = trains;
    }

    public TrainTimetables getTrainTimetables() {
        List<TrainTimetable> result = new LinkedList<TrainTimetable>();

        for (Train train : trains) {
            result.add(this.createTimetable(train));
        }

        return new TrainTimetables(result);
    }

    private TrainTimetable createTimetable(Train train) {
        TrainTimetable timetable = new TrainTimetable();
        timetable.setName(train.getName());
        timetable.setCompleteName(train.getCompleteName());
        this.extractRouteInfo(train, timetable);
        this.extractDieselElectric(train, timetable);
        if (train.oneLineHasAttribute("line.controlled", Boolean.TRUE))
            timetable.setControlled(true);
        WeightDataExtractor wex = new WeightDataExtractor(train);
        timetable.setWeightData(wex.getData());
        this.extractLengthData(train, timetable);
        this.extractRows(train, timetable);
        return timetable;
    }

    private void extractDieselElectric(Train train, TrainTimetable timetable) {
        if (Boolean.TRUE.equals(train.getAttribute("diesel")))
            timetable.setDiesel(true);
        if (Boolean.TRUE.equals(train.getAttribute("electric")))
            timetable.setElectric(true);
    }

    private void extractRouteInfo(Train train, TrainTimetable timetable) {
        if (train.getAttribute("route.info") == null || ((String)train.getAttribute("route.info")).trim().equals("")) {
            return;
        }
        String result = (String) train.getAttribute("route.info");
        timetable.setRouteInfo(new LinkedList<RouteInfoPart>());
        // split
        String[] splitted = result.split("-");
        for (String sPart : splitted) {
            sPart = sPart.trim();
            if ("$1".equals(sPart)) {
                timetable.getRouteInfo().add(new RouteInfoPart(train.getStartNode().getName(), true));
            } else if ("$2".equals(sPart)) {
                timetable.getRouteInfo().add(new RouteInfoPart(train.getEndNode().getName(), true));
            } else if ("$".equals(sPart)) {
                timetable.getRouteInfo().add(new RouteInfoPart(train.getStartNode().getName(), true));
                timetable.getRouteInfo().add(new RouteInfoPart(train.getEndNode().getName(), true));
            } else {
                timetable.getRouteInfo().add(new RouteInfoPart(sPart, null));
            }
        }
    }

    private void extractLengthData(Train train, TrainTimetable timetable) {
        if (Boolean.TRUE.equals(train.getAttribute("show.station.length"))) {
            // compute maximal length
            List<Integer> lengths = new LinkedList<Integer>();
            for (TimeInterval interval : train.getTimeIntervalList()) {
                if (interval.isNodeOwner() && interval.isStop() && TrainsHelper.shouldCheckLength(interval.getOwnerAsNode(), train))
                    lengths.add((Integer)interval.getOwnerAsNode().getAttribute("length"));
            }
            // check if all lengths are set and choose the minimum
            Integer minLength = null;
            for (Integer sLength : lengths) {
                if (sLength == null)
                    return;
                else {
                    if (minLength == null || sLength.intValue() < minLength.intValue())
                        minLength = sLength;
                }
            }
            // get length unit
            String lengthUnit = null;
            boolean lengthInAxles = false;
            if (Boolean.TRUE.equals(diagram.getAttribute("station.length.in.axles"))) {
                lengthInAxles = true;
            } else {
                lengthUnit = (String)diagram.getAttribute("station.length.unit");
            }
            LengthData data = new LengthData();
            data.setLength(minLength);
            data.setLengthInAxles(lengthInAxles);
            data.setLengthUnit(lengthUnit);
            timetable.setLengthData(data);
        }
    }

    private void extractRows(Train train, TrainTimetable timetable) {
        timetable.setRows(new LinkedList<TrainTimetableRow>());
        Iterator<TimeInterval> i = train.getTimeIntervalList().iterator();
        TimeInterval lastLineI = null;
        while (i.hasNext()) {
            TimeInterval nodeI = i.next();
            TimeInterval lineI = i.hasNext() ? i.next() : null;

            if (nodeI.getOwnerAsNode().getType() == NodeType.SIGNAL)
                continue;

            TrainTimetableRow row = new TrainTimetableRow();

            if (Boolean.TRUE.equals(nodeI.getOwnerAsNode().getAttribute("control.station")))
                row.setControlStation(true);
            if ("new.signals".equals(nodeI.getOwnerAsNode().getAttribute("interlocking.plant")))
                row.setLightSignals(true);
            row.setStation(nodeI.getOwnerAsNode().getName());
            row.setStationType(nodeI.getOwnerAsNode().getType().getKey());
            if (!nodeI.isFirst())
                row.setArrival(TimeConverter.convertFromIntToText(nodeI.getStart()));
            if (!nodeI.isLast())
                row.setDeparture(TimeConverter.convertFromIntToText(nodeI.getEnd()));
            if (lineI != null)
                row.setSpeed(lineI.getSpeed());

            // comment
            if (Boolean.TRUE.equals(nodeI.getAttribute("comment.shown"))) {
                String comment = (String)nodeI.getAttribute("comment");
                if (comment != null && !comment.trim().equals(""))
                    row.setComment(comment);
            }
            // check line end
            if (nodeI.isLast() || nodeI.isInnerStop()) {
                if (Boolean.TRUE.equals(nodeI.getTrack().getAttribute("line.end"))) {
                    row.setLineEnd(Boolean.TRUE);
                }
            }
            // check occupied track
            if (Boolean.TRUE.equals(nodeI.getAttribute("occupied"))) {
                row.setOccupied(Boolean.TRUE);
            }
            // check shunt
            if (Boolean.TRUE.equals(nodeI.getAttribute("shunt"))) {
                row.setShunt(Boolean.TRUE);
            }

            if (nodeI.getOwnerAsNode().getTracks().size() > 1)
                row.setTrack(nodeI.getTrack().getNumber());

            boolean onControlled = checkOnControlled(nodeI.getOwnerAsNode());
            if (onControlled)
                row.setOnControlled(true);

            if (lastLineI != null && lastLineI.getToStraightTrack() != null)
                row.setStraight(lastLineI.getToStraightTrack() == nodeI.getTrack());
            if (Boolean.TRUE.equals(nodeI.getOwnerAsNode().getAttribute("trapezoid.sign"))) {
                row.setTrapezoidTrains(this.getTrapezoidTrains(nodeI));
            }

            LineClass lineClass = lineI != null ? (LineClass) lineI.getOwnerAsLine().getAttribute("line.class") : null;
            if (lineClass != null)
                row.setLineClass(lineClass.getName());

            timetable.getRows().add(row);

            lastLineI = lineI;
        }
    }

    private boolean checkOnControlled(Node node) {
        // get all lines for node
        boolean check = true;
        for (Line line : diagram.getNet().getLinesOf(node)) {
            check = check && (Boolean)line.getAttribute("line.controlled");
        }
        return check;
    }

    private List<String> getTrapezoidTrains(TimeInterval interval) {
        Node node = interval.getOwnerAsNode();
        Set<TimeInterval> over = node.getOverlappingTimeIntervals(interval);
        // filter out ...
        for (Iterator<TimeInterval> i = over.iterator(); i.hasNext();) {
            TimeInterval checked = i.next();
            if (interval.getStart() < checked.getStart()) {
                i.remove();
            }
        }
        if (over.size() == 0) {
            return null;
        } else {
            List<Train> tTrains = new ArrayList<Train>(over.size());
            for (TimeInterval ti : over) {
                tTrains.add(ti.getTrain());
            }
            TrainSort s = new TrainSort(new TrainComparator(TrainComparator.Type.ASC, node.getTrainDiagram().getTrainsData().getTrainSortPattern()));
            tTrains = s.sort(tTrains);
            List<String> result = new LinkedList<String>();
            for (Train t : tTrains) {
                result.add(t.getName());
            }
            return result;
        }
    }
}