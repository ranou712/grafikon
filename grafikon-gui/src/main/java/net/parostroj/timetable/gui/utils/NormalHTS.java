package net.parostroj.timetable.gui.utils;

import java.awt.Color;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import net.parostroj.timetable.gui.*;
import net.parostroj.timetable.gui.components.GraphicalTimetableView;
import net.parostroj.timetable.model.TimeInterval;
import net.parostroj.timetable.model.Train;
import net.parostroj.timetable.output2.gt.*;

/**
 * Implementation of HighlightedTrains for normal timetable view.
 *
 * @author jub
 */
public class NormalHTS implements HighlightedTrains, RegionSelector<TimeInterval>, ApplicationModelListener {

    private Set<Train> set = Collections.emptySet();
    private final Color selectionColor;
    private TimeInterval selectedTimeInterval;
    private final ApplicationModel model;
    private final GraphicalTimetableView view;
    private boolean selection;

    public NormalHTS(final ApplicationModel model, Color selectionColor,
            final GraphicalTimetableView view) {
        model.addListener(new ApplicationModelListener() {

            @Override
            public void modelChanged(ApplicationModelEvent event) {
                if (!selection && event.getType() == ApplicationModelEventType.SELECTED_TRAIN_CHANGED) {
                    final Train selectedTrain = model.getSelectedTrain();
                    view.selectItems(selectedTrain == null ? Collections.<TimeInterval>emptyList() : selectedTrain.getTimeIntervalList(), TimeInterval.class);
                }
            }
        });
        this.selectionColor = selectionColor;
        this.model = model;
        this.view = view;
        this.model.addListener(this);
    }

    @Override
    public boolean isHighlighedInterval(TimeInterval interval) {
        return set.contains(interval.getTrain());
    }

    @Override
    public Color getColor(TimeInterval interval) {
        return this.selectionColor;
    }

    @Override
    public void regionsSelected(List<TimeInterval> intervals) {
        selection = true;
        try {
            TimeInterval interval = SelectorUtils.select(intervals, selectedTimeInterval, SelectorUtils.createUniqueTrainIntervalFilter());
            // set selected train
            Train selected = interval != null ? interval.getTrain() : null;
            set = selected == null ? Collections.<Train>emptySet() : Collections.singleton(selected);
            model.setSelectedTrain(selected);
            selectedTimeInterval = interval;
            model.getMediator().sendMessage(new IntervalSelectionMessage(interval));
        } finally {
            selection = false;
        }
    }

    @Override
    public List<TimeInterval> getSelected() {
        Train train = model.getSelectedTrain();
        return train == null ? Collections.<TimeInterval>emptyList() : train.getTimeIntervalList();
    }

    @Override
    public boolean editSelected() {
        model.fireEvent(new ApplicationModelEvent(ApplicationModelEventType.EDIT_SELECTED_TRAIN, model));
        return true;
    };

    @Override
    public void modelChanged(ApplicationModelEvent event) {
        if (event.getType() == ApplicationModelEventType.SET_DIAGRAM_CHANGED)
            view.setTrainDiagram(event.getModel().getDiagram());
    }
}
