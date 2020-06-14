package com.company.Model;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PointListB {
    private List<Point> pointList;

    public PointListB() {
        pointList = new ArrayList<>();
        pointList.add(new Point(0, 0));
    }

    public void addPoint(Point point) {
        pointList.add(point);
    }

    public void clear() {
        pointList.clear();
    }

    public List<Point> getPointList() {
        return pointList;
    }

}
