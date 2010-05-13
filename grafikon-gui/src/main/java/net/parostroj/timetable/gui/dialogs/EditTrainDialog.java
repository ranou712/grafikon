/*
 * EditTrainDialog.java
 *
 * Created on 18. září 2007, 14:47
 */
package net.parostroj.timetable.gui.dialogs;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import net.parostroj.timetable.gui.ApplicationModel;
import net.parostroj.timetable.gui.ApplicationModelEvent;
import net.parostroj.timetable.gui.ApplicationModelEventType;
import net.parostroj.timetable.model.Train;
import net.parostroj.timetable.model.TrainType;
import net.parostroj.timetable.utils.ResourceLoader;

/**
 * Dialog for editation of train properties.
 *
 * @author jub
 */
public class EditTrainDialog extends javax.swing.JDialog {

    private static final Logger LOG = Logger.getLogger(EditTrainDialog.class.getName());

    public ApplicationModel model;

    /**
     * Creates new form EditTrainDialog.
     *
     * @param parent
     * @param modal
     */
    public EditTrainDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    /**
     * fills the dialog with train data.
     */
    public void getSelectedTrainData() {
        if (model != null && model.getSelectedTrain() != null)  {
            Train train = model.getSelectedTrain();
            // model for train types
            typeComboBox.setModel(new DefaultComboBoxModel(model.getDiagram().getTrainTypes().toArray()));
            typeComboBox.setSelectedItem(train.getType());
            dieselCheckBox.setSelected((Boolean)train.getAttribute("diesel"));
            electricCheckBox.setSelected((Boolean)train.getAttribute("electric"));
            showLengthCheckBox.setSelected(Boolean.TRUE.equals(train.getAttribute("show.station.length")));
            if (train.getType().getCategory().getKey().equals("freight")) {
                emptyCheckBox.setEnabled(true);
                emptyCheckBox.setSelected(Boolean.TRUE.equals(train.getAttribute("empty")));
            } else {
                emptyCheckBox.setEnabled(false);
                emptyCheckBox.setSelected(false);
            }

            numberTextField.setText(train.getNumber());

            descriptionTextField.setText(train.getDescription());
            speedTextField.setText(Integer.toString(train.getTopSpeed()));

            weightTextField.setText((String)train.getAttribute("weight.info"));
            routeTextField.setText((String)train.getAttribute("route.info"));

            timeBeforeTextField.setText(Integer.toString(train.getTimeBefore() / 60));
            timeAfterTextField.setText(Integer.toString(train.getTimeAfter() / 60));
        }
    }

    public void setModel(ApplicationModel model) {
        this.model = model;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
        typeComboBox = new javax.swing.JComboBox();
        dieselCheckBox = new javax.swing.JCheckBox();
        electricCheckBox = new javax.swing.JCheckBox();
        showLengthCheckBox = new javax.swing.JCheckBox();
        emptyCheckBox = new javax.swing.JCheckBox();
        numberTextField = new javax.swing.JTextField();
        javax.swing.JLabel jLabel2 = new javax.swing.JLabel();
        descriptionTextField = new javax.swing.JTextField();
        javax.swing.JLabel jLabel3 = new javax.swing.JLabel();
        speedTextField = new javax.swing.JTextField();
        javax.swing.JLabel jLabel4 = new javax.swing.JLabel();
        weightTextField = new javax.swing.JTextField();
        routeTextField = new javax.swing.JTextField();
        javax.swing.JLabel jLabel5 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel6 = new javax.swing.JLabel();
        javax.swing.JButton cancelButton = new javax.swing.JButton();
        javax.swing.JButton okButton = new javax.swing.JButton();
        timeBeforeTextField = new javax.swing.JTextField();
        timeAfterTextField = new javax.swing.JTextField();
        javax.swing.JLabel jLabel7 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel8 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle(ResourceLoader.getString("edit.train")); // NOI18N
        setResizable(false);

        jLabel1.setText(ResourceLoader.getString("create.train.type")); // NOI18N

        dieselCheckBox.setText(ResourceLoader.getString("create.train.diesel")); // NOI18N
        dieselCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        dieselCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));

        electricCheckBox.setText(ResourceLoader.getString("create.train.electric")); // NOI18N
        electricCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        electricCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));

        showLengthCheckBox.setText(ResourceLoader.getString("create.train.show.station.length")); // NOI18N
        showLengthCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        showLengthCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));

        emptyCheckBox.setText(ResourceLoader.getString("create.train.empty")); // NOI18N
        emptyCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        emptyCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));

        numberTextField.setColumns(180);

        jLabel2.setText(ResourceLoader.getString("create.train.number")); // NOI18N

        jLabel3.setText(ResourceLoader.getString("create.train.description")); // NOI18N

        speedTextField.setColumns(10);

        jLabel4.setText(ResourceLoader.getString("create.train.speed")); // NOI18N

        jLabel5.setText(ResourceLoader.getString("edit.train.weight")); // NOI18N

        jLabel6.setText(ResourceLoader.getString("edit.train.route")); // NOI18N

        cancelButton.setText(ResourceLoader.getString("button.cancel")); // NOI18N
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        okButton.setText(ResourceLoader.getString("button.ok")); // NOI18N
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        jLabel7.setText(ResourceLoader.getString("create.train.technological.time")); // NOI18N

        jLabel8.setText(ResourceLoader.getString("create.train.time.before")); // NOI18N

        jLabel9.setText(ResourceLoader.getString("create.train.time.after")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(numberTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(speedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(279, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(timeBeforeTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(timeAfterTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                .addContainerGap(44, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(292, Short.MAX_VALUE)
                .addComponent(okButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cancelButton)
                .addGap(10, 10, 10))
            .addGroup(layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(emptyCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(showLengthCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(dieselCheckBox)
                        .addGap(18, 18, 18)
                        .addComponent(electricCheckBox)))
                .addContainerGap(108, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(weightTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(descriptionTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(typeComboBox, 0, 355, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(routeTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(typeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dieselCheckBox)
                    .addComponent(electricCheckBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(showLengthCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(emptyCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(numberTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(descriptionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(speedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(weightTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(routeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(timeBeforeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(timeAfterTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(okButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
    this.setVisible(false);
}//GEN-LAST:event_cancelButtonActionPerformed

private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
    Train train = model.getSelectedTrain();
    // set values to train ...
    if (train.getType() != typeComboBox.getSelectedItem())
        train.setType((TrainType)typeComboBox.getSelectedItem());
    if (!train.getAttribute("diesel").equals(dieselCheckBox.isSelected()))
        train.setAttribute("diesel", dieselCheckBox.isSelected());
    if (!train.getAttribute("electric").equals(electricCheckBox.isSelected()))
        train.setAttribute("electric", electricCheckBox.isSelected());
    if (showLengthCheckBox.isSelected() && !Boolean.TRUE.equals(train.getAttribute("show.station.length")))
        train.setAttribute("show.station.length", Boolean.TRUE);
    else if (!showLengthCheckBox.isSelected())
        train.removeAttribute("show.station.length");
    if (emptyCheckBox.isSelected() && !Boolean.TRUE.equals(train.getAttribute("empty")))
        train.setAttribute("empty", Boolean.TRUE);
    else if (!emptyCheckBox.isSelected())
        train.removeAttribute("empty");
    if (!numberTextField.getText().equals(train.getNumber()))
        train.setNumber(numberTextField.getText());
    if (!descriptionTextField.getText().equals(train.getDescription()))
        train.setDescription(descriptionTextField.getText());
    String newWI = weightTextField.getText().trim();
    String oldWI = (String)train.getAttribute("weight.info");
    String newRI = routeTextField.getText().trim();
    String oldRI = (String)train.getAttribute("route.info");
    if (!newWI.equals("") && !newWI.equals(oldWI)) {
        train.setAttribute("weight.info", newWI);
    } else if (newWI.equals("") && oldWI != null)
        train.removeAttribute("weight.info");
    if (!newRI.equals("") && !newRI.equals(oldRI)) {
        train.setAttribute("route.info", newRI);
    } else if (newRI.equals("") && oldRI != null)
        train.removeAttribute("route.info");

    boolean changed = false;
    // check max speed - modify if changed
    try {
        int maxSpeed = Integer.parseInt(speedTextField.getText());
        if (maxSpeed != train.getTopSpeed()) {
            // modify top speed
            train.setTopSpeed(maxSpeed);
            train.recalculate();
            // fire event
            changed = true;
        }
    } catch (NumberFormatException e) {
        LOG.log(Level.WARNING, "Cannot convert speed to number: {0}", speedTextField.getText());
    }

    // technological times
    try {
        int timeBefore = Integer.parseInt(timeBeforeTextField.getText()) * 60;
        int timeAfter = Integer.parseInt(timeAfterTextField.getText()) * 60;
        if (timeBefore != train.getTimeBefore()) {
            train.setTimeBefore(timeBefore);
            changed = true;
        }
        if (timeAfter != train.getTimeAfter()) {
            train.setTimeAfter(timeAfter);
            changed = true;
        }
    } catch (NumberFormatException e) {
        LOG.log(Level.WARNING, "Cannot convert technological time: {0}, {1}", new Object[]{timeBeforeTextField.getText(), timeAfterTextField.getText()});
    }

    // fire changed event
    if (changed)
        model.fireEvent(new ApplicationModelEvent(ApplicationModelEventType.MODIFIED_TRAIN,model,train));
    // fire event
    model.fireEvent(new ApplicationModelEvent(ApplicationModelEventType.MODIFIED_TRAIN_NAME_TYPE,model,train));

    this.setVisible(false);
}//GEN-LAST:event_okButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField descriptionTextField;
    private javax.swing.JCheckBox dieselCheckBox;
    private javax.swing.JCheckBox electricCheckBox;
    private javax.swing.JCheckBox emptyCheckBox;
    private javax.swing.JTextField numberTextField;
    private javax.swing.JTextField routeTextField;
    private javax.swing.JCheckBox showLengthCheckBox;
    private javax.swing.JTextField speedTextField;
    private javax.swing.JTextField timeAfterTextField;
    private javax.swing.JTextField timeBeforeTextField;
    private javax.swing.JComboBox typeComboBox;
    private javax.swing.JTextField weightTextField;
    // End of variables declaration//GEN-END:variables

}
