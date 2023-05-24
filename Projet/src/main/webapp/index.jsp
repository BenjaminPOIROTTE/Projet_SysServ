<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.projet.DatabaseDAO" %>
<%@ page import="com.example.projet.Client" %>
<%@ page import="com.example.projet.Employee" %>
<%@ page import="com.example.projet.Appointment" %>

<%
    DatabaseDAO databaseDAO = new DatabaseDAO();
    List<Client> clients = databaseDAO.getAllClients();
    List<Employee> employees = databaseDAO.getAllEmployees();
    List<Appointment> appointments = databaseDAO.getAllAppointments();
%>


<%
    if (request.getParameter("action") != null) {
        if (request.getParameter("action").equals("Ajouter")) {
            int clientId = Integer.parseInt(request.getParameter("client"));
            int employeeId = Integer.parseInt(request.getParameter("employee"));
            String appointmentDate = request.getParameter("date") + " " + request.getParameter("heure");

            databaseDAO.addAppointment(clientId, employeeId, appointmentDate);
        }
        /* NE FONCTIONNE PAS
        if (request.getParameter("action").equals("Modifier")) {

            int appointmentId = Integer.parseInt(request.getParameter("selectedAppointment"));
            int clientId = Integer.parseInt(request.getParameter("client"));
            int employeeId = Integer.parseInt(request.getParameter("employee"));
            String appointmentDate = request.getParameter("date") + " " + request.getParameter("heure");

            databaseDAO.updateAppointment(999, clientId, employeeId, appointmentDate);
        }*/
    }
%>


<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1>Réservation</h1>

<form action="" method="post">
    <label for="client">Client :</label>
    <select name="client" id="client">
        <% for (Client client : clients) { %>
        <option value="<%= client.getId() %>"><%= client.getNom() %></option>
        <% } %>
    </select>

    <br><br>

    <label for="employee">Employé :</label>
    <select name="employee" id="employee">
        <% for (Employee employee : employees) { %>
        <option value="<%= employee.getId() %>"><%= employee.getNom() %></option>
        <% } %>
    </select>


    <br><br>

    <label for="date">Date :</label>
    <input type="date" name="date" id="date">

    <br><br>

    <label for="heure">Heure :</label>
    <select name="heure" id="heure">
        <option value="9">09:00</option>
        <option value="10">10:00</option>
        <option value="11">11:00</option>
        <!-- Ajoutez d'autres options selon vos besoins -->
    </select>

    <br><br>

    <input type="submit" name="action" value="Ajouter">


</form>

<h2>Liste des rendez-vous :</h2>
<form action="" method="post">
    <select name="selectedAppointment">
        <% for (Appointment appointment : appointments) { %>
        <option value="<%= appointment.getId() %>"><%= appointment.getClient().getNom() %>, <%= appointment.getEmployee().getNom() %>, <%= appointment.getAppointmentDate() %> </option>
        <% } %>
    </select>
    <input type="submit" name="action" value="Modifier">
</form>

</body>
</html>
