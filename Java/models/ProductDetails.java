package models;

public class ProductDetails {
	private int prodID;
	private String name;
	private String description;
	private double price;
	private int category;
	private int quantity;

	public ProductDetails() {

	}

	public ProductDetails(int prodID, String name, String description, double price, int category) {
		this.prodID = prodID;
		this.name = name;
		this.description = description;
		this.price = price;
		this.category = category;
	}

	public int getProdID() {
		return prodID;
	}

	public void setProdID(int prodID) {
		this.prodID = prodID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}
	
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	// public String getImage() {
	// return image;
	// }
	//
	// public void setImage(String image) {
	// this.image = image;
	// }
}
