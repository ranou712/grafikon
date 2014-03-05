/*
 * EditInfoDialog.java
 *
 * Created on 13. říjen 2007, 13:03
 */
package net.parostroj.timetable.gui.dialogs;

import net.parostroj.timetable.gui.ApplicationModel;
import net.parostroj.timetable.gui.ApplicationModelEvent;
import net.parostroj.timetable.gui.ApplicationModelListener;
import net.parostroj.timetable.model.TrainDiagram;
import net.parostroj.timetable.utils.ResourceLoader;

/**
 * Dialog for editing additional information (temporary).
 *
 * @author jub
 */
public class EditInfoDialog extends javax.swing.JDialog implements ApplicationModelListener {

    private ApplicationModel model;

    /** Creates new form EditInfoDialog */
    public EditInfoDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public void setModel(ApplicationModel model) {
        this.model = model;
    }

    @Override
    public void modelChanged(ApplicationModelEvent event) {
        // do nothing
    }

    public void updateValues() {
        if (model.getDiagram() == null) {
            return;
        }
        routeNumberTextArea.setText((String) model.getDiagram().getAttribute(TrainDiagram.ATTR_ROUTE_NUMBERS));
        routesTextArea.setText((String) model.getDiagram().getAttribute(TrainDiagram.ATTR_ROUTE_NODES));
        validityTextField.setText((String) model.getDiagram().getAttribute(TrainDiagram.ATTR_ROUTE_VALIDITY));
    }

    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        dataPanel = new javax.swing.JPanel();
        javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
        javax.swing.JScrollPane scrollPane1 = new javax.swing.JScrollPane();
        routeNumberTextArea = new javax.swing.JTextArea();
        javax.swing.JLabel jLabel2 = new javax.swing.JLabel();
        javax.swing.JScrollPane scrollPane2 = new javax.swing.JScrollPane();
        routesTextArea = new javax.swing.JTextArea();
        javax.swing.JLabel jLabel3 = new javax.swing.JLabel();
        validityTextField = new javax.swing.JTextField();
        javax.swing.JPanel buttonsPanel = new javax.swing.JPanel();
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        FormListener formListener = new FormListener();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle(ResourceLoader.getString("info.title")); // NOI18N
        getContentPane().setLayout(new java.awt.GridBagLayout());

        dataPanel.setLayout(new java.awt.GridBagLayout());

        jLabel1.setText(ResourceLoader.getString("info.route.number")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        dataPanel.add(jLabel1, gridBagConstraints);

        routeNumberTextArea.setColumns(35);
        routeNumberTextArea.setFont(validityTextField.getFont());
        routeNumberTextArea.setRows(3);
        scrollPane1.setViewportView(routeNumberTextArea);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        dataPanel.add(scrollPane1, gridBagConstraints);

        jLabel2.setText(ResourceLoader.getString("info.routes")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        dataPanel.add(jLabel2, gridBagConstraints);

        routesTextArea.setColumns(35);
        routesTextArea.setFont(validityTextField.getFont());
        routesTextArea.setRows(5);
        scrollPane2.setViewportView(routesTextArea);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        dataPanel.add(scrollPane2, gridBagConstraints);

        jLabel3.setText(ResourceLoader.getString("info.validity")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        dataPanel.add(jLabel3, gridBagConstraints);

        validityTextField.setColumns(25);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        dataPanel.add(validityTextField, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(dataPanel, gridBagConstraints);

        okButton.setText(ResourceLoader.getString("button.ok")); // NOI18N
        okButton.addActionListener(formListener);
        buttonsPanel.add(okButton);

        cancelButton.setText(ResourceLoader.getString("button.cancel")); // NOI18N
        cancelButton.addActionListener(formListener);
        buttonsPanel.add(cancelButton);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        getContentPane().add(buttonsPanel, gridBagConstraints);

        pack();
    }

    private class FormListener implements java.awt.event.ActionListener {
        FormListener() {}
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            if (evt.getSource() == okButton) {
                EditInfoDialog.this.okButtonActionPerformed(evt);
            }
            else if (evt.getSource() == cancelButton) {
                EditInfoDialog.this.cancelButtonActionPerformed(evt);
            }
        }
    }

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // save values
        TrainDiagram diagram = model.getDiagram();
        String number = routeNumberTextArea.getText().trim(); if (number.equals("")) number = null;
        String nodes = routesTextArea.getText().trim(); if (nodes.equals("")) nodes = null;
        String validity = validityTextField.getText().trim(); if (validity.equals("")) validity = null;

        diagram.getAttributes().setRemove(TrainDiagram.ATTR_ROUTE_NUMBERS, number);
        diagram.getAttributes().setRemove(TrainDiagram.ATTR_ROUTE_NODES, nodes);
        diagram.getAttributes().setRemove(TrainDiagram.ATTR_ROUTE_VALIDITY, validity);

        this.setVisible(false);
    }

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {
        this.setVisible(false);
    }

    private javax.swing.JButton cancelButton;
    private javax.swing.JPanel dataPanel;
    private javax.swing.JButton okButton;
    private javax.swing.JTextArea routeNumberTextArea;
    private javax.swing.JTextArea routesTextArea;
    private javax.swing.JTextField validityTextField;
}
