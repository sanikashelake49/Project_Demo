package com.ebillsplitter.ui;

import java.awt.BorderLayout;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.*;

import javax.swing.*;

import com.ebillsplitter.config.DBConnection;

public class DashboardPanel extends JPanel {
    
	public DashboardPanel() {
		
		setLayout(new BorderLayout());
		
		JTextArea area=new JTextArea();
		JButton refresh=new JButton("Refresh Balances");
		
		add(new JScrollPane(area),BorderLayout.CENTER);
        add(refresh,BorderLayout.SOUTH);

        refresh.addActionListener(e -> {

            try(Connection con = DBConnection.getConnection()){

                Statement st = (Statement) con.createStatement();

                ResultSet rs = ((java.sql.Statement) st).executeQuery(
                        "SELECT m.name, IFNULL(SUM(e.amount),0) total " +
               
                        		"FROM members m LEFT JOIN expenses e " +
                                "ON m.id=e.paid_by GROUP BY m.id");

                area.setText("");

                while(rs.next()){
                    area.append(rs.getString(1)+" Paid: "+rs.getDouble(2)+"\n");
                }

            }catch(Exception ex){
                ex.printStackTrace();
            }

        });
	}
}
