package com.ebillsplitter.ui;

import com.ebillsplitter.config.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ExpensePanel extends JPanel {

    JTable table;
    DefaultTableModel model;

    public ExpensePanel() {

        setLayout(new BorderLayout());

        // 🔹 Top Panel (Input)
        JPanel topPanel = new JPanel();

        JTextField desc = new JTextField(10);
        JTextField amount = new JTextField(6);
        JTextField paidBy = new JTextField(4);
        JTextField members = new JTextField(4);

        JButton addButton = new JButton("Add Expense");

        topPanel.add(new JLabel("Description:"));
        topPanel.add(desc);

        topPanel.add(new JLabel("Amount:"));
        topPanel.add(amount);

        topPanel.add(new JLabel("Paid By ID:"));
        topPanel.add(paidBy);

        topPanel.add(new JLabel("Members:"));
        topPanel.add(members);

        topPanel.add(addButton);

        add(topPanel, BorderLayout.NORTH);

        // 🔹 Table
        model = new DefaultTableModel();

        model.addColumn("ID");
        model.addColumn("Description");
        model.addColumn("Amount");
        model.addColumn("Paid By");
        model.addColumn("Members");

        table = new JTable(model);

        add(new JScrollPane(table), BorderLayout.CENTER);

        // 🔹 Button Logic
        addButton.addActionListener(e -> {

            try {

                String description = desc.getText();
                double totalAmount = Double.parseDouble(amount.getText());
                int paidById = Integer.parseInt(paidBy.getText());
                int memberCount = Integer.parseInt(members.getText());

                Connection con = DBConnection.getConnection();

                // Insert into DB
                PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO expenses(description,amount,paid_by,member_count) VALUES(?,?,?,?)"
                );

                ps.setString(1, description);
                ps.setDouble(2, totalAmount);
                ps.setInt(3, paidById);
                ps.setInt(4, memberCount);

                ps.executeUpdate();

                JOptionPane.showMessageDialog(this, "Expense Added!");

                // 🔹 Calculate Split
                calculateSplit(paidById, totalAmount, memberCount);

                // Clear fields
                desc.setText("");
                amount.setText("");
                paidBy.setText("");
                members.setText("");

                // Reload table
                loadExpenses();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error!");
            }

        });

        // Load data initially
        loadExpenses();
    }

    // 🔹 Load Expenses from DB
    private void loadExpenses() {

        try {

            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("SELECT * FROM expenses");

            model.setRowCount(0);

            while (rs.next()) {

                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("description"),
                        rs.getDouble("amount"),
                        rs.getInt("paid_by"),
                        rs.getInt("member_count")
                });

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔹 Split Logic (WHO OWES WHOM)
    private void calculateSplit(int paidBy, double amount, int memberCount) {

        double share = amount / memberCount;

        StringBuilder result = new StringBuilder();
        result.append("Split Details:\n\n");

        for (int i = 1; i <= memberCount; i++) {

            if (i != paidBy) {
                result.append("Member ")
                        .append(i)
                        .append(" owes ₹")
                        .append(share)
                        .append(" to Member ")
                        .append(paidBy)
                        .append("\n");
            }
        }

        JOptionPane.showMessageDialog(this, result.toString());
    }
}