package com.Vhospitals.controller;



import java.io.IOException;
import java.util.Collections; // New import for sorting
import java.util.Comparator;  // New import for sorting
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Vhospitals.dao.DAOImpl;
import com.Vhospitals.entity.Appointment;
import com.google.gson.Gson; 


@WebServlet("/HistoryServlet")
public class HistoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get DAO instance
        DAOImpl dao = DAOImpl.getInstance();
        List<Appointment> appointments = dao.getAllAppointments();

        // --- START: Sorting Logic Added ---
        // Sort the list to display the most recently created appointments (highest ID) last.
        // NOTE: This assumes your 'Appointment' model has a public method 'getId()' 
        // that returns a comparable ID (e.g., int or long) which reflects creation order.
        Collections.sort(appointments, new Comparator<Appointment>() {
            @Override
            public int compare(Appointment a1, Appointment a2) {
                // Using Integer.compare for safe comparison of IDs. 
                // Ascending order (a1.getId() - a2.getId()) puts the oldest appointment first,
                // and the newest appointment (highest ID) last, as requested by the user.
                return Integer.compare(a1.getId(), a2.getId());
            }
        });
        // --- END: Sorting Logic Added ---

        // Convert the sorted list of appointments to JSON
        Gson gson = new Gson();
        String jsonAppointments = gson.toJson(appointments);

        // Set response headers
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Write JSON data to the response
        response.getWriter().write(jsonAppointments);
    }
}
