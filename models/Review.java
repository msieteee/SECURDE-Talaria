package models;

public class Review {
	private int custID;
	private int productID;
	private String comment;
	private String date;
	
	public Review() {
		
	}
	
	public Review(int custID, int productID, String comment, String date) {
		this.custID = custID;
		this.productID = productID;
		this.comment = comment;
		this.date = date;
	}
	
	public int getCustID() {
		return custID;
	}

	public void setCustID(int custID) {
		this.custID = custID;
	}

	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
}
