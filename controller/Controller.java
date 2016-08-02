/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Account;
import models.PasswordStorage;
import models.PasswordStorage.CannotPerformOperationException;
import models.PasswordStorage.InvalidHashException;
import security.Authenticator;
import models.ProductDetails;
import models.Queries;

/**
 *
 * @author berryraindrop
 */
@WebServlet(urlPatterns = {"/Controller", "/Products", "/Product", "/Register", "/Authentication"})
public class Controller extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public Controller() {
		super();
		// TODO Auto-generated constructor stub
	}

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, CannotPerformOperationException, SQLException, InvalidHashException {
        response.setContentType("text/html;charset=UTF-8");
        
        String path = request.getServletPath();
        
        Queries queries = new Queries();
        PasswordStorage passwordStorage = new PasswordStorage();
        
        if(path.equals("/Products")) {
            ArrayList<ProductDetails> products = new ArrayList<ProductDetails> ();
            products = queries.getProdcuts();
            
            request.setAttribute("products", products);
            request.getRequestDispatcher("products.jsp").forward(request, response);
        }else if(path.equals("/Product")) {
            ProductDetails product = new ProductDetails();
            System.out.println(request.getParameter("on-click"));
            // = queries.getOneProduct(path)
            request.getRequestDispatcher("productpage.jsp").forward(request, response);
        }else if(path.equals("/Register")) {
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
                //Get user information
                name = request.getParameter("name");
                username = request.getParameter("user");
                password = request.getParameter("pass");
                cpassword = request.getParameter("cpass");
                email = request.getParameter("email");
                
                //Get user billing address
                bahouseno = request.getParameter("bahouseno");
                bastreet = request.getParameter("bastreet");
                basubd = request.getParameter("basubd");
                bacity = request.getParameter("bacity");
                bapostal = request.getParameter("bapostal");
                bacountry = request.getParameter("bacountry");
                
                //Get user shipping address
                sahouseno = request.getParameter("sahouseno");
                sastreet = request.getParameter("sastreet");
                sasubd = request.getParameter("sasubd");
                sacity = request.getParameter("sacity");
                sapostal = request.getParameter("sapostal");
                sacountry = request.getParameter("sacountry");

                if(password.equals(cpassword)) {
                	hashedPassword = passwordStorage.createHash(password);
                }
                
                //Create new user
                queries.addAddress(bahouseno, bastreet, basubd, bacity, bapostal, bacountry);
                queries.addAddress(sahouseno, sastreet, sasubd, sacity, sapostal, sacountry);
                int ba_id = queries.getAddress(bahouseno, bastreet, basubd, bacity, bapostal, bacountry);
                int sa_id = queries.getAddress(sahouseno, sastreet, sasubd, sacity, sapostal, sacountry);
                queries.addCustomerDetails(name, email, ba_id, sa_id);
                int cust_id = queries.getCustID(name);
                queries.addUserAccount(cust_id, username, hashedPassword, "customer");
                
                //Redirect to login page
                response.sendRedirect("index.jsp");
            } catch (SQLException ex) {
                //Logger.getLogger(Extract.class.getName()).log(Level.SEVERE, null, ex);
                //Log this properly
            } finally {
            }
        }else if(path.equals("/Authentication")) {
        	Account userLoggedIn = null;
    		String clientAddress = null;
    		HttpSession userSession = null;
    		// Log in and return user details
    		Authenticator authenticator = new Authenticator();

    		userLoggedIn = (Account) authenticator.login(request, response);

    		// Create session information
    		if (userLoggedIn != null && userLoggedIn.getIsLoggedIn()) {
    			// Generate a new session
    			request.getSession().invalidate();
    			userSession = request.getSession(true);
    			// Set timeout to 30 minutes
    			userSession.setMaxInactiveInterval(1800);
    			// Get client IP Address
    			clientAddress = request.getRemoteAddr();

    			// Save user to session
    			userSession.setAttribute("session_user", userLoggedIn);

    			System.out.println(userLoggedIn.getUsername());
    			response.sendRedirect("Products");
    		} else {
    			response.sendRedirect("index.jsp");
    		}
        }else if(path.equals("/AddProductManager")) {
            String username = null;
            String password = null;
            String cpassword = null;
            String hashedPassword = null;

            username = request.getParameter("user");
            password = request.getParameter("pass");
            cpassword = request.getParameter("cpass");
            
            if(password.equals(cpassword)) {
                hashedPassword = passwordStorage.createHash(password);
            }
            
            queries.addProductManager(username, hashedPassword, 2);
            response.sendRedirect("index.jsp");
        }else if(path.equals("/AddAccountManager")) {
            String username = null;
            String password = null;
            String cpassword = null;
            String hashedPassword = null;

            username = request.getParameter("user");
            password = request.getParameter("pass");
            cpassword = request.getParameter("cpass");
            
            if(password.equals(cpassword)) {
                hashedPassword = passwordStorage.createHash(password);
            }
            
            queries.addAccountManager(username, hashedPassword, 3);
            response.sendRedirect("index.jsp");
        }
    }
        /*PrintWriter out = response.getWriter();
        try {
            // TODO output your page here. You may use following sample code. 
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Controller</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Controller at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }*/

    protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			processRequest(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
			processRequest(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
