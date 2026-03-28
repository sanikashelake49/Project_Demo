package com.ebillsplitter.ui;

import com.ebillsplitter.config.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class MemberPanel extends JPanel {

    JTable table;
    DefaultTableModel model;

    public MemberPanel() {

        setLayout(new BorderLayout());

        // 🔹 Top Panel (Input)
        JPanel topPanel = new JPanel(new FlowLayout());

        JTextField nameField = new JTextField(15);
        JButton addBtn = new JButton("Add Member");

        topPanel.add(new JLabel("Member Name:"));
        topPanel.add(nameField);
        topPanel.add(addBtn);

        add(topPanel, BorderLayout.NORTH);

        // 🔹 Table
        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Name");

        table = new JTable(model);

        add(new JScrollPane(table), BorderLayout.CENTER);

        // 🔹 Button Logic
        addBtn.addActionListener(e -> {

            try (Connection con = DBConnection.getConnection()) {

                PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO members(name) VALUES(?)"
                );

                ps.setString(1, nameField.getText());
                ps.executeUpdate();

                JOptionPane.showMessageDialog(this, "Member Added");

                // Clear input
                nameField.setText("");

                // Reload table (IMPORTANT 🔥)
                loadMembers();

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        });

        // 🔹 Load members when panel opens
        loadMembers();
    }

    // 🔹 Load Members from DB
    private void loadMembers() {

        try (Connection con = DBConnection.getConnection()) {

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("SELECT * FROM members");

            model.setRowCount(0); // clear old data

            while (rs.next()) {

                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name")
                });

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}