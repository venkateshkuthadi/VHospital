package com.Vhospitals.controller;



import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Vhospitals.dao.DAOImpl;


@WebServlet("/CancelAppointmentServlet")
public class CancelAppointmentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String appointmentIdStr = request.getParameter("appointmentId");

        response.setContentType("text/plain"); 

        if (appointmentIdStr == null || appointmentIdStr.isEmpty()) {
            response.getWriter().write("Error: Appointment ID is missing.");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); 
            return;
        }

        try {
            int appointmentId = Integer.parseInt(appointmentIdStr);
            DAOImpl dao = DAOImpl.getInstance();
            boolean success = dao.updateAppointmentStatus(appointmentId, "Canceled"); 

            if (success) {
                response.getWriter().write("Appointment ID " + appointmentId + " has been canceled.");
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.getWriter().write("Failed to cancel appointment ID " + appointmentId + ". It might already be canceled or not exist.");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); 
            }
        } catch (NumberFormatException e) {
            response.getWriter().write("Error: Invalid Appointment ID format.");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); 
            e.printStackTrace();
        } catch (Exception e) {
            response.getWriter().write("An unexpected error occurred during cancellation: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); 
            e.printStackTrace();
        }
    }
}
