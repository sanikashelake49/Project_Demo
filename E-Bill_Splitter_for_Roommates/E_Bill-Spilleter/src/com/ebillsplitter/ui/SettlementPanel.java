package com.ebillsplitter.ui;

import com.ebillsplitter.config.DBConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class SettlementPanel extends JPanel {

    public SettlementPanel() {

        setLayout(new FlowLayout());

        JTextField from = new JTextField(4);
        JTextField to = new JTextField(4);
        JTextField amount = new JTextField(6);

        JButton settle = new JButton("Record Settlement");

        add(new JLabel("From ID:"));
        add(from);

        add(new JLabel("To ID:"));
        add(to);

        add(new JLabel("Amount:"));
        add(amount);

        add(settle);

        settle.addActionListener(e -> {

            try(Connection con = DBConnection.getConnection()){

                PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO settlements(from_member,to_member,amount) VALUES(?,?,?)");

                ps.setInt(1,Integer.parseInt(from.getText()));
                ps.setInt(2,Integer.parseInt(to.getText()));
                ps.setDouble(3,Double.parseDouble(amount.getText()));

                ps.executeUpdate();

                JOptionPane.showMessageDialog(this,"Settlement Recorded");

            }catch(Exception ex){
                ex.printStackTrace();
            }

        });

    }
}