package net.parostroj.timetable.gui;

import net.parostroj.timetable.mediator.AbstractColleague;
import net.parostroj.timetable.model.events.*;

/**
 * Colleague for application model.
 *
 * @author jub
 */
public class ApplicationModelColleague extends AbstractColleague implements ApplicationModelListener {

    private final ApplicationModel model;

    public ApplicationModelColleague(ApplicationModel model) {
        this.model = model;
        model.addListener(this);
    }

    @Override
    public void receiveMessage(Object message) {
        if (!model.isModelChanged()) {
            // react to some events to set changed model
            if (message instanceof TrainDiagramEvent) {
                TrainDiagramEvent tde = (TrainDiagramEvent) message;
                if (tde.getType() == GTEventType.TEXT_ITEM_ADDED || tde.getType() == GTEventType.TEXT_ITEM_REMOVED
                        || tde.getType() == GTEventType.TEXT_ITEM_MOVED || tde.getType() == GTEventType.IMAGE_ADDED
                        || tde.getType() == GTEventType.IMAGE_REMOVED
                        || tde.getType() == GTEventType.OUTPUT_TEMPLATE_ADDED
                        || tde.getType() == GTEventType.OUTPUT_TEMPLATE_MOVED
                        || tde.getType() == GTEventType.OUTPUT_TEMPLATE_REMOVED
                        || tde.getType() == GTEventType.GROUP_ADDED || tde.getType() == GTEventType.GROUP_REMOVED
                        || tde.getType() == GTEventType.CYCLE_TYPE_ADDED
                        || tde.getType() == GTEventType.CYCLE_TYPE_REMOVED || tde.getType() == GTEventType.ROUTE_ADDED
                        || tde.getType() == GTEventType.ROUTE_REMOVED || tde.getType() == GTEventType.ATTRIBUTE)
                    model.setModelChanged(true);
                if (tde.getType() == GTEventType.NESTED) {
                    GTEvent<?> nestedEvent = tde.getLastNestedEvent();
                    if (nestedEvent instanceof TextItemEvent
                            || nestedEvent instanceof OutputTemplateEvent
                            || (nestedEvent instanceof TrainEvent && ((TrainEvent) nestedEvent).getType() == GTEventType.ATTRIBUTE))
                        model.setModelChanged(true);
                }

            } else if (message instanceof TrainEvent) {
                TrainEvent te = (TrainEvent) message;
                if (te.getType() == GTEventType.ATTRIBUTE)
                    model.setModelChanged(true);
            }
        }
    }

    @Override
    public void modelChanged(ApplicationModelEvent event) {
        this.sendMessage(event);
    }
}
