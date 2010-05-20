/*
 * ECDetailsView2.java
 *
 * Created on 4. června 2008, 14:01
 */
package net.parostroj.timetable.gui.views;

import net.parostroj.timetable.gui.ApplicationModel;
import net.parostroj.timetable.gui.ApplicationModelEvent;
import net.parostroj.timetable.gui.ApplicationModelListener;
import net.parostroj.timetable.model.TrainsCycle;
import net.parostroj.timetable.utils.ResourceLoader;

/**
 * View with details about selected engine cycle.
 * 
 * @author jub
 */
public class TCDetailsView2 extends javax.swing.JPanel implements ApplicationModelListener {
    
    private ApplicationModel model;
    
    private TCDelegate delegate;
    
    /** Creates new form ECDetailsView2 */
    public TCDetailsView2() {
        initComponents();
    }
    
    public void setModel(ApplicationModel model, TCDelegate delegate) {
        model.addListener(this);
        this.model = model;
        this.delegate = delegate;
    }

    @Override
    public void modelChanged(ApplicationModelEvent event) {
        TCDelegate.Action action = delegate.transformEventType(event.getType());
        if (action != null)
            switch (action) {
                case SELECTED_CHANGED:
                    this.updateValues((TrainsCycle)event.getObject());
                    break;
                case MODIFIED_CYCLE:
                    if (delegate.getSelectedCycle(model) == (TrainsCycle)event.getObject())
                        this.updateValues((TrainsCycle)event.getObject());
                    break;
            }
    }
    
    private void updateValues(TrainsCycle cycle) {
        if (cycle == null) {
            nameTextField.setText("");
            descriptionTextField.setText("");
        } else {
            nameTextField.setText(cycle.getName());
            descriptionTextField.setText(delegate.getCycleDescription(model));
        }
        editButton.setEnabled(cycle != null);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nameTextField = new javax.swing.JTextField();
        javax.swing.JLabel nameLabel = new javax.swing.JLabel();
        javax.swing.JLabel descriptionLabel = new javax.swing.JLabel();
        descriptionTextField = new javax.swing.JTextField();
        editButton = new javax.swing.JButton();

        nameTextField.setColumns(10);
        nameTextField.setEditable(false);

        nameLabel.setText(ResourceLoader.getString("ec.details.name")); // NOI18N

        descriptionLabel.setText(ResourceLoader.getString("ec.details.description")); // NOI18N

        descriptionTextField.setColumns(10);
        descriptionTextField.setEditable(false);

        editButton.setText(ResourceLoader.getString("button.edit") + " ..."); // NOI18N
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nameLabel)
                    .addComponent(descriptionLabel))
                .addContainerGap(75, Short.MAX_VALUE))
            .addComponent(descriptionTextField)
            .addComponent(nameTextField)
            .addComponent(editButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(nameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(descriptionLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(descriptionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed
    // call dialog
    delegate.showEditDialog(editButton, model);
}//GEN-LAST:event_editButtonActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField descriptionTextField;
    private javax.swing.JButton editButton;
    private javax.swing.JTextField nameTextField;
    // End of variables declaration//GEN-END:variables
    
}
