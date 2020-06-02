package com.company.Model;

import java.awt.*;
import java.util.Comparator;

public class PointsComparatorXAxis implements Comparator<Point> {
    @Override
    public int compare(Point p1, Point p2) {
        return p1.getX() >= p2.getX() ? 1 : 0;
    }
}
