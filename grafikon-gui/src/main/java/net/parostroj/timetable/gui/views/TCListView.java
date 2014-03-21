/*
 * TCListView.java
 *
 * Created on 12. září 2007, 13:35
 */
package net.parostroj.timetable.gui.views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.parostroj.timetable.actions.TrainsCycleSort;
import net.parostroj.timetable.gui.components.ChangeDocumentListener;
import net.parostroj.timetable.gui.utils.GuiComponentUtils;
import net.parostroj.timetable.gui.utils.GuiIcon;
import net.parostroj.timetable.gui.views.TCDelegate.Action;
import net.parostroj.timetable.model.Train;
import net.parostroj.timetable.model.TrainsCycle;
import net.parostroj.timetable.utils.IdGenerator;

/**
 * List with all engine cycles and buttons for create and remove.
 *
 * @author jub
 */
public class TCListView extends javax.swing.JPanel implements TCDelegate.Listener, ListSelectionListener {

    private TCDelegate delegate;

    /** Creates new form ECListView */
    public TCListView() {
        setLayout(new BorderLayout(0, 0));
        ecList = new javax.swing.JList();
        javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane();
        add(scrollPane, BorderLayout.CENTER);

        ecList.setPrototypeCellValue("mmmmmmmmm");
        ecList.setVisibleRowCount(3);
        scrollPane.setViewportView(ecList);
        ecList.addListSelectionListener(this);

        javax.swing.JPanel panel = new javax.swing.JPanel();
        add(panel, BorderLayout.SOUTH);
        GridBagLayout gbl_panel = new GridBagLayout();
        panel.setLayout(gbl_panel);
        newNameTextField = new javax.swing.JTextField();
        GridBagConstraints gbc_newNameTextField = new GridBagConstraints();
        gbc_newNameTextField.weightx = 1.0;
        gbc_newNameTextField.fill = GridBagConstraints.HORIZONTAL;
        gbc_newNameTextField.insets = new Insets(3, 0, 0, 5);
        gbc_newNameTextField.gridx = 0;
        gbc_newNameTextField.gridy = 0;
        panel.add(newNameTextField, gbc_newNameTextField);

        newNameTextField.setColumns(7);
        newNameTextField.getDocument().addDocumentListener(new ChangeDocumentListener() {
            @Override
            protected void change() {
                updateButtonStatus();
            }
        });
        createButton = GuiComponentUtils.createButton(GuiIcon.ADD, 2);
        GridBagConstraints gbc_createButton = new GridBagConstraints();
        gbc_createButton.fill = GridBagConstraints.BOTH;
        gbc_createButton.insets = new Insets(3, 0, 0, 5);
        gbc_createButton.gridx = 1;
        gbc_createButton.gridy = 0;
        panel.add(createButton, gbc_createButton);
        editButton = GuiComponentUtils.createButton(GuiIcon.EDIT, 2);
        GridBagConstraints gbc_editButton = new GridBagConstraints();
        gbc_editButton.fill = GridBagConstraints.BOTH;
        gbc_editButton.insets = new Insets(3, 0, 0, 5);
        gbc_editButton.gridx = 2;
        gbc_editButton.gridy = 0;
        panel.add(editButton, gbc_editButton);
        deleteButton = GuiComponentUtils.createButton(GuiIcon.REMOVE, 2);
        GridBagConstraints gbc_deleteButton = new GridBagConstraints();
        gbc_deleteButton.insets = new Insets(3, 0, 0, 0);
        gbc_deleteButton.fill = GridBagConstraints.BOTH;
        gbc_deleteButton.gridx = 3;
        gbc_deleteButton.gridy = 0;
        panel.add(deleteButton, gbc_deleteButton);

        deleteButton.setEnabled(false);
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                deleteButtonActionPerformed(e);
            }
        });

        createButton.setEnabled(false);
        createButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createButtonActionPerformed(e);
            }
        });

        editButton.setEnabled(false);
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (delegate.getSelectedCycle() != null)
                    delegate.showEditDialog(editButton);
            }
        });
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
            case NEW_CYCLE:
            case DELETED_CYCLE:
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
            List<TrainsCycle> sorted = (new TrainsCycleSort(TrainsCycleSort.Type.ASC)).sort(delegate.getTrainDiagram()
                    .getCycles(delegate.getType()));
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
        boolean status = delegate.getTrainDiagram() != null && delegate.getType() != null
                && newNameTextField.getText().trim().length() > 0;

        createButton.setEnabled(status);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting())
            return;
        // set selected engine
        Object[] selectedValues = ecList.getSelectedValues();
        if (selectedValues.length == 1)
            delegate.setSelectedCycle((TrainsCycle) ecList.getSelectedValue());
        else
            delegate.setSelectedCycle(null);
        deleteButton.setEnabled(selectedValues.length > 0);
        editButton.setEnabled(selectedValues.length > 0);
    }

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // get selected cycles ...
        Object[] selectedValues = ecList.getSelectedValues();
        for (Object selectedObject : selectedValues) {
            TrainsCycle cycle = (TrainsCycle) selectedObject;
            if (cycle != null) {
                // remove from diagram
                delegate.getTrainDiagram().removeCycle(cycle);
                // fire event
                delegate.fireEvent(TCDelegate.Action.DELETED_CYCLE, cycle);
            }
        }
    }

    private void createButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // get name from text field (ignore shorter than one character
        if (newNameTextField.getText().length() > 0 && delegate.getType() != null) {
            TrainsCycle cycle = new TrainsCycle(IdGenerator.getInstance().getId(), newNameTextField.getText(), "",
                    delegate.getTrainDiagram().getCyclesType(delegate.getType()));
            delegate.getTrainDiagram().addCycle(cycle);

            // clear field
            newNameTextField.setText("");
            // fire event
            delegate.fireEvent(TCDelegate.Action.NEW_CYCLE, cycle);
            // set selected
            ecList.setSelectedValue(cycle, true);
            delegate.fireEvent(TCDelegate.Action.SELECTED_CHANGED, cycle);
        }
    }

    private final javax.swing.JButton createButton;
    private final javax.swing.JButton deleteButton;
    private final javax.swing.JButton editButton;
    private final javax.swing.JList ecList;
    private final javax.swing.JTextField newNameTextField;
}
