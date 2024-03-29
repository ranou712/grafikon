package net.parostroj.timetable.mediator;

import net.parostroj.timetable.model.events.*;
import net.parostroj.timetable.visitors.EventVisitor;

/**
 * Colleague for receiving and processing events.
 *
 * @author jub
 */
public class GTEventsReceiverColleague extends AbstractColleague {

    private final EventVisitor visitor;

    public GTEventsReceiverColleague() {
        this.visitor = new EventVisitor() {
            @Override
            public void visit(OutputTemplateEvent event) {
                processGTEvent(event);
            }

            @Override
            public void visit(EngineClassEvent event) {
                processGTEvent(event);
            }

            @Override
            public void visit(TextItemEvent event) {
                processGTEvent(event);
            }

            @Override
            public void visit(TrainsCycleEvent event) {
                processTrainsCycleEvent(event);
            }

            @Override
            public void visit(TrainsCycleTypeEvent event) {
                processTrainsCycleTypeEvent(event);
            }

            @Override
            public void visit(TrainTypeEvent event) {
                processTrainTypeEvent(event);
            }

            @Override
            public void visit(TrainEvent event) {
                processTrainEvent(event);
            }

            @Override
            public void visit(LineEvent event) {
                processLineEvent(event);
            }

            @Override
            public void visit(NodeEvent event) {
                processNodeEvent(event);
            }

            @Override
            public void visit(NetEvent event) {
                processNetEvent(event);
            }

            @Override
            public void visit(TrainDiagramEvent event) {
                processTrainDiagramEvent(event);
            }

            @Override
            public void visit(FreightNetEvent event) {
                processFreightNetEvent(event);
            }
        };
    }

    @Override
    public void receiveMessage(Object message) {
        if (message instanceof GTEvent<?>) {
            processGTEventImpl((GTEvent<?>) message);
        }
    }

    private void processGTEventImpl(GTEvent<?> event) {
        // process by specific method
        event.accept(this.visitor);
        // process by common method
        processGTEventAll(event);
    }

    public void processLineEvent(LineEvent event) {}

    public void processNetEvent(NetEvent event) {}

    public void processNodeEvent(NodeEvent event) {}

    public void processTrainDiagramEvent(TrainDiagramEvent event) {}

    public void processTrainEvent(TrainEvent event) {}

    public void processTrainTypeEvent(TrainTypeEvent event) {}

    public void processTrainsCycleEvent(TrainsCycleEvent event) {}

    public void processTrainsCycleTypeEvent(TrainsCycleTypeEvent event) {}

    public void processFreightNetEvent(FreightNetEvent event) {}

    public void processGTEvent(GTEvent<?> event) {}

    public void processGTEventAll(GTEvent<?> event) {}

}
