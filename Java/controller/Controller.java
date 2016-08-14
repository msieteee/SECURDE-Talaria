/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.util.Date;
import java.util.regex.Pattern;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.common.cache.Cache;

import models.Account;
import security.Authenticator;
import security.BCrypt;
import security.PasswordStorage;
import security.PasswordStorage.CannotPerformOperationException;
import security.PasswordStorage.InvalidHashException;
import models.ProductDetails;
import models.PurchaseHistory;
import models.Queries;
import models.Review;
import models.Transaction;
import models.TransactionReport;

/**
 *
 * @author berryraindrop
 */
@WebServlet(urlPatterns = { "/Controller", "/StartServlet", "/Authentication", "/Register", "/AddProductManager",
		"/AddAccountManager", "/AddProduct", "/EditProduct", "/ToEditProduct", "/DeleteProduct",
		"/ProductManagerProducts", "/FilterProdMan", "/Products", "/Product", "/Filter", "/Purchase", "/Transaction",
		"/ConfirmTransaction", "/TransactionReport", "/Validation" })
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Controller() {
		super();
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, CannotPerformOperationException, SQLException, InvalidHashException {
		response.setContentType("text/html;charset=UTF-8");

		String path = request.getServletPath();

		Queries queries = new Queries();

		if (path.equals("/StartServlet")) {
			System.out.println("START SERVLET");
			request.getRequestDispatcher("index.jsp").forward(request, response);
		} else if (path.equals("/Authentication")) {
			HttpSession session = request.getSession(false);
			
			Cache<String, Boolean> csrfPreventionSaltCache = (Cache<String, Boolean>) session
					.getAttribute("csrfPreventionSaltCache");
			
			if (session != null)
				session.invalidate();

			Account userLoggedIn = null;
			String clientAddress = null;
			HttpSession userSession = null;

			String username = request.getParameter("user");
			String password = request.getParameter("pass");
			String hashedPassword = queries.getHashedPassword(username);

			if (BCrypt.checkpw(password, hashedPassword)) {
				userLoggedIn = queries.getAuthentication(username, hashedPassword);
				userLoggedIn.setIsLoggedIn(Boolean.TRUE);
			}

			// Create session information
			if (userLoggedIn != null && userLoggedIn.getIsLoggedIn()) {
				// Generate a new session
				request.getSession().invalidate();
				userSession = request.getSession(true);
				
				
				userSession.setAttribute("csrfPreventionSaltCache", csrfPreventionSaltCache);
				System.out.println(csrfPreventionSaltCache);
				
				// Set timeout to 30 minutes
				userSession.setMaxInactiveInterval(1800);
				// Get client IP Address
				clientAddress = request.getRemoteAddr();

				// Save user to session
				userSession.setAttribute("session_user", userLoggedIn);
				userSession.setAttribute("ba_id", queries.getBillingID(userLoggedIn.getCustomerID()));
				userSession.setAttribute("sa_id", queries.getShippingID(userLoggedIn.getCustomerID()));

				int custID = userLoggedIn.getCustomerID();

				queries.updateTimestamp(custID);
				java.util.Date today = new java.util.Date();
				java.sql.Timestamp currentTime = new java.sql.Timestamp(today.getTime());
				java.sql.Timestamp dateCreated = queries.getTimestampCreated(custID);
				long milliseconds1 = dateCreated.getTime();
				long milliseconds2 = currentTime.getTime();
				long diff = milliseconds2 - milliseconds1;
				long diffHours = diff / (60 * 60 * 1000);

				switch (userLoggedIn.getRoleID()) {
				case 1:
//					response.sendRedirect("admin.jsp");
					request.getRequestDispatcher("admin.jsp").forward(request, response);
					break;
				case 2:
					if (queries.getAccountStatus(custID) == 1)
//						response.sendRedirect("ProductManagerProducts");
						request.getRequestDispatcher("ProductManagerProducts").forward(request, response);
					else if (queries.getAccountStatus(custID) == 0)
//						response.sendRedirect("validation.jsp");
						request.getRequestDispatcher("validation.jsp").forward(request, response);
					else if (queries.getAccountStatus(custID) == 0 && diffHours > 24) {
						queries.updateAccountStatus(custID, 2);
//						response.sendRedirect("expired.jsp");
						request.getRequestDispatcher("expire.jsp").forward(request, response);
					}
					break;
				case 3:
					if (queries.getAccountStatus(custID) == 1)
//						response.sendRedirect("TransactionReport");
						request.getRequestDispatcher("TransactionReport").forward(request, response);
					else if (queries.getAccountStatus(custID) == 0)
//						response.sendRedirect("validation.jsp");
						request.getRequestDispatcher("validation.jsp").forward(request, response);
					else if (queries.getAccountStatus(custID) == 0 && diffHours > 24) {
						queries.updateAccountStatus(custID, 2);
//						response.sendRedirect("expired.jsp");
						request.getRequestDispatcher("expired.jsp").forward(request, response);
					}
					break;
				case 4:
					if (queries.getAccountStatus(custID) == 1)
//						response.sendRedirect("Products");
						request.getRequestDispatcher("Products").forward(request, response);
					else if (queries.getAccountStatus(custID) == 2)
//						response.sendRedirect("expired.jsp");
						request.getRequestDispatcher("expired.jsp").forward(request, response);
					break;
				default:
//					response.sendRedirect("index-error.jsp");
					request.getRequestDispatcher("index-error.jsp").forward(request, response);
					break;
				}
			} else {
//				response.sendRedirect("index-error.jsp");
				request.getRequestDispatcher("index-error.jsp").forward(request, response);
			}
		} else if (path.equals("/Register")) {
			String name = null;
			String username = null;
			String password = null;
			String cpassword = null;
			String email = null;
			String hashedPassword = null;
			String bahouseno = null;
			String bastreet = null;
			String basubd = null;
			String bacity = null;
			String bapostal = null;
			String bacountry = null;
			String sahouseno = null;
			String sastreet = null;
			String sasubd = null;
			String sacity = null;
			String sapostal = null;
			String sacountry = null;

			try {
				// Get user information
				name = request.getParameter("name");
				username = request.getParameter("user");
				password = request.getParameter("pass");
				cpassword = request.getParameter("cpass");
				email = request.getParameter("email");

				// Get user billing address
				bahouseno = request.getParameter("bahouseno");
				bastreet = request.getParameter("bastreet");
				basubd = request.getParameter("basubd");
				bacity = request.getParameter("bacity");
				bapostal = request.getParameter("bapostal");
				bacountry = request.getParameter("bacountry");

				// Get user shipping address
				sahouseno = request.getParameter("sahouseno");
				sastreet = request.getParameter("sastreet");
				sasubd = request.getParameter("sasubd");
				sacity = request.getParameter("sacity");
				sapostal = request.getParameter("sapostal");
				sacountry = request.getParameter("sacountry");

				String pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d.*)(?=.*\\W.*)[a-zA-Z0-9\\S]{8,15}$";
				boolean matches = Pattern.matches(pattern, password);

				if (password.equals(cpassword) && matches) {
					hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
				}
				
				queries.addAddress(bahouseno, bastreet, basubd, bacity, bapostal, bacountry);
				queries.addAddress(sahouseno, sastreet, sasubd, sacity, sapostal, sacountry);
				int ba_id = queries.getAddress(bahouseno, bastreet, basubd, bacity, bapostal, bacountry);
				int sa_id = queries.getAddress(sahouseno, sastreet, sasubd, sacity, sapostal, sacountry);
				queries.addCustomerDetails(name, email, ba_id, sa_id);
				int cust_id = queries.getCustID(name);
				int success = queries.addUserAccount(cust_id, username, hashedPassword, 4);
				if (success == 0) { //IF FAIL TO ADD IN ACCOUNT
					queries.deleteCustomerDetails(cust_id);
					if (ba_id == sa_id)
						queries.deleteAddress(ba_id);
					else {
						queries.deleteAddress(ba_id);
						queries.deleteAddress(sa_id);
					}
				}

				request.getSession().setAttribute("ba_id", ba_id);
				request.getSession().setAttribute("sa_id", sa_id);

//				response.sendRedirect("index.jsp");
				request.getRequestDispatcher("index.jsp").forward(request, response);
			} finally {
			}
		} else if (path.equals("/AddProductManager")) {
			String username = null;
			String password = null;
			String cpassword = null;
			String hashedPassword = null;

			username = request.getParameter("user");
			password = request.getParameter("pass");
			cpassword = request.getParameter("cpass");

			if (password.equals(cpassword)) {
				hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
			}

			queries.addProductManager(username, hashedPassword, 2);
			
//			response.sendRedirect("index.jsp");
			request.getRequestDispatcher("index.jsp").forward(request, response);
		} else if (path.equals("/AddAccountManager")) {
			String username = null;
			String password = null;
			String cpassword = null;
			String hashedPassword = null;

			username = request.getParameter("user");
			password = request.getParameter("pass");
			cpassword = request.getParameter("cpass");

			if (password.equals(cpassword)) {
				hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
			}

			queries.addAccountManager(username, hashedPassword, 3);
//			response.sendRedirect("index.jsp");
			request.getRequestDispatcher("index.jsp").forward(request, response);
		} else if (path.equals("/AddProduct")) {
			String name = null;
			String category = null;
			String description = null;
			double unitprice = 0;
			int qty = 0;

			name = request.getParameter("shipadd");
			category = request.getParameter("category");
			description = request.getParameter("description");
			unitprice = Double.parseDouble(request.getParameter("price"));
			qty = Integer.parseInt(request.getParameter("qty"));

			queries.addProduct(name, description, unitprice, category, qty);
			System.out.println("name " + name + ", " + description + ", " + unitprice + ", " + category + ", " + qty);

			ArrayList<ProductDetails> products = new ArrayList<ProductDetails>();
			products = queries.getProducts();

			request.getSession().setAttribute("allProducts", products);

//			response.sendRedirect("product-manager-products.jsp");
			request.getRequestDispatcher("product-manager-products.jsp").forward(request, response);
		} else if (path.equals("/EditProduct")) {
			ArrayList<ProductDetails> products = new ArrayList<ProductDetails>();
			products = queries.getProducts();

			request.getSession().setAttribute("allProducts", products);

			int index = Integer.parseInt(request.getParameter("index"));
			String name = null;
			String category = null;
			String description = null;
			double unitprice = 0;
			int qty = 0;

			name = request.getParameter("shipadd");
			category = request.getParameter("category");
			description = request.getParameter("description");
			unitprice = Double.parseDouble(request.getParameter("price"));
			qty = Integer.parseInt(request.getParameter("qty"));

			int prodID = products.get(index).getProdID();
			System.out.print("update category : " + category);
			int cat = queries.getCategory(category);
			System.out.print("update category int : " + cat);
			queries.editProduct(name, description, unitprice, cat, qty, prodID);

			// response.sendRedirect("product-manager-products.jsp");

			request.getRequestDispatcher("product-manager-products.jsp").forward(request, response);
		} else if (path.equals("/ToEditProduct")) {
			// JANICA HERE
			ArrayList<ProductDetails> products = new ArrayList<ProductDetails>();
			products = queries.getProducts();

			ArrayList<String> category = new ArrayList<String>();
			category.add("Shoes");
			category.add("Sandals");
			category.add("Slippers");
			category.add("Boots");

			int index = Integer.parseInt(request.getParameter("index"));

			request.getSession().setAttribute("product", products.get(index));
			request.getSession().setAttribute("index", index);
			String cat = null;
			System.out.println("CATEGORY " + products.get(index).getCategory());
			switch (products.get(index).getCategory()) {
			case 1:
				cat = "Boots";
				break;
			case 2:
				cat = "Shoes";
				break;
			case 3:
				cat = "Slippers";
				break;
			case 4:
				cat = "Sandals";
				break;
			}
			System.out.println("category is " + cat);
			request.setAttribute("selectedcat", cat);
			request.getSession().setAttribute("categories", category);

			request.getRequestDispatcher("product-manager-productpage.jsp").forward(request, response);
		} else if (path.equals("/DeleteProduct")) {
			int index = Integer.parseInt(request.getParameter("index"));
			ArrayList<ProductDetails> products = new ArrayList<ProductDetails>();
			products = queries.getProducts();

			System.out.println(products.get(index).getProdID());
			queries.deleteProduct(products.get(index).getProdID());

			products = queries.getProducts();
			request.getSession().setAttribute("allProducts", products);
			request.getRequestDispatcher("product-manager-products.jsp").forward(request, response);
		} else if (path.equals("/ProductManagerProducts")) {
			// Account a = (Account)
			// request.getSession().getAttribute("session_user");
			// int status = queries.getAccountStatus(a.getCustomerID());
			//
			// if (status == 1) {
			ArrayList<ProductDetails> products = new ArrayList<ProductDetails>();
			products = queries.getProducts();

			request.getSession().setAttribute("allProducts", products);

			request.getRequestDispatcher("product-manager-products.jsp").forward(request, response);
			// }
		} else if (path.equals("/FilterProdMan")) {
			String cat = request.getParameter("filter");
			//request.getSession().invalidate();
			request.getSession().removeAttribute("allProducts");
			String category = null;
			ArrayList<ProductDetails> products = new ArrayList<ProductDetails>();

			if ("ALL".equals(cat)) {
				products = queries.getProducts();
				System.out.println("FILTER: ALL");
			} else if ("SHOES".equals(cat)) {
				products = queries.getSpecificProduct("Shoes");
				System.out.println("FILTER: SHOES");
			} else if ("SANDALS".equals(cat)) {
				products = queries.getSpecificProduct("Sandals");
				System.out.println("FILTER: SANDALS");
			} else if ("BOOTS".equals(cat)) {
				products = queries.getSpecificProduct("Boots");
				System.out.println("FILTER: BOOTS");
			} else if ("SLIPPERS".equals(cat)) {
				products = queries.getSpecificProduct("Slippers");
				System.out.println("FILTER: SLIPPERS");
			}

			request.getSession().setAttribute("allProducts", products);

			request.getRequestDispatcher("product-manager-products.jsp").forward(request, response);
		} else if (path.equals("/Products")) {
			ArrayList<ProductDetails> products = new ArrayList<ProductDetails>();
			products = queries.getProducts();

			request.getSession().setAttribute("allProducts", products);

			request.getRequestDispatcher("products.jsp").forward(request, response);
		} else if (path.equals("/Product")) {
			int index = Integer.parseInt(request.getParameter("index"));

			ArrayList<ProductDetails> products = new ArrayList<ProductDetails>();
			ArrayList<Review> reviews = new ArrayList<Review>();
			ArrayList<String> customers = new ArrayList<String>();
			ProductDetails pd = new ProductDetails();

			products = (ArrayList<ProductDetails>) request.getSession().getAttribute("allProducts");
			pd = products.get(index);
			String description = pd.getDescription();
			reviews = queries.getAllReviews(pd.getProdID());

			for (int i = 0; i < reviews.size(); i++) {
				int custID = reviews.get(i).getCustID();
				String name = queries.getCustName(custID);
				customers.add(name);
			}

			request.getSession().setAttribute("product", pd);
			request.getSession().setAttribute("description", description);
			request.getSession().setAttribute("reviews", reviews);
			request.getSession().setAttribute("customers", customers);

			request.getRequestDispatcher("productpage.jsp").forward(request, response);
		} else if (path.equals("/Filter")) {
			String cat = request.getParameter("filter");
			//request.getSession().invalidate();
			request.getSession().removeAttribute("allProducts");
			String category = null;
			ArrayList<ProductDetails> products = new ArrayList<ProductDetails>();

			if ("ALL".equals(cat)) {
				products = queries.getProducts();
				System.out.println("FILTER: ALL");
			} else if ("SHOES".equals(cat)) {
				products = queries.getSpecificProduct("Shoes");
				System.out.println("FILTER: SHOES");
			} else if ("SANDALS".equals(cat)) {
				products = queries.getSpecificProduct("Sandals");
				System.out.println("FILTER: SANDALS");
			} else if ("BOOTS".equals(cat)) {
				products = queries.getSpecificProduct("Boots");
				System.out.println("FILTER: BOOTS");
			} else if ("SLIPPERS".equals(cat)) {
				products = queries.getSpecificProduct("Slippers");
				System.out.println("FILTER: SLIPPERS");
			}

			request.getSession().setAttribute("allProducts", products);

			request.getRequestDispatcher("products.jsp").forward(request, response);
		} else if (path.equals("/Purchase")) {
			int prodID = Integer.parseInt(request.getParameter("index"));
			ProductDetails productDetails = queries.getOneProduct(prodID);

			Account userLoggedIn = (Account) request.getSession().getAttribute("session_user");

			int custID = queries.getCustIDFromAccount(userLoggedIn.getUsername());
			String name = queries.getCustName(custID);
			String billad = queries.getCompleteAddress((Integer) request.getSession().getAttribute("ba_id"));
			String shipad = queries.getCompleteAddress((Integer) request.getSession().getAttribute("sa_id"));

			request.getSession().setAttribute("index", prodID);
			request.getSession().setAttribute("name", name);
			request.getSession().setAttribute("billad", billad);
			request.getSession().setAttribute("shipad", shipad);
			request.getSession().setAttribute("pd", productDetails);

			request.getRequestDispatcher("transaction.jsp").forward(request, response);
		} else if (path.equals("/Transaction")) {
			int prodID = (Integer) request.getSession().getAttribute("index");
			ProductDetails productDetails = queries.getOneProduct(prodID);

			String creditcard = request.getParameter("creditcard");
			int quantity = Integer.parseInt(request.getParameter("quantity"));

			StringBuilder sb = new StringBuilder();
			String mask = "xxxx-xxxx-xxxx-####";
			int index = 0;
			for (int i = 0; i < mask.length(); i++) {
				char c = mask.charAt(i);
				if (c == '#') {
					sb.append(creditcard.charAt(index));
					index++;
				} else if (c == 'x') {
					sb.append(c);
					index++;
				} else {
					sb.append(c);
				}
			}

			request.getSession().setAttribute("purchaseProduct", productDetails);
			request.getSession().setAttribute("quantity", quantity);
			request.getSession().setAttribute("creditcard", creditcard);
			request.getSession().setAttribute("maskedCredit", sb.toString());

			request.getRequestDispatcher("confirm-transaction.jsp").forward(request, response);
		} else if (path.equals("/ConfirmTransaction")) {
			Account userLoggedIn = (Account) request.getSession().getAttribute("session_user");

			int custID = queries.getCustIDFromAccount(userLoggedIn.getUsername());
			String creditcard = (String) request.getSession().getAttribute("creditcard");
			java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
			int id = queries.getMaxTransactionID() + 1;
			ProductDetails purchaseProduct = (ProductDetails) request.getSession().getAttribute("purchaseProduct");
			double unitPrice = purchaseProduct.getPrice();
			int quantity = (Integer) request.getSession().getAttribute("quantity");
			int prodID = purchaseProduct.getProdID();
			Account a = (Account) request.getSession().getAttribute("session_user");
			String password = request.getParameter("pass");
			String hashedPassword = queries.getHashedPassword(a.getUsername());

			if (BCrypt.checkpw(password, hashedPassword)) {
				queries.addTransaction(custID, creditcard, date);
				queries.addPurchaseHistory(id, unitPrice, quantity, prodID);

				response.sendRedirect("Products");
			}
		} else if (path.equals("/TransactionReport")) {
			ArrayList<PurchaseHistory> purchaseHistory = new ArrayList<PurchaseHistory>();
			ArrayList<TransactionReport> transactionReport = new ArrayList<TransactionReport>();
			double total = 0;

			purchaseHistory = queries.getAllPurchaseHistory();

			for (int i = 0; i < purchaseHistory.size(); i++) {
				int prodID = purchaseHistory.get(i).getProdID();
				String prodName = queries.getProductName(prodID);
				double unitPrice = purchaseHistory.get(i).getPrice();
				int quantity = purchaseHistory.get(i).getQuantity();
				double totalPrice = unitPrice * quantity;
				transactionReport.add(new TransactionReport(prodName, unitPrice, quantity, totalPrice));
				total += totalPrice;
			}

			request.getSession().setAttribute("purchases", purchaseHistory);
			request.getSession().setAttribute("transactionReport", transactionReport);
			request.getSession().setAttribute("total", total);

			request.getRequestDispatcher("accounting-manager.jsp").forward(request, response);

		} else if (path.equals("/Validation")) {
			Account a = (Account) request.getSession().getAttribute("session_user");

			String newPassword = request.getParameter("newpass");
			String cpassword = request.getParameter("cpass");
			String hashedPassword = null;

			String pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d.*)(?=.*\\W.*)[a-zA-Z0-9\\S]{8,15}$";
			boolean matches = Pattern.matches(pattern, newPassword);

			if (newPassword.equals(cpassword) && matches) {
				hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

				int custID = a.getCustomerID();

				queries.updatePassword(custID, hashedPassword);
				queries.updateAccountStatus(custID, 1);

				if (a.getRoleID() == 2)
//					response.sendRedirect("ProductManagerProducts");
					request.getRequestDispatcher("ProductManagerProducts").forward(request, response);
				else if (a.getRoleID() == 3)
//					response.sendRedirect("TransactionReport");
					request.getRequestDispatcher("TransactionReport").forward(request, response);
			}
		}
	}
	/*
	 * PrintWriter out = response.getWriter(); try { here. You may use following
	 * sample code. out.println("<!DOCTYPE html>"); out.println("<html>");
	 * out.println("<head>"); out.println("<title>Servlet Controller</title>");
	 * out.println("</head>"); out.println("<body>");
	 * out.println("<h1>Servlet Controller at " + request.getContextPath() +
	 * "</h1>"); out.println("</body>"); out.println("</html>"); } finally {
	 * out.close(); }
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
