package net.parostroj.timetable.gui.dialogs;

import java.io.File;
import javax.swing.JFileChooser;
import net.parostroj.timetable.gui.utils.ResourceLoader;

/**
 * Dialog for template selection.
 *
 * @author jub
 */
public class TemplateSelectDialog extends javax.swing.JDialog {

    private File template;
    private JFileChooser chooser;
    private boolean okPressed;

    /** Creates new form TemplateSelectDialog */
    public TemplateSelectDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public boolean selectTemplate(JFileChooser chooser, File file) {
        this.chooser = chooser;
        this.template = file;
        if (template != null)
            templateTextField.setText(template.getPath());
        this.okPressed = false;
        // set position
        this.setLocationRelativeTo(getParent());
        // show dialog
        this.setVisible(true);
        // forget chooser
        this.chooser = null;
        return okPressed;
    }

    public File getTemplate() {
        return template;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.JPanel selectPanel = new javax.swing.JPanel();
        templateTextField = new javax.swing.JTextField();
        javax.swing.JButton selectButton = new javax.swing.JButton();
        javax.swing.JPanel buttonsPanel = new javax.swing.JPanel();
        javax.swing.JButton okButton = new javax.swing.JButton();
        javax.swing.JButton cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        templateTextField.setColumns(35);
        templateTextField.setEditable(false);
        selectPanel.add(templateTextField);

        selectButton.setText(ResourceLoader.getString("button.select") + "..."); // NOI18N
        selectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectButtonActionPerformed(evt);
            }
        });
        selectPanel.add(selectButton);

        getContentPane().add(selectPanel, java.awt.BorderLayout.CENTER);

        buttonsPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        okButton.setText(ResourceLoader.getString("button.ok")); // NOI18N
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });
        buttonsPanel.add(okButton);

        cancelButton.setText(ResourceLoader.getString("button.cancel")); // NOI18N
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        buttonsPanel.add(cancelButton);

        getContentPane().add(buttonsPanel, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        okPressed = true;
        setVisible(false);
    }//GEN-LAST:event_okButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        setVisible(false);
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void selectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectButtonActionPerformed
        int returnValue = chooser.showOpenDialog(getParent());
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            this.template = chooser.getSelectedFile();
            // update text string
            templateTextField.setText(this.template.getPath());
        }
    }//GEN-LAST:event_selectButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField templateTextField;
    // End of variables declaration//GEN-END:variables

}