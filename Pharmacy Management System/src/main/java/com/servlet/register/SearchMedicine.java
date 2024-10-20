package com.servlet.register;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SearchMedicine
 */
@WebServlet("/SearchMedicine")
public class SearchMedicine extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchMedicine() {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("text/html");
    	PrintWriter out = response.getWriter();

    	String medicine = request.getParameter("medicine");

    	try {
    	    Class.forName("com.mysql.cj.jdbc.Driver");
    	    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mms", "root", "lakshmi1112");

    	    String query = "SELECT * FROM medicine WHERE medicine_name = ?";
    	    PreparedStatement ps = con.prepareStatement(query);
    	    ps.setString(1, medicine);

    	    out.print("<style>");
    	    out.print("body {");
    	    out.print("  background-image: url('images/pharm_home_bg.jpeg');");
    	    out.print("  background-size: cover;");
    	    out.print("  background-attachment: fixed;");
    	    out.print("  background-repeat: no-repeat;");
    	    out.print("  background-position: center;");
    	    out.print("  font-family: Arial, sans-serif;");
    	    out.print("}");
    	    out.print("table { width: 75%; border-collapse: collapse; margin: 20px auto; background-color: rgba(255, 255, 255, 0.8); }");
    	    out.print("th, td { border: 1px solid black; padding: 8px; text-align: center; }");
    	    out.print("th { background-color: #f2f2f2; }");
    	    out.print("</style>");

    	    out.print("<table>");
    	    out.print("<caption>Result</caption>");

    	    ResultSet rs = ps.executeQuery();
    	    ResultSetMetaData rsmd = rs.getMetaData();
    	    int totalCol = rsmd.getColumnCount();

    	    out.print("<tr>");
    	    for (int i = 1; i <= totalCol; i++) {
    	        out.print("<th>" + rsmd.getColumnName(i) + "</th>");
    	    }
    	    out.print("</tr>");

    	    while (rs.next()) {
    	        out.print("<tr>");
    	        for (int i = 1; i <= totalCol; i++) {
    	            out.print("<td>" + rs.getString(i) + "</td>");
    	        }
    	        out.print("</tr>");
    	    }

    	    out.print("</table>");

    	    rs.close();
    	    ps.close();
    	    con.close();
    	} catch (Exception e) {
    	    out.print("<p>Error: " + e.getMessage() + "</p>");
    	    e.printStackTrace();
    	} finally {
    	    out.close();
    	}

         
    }
}
