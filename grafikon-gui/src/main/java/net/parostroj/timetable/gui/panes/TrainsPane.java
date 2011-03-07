/*
 * TrainsPane.java
 *
 * Created on 3. září 2007, 10:22
 */
package net.parostroj.timetable.gui.panes;

import java.awt.Color;
import net.parostroj.timetable.gui.AppPreferences;
import net.parostroj.timetable.gui.ApplicationModel;
import net.parostroj.timetable.gui.StorableGuiData;
import net.parostroj.timetable.gui.components.GTViewSettings;
import net.parostroj.timetable.gui.utils.NormalHTS;
import net.parostroj.timetable.gui.views.TrainListView.TreeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Trains pane.
 * 
 * @author jub
 */
public class TrainsPane extends javax.swing.JPanel implements StorableGuiData {

    private static final Logger LOG = LoggerFactory.getLogger(TrainsPane.class.getName());
    
    /** Creates new form TrainsPane */
    public TrainsPane() {
        initComponents();
        
        scrollPane.getViewport().addChangeListener(graphicalTimetableView);
        scrollPane.getHorizontalScrollBar().setBlockIncrement(1000);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(100);
    }

    public void resizeColumns() {
        trainView.resizeColumns();
    }

    public void sortColumns() {
        trainView.sortColumns();
    }
    
    /**
     * sets model.
     * 
     * @param model application model
     */
    public void setModel(final ApplicationModel model) {
        trainListView.setModel(model);
        trainView.setModel(model);
        NormalHTS hts = new NormalHTS(model, Color.GREEN, graphicalTimetableView);
        graphicalTimetableView.setSettings(
                graphicalTimetableView.getSettings().set(GTViewSettings.Key.HIGHLIGHTED_TRAINS, hts));
        graphicalTimetableView.setTrainSelector(hts);
    }
    
    @Override
    public void loadFromPreferences(AppPreferences prefs) {
        int dividerLoc = prefs.getInt("trains.divider", splitPane.getDividerLocation());
        GTViewSettings gtvs = null;
        try {
            gtvs = GTViewSettings.parseStorageString(prefs.getString("trains.gtv", null));
        } catch (Exception e) {
            // use default values
            LOG.warn("Wrong GTView settings - using default values.");
        }
        if (gtvs != null)
            graphicalTimetableView.setSettings(graphicalTimetableView.getSettings().merge(gtvs));
        scrollPane.setVisible(prefs.getBoolean("trains.show.gtview", true));
        if (scrollPane.isVisible())
            splitPane.setDividerLocation(dividerLoc);
        else
            splitPane.setLastDividerLocation(dividerLoc);
        String treeType = prefs.getString("trains.listtype", "TYPES");
        trainListView.setTreeType(TreeType.valueOf(treeType));

        trainView.loadFromPreferences(prefs);
    }
    
    @Override
    public void saveToPreferences(AppPreferences prefs) {
        prefs.setInt("trains.divider", scrollPane.isVisible() ? splitPane.getDividerLocation() : splitPane.getLastDividerLocation());
        // save if the gtview in trains pane is visible
        prefs.setBoolean("trains.show.gtview", scrollPane.isVisible());
        prefs.setString("trains.gtv", graphicalTimetableView.getSettings().getStorageString());
        prefs.setString("trains.listtype", trainListView.getTreeType().name());
        trainView.saveToPreferences(prefs);
    }

    public void editColumns() {
        trainView.editColumns();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        splitPane = new javax.swing.JSplitPane();
        scrollPane = new javax.swing.JScrollPane();
        graphicalTimetableView = new net.parostroj.timetable.gui.components.GraphicalTimetableViewWithSave();
        panel = new javax.swing.JPanel();
        trainListView = new net.parostroj.timetable.gui.views.TrainListView();
        trainView = new net.parostroj.timetable.gui.views.TrainView();

        splitPane.setDividerLocation(350);
        splitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        javax.swing.GroupLayout graphicalTimetableViewLayout = new javax.swing.GroupLayout(graphicalTimetableView);
        graphicalTimetableView.setLayout(graphicalTimetableViewLayout);
        graphicalTimetableViewLayout.setHorizontalGroup(
            graphicalTimetableViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 4000, Short.MAX_VALUE)
        );
        graphicalTimetableViewLayout.setVerticalGroup(
            graphicalTimetableViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 172, Short.MAX_VALUE)
        );

        scrollPane.setViewportView(graphicalTimetableView);

        splitPane.setBottomComponent(scrollPane);

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addComponent(trainListView, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(trainView, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE))
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(trainView, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE)
            .addComponent(trainListView, javax.swing.GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE)
        );

        splitPane.setLeftComponent(panel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(splitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 665, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(splitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 548, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private net.parostroj.timetable.gui.components.GraphicalTimetableViewWithSave graphicalTimetableView;
    private javax.swing.JPanel panel;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JSplitPane splitPane;
    private net.parostroj.timetable.gui.views.TrainListView trainListView;
    private net.parostroj.timetable.gui.views.TrainView trainView;
    // End of variables declaration//GEN-END:variables

    public void setVisibilityOfGTView(boolean state) {
        if (state)
            splitPane.setDividerLocation(splitPane.getLastDividerLocation());
        else
            splitPane.setLastDividerLocation(splitPane.getDividerLocation());
        scrollPane.setVisible(state);
    }
}
