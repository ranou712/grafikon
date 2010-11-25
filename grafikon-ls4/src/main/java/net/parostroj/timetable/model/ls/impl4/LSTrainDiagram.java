package net.parostroj.timetable.model.ls.impl4;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import net.parostroj.timetable.model.TrainDiagram;

/**
 * Storage for train diagram data.
 * 
 * @author jub
 */
@XmlRootElement(name = "train_diagram")
@XmlType(propOrder = {"id", "trainsData", "attributes", "changesTrackingEnabled"})
public class LSTrainDiagram {
    
    private String id;
    private LSTrainsData trainsData;
    private LSAttributes attributes;
    private boolean changesTrackingEnabled;
    
    public LSTrainDiagram() {
    }
    
    public LSTrainDiagram(TrainDiagram diagram) {
        id = diagram.getId();
        trainsData = new LSTrainsData(diagram.getTrainsData());
        attributes = new LSAttributes(diagram.getAttributes());
        changesTrackingEnabled = diagram.getChangesTracker().isTrackingEnabled();
    }

    public LSAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(LSAttributes attributes) {
        this.attributes = attributes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlElement(name = "trains_data")
    public LSTrainsData getTrainsData() {
        return trainsData;
    }

    public void setTrainsData(LSTrainsData trainsData) {
        this.trainsData = trainsData;
    }

    public boolean isChangesTrackingEnabled() {
        return changesTrackingEnabled;
    }

    public void setChangesTrackingEnabled(boolean changesTrackingEnabled) {
        this.changesTrackingEnabled = changesTrackingEnabled;
    }
}
