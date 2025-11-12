package com.Vhospitals.controller;



import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Vhospitals.dao.DAOImpl;
// Make sure Gson is in your pom.xml
import com.google.gson.Gson;


@WebServlet("/GetAvailableSlotsServlet")
public class GetAvailableSlotsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get parameters from the AJAX request
        String doctorFullName = request.getParameter("doctorFullName");
        String appointmentDate = request.getParameter("appointmentDate");

        // Set response headers for JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();

        // Basic input validation
        if (doctorFullName == null || doctorFullName.isEmpty() ||
            appointmentDate == null || appointmentDate.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(gson.toJson("Error: Missing doctor or date parameter."));
            System.err.println("GetAvailableSlotsServlet: Missing parameters. Doctor: " + doctorFullName + ", Date: " + appointmentDate);
            return;
        }

        try {
            // Get DAO instance and fetch booked times
            DAOImpl dao = DAOImpl.getInstance();
            List<String> bookedTimes = dao.getBookedTimes(doctorFullName, appointmentDate);

            // Send the list of booked times as JSON
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(gson.toJson(bookedTimes));
            System.out.println("GetAvailableSlotsServlet: Sent " + bookedTimes.size() + " booked times for " + doctorFullName + " on " + appointmentDate);

        } catch (Exception e) {
            // Log and send error response
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(gson.toJson("An unexpected error occurred: " + e.getMessage()));
            System.err.println("GetAvailableSlotsServlet: Error fetching slots for Doctor: " + doctorFullName + ", Date: " + appointmentDate + " - " + e.getMessage());
        }
    }
}
