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

@WebServlet("/register")
public class CustomerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// Create the query
	private static final String INSERT_QUERY = "INSERT INTO CUSTOMER (MOBILE, CUSTOMER_NAME, AGE) VALUES(?,?,?)";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// Set Content type
		res.setContentType("text/plain");
		
		// Read the form values
		String name = req.getParameter("name");
		String age = req.getParameter("age");
		String mobile = req.getParameter("mobile");

		// Load the JDBC driver
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			res.getWriter().write("Driver Error: " + e.getMessage());
			return;
		}

		// Create the connection
		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mms", "root", "lakshmi1112");
				PreparedStatement ps = con.prepareStatement(INSERT_QUERY);) {
			// Set the values
			ps.setString(1, mobile);
			ps.setString(2, name);
			ps.setString(3, age);

			// Execute the query
			int count = ps.executeUpdate();
			if (count == 0) {
				res.getWriter().write("Error: Record not stored into Database");
			} else {
				res.getWriter().write("Success: Record Stored into Database");
			}

		} catch (SQLException se) {
			res.getWriter().write("SQL Error: " + se.getMessage());
		} catch (Exception e) {
			res.getWriter().write("Error: " + e.getMessage());
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
