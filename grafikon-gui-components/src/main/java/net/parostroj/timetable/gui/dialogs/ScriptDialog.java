package net.parostroj.timetable.gui.dialogs;

import net.parostroj.timetable.gui.utils.GuiComponentUtils;
import net.parostroj.timetable.gui.utils.ResourceLoader;
import net.parostroj.timetable.model.GrafikonException;
import net.parostroj.timetable.model.Script;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Dialog for editing scripts.
 *
 * @author jub
 */
public class ScriptDialog extends javax.swing.JDialog {

    private static final Logger log = LoggerFactory.getLogger(ScriptDialog.class);

    private Script selectedScript;

    /** Creates new form ScriptDialog */
    public ScriptDialog(java.awt.Window parent, boolean modal) {
        super(parent, modal ? ModalityType.APPLICATION_MODAL : ModalityType.MODELESS);
        initComponents();
    }

    public Script getScript() throws GrafikonException {
        return scriptEditBox.getScript();
    }

    public void setScript(Script script) {
        scriptEditBox.setScript(script);
    }

    public void setEditorSize(int columns, int rows) {
        scriptEditBox.setColumns(columns);
        scriptEditBox.setRows(rows);
    }

    public Script getSelectedScript() {
        return selectedScript;
    }

    private void initComponents() {
        scriptEditBox = new net.parostroj.timetable.gui.components.ScriptEditBox();
        javax.swing.JPanel buttonPanel = new javax.swing.JPanel();
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle(ResourceLoader.getString("script.editing")); // NOI18N

        scriptEditBox.setColumns(60);
        scriptEditBox.setRows(15);
        scriptEditBox.setScriptFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        getContentPane().add(scriptEditBox, java.awt.BorderLayout.CENTER);

        buttonPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

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

        getContentPane().add(buttonPanel, java.awt.BorderLayout.PAGE_END);

        pack();
    }

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            selectedScript = this.getScript();
            this.setVisible(false);
        } catch (GrafikonException e) {
            log.error("Error creating script.", e);
            String message = e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
            GuiComponentUtils.showError(message, this);
        }
    }

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {
        this.setVisible(false);
    }

    @Override
    public void setVisible(boolean b) {
        if (b)
            selectedScript = null;
        super.setVisible(b);
    }

    private javax.swing.JButton cancelButton;
    private javax.swing.JButton okButton;
    private net.parostroj.timetable.gui.components.ScriptEditBox scriptEditBox;
}
