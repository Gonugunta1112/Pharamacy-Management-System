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

@WebServlet("/issueMedicine")
public class IssueMedicineServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // SQL query to insert record into the ISSUE table
    private static final String INSERT_QUERY = "INSERT INTO ISSUE (MOBILE, MEDICINE_NAME, QUANTITY, PRICE, DATE_OF_PURCHASE) VALUES(?,?,?,?,?)";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // Set response content type
        res.setContentType("text/plain");
        PrintWriter pw = res.getWriter();

        // Read form fields
        String mobile = req.getParameter("mobile");
        String name = req.getParameter("name");
        String quantity = req.getParameter("quantity");
        String price = req.getParameter("price");
        String date_of_purchase = req.getParameter("date_of_purchase");

        // Load JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Updated driver class name
        } catch (ClassNotFoundException e) {
            pw.write("Error: JDBC Driver not found");
            e.printStackTrace();
            return;
        }

        // Create the connection and execute the query
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mms", "root", "lakshmi1112");
             PreparedStatement ps = con.prepareStatement(INSERT_QUERY)) {

            // Set query parameters
            ps.setString(1, mobile);
            ps.setString(2, name);
            ps.setInt(3, Integer.parseInt(quantity));
            ps.setDouble(4, Double.parseDouble(price));
            ps.setDate(5, java.sql.Date.valueOf(date_of_purchase));

            // Execute the query
            int count = ps.executeUpdate();
            
            if (count > 0) {
                pw.write("Success: Record stored into database");
            } else {
                pw.write("Error: Record not stored into database");
            }

        } catch (SQLException se) {
            pw.write("Error: " + se.getMessage());
            se.printStackTrace();
        } catch (NumberFormatException e) {
            pw.write("Error: Invalid number format");
            e.printStackTrace();
        } catch (Exception e) {
            pw.write("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Close the stream
            pw.close();
        }
    }
}
