package com.ebillsplitter.ui;

import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.*;

import com.ebillsplitter.config.DBConnection;

public class LoginFrame extends JFrame {
   
	public LoginFrame() {
		
		setTitle("Login - E Bill Splitter");
		setSize(400,250);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JTextField userField=new JTextField(15);
		JPasswordField passField=new JPasswordField(15);
		JButton loginBtn=new JButton("Login");
		
		setLayout(new FlowLayout());
		
		add(new JLabel("Username:"));
		add(userField);
		
		add(new JLabel("Password:"));
		add(passField);
		
		add(loginBtn);
		
		loginBtn.addActionListener((e -> {

            try(Connection con = DBConnection.getConnection()){

                PreparedStatement ps = con.prepareStatement(
                        "SELECT * FROM users WHERE username=? AND password=?");

                ps.setString(1,userField.getText());
                ps.setString(2,new String(passField.getPassword()));

                ResultSet rs = ps.executeQuery();

                if(rs.next()){
                    dispose();
                    new MainFrame().setVisible(true);
                }else{
                    JOptionPane.showMessageDialog(this,"Invalid Login");
                }

            }catch(Exception ex){
                ex.printStackTrace();
            }

        }));
		
	

		
		
	}
}
