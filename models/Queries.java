package models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Queries {

    private Connection connection;

    public Queries() {

    }

    public Account getAuthentication(String username, String password) {
        Account account = null;

        connection = new DBConnection().getConnection();
        
        PreparedStatement pst;
        String query = "SELECT * FROM accounts WHERE username = ? AND password = ?";
        
        try {
			pst = connection.prepareStatement(query);
			pst.setString(1, username);
	        pst.setString(2, password);
	        ResultSet rs = pst.executeQuery();

	        while (rs.next()) {
	            account = new Account();
	            account.setUsername(rs.getString("username"));
	            System.out.println("in queries: " + rs.getString("username"));
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return account;
    }
    
    public int getRole(String role) {
    	int roleID = 0;
    	
    	connection = new DBConnection().getConnection();
        
        PreparedStatement pst;
        String query = "SELECT id FROM roles WHERE role = ?";
        
        try {
			pst = connection.prepareStatement(query);
			pst.setString(1, role);
	        ResultSet rs = pst.executeQuery();

	        while (rs.next()) {
	            roleID = rs.getInt("id");
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return roleID;
    }

    public String getHashedPassword(String username) {
        String hashedPassword = null;

        connection = new DBConnection().getConnection();
        
        PreparedStatement pst;
        String query = "SELECT password FROM accounts WHERE username = ?";
        
        try {
			pst = connection.prepareStatement(query);
			pst.setString(1, username);
	        ResultSet rs = pst.executeQuery();

	        while (rs.next()) {
	            hashedPassword = rs.getString("password");
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return hashedPassword;
    }

    public void addUserAccount(int cust_id, String username, String password,String type) throws SQLException {
    	connection = new DBConnection().getConnection();

        PreparedStatement pst;
        String query = "INSERT INTO accounts (cust_id, username, password, role_id) VALUES (?, ?, ?, ?)";
        int role = getRole(type);
        
        try {
        	if (!doesUsernameExist(username)) {
	            pst = connection.prepareStatement(query);
	            pst.setInt(1, cust_id);
	            pst.setString(2, username);
	            pst.setString(3, password);
	            pst.setInt(4, role);
	            System.out.println(pst);
	            pst.executeUpdate();
	            System.out.println("yey");
        	}
        } catch (Exception e) {
            System.out.println("boooooo");
        }
    }
    
    public Boolean doesUsernameExist(String username) {
    	Boolean exist = false;
    	
    	connection = new DBConnection().getConnection();
        
        PreparedStatement pst;
        String query = "SELECT username FROM account WHERE acc_username = ?";
        ResultSet rs = null;
        
        try {
			pst = connection.prepareStatement(query);
			pst.setString(1, username);
	        rs = pst.executeQuery();
	        
	        if(rs != null)
	        	exist = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return exist;
    }
    
    public int getCustID(String name) {
    	int cust_id = 0;
    	
    	connection = new DBConnection().getConnection();
        
        PreparedStatement pst;
        String query = "SELECT id FROM customer_details WHERE name = ?";
        
        try {
			pst = connection.prepareStatement(query);
			pst.setString(1, name);
	        ResultSet rs = pst.executeQuery();

	        while (rs.next()) {
	            cust_id = rs.getInt("id");
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    	return cust_id;
    }
    
    public void addAddress(String houseNo, String street, String subd, String city, String postal, String country) {
    	connection = new DBConnection().getConnection();

    	if (getAddress(houseNo, street, subd, city, postal, country) == 0) {
	        PreparedStatement pst;
	        String query = "INSERT INTO address (housenum, street, subd, city, postal, country) VALUES (?, ?, ?, ?, ?, ?)";
	        
	        try {
	            pst = connection.prepareStatement(query);
	            pst.setString(1, houseNo);
	            pst.setString(2, street);
	            pst.setString(3, subd);
	            pst.setString(4, city);
	            pst.setString(5, postal);
	            pst.setString(6, country);
	            System.out.println(pst);
	            pst.executeUpdate();
	            System.out.println("yey");
	        } catch (Exception e) {
	            System.out.println("boooooo");
	        }
    	} else {
    		System.out.println("Duplicate");
    	}
    }
    
    public int getAddress(String houseNo, String street, String subd, String city, String postal, String country) {
    	int id = 0;
    	
    	connection = new DBConnection().getConnection();
        
        PreparedStatement pst;
        String query = "SELECT id FROM address WHERE housenum = ? AND street = ? AND subd = ? AND city = ? AND postal = ? AND country = ?";
        
        try {
			pst = connection.prepareStatement(query);
			pst.setString(1, houseNo);
			pst.setString(2, street);
			pst.setString(3, subd);
			pst.setString(4, city);
			pst.setString(5, postal);
			pst.setString(6, country);
	        ResultSet rs = pst.executeQuery();

	        while (rs.next()) {
	            id = rs.getInt("id");
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    	return id;
    }
    
    public void addCustomerDetails(String name, String email, int ba_id, int sa_id) {
    	connection = new DBConnection().getConnection();

        PreparedStatement pst;
        String query = "INSERT INTO customer_details (name, email, ba_id, sa_id) VALUES (?, ?, ?, ?)";
        
        try {
            pst = connection.prepareStatement(query);
            pst.setString(1, name);
            pst.setString(2, email);
            pst.setInt(3, ba_id);
            pst.setInt(4, sa_id);
            System.out.println(pst);
            pst.executeUpdate();
            System.out.println("yey");
        } catch (Exception e) {
            System.out.println("boooooo");
        }
    }
    
    public void addProduct(String name, String description, double price, String category) {
    	connection = new DBConnection().getConnection();

        PreparedStatement pst;
        String query = "INSERT INTO products (prod_name, prod_description, prod_price, prod_category) VALUES (?, ?, ?, ?)";
    	int cat = getCategory(category);
        
        try {
            pst = connection.prepareStatement(query);
            pst.setString(1, name);
            pst.setString(2, description);
            pst.setDouble(3, price);
            pst.setInt(4, cat);
            pst.executeUpdate();
            System.out.println("yey");
        } catch (Exception e) {
            System.out.println("boooooo");
        }
    }
    
    public ArrayList<ProductDetails> getProdcuts() {
    	System.out.println("YEY IN GET PRODUCTS QUERYYYYY");
    	ArrayList<ProductDetails> products = new ArrayList<ProductDetails> ();
    	
    	connection = new DBConnection().getConnection();
        
        PreparedStatement pst;
        String query = "SELECT * FROM product_details";
        ResultSet rs = null;
        
        try {
			pst = connection.prepareStatement(query);
	        rs = pst.executeQuery();
	        while(rs.next()) {
	        	String name = rs.getString("product_name");
	        	String description = rs.getString("product_description");
	        	double price = rs.getDouble("product_price");
	        	int category = rs.getInt("product_category");
	        	String image = rs.getString("product_image");
	        	ProductDetails pd = new ProductDetails(name, description, price, category, image);
	        	products.add(pd);
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return products;
    }
    
    public ArrayList<ProductDetails> getSpecificProduct(String cat) {
    	ArrayList<ProductDetails> products = new ArrayList<ProductDetails> ();
    	
    	connection = new DBConnection().getConnection();
    	
    	int prodCat = getCategory(cat);
        
        PreparedStatement pst;
        String query = "SELECT * FROM product_details WHERE product_category = ?";
        ResultSet rs = null;
        
        try {
			pst = connection.prepareStatement(query);
			pst.setInt(1, prodCat);
	        rs = pst.executeQuery();
	        while(rs.next()) {
	        	String name = rs.getString("product_name");
	        	String description = rs.getString("product_description");
	        	double price = rs.getDouble("product_price");
	        	int category = rs.getInt("product_category");
	        	String image = rs.getString("product_image");
	        	ProductDetails pd = new ProductDetails(name, description, price, category, image);
	        	products.add(pd);
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return products;
    }
    
    public ProductDetails getOneProduct(String productName) {
    	ProductDetails product = null;
    	
    	connection = new DBConnection().getConnection();
        
        PreparedStatement pst;
        String query = "SELECT * FROM product_details WHERE product_name = ?";
        ResultSet rs = null;
        
        try {
			pst = connection.prepareStatement(query);
			pst.setString(1, productName);
	        rs = pst.executeQuery();
	        while(rs.next()) {
	        	String name = rs.getString("product_name");
	        	String description = rs.getString("product_description");
	        	double price = rs.getDouble("product_price");
	        	int category = rs.getInt("product_category");
	        	String image = rs.getString("product_image");
	        	product = new ProductDetails(name, description, price, category, image);
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return product;
    }
    
    public int getProductID(String productName) {
    	int productID = 0;
    	
    	connection = new DBConnection().getConnection();
        
        PreparedStatement pst;
        String query = "SELECT * FROM product_details WHERE product_name = ?";
        ResultSet rs = null;
        
        try {
			pst = connection.prepareStatement(query);
			pst.setString(1, productName);
	        rs = pst.executeQuery();
	        while(rs.next()) {
	        	productID = rs.getInt("product_id");
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return productID;
    }
    
    public int getCategory(String category) {
    	int cat = 0;
    	
    	connection = new DBConnection().getConnection();
        
        PreparedStatement pst;
        String query = "SELECT cat_id FROM prod_category WHERE cat_name = ?";
        
        try {
			pst = connection.prepareStatement(query);
			pst.setString(1, category);
	        ResultSet rs = pst.executeQuery();

	        while (rs.next()) {
	            cat = rs.getInt("cat_id");
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return cat;
    }
    
    public void addReview(int prodId, int custId, String review, String date) {
    	connection = new DBConnection().getConnection();

        PreparedStatement pst;
        String query = "INSERT INTO reviews (prod_id, cust_id, rev_review, rev_date) VALUES (?, ?, ?, ?)";
        
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy"); 
        Date startDate = null;
        
        try {
            startDate = (Date) df.parse(date);
            String newDateString = df.format(startDate);
            System.out.println(newDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        try {
            pst = connection.prepareStatement(query);
            pst.setInt(1, prodId);
            pst.setInt(2, custId);
            pst.setString(3, review);
            pst.setDate(4, startDate);
            pst.executeUpdate();
            System.out.println("yey");
        } catch (Exception e) {
            System.out.println("boooooo");
        }
    }
    
    public ArrayList<Review> getAllReviews(int productID) {
    	ArrayList<Review> reviews = new ArrayList<Review> ();
    	
    	connection = new DBConnection().getConnection();
        
        PreparedStatement pst;
        String query = "SELECT * FROM review WHERE product_det_id = ?";
        ResultSet rs = null;
        
        try {
			pst = connection.prepareStatement(query);
			pst.setInt(1, productID);
	        rs = pst.executeQuery();
	        while(rs.next()) {
	        	int custID = rs.getInt("cust_id");
	        	String review = rs.getString("comment");
	        	String date = rs.getDate("date").toString();
	        	Review r = new Review(custID, productID, review, date);
	        	reviews.add(r);
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return reviews;
    }
}
