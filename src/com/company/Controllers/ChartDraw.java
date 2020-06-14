package com.company.Controllers;


import com.company.Model.PointListA;
import com.company.Model.PointListB;
import com.company.View.ChartOptionsViewPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ChartDraw {
    private PointListA pointListA;
    private PointListB pointListB;
    private ChartOptionsViewPanel chartOptionsViewPanel;

    public ChartDraw(JSplitPane splitPaneTableGraph) {
        pointListA = new PointListA();
        pointListB = new PointListB();
        chartOptionsViewPanel = new ChartOptionsViewPanel(this);
        splitPaneTableGraph.setRightComponent(chartOptionsViewPanel);
    }

    public void addPointToList(Point point, String graphName) {
        if (graphName.equals("A")) pointListA.addPoint(point);
        else pointListB.addPoint(point);
        getChartOptionsViewPanel().getChartDrawPanel().repaint();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void clearPointList(String graphName) {
        if (graphName.equals("A")) pointListA.clear();
        else pointListB.clear();

    }

    public List<Point> getPointList(String graphName) {
        if (graphName.equals("A")) return pointListA.getPointList();
        else return pointListB.getPointList();
    }

    public ChartOptionsViewPanel getChartOptionsViewPanel() {
        return chartOptionsViewPanel;
    }
}
