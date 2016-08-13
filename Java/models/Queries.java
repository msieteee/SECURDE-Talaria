package models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
		ResultSet rs = null;

		try {
			pst = connection.prepareStatement(query);
			pst.setString(1, username);
			pst.setString(2, password);
			rs = pst.executeQuery();

			while (rs.next()) {
				account = new Account();
				account.setCustomerID(rs.getInt("cust_id"));
				account.setUsername(rs.getString("username"));
				account.setRoleID(rs.getInt("role_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return account;
	}

	public int getRole(String role) {
		int roleID = 0;

		connection = new DBConnection().getConnection();

		PreparedStatement pst;
		String query = "SELECT id FROM roles WHERE role = ?";
		ResultSet rs = null;

		try {
			pst = connection.prepareStatement(query);
			pst.setString(1, role);
			rs = pst.executeQuery();

			while (rs.next())
				roleID = rs.getInt("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return roleID;
	}

	public String getHashedPassword(String username) {
		String hashedPassword = null;

		connection = new DBConnection().getConnection();

		PreparedStatement pst;
		String query = "SELECT password FROM accounts WHERE username = ?";
		ResultSet rs = null;

		try {
			pst = connection.prepareStatement(query);
			pst.setString(1, username);
			rs = pst.executeQuery();

			while (rs.next())
				hashedPassword = rs.getString("password");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return hashedPassword;
	}

	public int addUserAccount(int cust_id, String username, String password, int role) {
		int success = 0;
		connection = new DBConnection().getConnection();

		PreparedStatement pst;
		String query = "INSERT INTO accounts (cust_id, username, password, role_id, status, dateCreated) VALUES (?, ?, ?, ?, ?, ?)";

		try {
			if (!doesUsernameExist(username)) {
				pst = connection.prepareStatement(query);
				java.util.Date today = new java.util.Date();
				java.sql.Timestamp timestamp = new java.sql.Timestamp(today.getTime());
				pst.setInt(1, cust_id);
				pst.setString(2, username);
				pst.setString(3, password);
				pst.setInt(4, role);
				pst.setInt(5, 1);
				pst.setTimestamp(6, timestamp);
				pst.executeUpdate();
				success = 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return success;
	}

	public void addProductManager(String username, String password, int role) {
		connection = new DBConnection().getConnection();

		PreparedStatement pst;
		String query = "INSERT INTO accounts (username, password, role_id, status, dateCreated) VALUES (?, ?, ?, ?, ?)";

		try {
			pst = connection.prepareStatement(query);
			java.util.Date today = new java.util.Date();
			java.sql.Timestamp timestamp = new java.sql.Timestamp(today.getTime());
			pst.setString(1, username);
			pst.setString(2, password);
			pst.setInt(3, role);
			pst.setInt(4, 0);
			pst.setTimestamp(5, timestamp);
			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addAccountManager(String username, String password, int role) {
		connection = new DBConnection().getConnection();

		PreparedStatement pst;
		String query = "INSERT INTO accounts (username, password, role_id, status, dateCreated) VALUES (?, ?, ?, ?, ?)";

		try {
			pst = connection.prepareStatement(query);
			java.util.Date today = new java.util.Date();
			java.sql.Timestamp timestamp = new java.sql.Timestamp(today.getTime());
			pst.setString(1, username);
			pst.setString(2, password);
			pst.setInt(3, role);
			pst.setInt(4, 0);
			pst.setTimestamp(5, timestamp);
			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Boolean doesUsernameExist(String username) {
		Boolean exist = false;

		connection = new DBConnection().getConnection();

		PreparedStatement pst;
		String query = "SELECT username FROM accounts WHERE username = ?";
		ResultSet rs = null;

		try {
			pst = connection.prepareStatement(query);
			pst.setString(1, username);
			rs = pst.executeQuery();

			if (rs.next())
				exist = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return exist;
	}

	public void updateTimestamp(int custID) {
		connection = new DBConnection().getConnection();

		PreparedStatement pst;
		String query = "UPDATE accounts SET lastLoggedIn = ? WHERE cust_id = ?";

		try {
			pst = connection.prepareStatement(query);
			java.util.Date today = new java.util.Date();
			java.sql.Timestamp timestamp = new java.sql.Timestamp(today.getTime());
			pst.setTimestamp(1, timestamp);
			pst.setInt(2, custID);
			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getMaxCustIDFromAccount() {
		int cust_id = 0;

		connection = new DBConnection().getConnection();

		PreparedStatement pst;
		String query = "SELECT MAX(cust_id) FROM accounts";
		ResultSet rs = null;

		try {
			pst = connection.prepareStatement(query);
			rs = pst.executeQuery();

			while (rs.next())
				cust_id = rs.getInt("MAX(cust_id)");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return cust_id;
	}

	public int getMaxCustIDFromDetails() {
		int cust_id = 0;

		connection = new DBConnection().getConnection();

		PreparedStatement pst;
		String query = "SELECT MAX(id) FROM customer_details";
		ResultSet rs = null;

		try {
			pst = connection.prepareStatement(query);
			rs = pst.executeQuery();

			while (rs.next())
				cust_id = rs.getInt("MAX(id)");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return cust_id;
	}

	public int getCustIDFromAccount(String username) {
		int cust_id = 0;

		connection = new DBConnection().getConnection();

		PreparedStatement pst;
		String query = "SELECT cust_id FROM accounts WHERE username = ?";
		ResultSet rs = null;

		try {
			pst = connection.prepareStatement(query);
			pst.setString(1, username);
			rs = pst.executeQuery();

			while (rs.next())
				cust_id = rs.getInt("cust_id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return cust_id;
	}

	public int getCustID(String name) {
		int cust_id = 0;

		connection = new DBConnection().getConnection();

		PreparedStatement pst;
		String query = "SELECT id FROM customer_details WHERE name = ?";
		ResultSet rs = null;

		try {
			pst = connection.prepareStatement(query);
			pst.setString(1, name);
			rs = pst.executeQuery();

			while (rs.next())
				cust_id = rs.getInt("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return cust_id;
	}

	public String getCustName(int custID) {
		String name = null;

		connection = new DBConnection().getConnection();

		PreparedStatement pst;
		String query = "SELECT name FROM customer_details WHERE id = ?";
		ResultSet rs = null;

		try {
			pst = connection.prepareStatement(query);
			pst.setInt(1, custID);
			rs = pst.executeQuery();

			while (rs.next())
				name = rs.getString("name");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return name;
	}

	public int getBillingID(int custID) {
		int ba_id = 0;

		connection = new DBConnection().getConnection();

		PreparedStatement pst;
		String query = "SELECT ba_id FROM customer_details WHERE id = ?";
		ResultSet rs = null;

		try {
			pst = connection.prepareStatement(query);
			pst.setInt(1, custID);
			rs = pst.executeQuery();

			while (rs.next())
				ba_id = rs.getInt("ba_id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ba_id;
	}

	public int getShippingID(int custID) {
		int sa_id = 0;

		connection = new DBConnection().getConnection();

		PreparedStatement pst;
		String query = "SELECT sa_id FROM customer_details WHERE id = ?";
		ResultSet rs = null;

		try {
			pst = connection.prepareStatement(query);
			pst.setInt(1, custID);
			rs = pst.executeQuery();

			while (rs.next())
				sa_id = rs.getInt("sa_id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return sa_id;
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
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public int getAddress(String houseNo, String street, String subd, String city, String postal, String country) {
		int id = 0;

		connection = new DBConnection().getConnection();

		PreparedStatement pst;
		String query = "SELECT id FROM address WHERE housenum = ? AND street = ? AND subd = ? AND city = ? AND postal = ? AND country = ?";
		ResultSet rs = null;

		try {
			pst = connection.prepareStatement(query);
			pst.setString(1, houseNo);
			pst.setString(2, street);
			pst.setString(3, subd);
			pst.setString(4, city);
			pst.setString(5, postal);
			pst.setString(6, country);
			rs = pst.executeQuery();

			while (rs.next())
				id = rs.getInt("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return id;
	}

	public String getCompleteAddress(int addID) {
		String address = null;

		connection = new DBConnection().getConnection();

		PreparedStatement pst;
		String query = "SELECT CONCAT(housenum, \' \', street, \' \', subd, \' \', city, \' \', postal, \' \', country) AS caddress FROM address WHERE id = ?";
		ResultSet rs = null;

		try {
			pst = connection.prepareStatement(query);
			pst.setInt(1, addID);
			rs = pst.executeQuery();

			while (rs.next())
				address = rs.getString("caddress");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return address;
	}
	
	public void deleteAddress(int id) {
		connection = new DBConnection().getConnection();

		PreparedStatement pst;
		String query = "DELETE FROM address WHERE id = ?";

		try {
			pst = connection.prepareStatement(query);
			pst.setInt(1, id);
			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addCustomerDetails(String name, String email, int ba_id, int sa_id) {

		connection = new DBConnection().getConnection();

		PreparedStatement pst;
		String query = "INSERT INTO customer_details (id, name, email, ba_id, sa_id) VALUES (?, ?, ?, ?, ?)";

		int maxAccount = getMaxCustIDFromAccount();
		int maxDetails = getMaxCustIDFromDetails();
		int id = 0;
		if (maxAccount > maxDetails)
			id = maxAccount + 1;
		else
			id = maxDetails + 1;

		try {
			pst = connection.prepareStatement(query);
			pst.setInt(1, id);
			pst.setString(2, name);
			pst.setString(3, email);
			pst.setInt(4, ba_id);
			pst.setInt(5, sa_id);
			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteCustomerDetails(int custID) {
		connection = new DBConnection().getConnection();

		PreparedStatement pst;
		String query = "DELETE FROM customer_details WHERE id = ?";

		try {
			pst = connection.prepareStatement(query);
			pst.setInt(1, custID);
			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getAccountStatus(int custID) {
		int status = 0;

		connection = new DBConnection().getConnection();

		PreparedStatement pst;
		String query = "SELECT status FROM accounts WHERE cust_id = ?";
		ResultSet rs = null;

		try {
			pst = connection.prepareStatement(query);
			pst.setInt(1, custID);
			rs = pst.executeQuery();

			while (rs.next())
				status = rs.getInt("status");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return status;
	}
	
	public void updateAccountStatus(int custID, int status) {
		connection = new DBConnection().getConnection();

		PreparedStatement pst;
		String query = "UPDATE accounts SET status = ? WHERE cust_id = ?";

		try {
			pst = connection.prepareStatement(query);
			pst.setInt(1, status);
			pst.setInt(2, custID);
			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updatePassword(int custID, String newPassword) {
		connection = new DBConnection().getConnection();

		PreparedStatement pst;
		String query = "UPDATE accounts SET password = ? WHERE cust_id = ?";

		try {
			pst = connection.prepareStatement(query);
			pst.setString(1, newPassword);
			pst.setInt(2, custID);
			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public java.sql.Timestamp getTimestampCreated(int custID) {
		java.sql.Timestamp timestamp = null;
		
		connection = new DBConnection().getConnection();

		PreparedStatement pst;
		String query = "SELECT dateCreated FROM accounts WHERE cust_id = ?";
		ResultSet rs = null;

		try {
			pst = connection.prepareStatement(query);
			pst.setInt(1, custID);
			rs = pst.executeQuery();

			while (rs.next())
				timestamp = rs.getTimestamp("dateCreated");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return timestamp;
	}

	public void addProduct(String name, String description, double price, String category, int quantity) {
		connection = new DBConnection().getConnection();

		PreparedStatement pst;
		String query = "INSERT INTO product_details (product_name, product_description, product_price, product_category) VALUES (?, ?, ?, ?)";
		int cat = getCategory(category);

		try {
			pst = connection.prepareStatement(query);
			pst.setString(1, name);
			pst.setString(2, description);
			pst.setDouble(3, price);
			pst.setInt(4, cat);
			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		query = "INSERT INTO inventory (product_id, product_qty) VALUES (?, ?)";
		int prodID = getProductID(name);

		try {
			pst = connection.prepareStatement(query);
			pst.setInt(1, prodID);
			pst.setInt(2, quantity);
			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void editProduct(String name, String description, double price, int category, int quantity, int prodID) {
		connection = new DBConnection().getConnection();

		PreparedStatement pst;
		String query = "UPDATE product_details SET product_name = ?, product_description = ?, product_price = ?, product_category = ? WHERE product_id = ?";

		try {
			pst = connection.prepareStatement(query);
			pst.setString(1, name);
			pst.setString(2, description);
			pst.setDouble(3, price);
			pst.setInt(4, category);
			pst.setInt(5, prodID);
			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		query = "UPDATE inventory SET product_qty = ? WHERE product_id = ?";

		try {
			pst = connection.prepareStatement(query);
			pst.setInt(1, quantity);
			pst.setInt(2, prodID);
			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteProduct(int prodID) {
		connection = new DBConnection().getConnection();

		PreparedStatement pst;
		String query = "DELETE FROM product_details WHERE product_id = ?";

		try {
			pst = connection.prepareStatement(query);
			pst.setInt(1, prodID);
			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<ProductDetails> getProducts() {
		ArrayList<ProductDetails> products = new ArrayList<ProductDetails>();

		connection = new DBConnection().getConnection();

		PreparedStatement pst;
		String query = "SELECT * FROM product_details";
		ResultSet rs = null;

		try {
			pst = connection.prepareStatement(query);
			rs = pst.executeQuery();
			while (rs.next()) {
				int prodID = rs.getInt("product_id");
				String name = rs.getString("product_name");
				String description = rs.getString("product_description");
				double price = rs.getDouble("product_price");
				int category = rs.getInt("product_category");
				ProductDetails pd = new ProductDetails(prodID, name, description, price, category);
				products.add(pd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return products;
	}

	public ArrayList<ProductDetails> getSpecificProduct(String cat) {
		ArrayList<ProductDetails> products = new ArrayList<ProductDetails>();

		connection = new DBConnection().getConnection();

		int prodCat = getCategory(cat);

		PreparedStatement pst;
		String query = "SELECT * FROM product_details WHERE product_category = ?";
		ResultSet rs = null;

		try {
			pst = connection.prepareStatement(query);
			pst.setInt(1, prodCat);
			rs = pst.executeQuery();
			while (rs.next()) {
				int prodID = rs.getInt("product_id");
				String name = rs.getString("product_name");
				String description = rs.getString("product_description");
				double price = rs.getDouble("product_price");
				int category = rs.getInt("product_category");
				ProductDetails pd = new ProductDetails(prodID, name, description, price, category);
				products.add(pd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return products;
	}

	public ProductDetails getOneProduct(int prodID) {
		ProductDetails product = null;

		connection = new DBConnection().getConnection();

		PreparedStatement pst;
		String query = "SELECT * FROM product_details WHERE product_id = ?";
		ResultSet rs = null;

		try {
			pst = connection.prepareStatement(query);
			pst.setInt(1, prodID);
			rs = pst.executeQuery();
			while (rs.next()) {
				String name = rs.getString("product_name");
				String description = rs.getString("product_description");
				double price = rs.getDouble("product_price");
				int category = rs.getInt("product_category");
				product = new ProductDetails(prodID, name, description, price, category);
			}
		} catch (SQLException e) {
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
			while (rs.next())
				productID = rs.getInt("product_id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return productID;
	}

	public String getProductName(int productID) {
		String productName = null;

		connection = new DBConnection().getConnection();

		PreparedStatement pst;
		String query = "SELECT product_name FROM product_details WHERE product_id = ?";
		ResultSet rs = null;

		try {
			pst = connection.prepareStatement(query);
			pst.setInt(1, productID);
			rs = pst.executeQuery();
			while (rs.next())
				productName = rs.getString("product_name");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return productName;
	}

	public int getCategory(String category) {
		int cat = 0;

		connection = new DBConnection().getConnection();

		PreparedStatement pst;
		String query = "SELECT category_id FROM product_category WHERE category_name = ?";
		ResultSet rs = null;

		try {
			pst = connection.prepareStatement(query);
			pst.setString(1, category);
			rs = pst.executeQuery();

			while (rs.next())
				cat = rs.getInt("category_id");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return cat;
	}

	public void addReview(int prodId, int custId, String review, Date date) {
		connection = new DBConnection().getConnection();

		PreparedStatement pst;
		String query = "INSERT INTO reviews (prod_id, cust_id, rev_review, rev_date) VALUES (?, ?, ?, ?)";

		try {
			pst = connection.prepareStatement(query);
			pst.setInt(1, prodId);
			pst.setInt(2, custId);
			pst.setString(3, review);
			pst.setDate(4, date);
			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Review> getAllReviews(int productID) {
		ArrayList<Review> reviews = new ArrayList<Review>();

		connection = new DBConnection().getConnection();

		PreparedStatement pst;
		String query = "SELECT * FROM review WHERE product_det_id = ?";
		ResultSet rs = null;

		try {
			pst = connection.prepareStatement(query);
			pst.setInt(1, productID);
			rs = pst.executeQuery();
			while (rs.next()) {
				int custID = rs.getInt("cust_id");
				String review = rs.getString("comment");
				String date = rs.getDate("date").toString();
				Review r = new Review(custID, productID, review, date);
				reviews.add(r);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return reviews;
	}

	public void addTransaction(int custID, String creditCard, Date date) {
		connection = new DBConnection().getConnection();

		PreparedStatement pst;
		String query = "INSERT INTO transaction (cust_id, credit_num, date) VALUES (?, ?, ?)";

		try {
			pst = connection.prepareStatement(query);
			pst.setInt(1, custID);
			pst.setString(2, creditCard);
			pst.setDate(3, date);
			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addPurchaseHistory(int transactionID, double price, int quantity, int prodID) {
		connection = new DBConnection().getConnection();

		PreparedStatement pst;
		String query = "INSERT INTO purchase_history (transact_id, unit_price, quantity, product_det_id) VALUES (?, ?, ?, ?)";

		try {
			pst = connection.prepareStatement(query);
			pst.setInt(1, transactionID);
			pst.setDouble(2, price);
			pst.setInt(3, quantity);
			pst.setInt(4, prodID);
			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Transaction> getAllTransactions() {
		ArrayList<Transaction> transactions = new ArrayList<Transaction>();

		connection = new DBConnection().getConnection();

		PreparedStatement pst;
		String query = "SELECT * FROM transaction";
		ResultSet rs = null;

		try {
			pst = connection.prepareStatement(query);
			rs = pst.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				int custID = rs.getInt("cust_id");
				String creditcard = rs.getString("credit_num");
				String date = rs.getDate("date").toString();
				Transaction t = new Transaction(id, custID, creditcard, date);
				transactions.add(t);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return transactions;
	}

	public ArrayList<PurchaseHistory> getAllPurchaseHistory() {
		ArrayList<PurchaseHistory> purchaseHistory = new ArrayList<PurchaseHistory>();

		connection = new DBConnection().getConnection();

		PreparedStatement pst;
		String query = "SELECT * FROM purchase_history";
		ResultSet rs = null;

		try {
			pst = connection.prepareStatement(query);
			rs = pst.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				int transactID = rs.getInt("transact_id");
				double unitPrice = rs.getDouble("unit_price");
				int quantity = rs.getInt("quantity");
				int prodID = rs.getInt("product_det_id");
				PurchaseHistory ps = new PurchaseHistory(id, transactID, unitPrice, quantity, prodID);
				purchaseHistory.add(ps);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return purchaseHistory;
	}

	public int getMaxTransactionID() {
		int id = 0;

		PreparedStatement pst;
		String query = "SELECT MAX(id) FROM transaction";
		ResultSet rs = null;

		try {
			pst = connection.prepareStatement(query);
			rs = pst.executeQuery();
			while (rs.next())
				id = rs.getInt("MAX(id)");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return id;
	}

	public void updateInventory(int prodID, int quantity) {
		connection = new DBConnection().getConnection();

		PreparedStatement pst;
		String query = "UPDATE inventory SET prod";

		try {
			pst = connection.prepareStatement(query);
			pst.setInt(1, quantity);
			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}