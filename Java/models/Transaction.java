package models;

public class Transaction {
	private int transactionID;
	private int customerID;
	private String creditcard;
	private String date;
	
	public Transaction() {
		
	}
	
	public Transaction(int transactionID, int customerID, String creditcard, String date) {
		this.transactionID = transactionID;
		this.customerID = customerID;
		this.creditcard = creditcard;
		this.date = date;
	}
	
	public int getTransactionID() {
		return transactionID;
	}
	
	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}
	
	public int getCustomerID() {
		return customerID;
	}
	
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
	
	public String getCreditcard() {
		return creditcard;
	}
	
	public void setCreditcard(String creditcard) {
		this.creditcard = creditcard;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
}
