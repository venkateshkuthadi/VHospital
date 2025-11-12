package com.Vhospitals.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Vhospitals.dao.DAOImpl;
import com.Vhospitals.entity.Appointment;


@WebServlet("/BookAppointmntaServlet")
public class BookAppointmntaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String doctorFullName = request.getParameter("doctorFullName");
        String doctorId = request.getParameter("doctorId"); // Get doctorId from the hidden input
        String patientName = request.getParameter("patientName"); // New: Get patient name
        String appointmentDate = request.getParameter("appointmentDate");
        String appointmentTime = request.getParameter("appointmentTime");

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        if (doctorFullName == null || doctorFullName.isEmpty() ||
            doctorId == null || doctorId.isEmpty() || // Validate doctorId
            patientName == null || patientName.isEmpty() || // Validate patientName
            appointmentDate == null || appointmentDate.isEmpty() ||
            appointmentTime == null || appointmentTime.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("Missing appointment details. Please go back and fill all required fields (Doctor, Patient Name, Date, Time).");
            System.err.println("Booking attempt failed: Missing parameters - Doctor Full Name: " + doctorFullName + ", Doctor ID: " + doctorId + ", Patient Name: " + patientName + ", Date: " + appointmentDate + ", Time: " + appointmentTime);
            return;
        }

        DAOImpl dao = DAOImpl.getInstance();

        // Create an Appointment object with the new fields
        Appointment newAppointment = Appointment.createAppointment(doctorFullName, doctorId, patientName, appointmentDate, appointmentTime, "Confirmed");

        boolean isInserted = dao.insertAppointment(newAppointment);

        if (isInserted) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println("Appointment booked successfully! Your appointment ID is: " + newAppointment.getId());
            System.out.println("Appointment booked successfully for Doctor: " + doctorFullName + " (ID: " + doctorId + "), Patient: " + patientName + " on " + appointmentDate + " at " + appointmentTime);
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Failed to book appointment due to a server error. Please try again later.");
            System.err.println("Failed to insert appointment into DB for Doctor: " + doctorFullName + " (ID: " + doctorId + "), Patient: " + patientName + " on " + appointmentDate + " at " + appointmentTime);
        }
    }
}