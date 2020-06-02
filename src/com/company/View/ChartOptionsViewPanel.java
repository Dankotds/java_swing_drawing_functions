package com.company.View;

import com.company.Controllers.ChartDraw;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class ChartOptionsViewPanel extends JPanel {
    private ChartDrawPanel chartDrawPanel;
    private JLabel scaleTextLabel;
    private JLabel funcALabel = new JLabel("func A");
    private JLabel funcBLabel = new JLabel("func B");
    private JSlider zoomValueSlider;

    public ChartOptionsViewPanel(ChartDraw controller) {
        super();
        this.setLayout(new BorderLayout());
        JToolBar toolBar = new JToolBar(JToolBar.HORIZONTAL);
        zoomValueSlider = new JSlider(JSlider.HORIZONTAL, 50, 150, 100);
        zoomValueSlider.setMajorTickSpacing(25);
        zoomValueSlider.setMinorTickSpacing(5);
        zoomValueSlider.setPaintLabels(true);
        zoomValueSlider.setPaintTicks(true);
        zoomValueSlider.setSnapToTicks(true);

        chartDrawPanel = new ChartDrawPanel(controller);
        scaleTextLabel = new JLabel(chartDrawPanel.getZoomValue() * 100 + "%");

        toolBar.setFloatable(false);
        toolBar.setLayout(new FlowLayout());


        JScrollPane scrollPanel = new JScrollPane(chartDrawPanel);

        scrollPanel.setAutoscrolls(true);
        MouseAdapter mouseAdapter = new MouseAdapter() {
            private Point origin;

            @Override
            public void mousePressed(MouseEvent e) {
                origin = e.getPoint();
            }


            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (origin != null) {
                    JViewport viewPort = (JViewport) SwingUtilities.getAncestorOfClass(JViewport.class, chartDrawPanel);
                    if (viewPort != null) {
                        int deltaX = origin.x - e.getX();
                        int deltaY = origin.y - e.getY();

                        Rectangle view = viewPort.getViewRect();
                        view.x += deltaX * 0.3;
                        view.y += deltaY * 0.2;
                        chartDrawPanel.scrollRectToVisible(view);
                    }
                }
            }
        };
        chartDrawPanel.addMouseListener(mouseAdapter);
        chartDrawPanel.addMouseMotionListener(mouseAdapter);
        scrollPanel.setPreferredSize(new Dimension(800, 600));
        scrollPanel.setVisible(true);

        funcALabel.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 15));
        funcALabel.setForeground(new Color(212, 103, 219));
        funcBLabel.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 15));
        funcBLabel.setForeground(new Color(0, 205, 0));

        toolBar.add(funcALabel);
        toolBar.add(zoomValueSlider);
        toolBar.add(funcBLabel);
        this.add(scrollPanel, BorderLayout.WEST);
        this.add(toolBar, BorderLayout.SOUTH);
        this.setVisible(true);

        scrollPanel.addMouseWheelListener(new MouseWheelListener() {
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.isControlDown()) {
                    if (e.getWheelRotation() < 0) {
                        chartDrawPanel.setZoomValue(chartDrawPanel.getZoomValue() + 0.1);

                    } else {
                        chartDrawPanel.setZoomValue(chartDrawPanel.getZoomValue() - 0.1);
                    }
                    zoomValueSlider.setValue((int) (chartDrawPanel.getZoomValue() * 100));
                    chartDrawPanel.revalidate();
                }
            }
        });
    }

    public ChartDrawPanel getChartDrawPanel() {
        return chartDrawPanel;
    }
}
