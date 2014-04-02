package net.parostroj.timetable.gui.panes;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import net.parostroj.timetable.actions.FreightHelper;
import net.parostroj.timetable.gui.*;
import net.parostroj.timetable.gui.utils.GuiComponentUtils;
import net.parostroj.timetable.gui.utils.GuiIcon;
import net.parostroj.timetable.gui.views.graph.FreightNetGraphAdapter;
import net.parostroj.timetable.gui.views.graph.FreightNetGraphComponent;
import net.parostroj.timetable.model.*;
import net.parostroj.timetable.model.events.FreightNetEvent;
import net.parostroj.timetable.model.events.GTEventType;
import net.parostroj.timetable.model.events.TrainEvent;
import net.parostroj.timetable.utils.ResourceLoader;
import net.parostroj.timetable.utils.Tuple;

import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.mxGraphOutline;
import com.mxgraph.swing.handler.*;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource.mxIEventListener;
import com.mxgraph.view.mxCellState;
import com.mxgraph.view.mxGraphSelectionModel;

public class FreightNetPane extends javax.swing.JPanel implements StorableGuiData {

    private final class ConnectionPreview extends mxConnectPreview {
        private ConnectionPreview(mxGraphComponent graphComponent) {
            super(graphComponent);
        }

        @Override
        protected Object createCell(mxCellState startState, String style) {
            mxCell cell = (mxCell) super.createCell(startState, style);
            if (graph.getModel().isEdge(cell)) {
                cell.setStyle((style == null || "".equals(style) ? "" : style + ";")
                        + "endArrow=classic;startArrow=none");
            }
            return cell;
        }

        @Override
        public Object stop(boolean commit, MouseEvent e) {
            Object result = super.stop(commit, e);
            if (commit && result instanceof mxCell && ((mxCell) result).isEdge()) {
                // check if the connection is possible an create a new connection
                mxCell cell = (mxCell) result;
                FNNode from = (FNNode) cell.getSource().getValue();
                FNNode to = (FNNode) cell.getTarget().getValue();

                createConnection(from, to);

                graph.removeCells(new Object[] { result });
            }
            return result;
        }
    }

    private final class SelectionListener implements mxIEventListener {
        @Override
        public void invoke(Object sender, mxEventObject evt) {
            mxGraphSelectionModel mm = (mxGraphSelectionModel) sender;
            boolean selected = false;
            int length = mm.getCells().length;
            if (length > 0) {
                for (Object cell : mm.getCells()) {
                    Object value = ((mxCell) cell).getValue();
                    if (value instanceof FNConnection) {
                        selected = true;
                        break;
                    }
                    if (length == 1 && value instanceof FNNode) {
                        model.setSelectedTrain(((FNNode) value).getTrain());
                    }
                }
            }
            removeAction.setEnabled(selected);
        }
    }

    private TrainDiagram diagram;
    private FreightNetGraphAdapter graph;
    private FreightNetGraphComponent graphComponent;
    private final JPanel panel;
    private mxGraphOutline graphOutline;
    private mxRubberband selectionHandler;
    private final Action removeAction;
    private ApplicationModel model;

    public FreightNetPane() {
        setLayout(new BorderLayout());

        panel = new JPanel();
        add(panel, BorderLayout.SOUTH);
        panel.setLayout(new BorderLayout(0, 0));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel.add(buttonPanel, BorderLayout.WEST);

        buttonPanel.setLayout(new GridLayout(0, 1));
        JToggleButton selectButton = GuiComponentUtils.createToggleButton(GuiIcon.SELECT, 2);
        buttonPanel.add(selectButton);
        JToggleButton connectButton = GuiComponentUtils.createToggleButton(GuiIcon.CONNECT, 2);
        buttonPanel.add(connectButton);
        removeAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (graph.getSelectionCount() > 0) {
                    Object[] cells = graph.getSelectionCells();
                    for (Object cell : cells) {
                        mxCell m = (mxCell) cell;
                        if (m.getValue() instanceof FNConnection) {
                            diagram.getFreightNet().removeConnection((FNConnection) m.getValue());
                        }
                    }
                }
            }
        };
        removeAction.setEnabled(false);
        JButton removeButton = GuiComponentUtils.createButton(GuiIcon.REMOVE, 2, removeAction);
        buttonPanel.add(removeButton);

        ButtonGroup bg = new ButtonGroup();
        bg.add(selectButton);
        bg.add(connectButton);
        selectButton.setSelected(true);

        selectButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (selectionHandler != null)
                    selectionHandler.setEnabled(e.getStateChange() == ItemEvent.SELECTED);
            }
        });
        connectButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (graphComponent != null)
                    graphComponent.getConnectionHandler().setEnabled(e.getStateChange() == ItemEvent.SELECTED);
            }
        });
    }

    @Override
    public void saveToPreferences(AppPreferences prefs) {
    }

    @Override
    public void loadFromPreferences(AppPreferences prefs) {
    }

    public void setModel(ApplicationModel model) {
        this.model = model;
        model.getMediator().addColleague(new ApplicationGTEventColleague() {

            @Override
            public void processApplicationEvent(ApplicationModelEvent event) {
                if (event.getType() == ApplicationModelEventType.SET_DIAGRAM_CHANGED) {
                    diagram = event.getModel().get();
                    refreshDiagram();
                } else if (event.getType() == ApplicationModelEventType.SELECTED_TRAIN_CHANGED) {
                    selectTrain((Train) event.getObject());
                }
            }

            @Override
            public void processTrainEvent(TrainEvent event) {
                if (event.getType() == GTEventType.ATTRIBUTE && event.getAttributeChange().checkName(Train.ATTR_NAME)) {
                    updateNode(event.getSource());
                }
            }

            @Override
            public void processFreightNetEvent(FreightNetEvent event) {
                if (event.getType() == GTEventType.FREIGHT_NET_NODE_ADDED) {
                    updateNodeLocation(event.getNode());
                }
            }
        });
    }

    private void selectTrain(Train train) {
        if (train != null) {
            FNNode node = diagram.getFreightNet().getNode(train);
            mxCell cell = graph.getVertexToCellMap().get(node);
            graph.setSelectionCell(cell);
        }
    }

    private void refreshDiagram() {
        if (diagram != null) {
            if (graphComponent != null) {
                remove(graphComponent);
                panel.remove(graphOutline);
            }

            graph = new FreightNetGraphAdapter(diagram.getFreightNet().getGraph());
            graphComponent = new FreightNetGraphComponent(graph);
            graphComponent.setPageBackgroundColor(panel.getBackground());
            add(graphComponent, BorderLayout.CENTER);
            graphOutline = graphComponent.createOutline();
            panel.add(BorderLayout.CENTER, graphOutline);

            selectionHandler = new mxRubberband(graphComponent);

            mxConnectionHandler connectionHandler = graphComponent.getConnectionHandler();
            mxCellMarker marker = connectionHandler.getMarker();
            marker.setHotspot(1.0);
            graph.getSelectionModel().addListener(mxEvent.CHANGE, new SelectionListener());
            connectionHandler.setConnectPreview(new ConnectionPreview(graphComponent));
            graph.getModel().beginUpdate();
            try {
                for (FNNode node : diagram.getFreightNet().getNodes()) {
                    updateNodeLocation(node);
                }
            } finally {
                graph.getModel().endUpdate();
            }
        }
    }

    private void updateNode(Train train) {
        FNNode node = diagram.getFreightNet().getNode(train);
        mxCell cell = graph.getVertexToCellMap().get(node);
        graph.cellLabelChanged(cell, node, true);
    }

    private void updateNodeLocation(FNNode node) {
        mxCell cell = graph.getVertexToCellMap().get(node);
        graph.moveCells(new Object[] { cell }, node.getLocation().getX(), node.getLocation().getY());
    }

    private void createConnection(FNNode from, FNNode to) {
        if (diagram.getFreightNet().getConnection(from, to) != null || diagram.getFreightNet().getConnection(to, from) != null) {
            return;
        }
        // compute common nodes
        List<Tuple<TimeInterval>> selectedList = new LinkedList<Tuple<TimeInterval>>();
        for (TimeInterval fromInterval : FreightHelper.getNodeIntervalsFreightTo(from.getTrain().getTimeIntervalList())) {
            for (TimeInterval toInterval : FreightHelper.getNodeIntervalsFreightFrom(to.getTrain().getTimeIntervalList())) {
                Node toNode = toInterval.getOwnerAsNode();
                Node fromNode = fromInterval.getOwnerAsNode();
                if (toNode == fromNode && fromInterval.getEnd() < toInterval.getStart()) {
                    selectedList.add(new Tuple<TimeInterval>(fromInterval, toInterval));
                }
            }
        }
        if (!selectedList.isEmpty()) {
            Tuple<?> selected = null;
            if (selectedList.size() == 1) {
                selected = selectedList.get(0);
            } else {
                Object selection = JOptionPane.showInputDialog(this, ResourceLoader.getString("freight.connection") + ":",
                        ResourceLoader.getString("freight.connection"), JOptionPane.QUESTION_MESSAGE, null, selectedList.toArray(),
                        selectedList.get(0));
                if (selection != null) {
                    selected = (Tuple<?>) selection;
                }
            }
            if (selected != null) {
                diagram.getFreightNet().addConnection(from, to, (TimeInterval) selected.first, (TimeInterval) selected.second);
            }
        }
    }
}
