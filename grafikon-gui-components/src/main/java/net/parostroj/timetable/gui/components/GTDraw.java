package net.parostroj.timetable.gui.components;

import java.awt.Graphics2D;

import net.parostroj.timetable.model.Route;

public interface GTDraw {

    enum Change {
        REMOVED_TRAIN, TRAIN_TEXT_CHANGED, NODE_TEXT_CHANGED, ALL_TRAIN_TEXTS_CHANGED
    }

    void draw(Graphics2D g);

    void paintStationNames(Graphics2D g);

    Route getRoute();

    void setPositionX(int positionX);

    float getFontSize();

    void changed(Change change, Object object);
}
