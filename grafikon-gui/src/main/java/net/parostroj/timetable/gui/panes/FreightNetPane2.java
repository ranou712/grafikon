package net.parostroj.timetable.gui.panes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import net.parostroj.timetable.gui.*;
import net.parostroj.timetable.gui.components.*;
import net.parostroj.timetable.model.TimeInterval;
import net.parostroj.timetable.utils.ResourceLoader;
import net.parostroj.timetable.utils.Tuple;

import org.ini4j.Ini;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FreightNetPane2 extends JPanel implements StorableGuiData {

    private class HighlightSelection implements HighlightedTrains, TimeIntervalSelector {

        @Override
        public boolean isHighlighedInterval(TimeInterval interval) {
            return interval == connection.first || interval == connection.second;
        }

        @Override
        public Color getColor() {
            return Color.GREEN;
        }

        @Override
        public void intervalsSelected(List<TimeInterval> intervals) {
            TimeInterval interval = this.chooseInterval(intervals);
            boolean enabled = false;
            if (interval == null) {
                connection.first = null;
                connection.second = null;
            } else {
                if (connection.first != null && connection.second != null) {
                    connection.first = null;
                    connection.second = null;
                }
                if (connection.first == null) {
                    connection.first = interval;
                } else if (connection.second == null) {
                    connection.second = interval;
                    enabled = checkEnabled();
                }
            }
            newButton.setEnabled(enabled);
            graphicalTimetableView.repaint();
        }

        private TimeInterval lastInterval;

        private TimeInterval chooseInterval(List<TimeInterval> intervals) {
            TimeInterval selected = null;
            TimeInterval first = null;
            for (TimeInterval interval : intervals) {
                if (interval.isNodeOwner() && first == null) {
                    first = interval;
                }
                if (interval.isNodeOwner() && selected == null) {
                    selected = interval;
                } else if (interval == lastInterval) {
                    selected = null;
                }
            }
            if (selected == null) {
                selected = first;
            }
            lastInterval = selected;
            return selected;
        }

        @Override
        public void editSelected() {
            // nothing ...
        }
    }

    private static final Logger log = LoggerFactory.getLogger(FreightNetPane2.class);

    private final GraphicalTimetableViewWithSave graphicalTimetableView;
    private final GTLayeredPane2 scrollPane;

    private final Tuple<TimeInterval> connection = new Tuple<TimeInterval>();

    private final JButton newButton;

    private ApplicationModel model;

    public FreightNetPane2() {
        setLayout(new BorderLayout());
        graphicalTimetableView = new net.parostroj.timetable.gui.components.GraphicalTimetableViewWithSave();
        graphicalTimetableView.setDrawFactory(new ManagedFreightGTDrawFactory());
        scrollPane = new GTLayeredPane2(graphicalTimetableView);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        newButton = new JButton(ResourceLoader.getString("button.new"));
        newButton.setEnabled(false);
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.getDiagram().getFreightNet().addConnection(connection.first, connection.second);
                connection.first = null;
                connection.second = null;
                graphicalTimetableView.repaint();
                newButton.setEnabled(false);
            }
        });
        buttonPanel.add(newButton);
        add(buttonPanel, BorderLayout.NORTH);
    }

    @Override
    public Ini.Section saveToPreferences(Ini prefs) {
        Ini.Section section = AppPreferences.getSection(prefs, "freigh.net");
        section.put("gtv", graphicalTimetableView.getSettings().getStorageString());
        return section;
    }

    @Override
    public Ini.Section loadFromPreferences(Ini prefs) {
        Ini.Section section = AppPreferences.getSection(prefs, "freigh.net");
        GTViewSettings gtvs = null;
        try {
            gtvs = GTViewSettings.parseStorageString(section.get("gtv"));
        } catch (Exception e) {
            // use default values
            log.warn("Wrong GTView settings - using default values.");
        }
        if (gtvs != null) {
            graphicalTimetableView.setSettings(graphicalTimetableView.getSettings().merge(gtvs));
        }
        return section;
    }

    private boolean checkEnabled() {
        boolean result = false;
        if (connection.first.getOwnerAsNode() == connection.second.getOwnerAsNode()) {
            result = connection.first.getStart() < connection.second.getEnd();
        }
        return result;
    }

    public void setModel(ApplicationModel model) {
        this.model = model;
        HighlightSelection hts = new HighlightSelection();
        graphicalTimetableView.setSettings(
                graphicalTimetableView.getSettings().set(GTViewSettings.Key.HIGHLIGHTED_TRAINS, hts));
        graphicalTimetableView.setTrainSelector(hts);
        model.addListener(new ApplicationModelListener() {
            public void modelChanged(ApplicationModelEvent event) {
                if (event.getType() == ApplicationModelEventType.SET_DIAGRAM_CHANGED) {
                    scrollPane.setTrainDiagram(event.getModel().getDiagram());
                    graphicalTimetableView.setTrainDiagram(event.getModel().getDiagram());
                }
            }
        });
    }
}
