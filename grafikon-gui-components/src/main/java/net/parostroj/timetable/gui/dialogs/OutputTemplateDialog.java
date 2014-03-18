/*
 * OutputTemplateDialog.java
 *
 * Created on 15.4.2011, 13:22:54
 */
package net.parostroj.timetable.gui.dialogs;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.util.Collections;

import net.parostroj.timetable.gui.actions.execution.ActionUtils;
import net.parostroj.timetable.gui.utils.ResourceLoader;
import net.parostroj.timetable.model.GrafikonException;
import net.parostroj.timetable.model.OutputTemplate;
import net.parostroj.timetable.model.TextTemplate;
import net.parostroj.timetable.model.TextTemplate.Language;
import net.parostroj.timetable.output2.OutputFactory;

import org.slf4j.LoggerFactory;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * Dialog for editing text template.
 *
 * @author jub
 */
public class OutputTemplateDialog extends javax.swing.JDialog {

    private OutputTemplate template;

    public OutputTemplateDialog(Window parent, boolean modal) {
        super(parent, modal ? ModalityType.APPLICATION_MODAL : ModalityType.MODELESS);
        initComponents();
        init();
    }

    public void showDialog(OutputTemplate template) {
        this.template = template;
        this.updateValues(null);
        this.setVisible(true);
    }

    private void init() {
        textTemplateEditBox.setTemplateLanguages(Collections.singleton(Language.GROOVY));
        for (String type : OutputFactory.newInstance("groovy").getOutputTypes()) {
            outputTypeComboBox.addItem(type);
        }
    }

    public OutputTemplate getTemplate() {
        return template;
    }

    private void updateValues(Boolean defaultTemplate) {
        if (defaultTemplate == null) {
            defaultTemplate = this.template.getAttributes().get("default.template", Boolean.class);
            this.defaultTemplateCheckbox.setSelected(defaultTemplate == Boolean.TRUE);
        }
        textTemplateEditBox.setEnabled(defaultTemplate != Boolean.TRUE);
        textTemplateEditBox.setTemplate(defaultTemplate == Boolean.TRUE ? null : this.template.getTemplate());
        extensionTextField.setText(this.template.getAttributes().get(OutputTemplate.ATTR_OUTPUT_EXTENSION, String.class));
        outputTypeComboBox.setSelectedItem(this.template.getAttribute(OutputTemplate.ATTR_OUTPUT_TYPE));
    }

    private void initComponents() {
        textTemplateEditBox = new net.parostroj.timetable.gui.components.TextTemplateEditBox2();
        javax.swing.JPanel controlPanel = new javax.swing.JPanel();
        javax.swing.JPanel buttonPanel = new javax.swing.JPanel();
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        javax.swing.JPanel verifyPanel = new javax.swing.JPanel();
        outputTypeComboBox = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        textTemplateEditBox.setTemplateFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        textTemplateEditBox.setColumns(80);
        textTemplateEditBox.setRows(25);
        getContentPane().add(textTemplateEditBox, java.awt.BorderLayout.CENTER);

        controlPanel.setLayout(new java.awt.BorderLayout());

        okButton.setText(ResourceLoader.getString("button.ok")); // NOI18N
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(okButton);

        cancelButton.setText(ResourceLoader.getString("button.cancel")); // NOI18N
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(cancelButton);

        controlPanel.add(buttonPanel, java.awt.BorderLayout.EAST);

        verifyPanel.add(outputTypeComboBox);

        controlPanel.add(verifyPanel, java.awt.BorderLayout.WEST);

        defaultTemplateCheckbox = new JCheckBox(ResourceLoader.getString("ot.checkbox.default.template"));
        verifyPanel.add(defaultTemplateCheckbox);
        verifyButton = new javax.swing.JButton();

                verifyButton.setText(ResourceLoader.getString("ot.button.verify")); // NOI18N
                verifyButton.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        verifyButtonActionPerformed(evt);
                    }
                });
                verifyPanel.add(verifyButton);

        JLabel suffixLabel = new JLabel(ResourceLoader.getString("ot.extension") + ":");
        verifyPanel.add(suffixLabel);

        extensionTextField = new JTextField();
        verifyPanel.add(extensionTextField);
        extensionTextField.setColumns(10);
        defaultTemplateCheckbox.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateValues(defaultTemplateCheckbox.isSelected());
            }
        });

        getContentPane().add(controlPanel, java.awt.BorderLayout.SOUTH);

        pack();
    }

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            TextTemplate textTemplate = this.convertToTemplate();
            this.template.setTemplate(textTemplate);
            this.template.setAttribute(OutputTemplate.ATTR_OUTPUT_TYPE, outputTypeComboBox.getSelectedItem());
            String ext = extensionTextField.getText().trim();
            if ("".equals(ext)) {
                this.template.removeAttribute(OutputTemplate.ATTR_OUTPUT_EXTENSION);
            } else {
                this.template.setAttribute(OutputTemplate.ATTR_OUTPUT_EXTENSION, ext);
            }
            if (defaultTemplateCheckbox.isSelected()) {
                this.template.setAttribute("default.template", true);
            } else {
                this.template.removeAttribute("default.template");
            }
            this.setVisible(false);
        } catch (GrafikonException e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e);
            ActionUtils.showError(e.getMessage(), this);
        }
    }

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {
        this.template = null;
        this.setVisible(false);
    }

    private void verifyButtonActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            this.convertToTemplate();
            ActionUtils.showInformation(ResourceLoader.getString("ot.verification.ok"), this);
        } catch (GrafikonException e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e);
            ActionUtils.showError(e.getMessage(), this);
        }
    }

    private TextTemplate convertToTemplate() throws GrafikonException {
        return textTemplateEditBox.getTemplate();
    }

    private javax.swing.JButton cancelButton;
    private javax.swing.JButton okButton;
    private javax.swing.JComboBox outputTypeComboBox;
    private net.parostroj.timetable.gui.components.TextTemplateEditBox2 textTemplateEditBox;
    private javax.swing.JButton verifyButton;
    private JCheckBox defaultTemplateCheckbox;
    private JTextField extensionTextField;
}
