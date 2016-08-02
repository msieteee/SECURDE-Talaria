package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.PasswordStorage.CannotPerformOperationException;
import models.ProductDetails;
import models.Queries;

/**
 * Servlet implementation class Register
 */
//@WebServlet("/Products")
public class Products extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, CannotPerformOperationException {
        response.setContentType("text/html;charset=UTF-8");
        
        Queries queries = new Queries();
        ArrayList<ProductDetails> products = new ArrayList<ProductDetails> ();
        products = queries.getProdcuts();
        
        request.setAttribute("products", products);
        request.getRequestDispatcher("products.jsp").forward(request, response);
        
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
