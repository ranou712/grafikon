/*
 * TrainCycleType.java
 *
 * Created on 15.9.2007, 20:43:01
 */

package net.parostroj.timetable.model;

import java.util.*;

import net.parostroj.timetable.model.events.*;
import net.parostroj.timetable.utils.ObjectsUtil;
import net.parostroj.timetable.utils.ResourceBundleUtil;
import net.parostroj.timetable.visitors.TrainDiagramVisitor;
import net.parostroj.timetable.visitors.Visitable;

/**
 * Train cycle type.
 *
 * @author jub
 */
public class TrainsCycleType implements AttributesHolder, ObjectWithId, Visitable, TrainsCycleTypeAttributes {

    public static final String ENGINE_CYCLE = "ENGINE_CYCLE";
    public static final String DRIVER_CYCLE = "DRIVER_CYCLE";
    public static final String TRAIN_UNIT_CYCLE = "TRAIN_UNIT_CYCLE";

    public static boolean isDefaultType(String typeName) {
        return TrainsCycleType.DRIVER_CYCLE.equals(typeName) ||
            TrainsCycleType.ENGINE_CYCLE.equals(typeName) ||
            TrainsCycleType.TRAIN_UNIT_CYCLE.equals(typeName);
    }

    public static boolean isDefaultType(TrainsCycleType type) {
        return isDefaultType(type.getName());
    }

    private final String id;
    private String name;
    private String description;
    private Attributes attributes;
    private final List<TrainsCycle> cycles;

    private AttributesListener attributesListener;
    private final GTListenerSupport<TrainsCycleTypeListener, TrainsCycleTypeEvent> listenerSupport;

    private String _cachedDescription;

    public TrainsCycleType(String id) {
        this.id = id;
        this.cycles = new LinkedList<TrainsCycle>();
        listenerSupport = new GTListenerSupport<TrainsCycleTypeListener, TrainsCycleTypeEvent>(new GTEventSender<TrainsCycleTypeListener, TrainsCycleTypeEvent>() {
            @Override
            public void fireEvent(TrainsCycleTypeListener listener, TrainsCycleTypeEvent event) {
                listener.trainsCycleTypeChanged(event);
            }
        });
        this.setAttributes(new Attributes());
    }

    public TrainsCycleType(String id, String name) {
        this(id);
        this.name = name;
    }

    public TrainsCycleType(String id, String name, String description) {
        this(id, name);
        this.description = description;
    }

    @Override
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (!ObjectsUtil.compareWithNull(name, this.name)) {
            String oldName = this.name;
            this.name = name;
            this._cachedDescription = null;
            this.listenerSupport.fireEvent(new TrainsCycleTypeEvent(this, new AttributeChange(ATTR_NAME, oldName, name)));
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (!ObjectsUtil.compareWithNull(description, this.description)) {
            String oldDescription = this.description;
            this.description = description;
            this._cachedDescription = null;
            this.listenerSupport.fireEvent(new TrainsCycleTypeEvent(this, new AttributeChange(ATTR_DESCRIPTION,
                    oldDescription, description)));
        }
    }

    @Override
    public Attributes getAttributes() {
        return attributes;
    }

    @Override
    public void setAttributes(Attributes attributes) {
        if (this.attributes != null && attributesListener != null)
            this.attributes.removeListener(attributesListener);
        this.attributes = attributes;
        this.attributesListener = new AttributesListener() {

            @Override
            public void attributeChanged(Attributes attributes, AttributeChange change) {
                listenerSupport.fireEvent(new TrainsCycleTypeEvent(TrainsCycleType.this, change));
            }
        };
        this.attributes.addListener(attributesListener);
    }

    @Override
    public <T> T getAttribute(String key, Class<T> clazz) {
        return attributes.get(key, clazz);
    }

    @Override
    public void setAttribute(String key, Object value) {
        attributes.set(key, value);
    }

    @Override
    public Object removeAttribute(String key) {
        return attributes.remove(key);
    }

    List<TrainsCycle> getCycles() {
        return cycles;
    }

    public String getDescriptionText() {
        return getDescriptionText(Locale.getDefault());
    }

    public String getDescriptionText(Locale locale) {
        if (_cachedDescription == null) {
            if (isDefaultType(name)) {
                ResourceBundle bundle = ResourceBundleUtil.getBundle("net.parostroj.timetable.model.cycle_type_texts", TrainsCycleType.class.getClassLoader(), locale, Locale.ENGLISH);
                _cachedDescription = bundle.getString(name);
            } else {
                _cachedDescription = (description != null) ? description : name;
            }
        }
        return _cachedDescription;
    }

    public void addListener(TrainsCycleTypeListener listener) {
        listenerSupport.addListener(listener);
    }

    public void removeListener(TrainsCycleTypeListener listener) {
        listenerSupport.removeListener(listener);
    }

    public boolean isDefaultType() {
        return isDefaultType(this);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public void accept(TrainDiagramVisitor visitor) {
        visitor.visit(this);
        for (TrainsCycle cycle : getCycles()) {
            cycle.accept(visitor);
        }
    }
}
