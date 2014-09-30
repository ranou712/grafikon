package net.parostroj.timetable.output2.gt;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import net.parostroj.timetable.model.*;

public abstract class GTDrawDecorator implements GTDraw {

    protected GTDraw draw;

    public GTDrawDecorator(GTDraw draw) {
        this.draw = draw;
    }

    public GTDraw getDraw() {
        return draw;
    }

    @Override
    public void draw(Graphics2D g) {
        draw.draw(g);
    }

    @Override
    public void paintStationNames(Graphics2D g) {
        draw.paintStationNames(g);
    }

    @Override
    public Route getRoute() {
        return draw.getRoute();
    }

    @Override
    public void setStationNamesPosition(int position) {
        draw.setStationNamesPosition(position);
    }

    @Override
    public void changed(Change change, Object object) {
        draw.changed(change, object);
    }

    @Override
    public int getX(int time) {
        return draw.getX(time);
    }

    @Override
    public int getY(Node node, Track track) {
        return draw.getY(node, track);
    }

    @Override
    public int getY(TimeInterval interval) {
        return draw.getY(interval);
    }

    @Override
    public Rectangle2D getDigitSize(Graphics2D g) {
        return draw.getDigitSize(g);
    }

    @Override
    public Rectangle2D getMSize(Graphics2D g) {
        return draw.getMSize(g);
    }
}