package login;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.CustomerBean;
import beans.ItemBean;
import model.DataManager;
import model.Validation;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataManager dataManager;

    /** @see HttpServlet#HttpServlet() */
    public LoginServlet() {
        super();
    }
    
    @Override
    public void init(ServletConfig config) throws ServletException {
    	super.init();
    	dataManager = new DataManager(
    							config.getInitParameter("jdbcDriver"),
    							config.getInitParameter("dbURL"), 
    							config.getInitParameter("dbUserName"), 
    							config.getInitParameter("dbPassword"));
    }
    
	/** @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response) */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String id = request.getParameter("id");
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        
        System.out.println("username: " + userName);
        System.out.println("password: " + password);

        if(dataManager.validate(userName, password)) {
        	RequestDispatcher dispatcher = request.getRequestDispatcher("/welcomePage.jsp");
        	dispatcher.forward(request, response);
        } else {
            out.println("Validation failed");
        }
	}
	
}
