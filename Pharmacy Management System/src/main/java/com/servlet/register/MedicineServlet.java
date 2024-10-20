package com.servlet.register;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/addMedicine")
public class MedicineServlet extends HttpServlet {
   

	private static final long serialVersionUID = 1L;
	
	//create the query
	private static final String INSERT_QUERY = "INSERT INTO MEDICINE (MEDICINE_NAME, EXPIRY_DATE, QUANTITY, PRICE) VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE QUANTITY = QUANTITY + VALUES(QUANTITY)";


	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
       //get PrintWriter
		PrintWriter pw = res.getWriter();
		//set Content type
		res.setContentType("text/html");
		//read the form values
		String name = req.getParameter("name");
		String expiry = req.getParameter("expiry");
		String quantity = req.getParameter("quantity");
		String price = req.getParameter("price");
		
		//load the jdbc driver
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//create the connection
		try(Connection con = DriverManager.getConnection("jdbc:mysql:///mms","root","lakshmi1112");
				PreparedStatement ps = con.prepareStatement(INSERT_QUERY);){
			//set the values
			ps.setString(1, name);
			ps.setString(2, expiry);
			ps.setString(3, quantity);
			ps.setString(4, price);
			
			//execute the query
			int count = ps.executeUpdate();
			
			if(count==0) {
				pw.println("Record not stored into Database");
			}
			else {
				pw.println("Record Stored into Database");
			}
			
		}
		catch(SQLException se) {
			pw.println(se.getMessage());
			se.printStackTrace();
		}
		catch(Exception e) {
			pw.println(e.getMessage());
			e.printStackTrace();
		}
				
		
		
		//close the stream
		pw.close();
		
    }  
	
	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(req, resp);
    }
}



