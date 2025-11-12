package com.Vhospitals.entity;

public class Appointment {
    private int id;
    private String doctorName;
    private String doctorId; // New field
    private String patientName; // New field
    private String appointmentDate;
    private String appointmentTime;
    private String status;

    // Constructors
    public Appointment() {
    }

    public Appointment(int id, String doctorName, String doctorId, String patientName, String appointmentDate, String appointmentTime, String status) {
        this.id = id;
        this.doctorName = doctorName;
        this.doctorId = doctorId;
        this.patientName = patientName;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.status = status;
    }

    // Static factory method to create an Appointment object
    public static Appointment createAppointment(String doctorName, String doctorId, String patientName, String appointmentDate, String appointmentTime, String status) {
        Appointment newAppointment = new Appointment();
        newAppointment.setDoctorName(doctorName);
        newAppointment.setDoctorId(doctorId);
        newAppointment.setPatientName(patientName);
        newAppointment.setAppointmentDate(appointmentDate);
        newAppointment.setAppointmentTime(appointmentTime);
        newAppointment.setStatus(status);
        return newAppointment;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Appointment{" +
               "id=" + id +
               ", doctorName='" + doctorName + '\'' +
               ", doctorId='" + doctorId + '\'' +
               ", patientName='" + patientName + '\'' +
               ", appointmentDate='" + appointmentDate + '\'' +
               ", appointmentTime='" + appointmentTime + '\'' +
               ", status='" + status + '\'' +
               '}';
    }
}