package net.parostroj.timetable.gui.components;

import java.awt.event.ItemEvent;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

/**
 * Editing component for length.
 *
 * @author jub
 */
public class LengthEditBox extends javax.swing.JPanel {

    private static final Logger LOG = Logger.getLogger(LengthEditBox.class.getName());

    private int value;

    /** Creates new form LengthEditBox */
    public LengthEditBox() {
        initComponents();

        // fill unit combo box
        for (LengthUnit unit : LengthUnit.values()) {
            unitComboBox.addItem(unit);
        }

        DecimalFormat format = new  DecimalFormat("#0.########");
        format.setDecimalSeparatorAlwaysShown(false);
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setMinimum(Double.valueOf(0.0));
        valueTextField.setFormatterFactory(new DefaultFormatterFactory(formatter));

        setValue(0);
    }

    public void setValueColumns(int length) {
        valueTextField.setColumns(length);
    }

    public int getValueColumns() {
        return valueTextField.getColumns();
    }

    public int getValue() {
        return getValueImpl(getUnit());
    }

    private int getValueImpl(LengthUnit unit) {
        // convert to double
        try {
            valueTextField.commitEdit();
            Double valueDouble = this.getValueFromField();
            return unit.convertFrom(valueDouble);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
            setValueImpl(this.value, unit);
            return this.value;
        }
    }

    public void setValue(int value) {
        this.value = value;
        setValueImpl(value, getUnit());
    }

    private void setValueImpl(int value, LengthUnit unit) {
        double valueDouble = unit.convertTo(value);
        this.setValueToField(valueDouble);
        valueTextField.setCaretPosition(0);
    }

    public LengthUnit getUnit() {
        return (LengthUnit) unitComboBox.getSelectedItem();
    }

    public void setUnit(LengthUnit unit) {
        unitComboBox.setSelectedItem(unit);
    }

    private double getValueFromField() {
        return ((Number) valueTextField.getValue()).doubleValue();
    }

    private void setValueToField(double dValue) {
        valueTextField.setValue(dValue);
    }

    @Override
    public void setEnabled(boolean enabled) {
        unitComboBox.setEnabled(enabled);
        valueTextField.setEnabled(enabled);
        super.setEnabled(enabled);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        valueTextField = new javax.swing.JFormattedTextField();
        unitComboBox = new javax.swing.JComboBox();

        setLayout(new java.awt.BorderLayout());
        add(valueTextField, java.awt.BorderLayout.CENTER);

        unitComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                unitComboBoxItemStateChanged(evt);
            }
        });
        add(unitComboBox, java.awt.BorderLayout.LINE_END);
    }// </editor-fold>//GEN-END:initComponents

    private LengthUnit deselected;

    private void unitComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_unitComboBoxItemStateChanged
        if (evt.getStateChange() == ItemEvent.DESELECTED) {
            deselected = (LengthUnit) evt.getItem();
        } else {
            if (deselected != null) {
                LengthUnit selected = (LengthUnit) evt.getItem();
                // conversion
                int cValue = getValueImpl(deselected);
                setValueImpl(cValue, selected);
                deselected = null;
            }
        }
    }//GEN-LAST:event_unitComboBoxItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox unitComboBox;
    private javax.swing.JFormattedTextField valueTextField;
    // End of variables declaration//GEN-END:variables

}
