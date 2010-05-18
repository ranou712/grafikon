package net.parostroj.timetable.model.events;

/**
 * Event type.
 *
 * @author jub
 */
public enum GTEventType {
    ATTRIBUTE, CYCLE_ITEM_ADDED, CYCLE_ITEM_REMOVED, CYCLE_ITEM_UPDATED, CYCLE_ITEM_MOVED,
    TRACK_ADDED, TRACK_REMOVED, TRACK_MOVED, TIME_INTERVAL_ATTRIBUTE,
    TIME_INTERVAL_ADDED, TIME_INTERVAL_REMOVED, TIME_INTERVAL_UPDATED, TRACK_ATTRIBUTE,
    NODE_ADDED, NODE_REMOVED, LINE_ADDED, LINE_REMOVED,
    LINE_CLASS_ADDED, LINE_CLASS_REMOVED, LINE_CLASS_MOVED, NESTED,
    ROUTE_ADDED, ROUTE_REMOVED, TRAIN_ADDED, TRAIN_REMOVED, TRAIN_TYPE_ADDED, TRAIN_TYPE_REMOVED,
    TRAIN_TYPE_MOVED, ENGINE_CLASS_ADDED, ENGINE_CLASS_REMOVED, ENGINE_CLASS_MOVED,
    WEIGHT_TABLE_MODIFIED, TIME_INTERVAL_LIST, TECHNOLOGICAL,
    TEXT_ITEM_ADDED, TEXT_ITEM_REMOVED, TEXT_ITEM_MOVED,
    IMAGE_ADDED, IMAGE_REMOVED, TRAINS_CYCLE_ADDED, TRAINS_CYCLE_REMOVED;
}
