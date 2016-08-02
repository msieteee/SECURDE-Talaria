
package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Account;
import models.PasswordStorage;
import security.Authenticator;

/**
 * Servlet implementation class Authentication
 */
// @WebServlet(name = "/Authentication", urlPatterns = {"/Authentication"})
public class Authentication extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected synchronized void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException, PasswordStorage.CannotPerformOperationException,
			PasswordStorage.InvalidHashException {
		response.setContentType("text/html;charset=UTF-8");
		
		Account userLoggedIn = null;
		// String clientSalt = null;
		// String clientToken = null;
		// Cookie clientCookie = null;
		String clientAddress = null;
		HttpSession userSession = null;
		// PrintWriter out = response.getWriter();
		// AccessManager accessSession = null;
		// Hasher hashGenerator = new Hasher(Hasher.SHA256);
		// Randomizer randomGenerator = new Randomizer(1234);

		// Log in and return user details
		Authenticator authenticator = new Authenticator();

		userLoggedIn = (Account) authenticator.login(request, response);

		//System.out.println("IN AUTHENTICATION: " + userLoggedIn.getUsername());

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

			// Save access manager to session
			// userSession.setAttribute("session_access", accessSession);
			// loggedInUser.addSession(userSession);
			// Redirect to main page
			// response.sendRedirect("Extract");
			System.out.println(userLoggedIn.getUsername());
			System.out.println("YEY LOGGED IN!!!!!");
			response.sendRedirect("\\Products");
		} else {
			// Get client remote address
			// Get
			// Redirect to log in page
			response.sendRedirect("index.jsp");
			System.out.println("YEY NOT LOGGED IN!!!!!");
		}

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at:
		// ").append(request.getContextPath());
		try {
			processRequest(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PasswordStorage.CannotPerformOperationException ex) {
			Logger.getLogger(Authentication.class.getName()).log(Level.SEVERE, null, ex);
		} catch (PasswordStorage.InvalidHashException ex) {
			Logger.getLogger(Authentication.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			processRequest(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PasswordStorage.CannotPerformOperationException ex) {
			Logger.getLogger(Authentication.class.getName()).log(Level.SEVERE, null, ex);
		} catch (PasswordStorage.InvalidHashException ex) {
			Logger.getLogger(Authentication.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}
