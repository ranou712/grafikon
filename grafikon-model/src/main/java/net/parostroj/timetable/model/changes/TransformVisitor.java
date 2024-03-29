package net.parostroj.timetable.model.changes;

import net.parostroj.timetable.model.*;
import net.parostroj.timetable.model.events.*;
import net.parostroj.timetable.visitors.EventVisitor;
import net.parostroj.timetable.visitors.Visitable;

/**
 * Transforms diagram event into DiagramChange.
 *
 * @author jub
 */
public class TransformVisitor implements EventVisitor {

    private DiagramChange change;
    private final EventToChangeConvert converter = new EventToChangeConvert();
    private final NamesVisitor names = new NamesVisitor();

    @Override
    public void visit(TrainDiagramEvent event) {
        DiagramChange.Type type = converter.getType(event.getType());
        DiagramChange.Action action = converter.getAction(event.getType());
        if (type != null) {
            if (action == null)
                throw new IllegalArgumentException("Action missing: " + event.getType());
            change = new DiagramChange(type, action, ((ObjectWithId)event.getObject()).getId());
            // get name
            change.setObject(this.getObjectStr(event.getObject()));
        } else {
            change = new DiagramChange(DiagramChange.Type.DIAGRAM, action, event.getSource().getId()/*event.getObject() != null ? ((ObjectWithId) event.getObject()).getId(): event.getSource().getId()*/);
            if (action == null)
                throw new IllegalArgumentException("Action missing: " + event.getType());
            this.addDescription(event);
        }
    }

    @Override
    public void visit(NetEvent event) {
        DiagramChange.Type type = converter.getType(event.getType());
        DiagramChange.Action action = converter.getAction(event.getType());
        if (type != null) {
            if (action == null)
                throw new IllegalArgumentException("Action missing: " + event.getType());
            change = new DiagramChange(type, action, ((ObjectWithId)event.getObject()).getId());
            // get name
            change.setObject(this.getObjectStr(event.getObject()));
        }
    }

    @Override
    public void visit(FreightNetEvent event) {
        DiagramChange.Type type = converter.getType(event.getType());
        DiagramChange.Action action = converter.getAction(event.getType());
        ObjectWithId o = event.getConnection();
        if (type != null) {
            if (action == null) {
                throw new IllegalArgumentException("Action missing: " + event.getType());
            }
            change = new DiagramChange(type, action, o.getId());
        } else {
            change = new DiagramChange(DiagramChange.Type.FREIGHT_NET, action, event.getSource().getId());
            if (action == null) {
                throw new IllegalArgumentException("Action missing: " + event.getType());
            }
            this.addDescription(event);
        }
        if (o != null) {
            change.setObject(this.getObjectStr(o));
        }
    }

    @Override
    public void visit(NodeEvent event) {
        change = new DiagramChange(DiagramChange.Type.NODE, event.getSource().getId());
        change.setObject(event.getSource().getName());
        change.setAction(DiagramChange.Action.MODIFIED);
        this.addDescription(event);
    }

    @Override
    public void visit(LineEvent event) {
        change = new DiagramChange(DiagramChange.Type.LINE, event.getSource().getId());
        Line line = event.getSource();
        change.setObject(line.getFrom().getName() + " - " + line.getTo().getName());
        change.setAction(DiagramChange.Action.MODIFIED);
        this.addDescription(event);
    }

    @Override
    public void visit(TrainEvent event) {
        change = new DiagramChange(DiagramChange.Type.TRAIN, event.getSource().getId());
        change.setObject(event.getSource().getName());
        change.setAction(DiagramChange.Action.MODIFIED);
        String desc = this.addDescription(event);
        switch (event.getType()) {
            case TECHNOLOGICAL:
                change.addDescription(new DiagramChangeDescription(desc));
                break;
            case TIME_INTERVAL_ATTRIBUTE:
                TimeInterval ti = event.getSource().getTimeIntervalList().get(event.getChangedInterval());
                change.addDescription(new DiagramChangeDescription(desc,
                        new Parameter(event.getAttributeChange().getName(), true),
                        new Parameter(this.getSegmentDescription(ti))));
                break;
            case TIME_INTERVAL_LIST:
                desc = converter.getTilDesc(event.getTimeIntervalListType());
                DiagramChangeDescription dcd = new DiagramChangeDescription(desc);
                switch (event.getTimeIntervalListType()) {
                    case SPEED: case STOP_TIME: case TRACK:
                        dcd.setParams(new Parameter(getSegmentDescription(getChangedInterval(event))));
                        break;
                    default:
                        break;
                }
                change.addDescription(dcd);
                break;
            default:
                break;
        }
    }

    private TimeInterval getChangedInterval(TrainEvent event) {
        return event.getSource().getTimeIntervalList().get(event.getChangedInterval());
    }

    @Override
    public void visit(TrainTypeEvent event) {
        change = new DiagramChange(DiagramChange.Type.TRAIN_TYPE, event.getSource().getId());
        change.setObject(this.getObjectStr(event.getSource()));
        change.setAction(DiagramChange.Action.MODIFIED);
        this.addDescription(event);
    }

    @Override
    public void visit(TrainsCycleEvent event) {
        change = new DiagramChange(DiagramChange.Type.TRAINS_CYCLE, event.getSource().getId());
        change.setObject(event.getSource().getName());
        change.setAction(DiagramChange.Action.MODIFIED);
        String desc = this.addDescription(event);
        switch (event.getType()) {
            case CYCLE_ITEM_ADDED:
            case CYCLE_ITEM_MOVED:
            case CYCLE_ITEM_REMOVED:
            case CYCLE_ITEM_UPDATED:
                Train t = event.getNewCycleItem() != null ? event.getNewCycleItem().getTrain() : event.getOldCycleItem().getTrain();
                change.addDescription(new DiagramChangeDescription(desc, new Parameter(this.getObjectStr(t))));
                break;
            default:
                break;
        }
    }

    @Override
    public void visit(TrainsCycleTypeEvent event) {
        change = new DiagramChange(DiagramChange.Type.CYCLE_TYPE, event.getSource().getId());
        change.setObject(event.getSource().getName());
        change.setAction(DiagramChange.Action.MODIFIED);
        this.addDescription(event);
    }

    @Override
    public void visit(TextItemEvent event) {
        change = new DiagramChange(DiagramChange.Type.TEXT_ITEM, event.getSource().getId());
        change.setObject(event.getSource().getName());
        change.setAction(DiagramChange.Action.MODIFIED);
        this.addDescription(event);
    }

    @Override
    public void visit(OutputTemplateEvent event) {
        change = new DiagramChange(DiagramChange.Type.OUTPUT_TEMPLATE, event.getSource().getId());
        change.setObject(event.getSource().getName());
        change.setAction(DiagramChange.Action.MODIFIED);
        this.addDescription(event);
    }

    @Override
    public void visit(EngineClassEvent event) {
        change = new DiagramChange(DiagramChange.Type.ENGINE_CLASS, event.getSource().getId());
        change.setObject(event.getSource().getName());
        change.setAction(DiagramChange.Action.MODIFIED);
        String desc = this.addDescription(event);
        if (event.getType() == GTEventType.WEIGHT_TABLE_MODIFIED)
            change.addDescription(new DiagramChangeDescription(desc));
    }

    public DiagramChange getChange() {
        DiagramChange c = this.change;
        this.change = null;
        return c;
    }

    private String getObjectStr(Object object) {
        if (object instanceof Visitable) {
            ((Visitable)object).accept(names);
            return names.getName();
        } else {
            throw new IllegalArgumentException("Not known class: " + object.getClass());
        }
    }

    private String addDescription(GTEvent<?> event) {
        String desc = converter.getDesc(event.getType());
        AttributeChange aC = null;
        switch (event.getType()) {
            case ATTRIBUTE:
            case FREIGHT_NET_CONNECTION_ATTRIBUTE:
                // TODO transformation of attribute name? transformation table?
                aC = event.getAttributeChange();
                change.addDescription(new DiagramChangeDescription(desc,
                        new Parameter(aC.getName(), true)));
                break;
            case TRACK_ATTRIBUTE:
                aC = event.getAttributeChange();
                RouteSegmentEvent<?,?> rse = (RouteSegmentEvent<?, ?>) event;
                change.addDescription(new DiagramChangeDescription(desc,
                        new Parameter(aC.getName(), true),
                        new Parameter(rse.getTrack().getNumber())));
                break;
            default:
                break;
        }
        return desc;
    }

    private String getSegmentDescription(TimeInterval interval) {
        if (interval.isLineOwner()) {
            return interval.getFrom().getAbbr() + "-" + interval.getTo().getAbbr();
        } else {
            return interval.getOwnerAsNode().getName();
        }
    }
}
