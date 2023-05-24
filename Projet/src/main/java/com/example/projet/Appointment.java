package com.example.projet;

import java.util.Date;

public class Appointment {
    private int id;
    private Client client;
    private Employee employee;
    private String appointmentDate; // Modification du type à String pour inclure date et heure

    public Appointment(int id, Client client, Employee employee, String appointmentDate) {
        this.id = id;
        this.client = client;
        this.employee = employee;
        this.appointmentDate = appointmentDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }
}
