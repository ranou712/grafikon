package net.parostroj.timetable.output2.gt;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;

public interface GTDrawOrientationDelegate {
    public void drawLine(Graphics2D g, int x1, int y1, int x2, int y2);

    public Line2D createLine(double x1, double y1, double x2, double y2);

    public int getHoursSize(Dimension size);

    public int getStationsSize(Dimension size);

    public int getHoursStart(Point p);

    public int getStationsStart(Point p);
}