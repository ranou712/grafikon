/*
 * NewJApplet.java
 *
 * Created on 5. červenec 2008, 17:09
 */
package net.parostroj.timetable.applet;

import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipInputStream;
import javax.swing.UIManager;
import net.parostroj.timetable.model.TrainDiagram;
import net.parostroj.timetable.model.ls.FileLoadSave;
import net.parostroj.timetable.model.ls.LSFileFactory;

/**
 * @author jub
 */
public class Applet extends javax.swing.JApplet {

    /** Initializes the applet NewJApplet */
    @Override
    public void init() {
        Logger.getLogger("net.parostroj").setLevel(Level.FINE);
        Logger.getLogger("").getHandlers()[0].setLevel(Level.ALL);
        try {
            java.awt.EventQueue.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    try {
                        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                        initComponents();
                        LSFileFactory factory = LSFileFactory.getInstance();
                        URL u = getCodeBase();
                        if (getParameter("file") != null) {
                            u = new URL(u,getParameter("file"));
                            Logger.getLogger(Applet.class.getName()).log(Level.FINE, "Loading file: {0}", u);
                            ZipInputStream is = new ZipInputStream(u.openStream());
                            FileLoadSave ls = factory.createForLoad(is);
                            TrainDiagram td = ls.load(is);
                            graphicalTimetableView1.setTrainDiagram(td);
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(Applet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        } catch (Exception ex) {
            Logger.getLogger(Applet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /** This method is called from within the init() method to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        graphicalTimetableView1 = new net.parostroj.timetable.gui.components.GraphicalTimetableView();

        jScrollPane1.setBorder(null);

        javax.swing.GroupLayout graphicalTimetableView1Layout = new javax.swing.GroupLayout(graphicalTimetableView1);
        graphicalTimetableView1.setLayout(graphicalTimetableView1Layout);
        graphicalTimetableView1Layout.setHorizontalGroup(
            graphicalTimetableView1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 4000, Short.MAX_VALUE)
        );
        graphicalTimetableView1Layout.setVerticalGroup(
            graphicalTimetableView1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 306, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(graphicalTimetableView1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private net.parostroj.timetable.gui.components.GraphicalTimetableView graphicalTimetableView1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

}
