package com.Vhospitals.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.Vhospitals.entity.Appointment;


public class DAOImpl {

    private static DAOImpl instance = null;
    private Connection con;

    public DAOImpl() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/vtalent", "root", "Root");
            System.out.println("✅ Connected to database successfully");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error connecting to database: " + e.getMessage());
            con = null;
        }
    }

    public static DAOImpl getInstance() {
        if (instance == null) {
            synchronized (DAOImpl.class) {
                if (instance == null) {
                    instance = new DAOImpl();
                }
            }
        }
        return instance;
    }

    public boolean insertAppointment(Appointment appointment) {
        boolean success = false;
        if (con == null) {
            System.err.println("Database connection not established. Cannot insert appointment.");
            return false;
        }
        try {
            // Updated SQL query to include doctor_id and patient_name
            String sqlQuery = "INSERT INTO appointments (doctor_name, doctor_id, patient_name, appointment_date, appointment_time, status) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, appointment.getDoctorName());
            ps.setString(2, appointment.getDoctorId()); // New
            ps.setString(3, appointment.getPatientName()); // New
            ps.setString(4, appointment.getAppointmentDate());
            ps.setString(5, appointment.getAppointmentTime());
            ps.setString(6, appointment.getStatus()); // 'Confirmed'

            int rows = ps.executeUpdate();
            if (rows > 0) {
                success = true;
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    appointment.setId(rs.getInt(1));
                }
                System.out.println("✅ Appointment inserted successfully: Doctor=" + appointment.getDoctorName() + ", Patient=" + appointment.getPatientName() + ", Date=" + appointment.getAppointmentDate() + ", ID=" + appointment.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error inserting appointment: " + e.getMessage());
        }
        return success;
    }

    public boolean deleteAppointment(int id) {
        boolean deleted = false;
        if (con == null) {
            System.err.println("Database connection not established. Cannot delete appointment.");
            return false;
        }
        try {
            String sqlQuery = "DELETE FROM appointments8 WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sqlQuery);
            ps.setInt(1, id);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                deleted = true;
                System.out.println("🗑 Appointment deleted successfully: ID=" + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error deleting appointment: " + e.getMessage());
        }
        return deleted;
    }

    public List<Appointment> getAllAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        if (con == null) {
            System.err.println("Database connection not established. Cannot fetch appointments.");
            return appointments;
        }
        try {
            // Updated SQL query to select doctor_id and patient_name
            String sqlQuery = "SELECT id, doctor_name, doctor_id, patient_name, appointment_date, appointment_time, status FROM appointments8 ORDER BY appointment_date DESC, appointment_time DESC";
            PreparedStatement ps = con.prepareStatement(sqlQuery);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Appointment appt = new Appointment();
                appt.setId(rs.getInt("id"));
                appt.setDoctorName(rs.getString("doctor_name"));
                appt.setDoctorId(rs.getString("doctor_id")); // New
                appt.setPatientName(rs.getString("patient_name")); // New
                appt.setAppointmentDate(rs.getString("appointment_date"));
                appt.setAppointmentTime(rs.getString("appointment_time"));
                appt.setStatus(rs.getString("status"));
                appointments.add(appt);
            }
            System.out.println("✅ Fetched " + appointments.size() + " appointments from DB.");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error fetching appointments: " + e.getMessage());
        }
        return appointments;
    }

    public boolean updateAppointmentStatus(int id, String newStatus) {
        boolean updated = false;
        if (con == null) {
            System.err.println("Database connection not established. Cannot update appointment status.");
            return false;
        }
        try {
            String sqlQuery = "UPDATE appointments8 SET status = ? WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sqlQuery);
            ps.setString(1, newStatus);
            ps.setInt(2, id);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                updated = true;
                System.out.println("✅ Appointment status updated to '" + newStatus + "' for ID: " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error updating appointment status: " + e.getMessage());
        }
        return updated;
    }

    public List<String> getBookedTimes(String doctorName, String appointmentDate) {
        List<String> bookedTimes = new ArrayList<>();
        if (con == null) {
            System.err.println("Database connection not established. Cannot fetch booked times.");
            return bookedTimes;
        }
        try {
            String sqlQuery = "SELECT appointment_time FROM appointments8 WHERE doctor_name = ? AND appointment_date = ? AND status = 'Confirmed'";
            PreparedStatement ps = con.prepareStatement(sqlQuery);
            ps.setString(1, doctorName);
            ps.setString(2, appointmentDate);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                bookedTimes.add(rs.getString("appointment_time"));
            }
            System.out.println("✅ Fetched " + bookedTimes.size() + " confirmed booked times for Doctor=" + doctorName + ", Date=" + appointmentDate);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error fetching booked times: " + e.getMessage());
        }
        return bookedTimes;
    }

    public static void main(String[] args) {
        DAOImpl dao = DAOImpl.getInstance();
        // You can add test cases here if needed
    }
}