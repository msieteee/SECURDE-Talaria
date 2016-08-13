package models;

public class PurchaseHistory {
	private int id;
	private int transactionID;
	private double price;
	private int quantity;
	private int prodID;
	
	public PurchaseHistory() {
		
	}
	
	public PurchaseHistory(int id, int transactionID, double price, int quantity, int prodID) {
		this.id = id;
		this.transactionID = transactionID;
		this.price = price;
		this.quantity = quantity;
		this.prodID = prodID;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getTransactionID() {
		return transactionID;
	}
	
	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getProdID() {
		return prodID;
	}
	
	public void setProdID(int prodID) {
		this.prodID = prodID;
	}
}
