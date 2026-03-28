package com.ebillsplitter.model;

public class Expense {
   private int id;
   private String description;
   private double amount;
   private int paidBy;
public Expense() {
	super();
	// TODO Auto-generated constructor stub
}
public Expense(int id, String description, double amount, int paidBy) {
	super();
	this.id = id;
	this.description = description;
	this.amount = amount;
	this.paidBy = paidBy;
}
   
   
}
