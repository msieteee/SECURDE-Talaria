package servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.PasswordStorage;
import models.PasswordStorage.CannotPerformOperationException;
import models.Queries;

/**
 * Servlet implementation class Register
 */
//@WebServlet("/Register")
public class Register extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, CannotPerformOperationException {
        response.setContentType("text/html;charset=UTF-8");

        Queries queries = new Queries();
        PasswordStorage passwordStorage = new PasswordStorage();

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
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        //response.getWriter().append("Served at: ").append(request.getContextPath());
        try {
            processRequest(request, response);
        } catch (CannotPerformOperationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated catch block
        try {
            processRequest(request, response);
        } catch (CannotPerformOperationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
