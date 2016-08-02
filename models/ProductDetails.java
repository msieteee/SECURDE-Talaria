package models;

public class ProductDetails {
	private String name;
	private String description;
	private double price;
	private int category;
	private String image;
	
	public ProductDetails() {
		
	}
	
	public ProductDetails(String name, String description, double price, int category, String image) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.category = category;
		this.image = image;
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
	
	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
}
