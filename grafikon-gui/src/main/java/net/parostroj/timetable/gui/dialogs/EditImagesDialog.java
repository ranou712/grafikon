/*
 * EditImagesDialog.java
 *
 * Created on 12. říjen 2007, 19:44
 */
package net.parostroj.timetable.gui.dialogs;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.channels.FileChannel;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import net.parostroj.timetable.gui.ApplicationModel;
import net.parostroj.timetable.gui.ApplicationModelEvent;
import net.parostroj.timetable.gui.ApplicationModelListener;
import net.parostroj.timetable.gui.wrappers.TimetableImageWrapper;
import net.parostroj.timetable.gui.wrappers.WrapperListModel;
import net.parostroj.timetable.model.TimetableImage;
import net.parostroj.timetable.utils.IdGenerator;
import net.parostroj.timetable.utils.ResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Edit dialog for images for timetable.
 * 
 * @author jub
 */
public class EditImagesDialog extends javax.swing.JDialog implements ApplicationModelListener {
    
    private static final Logger LOG = LoggerFactory.getLogger(EditImagesDialog.class.getName());
    private static JFileChooser fileChooserInstance;

    private ApplicationModel model;
    private WrapperListModel<TimetableImage> listModel;
    
    private synchronized static JFileChooser getFileChooser() {
        if (fileChooserInstance == null) {
            fileChooserInstance = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "jpg", "jpeg", "gif", "bmp", "png");
            fileChooserInstance.addChoosableFileFilter(filter);
        }
        return fileChooserInstance;
    }
    
    /** Creates new form EditImagesDialog */
    public EditImagesDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        // preload filechooser
        getFileChooser();
    }
    
    public void setModel(ApplicationModel model) {
        this.model = model;
        this.model.addListener(this);
        
        this.updateValues();
    }

    @Override
    public void modelChanged(ApplicationModelEvent event) {
        switch(event.getType()) {
            case SET_DIAGRAM_CHANGED:
                this.updateValues();
                break;
        }
    }
    
    private void updateValues() {
        listModel = new WrapperListModel<TimetableImage>();
        imagesList.setModel(listModel);
        if (this.model.getDiagram() != null) {
            for (TimetableImage item : this.model.getDiagram().getImages())
                listModel.addWrapper(new TimetableImageWrapper(item));
        }
    }

    private boolean checkExistence(String filename, TimetableImage ignore) {
        for (TimetableImage image : model.getDiagram().getImages()) {
            if (image != ignore) {
                if (image.getFilename().equals(filename))
                    return true;
            }
        }
        return false;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPane = new javax.swing.JScrollPane();
        imagesList = new javax.swing.JList();
        newButton = new javax.swing.JButton();
        renameButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        exitButton = new javax.swing.JButton();

        FormListener formListener = new FormListener();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle(ResourceLoader.getString("images.edit.title")); // NOI18N

        imagesList.addListSelectionListener(formListener);
        scrollPane.setViewportView(imagesList);

        newButton.setText(ResourceLoader.getString("button.new") + " ..."); // NOI18N
        newButton.addActionListener(formListener);

        renameButton.setText(ResourceLoader.getString("button.rename")); // NOI18N
        renameButton.setEnabled(false);
        renameButton.addActionListener(formListener);

        deleteButton.setText(ResourceLoader.getString("button.delete")); // NOI18N
        deleteButton.setEnabled(false);
        deleteButton.addActionListener(formListener);

        exitButton.setText(ResourceLoader.getString("button.ok")); // NOI18N
        exitButton.addActionListener(formListener);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(exitButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(renameButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(deleteButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(newButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(scrollPane, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(newButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(renameButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 153, Short.MAX_VALUE)
                        .addComponent(exitButton)))
                .addContainerGap())
        );

        pack();
    }

    // Code for dispatching events from components to event handlers.

    private class FormListener implements java.awt.event.ActionListener, javax.swing.event.ListSelectionListener {
        FormListener() {}
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            if (evt.getSource() == newButton) {
                EditImagesDialog.this.newButtonActionPerformed(evt);
            }
            else if (evt.getSource() == renameButton) {
                EditImagesDialog.this.renameButtonActionPerformed(evt);
            }
            else if (evt.getSource() == deleteButton) {
                EditImagesDialog.this.deleteButtonActionPerformed(evt);
            }
            else if (evt.getSource() == exitButton) {
                EditImagesDialog.this.exitButtonActionPerformed(evt);
            }
        }

        public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
            if (evt.getSource() == imagesList) {
                EditImagesDialog.this.imagesListValueChanged(evt);
            }
        }
    }// </editor-fold>//GEN-END:initComponents

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_exitButtonActionPerformed

    private void newButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newButtonActionPerformed
        // new image based on selected file ...
        JFileChooser chooser = getFileChooser();
        
        int result = chooser.showOpenDialog(this);
        
        if (result == JFileChooser.APPROVE_OPTION) {
            String fileName = chooser.getSelectedFile().getName();

            if (checkExistence(fileName, null)) {
                // show error message and return
                JOptionPane.showMessageDialog(this,
                        ResourceLoader.getString("dialog.error.duplicatefile"),
                        ResourceLoader.getString("dialog.error.title"),
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                // get size of the image
                BufferedImage img = ImageIO.read(chooser.getSelectedFile());
                TimetableImage image = model.getDiagram().createImage(IdGenerator.getInstance().getId(), fileName, img.getWidth(), img.getHeight());

                File tempFile = File.createTempFile("gt_", ".temp");
                FileChannel ic = new FileInputStream(chooser.getSelectedFile()).getChannel();
                FileChannel oc = new FileOutputStream(tempFile).getChannel();
                ic.transferTo(0, ic.size(), oc);
                ic.close();
                oc.close();
                image.setImageFile(tempFile);
                tempFile.deleteOnExit();
                model.getDiagram().addImage(image);
                listModel.addWrapper(new TimetableImageWrapper(image));
            } catch (IOException e) {
                LOG.warn("Cannot save temporary image file.", e);
                JOptionPane.showMessageDialog(this,
                        ResourceLoader.getString("dialog.error.temporaryfile"),
                        ResourceLoader.getString("dialog.error.title"),
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_newButtonActionPerformed

    private void renameButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_renameButtonActionPerformed
        TimetableImage selected = ((TimetableImageWrapper)imagesList.getSelectedValue()).getElement();
        // ask for a new name
        String newName = JOptionPane.showInputDialog(this, ResourceLoader.getString("images.edit.name"),selected.getFilename());
        if (newName != null && !newName.equals(selected.getFilename())) {
            TimetableImage newImage = model.getDiagram().createImage(IdGenerator.getInstance().getId(), newName, selected.getImageWidth(), selected.getImageHeight());
            if (checkExistence(newName, selected)) {
                // show error message and return
                JOptionPane.showMessageDialog(this,
                        ResourceLoader.getString("dialog.error.duplicatefile"),
                        ResourceLoader.getString("dialog.error.title"),
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            newImage.setImageFile(selected.getImageFile());
            // train diagram
            model.getDiagram().removeImage(selected);
            model.getDiagram().addImage(newImage);
            // list model
            listModel.removeObject(selected);
            TimetableImageWrapper newWrapper = new TimetableImageWrapper(newImage);
            listModel.addWrapper(newWrapper);
            // set selected
            imagesList.setSelectedValue(newWrapper, true);
        }
    }//GEN-LAST:event_renameButtonActionPerformed

    private void imagesListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_imagesListValueChanged
        if (!evt.getValueIsAdjusting()) {
            boolean selected = imagesList.getSelectedIndex() != -1;
            renameButton.setEnabled(selected);
            deleteButton.setEnabled(selected);
            renameButton.setEnabled(selected);
        }
    }//GEN-LAST:event_imagesListValueChanged

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        TimetableImage selected = ((TimetableImageWrapper)imagesList.getSelectedValue()).getElement();
        model.getDiagram().removeImage(selected);
        listModel.removeIndex(imagesList.getSelectedIndex());
        // remove temp file
        if (!selected.getImageFile().delete())
            LOG.debug("Cannot remove temporary file: {}", selected.getImageFile().getPath());
    }//GEN-LAST:event_deleteButtonActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton exitButton;
    private javax.swing.JList imagesList;
    private javax.swing.JButton newButton;
    private javax.swing.JButton renameButton;
    private javax.swing.JScrollPane scrollPane;
    // End of variables declaration//GEN-END:variables
    
}
