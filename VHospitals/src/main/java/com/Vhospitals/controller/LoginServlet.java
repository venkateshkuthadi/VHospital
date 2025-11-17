package com.Vhospitals.controller;

import com.Vhospitals.dao.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String role = request.getParameter("role");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (role == null || username == null || password == null ||
                username.isEmpty() || password.isEmpty()) {
            response.sendRedirect("Login.html");
            return;
        }

        String table;
        String sqlUserCheck;
        String sqlPasswordCheck;

        if ("admin".equals(role)) {
            table = "admin";
            sqlUserCheck = "SELECT * FROM " + table + " WHERE username=?";
            sqlPasswordCheck = "SELECT * FROM " + table + " WHERE username=? AND password=?";
        } else {
            table = "doctors";
            sqlUserCheck = "SELECT * FROM " + table + " WHERE doctor_id=?";
            sqlPasswordCheck = "SELECT * FROM " + table + " WHERE doctor_id=? AND password=?";
        }

        try (Connection conn = DBUtil.getConnection()) {

            try (PreparedStatement ps1 = conn.prepareStatement(sqlUserCheck)) {
                ps1.setString(1, username);
                try (ResultSet rs1 = ps1.executeQuery()) {
                    if (!rs1.next()) {
                        // username not found
                        response.getWriter().println("<script>alert('Incorrect username');window.location='Login.html';</script>");
                        return;
                    }
                }
            }

            try (PreparedStatement ps2 = conn.prepareStatement(sqlPasswordCheck)) {
                ps2.setString(1, username);
                ps2.setString(2, password);
                try (ResultSet rs2 = ps2.executeQuery()) {
                    if (rs2.next()) {
                        HttpSession session = request.getSession();
                        if ("admin".equals(role)) {
                            session.setAttribute("username", rs2.getString("username"));
                            session.setAttribute("fullname", rs2.getString("full_name"));
                            session.setAttribute("role", "admin");
                            response.sendRedirect("admin/admindashboard.html");
                        } else {
                            session.setAttribute("doctor_id", rs2.getString("doctor_id"));
                            session.setAttribute("fullname", rs2.getString("full_name"));
                            session.setAttribute("role", "doctor");
                            response.sendRedirect("doctor/appointments.html");
                        }
                    } else {
                        // username correct, password incorrect
                        response.getWriter().println("<script>alert('Incorrect password');window.location='Login.html';</script>");
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("Login.html");
        }
    }
}
