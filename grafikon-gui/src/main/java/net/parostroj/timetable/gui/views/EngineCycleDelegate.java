/*
 * EngineCycleDelegate.java
 *
 * Created on 16.9.2007, 15:35:44
 */
package net.parostroj.timetable.gui.views;

import java.util.List;

import javax.swing.JComponent;

import net.parostroj.timetable.gui.ApplicationModel;
import net.parostroj.timetable.gui.dialogs.TCDetailsViewDialogEngineClass;
import net.parostroj.timetable.model.TimeConverter;
import net.parostroj.timetable.model.TrainsCycle;
import net.parostroj.timetable.model.TrainsCycleItem;
import net.parostroj.timetable.model.TrainsCycleType;
import net.parostroj.timetable.utils.ResourceLoader;
import net.parostroj.timetable.utils.TransformUtil;
import net.parostroj.timetable.utils.Tuple;

/**
 * Implementation of the interface for engine cycle.
 *
 * @author jub
 */
public class EngineCycleDelegate extends TCDelegate {

    private TCDetailsViewDialogEngineClass editDialog;

    public EngineCycleDelegate(ApplicationModel model) {
        super(model);
    }

    @Override
    public String getTrainCycleErrors(TrainsCycle cycle) {
        StringBuilder result = new StringBuilder();
        List<Tuple<TrainsCycleItem>> conflicts = cycle.checkConflicts();
        for (Tuple<TrainsCycleItem> item : conflicts) {
            if (item.first.getToInterval().getOwnerAsNode() != item.second.getFromInterval().getOwnerAsNode()) {
                if (result.length() != 0) {
                    result.append('\n');
                }
                result.append(String.format(ResourceLoader.getString("ec.problem.nodes"),item.first.getTrain().getName(),item.first.getToInterval().getOwnerAsNode().getName(),item.second.getTrain().getName(),item.second.getFromInterval().getOwnerAsNode().getName()));
            } else if (item.first.getEndTime() >= item.second.getStartTime()) {
                if (result.length() != 0) {
                    result.append('\n');
                }
                TimeConverter c = item.first.getTrain().getDiagram().getTimeConverter();
                result.append(String.format(ResourceLoader.getString("ec.problem.time"),item.first.getTrain().getName(), c.convertIntToText(item.first.getEndTime()),item.second.getTrain().getName(), c.convertIntToText(item.second.getStartTime())));
            }
        }
        List<TrainsCycleItem> items = cycle.getItems();
        if (!items.isEmpty()) {
            TrainsCycleItem first = items.get(0);
            TrainsCycleItem last = items.get(items.size() - 1);
            if (first.getFromInterval().getOwnerAsNode() != last.getToInterval().getOwnerAsNode()) {
                if (result.length() != 0) {
                    result.append('\n');
                }
                result.append(ResourceLoader.getString("ec.problem.startend"));
            }
        }
        return result.toString();
    }

    @Override
    public void showEditDialog(JComponent component) {
        if (editDialog == null) {
            editDialog = new TCDetailsViewDialogEngineClass((java.awt.Frame)component.getTopLevelAncestor(), true);
        }
        editDialog.setLocationRelativeTo(component);
        editDialog.updateValues(this, model.getDiagram());
        editDialog.setVisible(true);
    }

    @Override
    public String getCycleDescription() {
        TrainsCycle cycle = getSelectedCycle();
        return TransformUtil.getEngineCycleDescription(cycle);
    }

    @Override
    public boolean isOverlappingEnabled() {
        return true;
    }

    @Override
    public TrainsCycleType getType() {
        return model.getDiagram() != null ? model.getDiagram().getEngineCycleType() : null;
    }
}
