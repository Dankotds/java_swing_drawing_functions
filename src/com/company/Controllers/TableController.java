package com.company.Controllers;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TableController {
    public JTable createTable() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("n");
        model.addColumn("time");
        JTable table = new JTable(model);
        return table;
    }

    public JTable addRow(JTable table, String data1, String data2) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(new Object[]{data1, data2});
        return table;
    }

}
