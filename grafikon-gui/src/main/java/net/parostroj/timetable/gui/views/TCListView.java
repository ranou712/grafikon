/*
 * TCListView.java
 *
 * Created on 12. září 2007, 13:35
 */
package net.parostroj.timetable.gui.views;

import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.parostroj.timetable.actions.TrainsCycleSort;
import net.parostroj.timetable.gui.views.TCDelegate.Action;
import net.parostroj.timetable.model.Train;
import net.parostroj.timetable.model.TrainsCycle;
import net.parostroj.timetable.utils.IdGenerator;
import net.parostroj.timetable.utils.ResourceLoader;

/**
 * List with all engine cycles and buttons for create and remove.
 *
 * @author jub
 */
public class TCListView extends javax.swing.JPanel implements TCDelegate.Listener, ListSelectionListener {
    
    private TCDelegate delegate;
    
    /** Creates new form ECListView */
    public TCListView() {
        initComponents();
        ecList.addListSelectionListener(this);
    }
    
    public void setModel(TCDelegate delegate) {
        this.delegate = delegate;
        this.delegate.addListener(this);

        this.updateView();
    }
    
    @Override
    public void tcEvent(Action action, TrainsCycle cycle, Train train) {
        switch (action) {
            case REFRESH:
                this.updateView();
                break;
            case NEW_CYCLE: case DELETED_CYCLE:
                this.updateView();
                break;
            case MODIFIED_CYCLE:
                ecList.repaint();
                break;
            case SELECTED_CHANGED:
                break;
            default:
                // nothing
        }
    }
    
    private void updateView() {
        if (delegate.getTrainDiagram() != null && delegate.getType() != null) {
            // set list
            List<TrainsCycle> sorted = (new TrainsCycleSort(TrainsCycleSort.Type.ASC)).sort(delegate.getTrainDiagram().getCycles(delegate.getType()));
            DefaultListModel listModel = new DefaultListModel();
            for (TrainsCycle cycle : sorted) {
                listModel.addElement(cycle);
            }
            ecList.setModel(listModel);
        } else {
            ecList.setModel(new DefaultListModel());
        }
        
        this.updateButtonStatus();
    }
    
    private void updateButtonStatus() {
        boolean status = delegate.getTrainDiagram() != null &&
            delegate.getType() != null && newNameTextField.getText().length() > 0;
        
        createButton.setEnabled(status);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting())
            return;
        // set selected engine
        Object[] selectedValues = ecList.getSelectedValues();
        if (selectedValues.length == 1)
            delegate.setSelectedCycle((TrainsCycle)ecList.getSelectedValue());
        else
            delegate.setSelectedCycle(null);
        deleteButton.setEnabled(selectedValues.length > 0);
    }
            
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPane = new javax.swing.JScrollPane();
        ecList = new javax.swing.JList();
        newNameTextField = new javax.swing.JTextField();
        createButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();

        scrollPane.setPreferredSize(new java.awt.Dimension(0, 132));

        ecList.setPrototypeCellValue("mmmmmmmmm");
        ecList.setVisibleRowCount(3);
        scrollPane.setViewportView(ecList);

        newNameTextField.setColumns(10);
        newNameTextField.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                newNameTextFieldCaretUpdate(evt);
            }
        });

        createButton.setText(ResourceLoader.getString("button.new")); // NOI18N
        createButton.setEnabled(false);
        createButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createButtonActionPerformed(evt);
            }
        });

        deleteButton.setText(ResourceLoader.getString("button.delete")); // NOI18N
        deleteButton.setEnabled(false);
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(createButton, javax.swing.GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(deleteButton, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE))
            .addComponent(newNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
            .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(newNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(deleteButton)
                    .addComponent(createButton)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        // get selected cycles ...
        Object[] selectedValues = ecList.getSelectedValues();
        for (Object selectedObject : selectedValues) {
            TrainsCycle cycle = (TrainsCycle)selectedObject;
            if (cycle != null) {
                // remove from diagram
                delegate.getTrainDiagram().removeCycle(cycle);
                // fire event
                delegate.fireEvent(TCDelegate.Action.DELETED_CYCLE, cycle);
            }
        }
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void createButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createButtonActionPerformed
        // get name from text field (ignore shorter than one character
        if (newNameTextField.getText().length() > 0 && delegate.getType() != null) {
            TrainsCycle cycle = new TrainsCycle(IdGenerator.getInstance().getId(), newNameTextField.getText(),"", delegate.getType());
            delegate.getTrainDiagram().addCycle(cycle);
            
            // clear field
            newNameTextField.setText("");
            // fire event
            delegate.fireEvent(TCDelegate.Action.NEW_CYCLE, cycle);
            // set selected
            ecList.setSelectedValue(cycle, true);
            delegate.fireEvent(TCDelegate.Action.SELECTED_CHANGED, cycle);
        }
    }//GEN-LAST:event_createButtonActionPerformed

    private void newNameTextFieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_newNameTextFieldCaretUpdate
        this.updateButtonStatus();
    }//GEN-LAST:event_newNameTextFieldCaretUpdate

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton createButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JList ecList;
    private javax.swing.JTextField newNameTextField;
    private javax.swing.JScrollPane scrollPane;
    // End of variables declaration//GEN-END:variables
}
