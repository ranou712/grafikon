/*
 * SettingsDialog.java
 *
 * Created on 22. září 2007, 18:07
 */
package net.parostroj.timetable.gui.dialogs;

import java.math.BigDecimal;
import java.util.Arrays;
import javax.swing.JOptionPane;
import net.parostroj.timetable.gui.utils.ResourceLoader;
import net.parostroj.timetable.model.*;
import net.parostroj.timetable.model.units.LengthUnit;
import net.parostroj.timetable.model.units.UnitUtil;
import net.parostroj.timetable.model.units.WeightUnit;
import net.parostroj.timetable.utils.TimeConverter;
import net.parostroj.timetable.utils.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.awt.Insets;
import java.awt.GridBagConstraints;

/**
 * Dialog for settings modification of the train diagram.
 *
 * @author jub
 */
public class SettingsDialog extends javax.swing.JDialog {

    private static final Logger LOG = LoggerFactory.getLogger(SettingsDialog.class.getName());
    private boolean diagramChanged;
    private boolean recalculate;
    private TrainDiagram diagram;

    /** Creates new form SettingsDialog */
    public SettingsDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        diagramChanged = false;
        recalculate = false;

        sortComboBox.addItem(ResourceLoader.getString("modelinfo.sort.number"));
        sortComboBox.addItem(ResourceLoader.getString("modelinfo.sort.string"));
        sortComboBox.setPrototypeDisplayValue("nnnnnnnnnnnnn");

        nameTemplateEditBox.setLanguages(Arrays.asList(TextTemplate.Language.values()));
        cNameTemplateEditBox.setLanguages(Arrays.asList(TextTemplate.Language.values()));

        emptyWeightEditBox.setUnits(Arrays.asList(WeightUnit.values()));
        loadedWeightEditBox.setUnits(Arrays.asList(WeightUnit.values()));
        emptyWeightEditBox.setUnit(WeightUnit.T);
        loadedWeightEditBox.setUnit(WeightUnit.T);
        lengthPerAxleEditBox.setUnits(LengthUnit.getScaleDependent());
        lengthPerAxleEditBox.setUnit(LengthUnit.M);

        for (LengthUnit unit : LengthUnit.values()) {
            lengthUnitComboBox.addItem(unit);
        }

        pack();
    }

    public void setTrainDiagram(TrainDiagram diagram) {
        this.diagram = diagram;
        this.diagramChanged = false;
        this.recalculate = false;

        for (Scale scale : Scale.getPredefined()) {
            scaleComboBox.addItem(scale);
        }

        // set some values for speed
        for (double d = 4.0; d <= 6.0 ;) {
            ratioComboBox.addItem(Double.toString(d));
            d += 0.5;
        }

        this.updateValues();
    }

    private void updateValues() {
        if (diagram != null) {
            // set original values ...
            scaleComboBox.setSelectedItem(diagram.getAttribute(TrainDiagram.ATTR_SCALE));
            ratioComboBox.setSelectedItem(((Double)diagram.getAttribute(TrainDiagram.ATTR_TIME_SCALE)).toString());

            // sorting
            TrainsData trainsData = diagram.getTrainsData();
            SortPatternGroup firstGroup = trainsData.getTrainSortPattern().getGroups().get(0);
            if (firstGroup.getType() == SortPatternGroup.Type.NUMBER) {
                sortComboBox.setSelectedIndex(0);
            } else {
                sortComboBox.setSelectedIndex(1);
            }
            cNameTemplateEditBox.setTemplate(trainsData.getTrainCompleteNameTemplate());
            nameTemplateEditBox.setTemplate(trainsData.getTrainNameTemplate());

            // set crossing time in minutes
            Integer transferTime = (Integer)diagram.getAttribute(TrainDiagram.ATTR_STATION_TRANSFER_TIME);
            if (transferTime != null) {
                stationTransferTextField.setText(transferTime.toString());
            } else {
                LOG.warn("Station transfer time information missing.");
                stationTransferTextField.setText("");
            }

            // changes tracking
            changesTrackingCheckBox.setSelected(diagram.getChangesTracker().isTrackingEnabled());

            // script
            scriptEditBox.setScript(trainsData.getRunningTimeScript());

            // route length
            Double routeLengthRatio = (Double)diagram.getAttribute(TrainDiagram.ATTR_ROUTE_LENGTH_RATIO);
            rlRatioTextField.setText(routeLengthRatio != null ? routeLengthRatio.toString() : "");
            String routeLengthUnit = (String)diagram.getAttribute(TrainDiagram.ATTR_ROUTE_LENGTH_UNIT);
            rlUnitTextField.setText(routeLengthUnit != null ? routeLengthUnit : "");

            // weight -> length conversion
            loadedWeightEditBox.setValueInUnit(new BigDecimal((Integer) diagram.getAttribute(TrainDiagram.ATTR_WEIGHT_PER_AXLE)), WeightUnit.KG);
            emptyWeightEditBox.setValueInUnit(new BigDecimal((Integer) diagram.getAttribute(TrainDiagram.ATTR_WEIGHT_PER_AXLE_EMPTY)), WeightUnit.KG);
            lengthPerAxleEditBox.setValueInUnit(new BigDecimal((Integer) diagram.getAttribute(TrainDiagram.ATTR_LENGTH_PER_AXLE)), LengthUnit.MM);
            lengthUnitComboBox.setSelectedItem(diagram.getAttribute(TrainDiagram.ATTR_LENGTH_UNIT));

            // time range
            Integer fromTime = (Integer) diagram.getAttribute(TrainDiagram.ATTR_FROM_TIME);
            Integer toTime = (Integer) diagram.getAttribute(TrainDiagram.ATTR_TO_TIME);
            this.setTimeRange(fromTime, toTime);
        }
    }

    private void setTimeRange(Integer from, Integer to) {
        fromTimeTextField.setText(TimeConverter.convertFromIntToText(from != null ? from : 0));
        toTimeTextField.setText(TimeConverter.convertFromIntToTextNN(to != null ? to : TimeInterval.DAY));
    }

    private Tuple<Integer> getTimeRange() {
        int from = TimeConverter.convertFromTextToInt(fromTimeTextField.getText());
        int to = TimeConverter.convertFromTextToInt(toTimeTextField.getText());
        Integer fromTime = from == -1 ? 0 : from;
        Integer toTime = to == -1 ? TimeInterval.DAY : to;
        if (toTime == 0)
            toTime = TimeInterval.DAY;
        // check range (a least an hour)
        if (fromTime > toTime) {
            // swap
            Integer swap = fromTime;
            fromTime = toTime;
            toTime = swap;
        }
        // check minimal distance
        if ((toTime - fromTime) < 3600) {
            toTime = fromTime + 3600;
            if (toTime > TimeInterval.DAY) {
                toTime = TimeInterval.DAY;
                fromTime = toTime - 3600;
            }
        }
        if (fromTime == 0)
            fromTime = null;
        if (toTime == TimeInterval.DAY || toTime == 0)
            toTime = null;
        return new Tuple<Integer>(fromTime, toTime);
    }

    public boolean isDiagramChanged() {
        return diagramChanged;
    }

    public boolean isRecalculate() {
        return recalculate;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
        scaleComboBox = new javax.swing.JComboBox();
        javax.swing.JLabel jLabel2 = new javax.swing.JLabel();
        ratioComboBox = new javax.swing.JComboBox();
        javax.swing.JLabel jLabel3 = new javax.swing.JLabel();
        nameTemplateEditBox = new net.parostroj.timetable.gui.components.TextTemplateEditBox();
        javax.swing.JLabel jLabel4 = new javax.swing.JLabel();
        cNameTemplateEditBox = new net.parostroj.timetable.gui.components.TextTemplateEditBox();
        javax.swing.JLabel jLabel5 = new javax.swing.JLabel();
        sortComboBox = new javax.swing.JComboBox();
        javax.swing.JLabel jLabel6 = new javax.swing.JLabel();
        stationTransferTextField = new javax.swing.JTextField();
        changesTrackingCheckBox = new javax.swing.JCheckBox();
        javax.swing.JLabel jLabel11 = new javax.swing.JLabel();
        javax.swing.JPanel routeLengthPanel = new javax.swing.JPanel();
        javax.swing.JLabel jLabel12 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel14 = new javax.swing.JLabel();
        rlRatioTextField = new javax.swing.JTextField();
        javax.swing.JLabel jLabel13 = new javax.swing.JLabel();
        rlUnitTextField = new javax.swing.JTextField();
        javax.swing.JPanel weightPerAxlePanel = new javax.swing.JPanel();
        javax.swing.JLabel jLabel7 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel8 = new javax.swing.JLabel();
        loadedWeightEditBox = new net.parostroj.timetable.gui.components.ValueWithUnitEditBox();
        javax.swing.JLabel jLabel9 = new javax.swing.JLabel();
        emptyWeightEditBox = new net.parostroj.timetable.gui.components.ValueWithUnitEditBox();
        javax.swing.JPanel lengthPanel = new javax.swing.JPanel();
        javax.swing.JLabel jLabel10 = new javax.swing.JLabel();
        lengthPerAxleEditBox = new net.parostroj.timetable.gui.components.ValueWithUnitEditBox();
        javax.swing.JLabel jLabel15 = new javax.swing.JLabel();
        lengthUnitComboBox = new javax.swing.JComboBox();
        timeRangePanel = new javax.swing.JPanel();
        javax.swing.JLabel jLabel16 = new javax.swing.JLabel();
        fromTimeTextField = new javax.swing.JTextField();
        javax.swing.JLabel jLabel17 = new javax.swing.JLabel();
        toTimeTextField = new javax.swing.JTextField();
        scriptEditBox = new net.parostroj.timetable.gui.components.ScriptEditBox();
        javax.swing.JPanel panel1 = new javax.swing.JPanel();
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle(ResourceLoader.getString("modelinfo")); // NOI18N
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jLabel1.setText(ResourceLoader.getString("modelinfo.scales")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(5, 5, 5, 0);
        getContentPane().add(jLabel1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(5, 2, 5, 10);
        getContentPane().add(scaleComboBox, gridBagConstraints);

        jLabel2.setText(ResourceLoader.getString("modelinfo.ratio")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(0, 5, 5, 0);
        getContentPane().add(jLabel2, gridBagConstraints);

        ratioComboBox.setEditable(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 5, 10);
        getContentPane().add(ratioComboBox, gridBagConstraints);

        jLabel3.setText(ResourceLoader.getString("edit.traintypes.nametemplate")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(0, 5, 5, 0);
        getContentPane().add(jLabel3, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(0, 5, 5, 5);
        getContentPane().add(nameTemplateEditBox, gridBagConstraints);

        jLabel4.setText(ResourceLoader.getString("edit.traintypes.completenametemplate")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 5, 0);
        getContentPane().add(jLabel4, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(0, 5, 5, 5);
        getContentPane().add(cNameTemplateEditBox, gridBagConstraints);

        jLabel5.setText(ResourceLoader.getString("modelinfo.sort")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(0, 5, 5, 0);
        getContentPane().add(jLabel5, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 5, 10);
        getContentPane().add(sortComboBox, gridBagConstraints);

        jLabel6.setText(ResourceLoader.getString("modelinfo.crossing")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(0, 5, 5, 0);
        getContentPane().add(jLabel6, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(0, 5, 5, 10);
        getContentPane().add(stationTransferTextField, gridBagConstraints);

        changesTrackingCheckBox.setText(ResourceLoader.getString("modelinfo.tracking.changes")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(0, 2, 0, 10);
        getContentPane().add(changesTrackingCheckBox, gridBagConstraints);

        jLabel11.setText(ResourceLoader.getString("modelinfo.running.time.script")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(5, 5, 5, 0);
        getContentPane().add(jLabel11, gridBagConstraints);

        routeLengthPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel12.setText(ResourceLoader.getString("modelinfo.route.length") + " -"); // NOI18N
        routeLengthPanel.add(jLabel12);

        jLabel14.setText(ResourceLoader.getString("modelinfo.route.length.ratio") + ":"); // NOI18N
        routeLengthPanel.add(jLabel14);

        rlRatioTextField.setColumns(7);
        routeLengthPanel.add(rlRatioTextField);

        jLabel13.setText(ResourceLoader.getString("modelinfo.route.length.unit") + ":"); // NOI18N
        routeLengthPanel.add(jLabel13);

        rlUnitTextField.setColumns(5);
        routeLengthPanel.add(rlUnitTextField);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        getContentPane().add(routeLengthPanel, gridBagConstraints);

        weightPerAxlePanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel7.setText(ResourceLoader.getString("modelinfo.weight.per.axle") + " - "); // NOI18N
        weightPerAxlePanel.add(jLabel7);

        jLabel8.setText(ResourceLoader.getString("modelinfo.weight.per.axle.loaded") + ":"); // NOI18N
        weightPerAxlePanel.add(jLabel8);

        loadedWeightEditBox.setValueColumns(5);
        weightPerAxlePanel.add(loadedWeightEditBox);

        jLabel9.setText(ResourceLoader.getString("modelinfo.weight.per.axle.empty") + ":"); // NOI18N
        weightPerAxlePanel.add(jLabel9);

        emptyWeightEditBox.setValueColumns(5);
        weightPerAxlePanel.add(emptyWeightEditBox);

        gridBagConstraints_1 = new java.awt.GridBagConstraints();
        gridBagConstraints_1.gridx = 0;
        gridBagConstraints_1.gridy = 10;
        gridBagConstraints_1.gridwidth = 3;
        gridBagConstraints_1.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints_1.anchor = java.awt.GridBagConstraints.WEST;
        getContentPane().add(weightPerAxlePanel, gridBagConstraints_1);

        lengthPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel10.setText(ResourceLoader.getString("modelinfo.axle.length") + ":"); // NOI18N
        lengthPanel.add(jLabel10);

        lengthPerAxleEditBox.setValueColumns(5);
        lengthPanel.add(lengthPerAxleEditBox);

        jLabel15.setText(ResourceLoader.getString("modelinfo.length.unit") + ":"); // NOI18N
        lengthPanel.add(jLabel15);

        lengthPanel.add(lengthUnitComboBox);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        getContentPane().add(lengthPanel, gridBagConstraints);

        timeRangePanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel16.setText(ResourceLoader.getString("modelinfo.from.time")); // NOI18N
        timeRangePanel.add(jLabel16);

        fromTimeTextField.setColumns(7);
        fromTimeTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                timeTextFieldFocusLost(evt);
            }
        });
        timeRangePanel.add(fromTimeTextField);

        jLabel17.setText(ResourceLoader.getString("modelinfo.to.time")); // NOI18N
        timeRangePanel.add(jLabel17);

        toTimeTextField.setColumns(7);
        toTimeTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                timeTextFieldFocusLost(evt);
            }
        });
        timeRangePanel.add(toTimeTextField);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        getContentPane().add(timeRangePanel, gridBagConstraints);

        scriptEditBox.setColumns(80);
        scriptEditBox.setRows(8);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(scriptEditBox, gridBagConstraints);

        okButton.setText(ResourceLoader.getString("button.ok")); // NOI18N
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });
        panel1.add(okButton);

        cancelButton.setText(ResourceLoader.getString("button.cancel")); // NOI18N
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        panel1.add(cancelButton);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        getContentPane().add(panel1, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        boolean recalculateUpate = false;
        boolean clear = false;

        // get templates values
        TrainsData trainsData = diagram.getTrainsData();
        TextTemplate completeName = null;
        TextTemplate name = null;
        try {
            completeName = cNameTemplateEditBox.getTemplate();
            name = nameTemplateEditBox.getTemplate();
        } catch (GrafikonException e) {
            JOptionPane.showMessageDialog(this.getParent(), ResourceLoader.getString("dialog.error.emptytemplates"),
                    ResourceLoader.getString("dialog.error.title"), JOptionPane.ERROR_MESSAGE);
            LOG.debug("Error setting templates.", e);
            return;
        }

        // set scale
        Scale s = (Scale)scaleComboBox.getSelectedItem();
        // set ratio
        double sp = 1.0;
        try {
            sp = Double.parseDouble((String)ratioComboBox.getSelectedItem());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this.getParent(), ResourceLoader.getString("dialog.error.badratio"),
                    ResourceLoader.getString("dialog.error.title"), JOptionPane.ERROR_MESSAGE);
            LOG.debug("Cannot covert ratio.", ex);
            return;
        }
        if (s != null && !s.equals(diagram.getAttribute(TrainDiagram.ATTR_SCALE))) {
            diagram.setAttribute(TrainDiagram.ATTR_SCALE, s);
            recalculateUpate = true;
        }
        if (sp != ((Double)diagram.getAttribute(TrainDiagram.ATTR_TIME_SCALE)).doubleValue()) {
            diagram.setAttribute(TrainDiagram.ATTR_TIME_SCALE, sp);
            recalculateUpate = true;
        }

        // set templates
        if (!completeName.equals(trainsData.getTrainCompleteNameTemplate())) {
            trainsData.setTrainCompleteNameTemplate(completeName);
            clear = true;
        }
        if (!name.equals(trainsData.getTrainNameTemplate())) {
            trainsData.setTrainNameTemplate(name);
            clear = true;
        }

        // set sorting
        SortPattern sPattern = null;
        if (sortComboBox.getSelectedIndex() == 0) {
            sPattern = new SortPattern("(\\d*)(.*)");
            sPattern.getGroups().add(new SortPatternGroup(1, SortPatternGroup.Type.NUMBER));
            sPattern.getGroups().add(new SortPatternGroup(2, SortPatternGroup.Type.STRING));
        } else {
            sPattern = new SortPattern("(.*)");
            sPattern.getGroups().add(new SortPatternGroup(1, SortPatternGroup.Type.STRING));
        }
        if (!sPattern.getPattern().equals(trainsData.getTrainSortPattern().getPattern()))
            trainsData.setTrainSortPattern(sPattern);

        // set transfer time
        try {
            Integer difference = Integer.valueOf(stationTransferTextField.getText());
            if (difference != null && !difference.equals(diagram.getAttribute(TrainDiagram.ATTR_STATION_TRANSFER_TIME)))
                diagram.setAttribute(TrainDiagram.ATTR_STATION_TRANSFER_TIME, difference);
        } catch (NumberFormatException e) {
            LOG.warn("Cannot parse station transfer time: {}", stationTransferTextField.getText());
        }

        // changes tracking
        if (changesTrackingCheckBox.isSelected() != diagram.getChangesTracker().isTrackingEnabled()) {
            if (changesTrackingCheckBox.isSelected() && !diagram.getChangesTracker().isTrackingEnabled() &&
                    diagram.getChangesTracker().getCurrentChangeSet() == null) {
                diagram.getChangesTracker().addVersion(null, null, null);
                diagram.getChangesTracker().setLastAsCurrent();
            }
            diagram.getChangesTracker().setTrackingEnabled(changesTrackingCheckBox.isSelected());
        }

        // set running time script
        try {
            Script newScript = scriptEditBox.getScript();
            if (!diagram.getTrainsData().getRunningTimeScript().equals(newScript) && newScript != null) {
                diagram.getTrainsData().setRunningTimeScript(newScript);
                recalculateUpate = true;
            }
        } catch (GrafikonException e) {
            JOptionPane.showMessageDialog(this.getParent(), String.format(ResourceLoader.getString("dialog.error.script"), e.getCause().getMessage()),
                    ResourceLoader.getString("dialog.error.title"), JOptionPane.ERROR_MESSAGE);
            LOG.debug("Error setting script.", e);
            return;
        }

        // weight -> length conversion
        BigDecimal wpa = loadedWeightEditBox.getValueInUnit(WeightUnit.KG);
        BigDecimal wpae = emptyWeightEditBox.getValueInUnit(WeightUnit.KG);
        BigDecimal lpa = lengthPerAxleEditBox.getValueInUnit(LengthUnit.MM);
        LengthUnit lu = (LengthUnit) lengthUnitComboBox.getSelectedItem();
        if (lu != diagram.getAttribute(TrainDiagram.ATTR_LENGTH_UNIT))
            diagram.setAttribute(TrainDiagram.ATTR_LENGTH_UNIT, lu);
        this.setAttributeBDtoInt(TrainDiagram.ATTR_WEIGHT_PER_AXLE, wpa);
        this.setAttributeBDtoInt(TrainDiagram.ATTR_WEIGHT_PER_AXLE_EMPTY, wpae);
        this.setAttributeBDtoInt(TrainDiagram.ATTR_LENGTH_PER_AXLE, lpa);

        // route length
        try {
            if (rlRatioTextField.getText() != null && !"".equals(rlRatioTextField.getText())) {
                Double rlRatio = Double.valueOf(rlRatioTextField.getText());
                if (!rlRatio.equals(diagram.getAttribute(TrainDiagram.ATTR_ROUTE_LENGTH_RATIO)))
                    diagram.setAttribute(TrainDiagram.ATTR_ROUTE_LENGTH_RATIO, rlRatio);
            } else {
                if (diagram.getAttribute(TrainDiagram.ATTR_ROUTE_LENGTH_RATIO) != null)
                    diagram.removeAttribute(TrainDiagram.ATTR_ROUTE_LENGTH_RATIO);
            }
        } catch (NumberFormatException e) {
            LOG.warn("Cannot convert route length ratio to double.", e);
        }
        if (rlUnitTextField.getText() != null && !rlUnitTextField.getText().equals("")) {
            if (!rlUnitTextField.getText().trim().equals(diagram.getAttribute(TrainDiagram.ATTR_ROUTE_LENGTH_UNIT)))
                diagram.setAttribute(TrainDiagram.ATTR_ROUTE_LENGTH_UNIT, rlUnitTextField.getText().trim());
        } else {
            if (diagram.getAttribute(TrainDiagram.ATTR_ROUTE_LENGTH_UNIT) != null)
                diagram.removeAttribute(TrainDiagram.ATTR_ROUTE_LENGTH_UNIT);
        }

        // time range
        Tuple<Integer> timeRange = this.getTimeRange();
        if ((timeRange.first != null && !timeRange.first.equals(diagram.getAttribute(TrainDiagram.ATTR_FROM_TIME)))
                || (timeRange.first == null && diagram.getAttribute(TrainDiagram.ATTR_FROM_TIME) != null)) {
            if (timeRange.first == null)
                diagram.removeAttribute(TrainDiagram.ATTR_FROM_TIME);
            else
                diagram.setAttribute(TrainDiagram.ATTR_FROM_TIME, timeRange.first);
        }
        if ((timeRange.second != null && !timeRange.second.equals(diagram.getAttribute(TrainDiagram.ATTR_TO_TIME)))
                || (timeRange.second == null && diagram.getAttribute(TrainDiagram.ATTR_TO_TIME) != null)) {
            if (timeRange.second == null)
                diagram.removeAttribute(TrainDiagram.ATTR_TO_TIME);
            else
                diagram.setAttribute(TrainDiagram.ATTR_TO_TIME, timeRange.second);
        }

        // clear cached information for train names
        if (clear)
            for (Train train : diagram.getTrains())
                train.clearCachedData();

        this.updateValues();

        this.setVisible(false);
        this.recalculate = recalculateUpate;
        this.diagramChanged = true;
    }//GEN-LAST:event_okButtonActionPerformed

    private void setAttributeBDtoInt(String attr, BigDecimal value) {
        try {
            Integer cValue = UnitUtil.convert(value);
            if (!cValue.equals(diagram.getAttribute(attr)))
                diagram.setAttribute(attr, cValue);
        } catch (ArithmeticException e) {
            LOG.warn("Cannot convert {} attribute to int: {}", attr, e.getMessage());
        }
    }

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        this.updateValues();
        this.setVisible(false);
        this.diagramChanged = false;
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void timeTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_timeTextFieldFocusLost
        // focus lost - check time information
        Tuple<Integer> timeRange = this.getTimeRange();
        this.setTimeRange(timeRange.first, timeRange.second);
    }//GEN-LAST:event_timeTextFieldFocusLost

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private net.parostroj.timetable.gui.components.TextTemplateEditBox cNameTemplateEditBox;
    private javax.swing.JButton cancelButton;
    private javax.swing.JCheckBox changesTrackingCheckBox;
    private net.parostroj.timetable.gui.components.ValueWithUnitEditBox emptyWeightEditBox;
    private javax.swing.JTextField fromTimeTextField;
    private net.parostroj.timetable.gui.components.ValueWithUnitEditBox lengthPerAxleEditBox;
    private javax.swing.JComboBox lengthUnitComboBox;
    private net.parostroj.timetable.gui.components.ValueWithUnitEditBox loadedWeightEditBox;
    private net.parostroj.timetable.gui.components.TextTemplateEditBox nameTemplateEditBox;
    private javax.swing.JButton okButton;
    private javax.swing.JComboBox ratioComboBox;
    private javax.swing.JTextField rlRatioTextField;
    private javax.swing.JTextField rlUnitTextField;
    private javax.swing.JComboBox scaleComboBox;
    private net.parostroj.timetable.gui.components.ScriptEditBox scriptEditBox;
    private javax.swing.JComboBox sortComboBox;
    private javax.swing.JTextField stationTransferTextField;
    private javax.swing.JPanel timeRangePanel;
    private javax.swing.JTextField toTimeTextField;
    private GridBagConstraints gridBagConstraints_1;
    // End of variables declaration//GEN-END:variables
}
