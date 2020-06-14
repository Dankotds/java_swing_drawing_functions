package com.company.Model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PointListA {
    private List<Point> pointList;

    public PointListA() {
        pointList = new ArrayList<>();
        pointList.add(new Point(0, 0));
    }

    public void addPoint(Point point) {
        pointList.add(point);
    }

    public List<Point> getPointList() {
        return pointList;
    }

    public void clear() {
        pointList.clear();
    }
}
