package com.example.projet;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseDAO {
    private Connection connection = null;

    public DatabaseDAO() throws SQLException {
        // Initialiser la connexion à la base de données
        String url = "jdbc:mysql://localhost:3306/projet";
        String username = "root";
        String password = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();

        try {

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM clients ORDER BY nom  ASC");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");

                Client client = new Client(id, nom);
                clients.add(client);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clients;
    }

    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM employees ORDER BY nom   ASC");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");

                Employee employee = new Employee(id, nom);
                employees.add(employee);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employees;
    }

    public List<Appointment> getAllAppointments() {
        List<Appointment> appointments = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM `appointments` ORDER BY `appointments`.`appointment_date` ASC ");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int clientId = resultSet.getInt("client_id");
                int employeeId = resultSet.getInt("employee_id");
                Timestamp appointmentTimestamp = resultSet.getTimestamp("appointment_date");

                Client client = getClientById(clientId);
                Employee employee = getEmployeeById(employeeId);

                // Conversion de la timestamp en objet Date
                Date appointmentDate = new Date(appointmentTimestamp.getTime());

                // Formatage de la date et de l'heure
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                String formattedDate = dateFormat.format(appointmentDate);
                String formattedTime = timeFormat.format(appointmentDate);

                Appointment appointment = new Appointment(id, client, employee, formattedDate+' '+ formattedTime);
                appointments.add(appointment);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointments;
    }


    public Client getClientById(int clientId) {
        Client client = null;

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM clients WHERE id = ?");
            statement.setInt(1, clientId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");

                client = new Client(id, nom);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return client;
    }

    public Employee getEmployeeById(int employeeId) {
        Employee employee = null;

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM employees WHERE id = ?");
            statement.setInt(1, employeeId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");

                employee = new Employee(id, nom);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employee;
    }


    public void addAppointment(int clientId, int employeeId, String appointmentDate) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO appointments (client_id, employee_id, appointment_date) VALUES (?, ?, ?)");
            statement.setInt(1, clientId);
            statement.setInt(2, employeeId);
            statement.setString(3, appointmentDate);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateAppointment(int appointmentId, int clientId, int employeeId, String appointmentDate) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE appointments SET client_id=?, employee_id=?, appointment_date=? WHERE id=?");
            statement.setInt(1, clientId);
            statement.setInt(2, employeeId);
            statement.setString(3, appointmentDate);
            statement.setInt(4, appointmentId);
            System.out.println(statement);

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
