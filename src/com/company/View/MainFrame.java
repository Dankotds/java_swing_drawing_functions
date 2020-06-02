package com.company.View;

import com.company.Controllers.ChartDraw;
import com.company.Controllers.MainFrameController;

import javax.swing.*;
import java.awt.*;

public class MainFrame {
    MainFrameController mainFrameController = new MainFrameController();
    private JFrame mainFrame = new JFrame("Functions");
    private JPanel backgroundPanel = new JPanel();
    private JPanel infoPanel = new JPanel();
    private JSplitPane splitPaneH;
    private JSplitPane splitPaneTableGraph = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    private ChartDraw controller = new ChartDraw(splitPaneTableGraph);


    public void initialize() {
        backgroundPanel.setLayout(new BorderLayout());

        infoPanel = mainFrameController.createInfoPanel();
        infoPanel.setBorder(BorderFactory.createEtchedBorder());

        splitPaneH = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        backgroundPanel.add(splitPaneH, BorderLayout.CENTER);
        splitPaneH.setLeftComponent(infoPanel);
        splitPaneH.setRightComponent(splitPaneTableGraph);
        mainFrame.add(backgroundPanel);
        mainFrame.setSize(1200, 700);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrameController.action(mainFrame, splitPaneTableGraph, controller);
        mainFrame.setVisible(true);
    }
}
