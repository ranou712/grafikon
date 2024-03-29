/*
 * RouteSegment.java
 *
 * Created on 31.8.2007, 9:59:23
 */
package net.parostroj.timetable.model;

import java.util.List;

/**
 * Route segment.
 *
 * @author jub
 */
public interface RouteSegment extends ObjectWithId {

    public Line asLine();

    public Node asNode();

    public boolean isLine();

    public boolean isNode();

    public void addTimeInterval(TimeInterval interval);

    public void removeTimeInterval(TimeInterval interval);

    public void updateTimeInterval(TimeInterval interval);

    public List<? extends Track> getTracks();

    public boolean isEmpty();

    @Override
    public String getId();

    public Track getTrackById(String id);

    public Track getTrackByNumber(String name);

    public Iterable<TimeInterval> getTimeIntervals();
}
