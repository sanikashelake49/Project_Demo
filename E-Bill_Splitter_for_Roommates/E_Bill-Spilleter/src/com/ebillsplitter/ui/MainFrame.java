package com.ebillsplitter.ui;

import javax.swing.*;

public class MainFrame extends JFrame {
	
	public MainFrame() {
		
		setTitle("E-Bill Splitter");
		setSize(900,600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JTabbedPane tabs=new JTabbedPane();
		
		tabs.add("Members", new MemberPanel());
		tabs.add("Expenses", new ExpensePanel());
		tabs.add("Dashboard", new DashboardPanel());
		tabs.add("Settlements", new SettlementPanel());
	
	     add(tabs);
	}

}
