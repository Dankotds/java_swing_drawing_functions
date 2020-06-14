package com.company.Controllers;

import javax.swing.*;
import java.awt.*;

import java.util.Random;

import static javax.swing.GroupLayout.Alignment.*;

public class MainFrameController {
    TableController tableController = new TableController();
    private boolean drawingIsStopped = false;
    private static final int STRAP_DIGIT = 10;

    private ChartDraw controller;

    private JButton stopDrawingBt = new JButton("Stop drawing");
    private JButton drawFuncABt = new JButton("Draw function A");
    private JButton drawFuncBBt = new JButton("Draw function B");

    private JLabel funcALabel = new JLabel("        Function A :  f(x) = 4x-6");
    private JLabel funcAStartEndPointsLabel = new JLabel("                     x [2..15]");

    private JLabel funcBTaskLabel = new JLabel("  Function B:  Pyramid sort (array)");

    private JLabel funcBElementsInArrayLabel = new JLabel("         n = ");
    private JTextField funcBElementsInArrayText = new JTextField();
    private JLabel funcBNumOfArraysLabel = new JLabel("         k = ");
    private JTextField funcBNumOfArraysText = new JTextField();

    private JLabel funcBNote1Label = new JLabel("Func B will be drawn with:");
    private JLabel funcBNote2Label = new JLabel("                    x = n");
    private JLabel funcBNote3Label = new JLabel("       y = time/10 (time=ms)");

    private Thread threadA = new Thread();
    private Thread threadB = new Thread();

    private JPanel tableBPanel;
    static JPanel funcAPanel;
    private JTable funcBTable;
    private JScrollPane scrollPane;

    private void errorEmptyFields() {
        JOptionPane.showMessageDialog(funcBElementsInArrayText, "Ошибка!\nПоля не заполнены!");
    }

    public JPanel createInfoPanel() {
        JPanel infoPanel = new JPanel();

        GroupLayout layout = new GroupLayout(infoPanel);
        infoPanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        funcBElementsInArrayText.setMaximumSize(new Dimension(100, 25));
        funcBElementsInArrayText.setText("20");
        funcBNumOfArraysText.setMaximumSize(new Dimension(100, 25));
        funcBNumOfArraysText.setText("20");

        //Creating correct Horizontal&Vertical groups of elements
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(LEADING)
                    .addComponent(stopDrawingBt)
                    .addComponent(funcALabel)
                    .addComponent(funcAStartEndPointsLabel)
                    .addComponent(drawFuncABt)
                    .addComponent(funcBTaskLabel)
                    .addGroup(layout.createSequentialGroup()
                            .addComponent(funcBElementsInArrayLabel)
                            .addComponent(funcBElementsInArrayText))
                    .addGroup(layout.createSequentialGroup()
                            .addComponent(funcBNumOfArraysLabel)
                            .addComponent(funcBNumOfArraysText))
                    .addComponent(funcBNote1Label)
                    .addComponent(funcBNote2Label)
                    .addComponent(funcBNote3Label)
                    .addComponent(drawFuncBBt)
                )
        );

        layout.linkSize(SwingConstants.HORIZONTAL, funcALabel, funcAStartEndPointsLabel, funcBTaskLabel);

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(LEADING).addComponent(stopDrawingBt))
                .addGroup(layout.createParallelGroup(LEADING).addComponent(funcALabel))
                .addGroup(layout.createParallelGroup(LEADING).addComponent(funcAStartEndPointsLabel))
                .addGroup(layout.createParallelGroup(LEADING).addComponent(drawFuncABt))
                .addGroup(layout.createParallelGroup(LEADING).addComponent(funcBTaskLabel))

                .addGroup(layout.createParallelGroup(LEADING)
                        .addComponent(funcBElementsInArrayLabel)
                        .addComponent(funcBElementsInArrayText))
                .addGroup(layout.createParallelGroup(LEADING)
                        .addComponent(funcBNumOfArraysLabel)
                        .addComponent(funcBNumOfArraysText))

                .addGroup(layout.createParallelGroup(LEADING).addComponent(funcBNote1Label))
                .addGroup(layout.createParallelGroup(LEADING).addComponent(funcBNote2Label))
                .addGroup(layout.createParallelGroup(LEADING).addComponent(funcBNote3Label))
                .addGroup(layout.createSequentialGroup().addComponent(drawFuncBBt))
        );

        return infoPanel;
    }

    public JPanel createTableBPanel(JScrollPane scrollPane) {
        JPanel panel = new JPanel();
        scrollPane.setMinimumSize(new Dimension(100, 180));
        panel.setLayout(new BorderLayout());

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    public void action(JFrame mainFrame, JSplitPane splitPaneTableGraph, ChartDraw controller) {
        this.controller = controller;

        drawFuncABt.addActionListener(e -> {

            drawingIsStopped = false;
            if (threadA.isAlive()) {
                threadA.stop();
            }
            controller.clearPointList("A");
            threadA = new Thread(new Runnable() {
                @Override
                public void run() {
                    int x = 2;
                    while (x <= 15 && !drawingIsStopped) {
                        int y = funcA(x);
                        Point point = new Point(x * 100, y * 10000);
                        controller.addPointToList(point, "A");
                        x += 1;
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException en) {
                            JOptionPane.showMessageDialog(mainFrame, "Ошибка!\nЧто-то пошло не так!");
                        }
                    }
                    Thread.interrupted();
                }

                int funcA (int x) {
                    return (4 * x - 6);
                }
            });

            threadA.start();

            splitPaneTableGraph.setLeftComponent(funcAPanel);
        });

        drawFuncBBt.addActionListener(e -> {

            if (splitPaneTableGraph.getLeftComponent() != null) {
                tableBPanel.remove(scrollPane);
            }

            funcBTable = tableController.createTable();
            scrollPane = new JScrollPane(funcBTable);
            tableBPanel = createTableBPanel(scrollPane);
            splitPaneTableGraph.setLeftComponent(tableBPanel);
            drawingIsStopped = false;
            if (threadB.isAlive()) {
                threadB.stop();
            }
            controller.clearPointList("B");
            threadB = new Thread(new Runnable() {
                @Override
                public void run() {
                    if (!funcBElementsInArrayText.getText().equals("") && !funcBNumOfArraysText.getText().equals("")) {

                            //Taking n and k
                            String n = funcBElementsInArrayText.getText();
                            String k = funcBNumOfArraysText.getText();
                            int nElementsInArray = Integer.parseInt(n);
                            int kNumOfArrays = Integer.parseInt(k);

                            //Counting averageTime
                            for (int currentArraySize = 2; currentArraySize < nElementsInArray; currentArraySize++) {
                                if (!drawingIsStopped) {
                                    double averageSortTime = averSortTime(nElementsInArray, kNumOfArrays);

                                    Point point = new Point((int) currentArraySize * 100, (int) averageSortTime * 1000);
                                    controller.addPointToList(point, "B");
                                    funcBTable = tableController.addRow(funcBTable, String.format("%d",
                                            currentArraySize), String.format("%.0f", averageSortTime));
                                } else break;
                            }
                    } else {
                        errorEmptyFields();
                    }
                    Thread.interrupted();
                }

                private int averSortTime(int size, int k) {
                    int commonSortTime = 0;
                    for (int currentArrayCount = 1; currentArrayCount < k; currentArrayCount++) {
                        commonSortTime += comSortTime(generateRandomArray(size));
                    }
                    int averageSortTime = commonSortTime / k;

                    return averageSortTime;
                }

                private long comSortTime(int[] arrayToSort) {
                    long startTime = System.nanoTime() / STRAP_DIGIT;

                    HeapSort sorting = new HeapSort();
                    sorting.pyramidSort(arrayToSort);

                    long endTime = System.nanoTime() / STRAP_DIGIT;
                    long result = endTime - startTime;
                    if (result > 2000) {
                        result = comSortTime(arrayToSort);
                    }
                    return result;
                }

                private int[] generateRandomArray(int currentArraySize) {
                    int[] result = new int[currentArraySize];
                    Random random = new Random();
                    for (int i = 0; i < result.length; i++) {
                        result[i] = random.nextInt(currentArraySize);
                    }
                    return result;
                }
            });

            threadB.start();
        });

        stopDrawingBt.addActionListener(e -> drawingIsStopped = !drawingIsStopped);
    }
}