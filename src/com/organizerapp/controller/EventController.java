package com.organizerapp.controller;

import com.organizerapp.model.Contact;
import com.organizerapp.model.Event;
import com.organizerapp.model.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EventController {
    private Connection connection;
    private List<Event> events;

    public EventController() {
        // Użyj klasy DatabaseConnection do uzyskania połączenia z bazą danych
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            System.err.println("Error connecting to database: " + e.getMessage());
        }
    }

    public void addEvent(Event event) {
        String insertSQL = "INSERT INTO events (name, description, date, category) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, event.getName());
            preparedStatement.setString(2, event.getDescription());
            preparedStatement.setTimestamp(3, new java.sql.Timestamp(event.getDate().getTime()));
            preparedStatement.setString(4, event.getCategory());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                event.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            System.err.println("Error adding event to database: " + e.getMessage());
        }
    }

    public void editEvent(Event oldEvent, Event newEvent) {
        String updateSQL = "UPDATE events SET name = ?, description = ?, date = ?, category = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);
            preparedStatement.setString(1, newEvent.getName());
            preparedStatement.setString(2, newEvent.getDescription());
            preparedStatement.setTimestamp(3, new java.sql.Timestamp(newEvent.getDate().getTime()));
            preparedStatement.setString(4, newEvent.getCategory());
            preparedStatement.setInt(5, oldEvent.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating event in database: " + e.getMessage());
        }
    }

    public void removeEvent(Event event) {
        String deleteSQL = "DELETE FROM events WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL);
            preparedStatement.setInt(1, event.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting event from database: " + e.getMessage());
        }
    }

    public List<Event> getEvents() {
        List<Event> events = new ArrayList<>();
        String selectSQL = "SELECT * FROM events";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectSQL);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                Date date = new Date(resultSet.getTimestamp("date").getTime());
                String category = resultSet.getString("category");

                Event event = new Event(id, name, description, date, category);
                events.add(event);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving events from database: " + e.getMessage());
        }

        return events;
    }

    public List<Event> getEventsForDate(int year, int month, int day) {
        List<Event> eventsForDate = new ArrayList<>();
        String selectSQL = "SELECT * FROM events WHERE YEAR(date) = ? AND MONTH(date) = ? AND DAY(date) = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, year);
            preparedStatement.setInt(2, month + 1); // SQL months are 1-based
            preparedStatement.setInt(3, day);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                Date date = new Date(resultSet.getTimestamp("date").getTime());
                String category = resultSet.getString("category");

                Event event = new Event(id, name, description, date, category);
                eventsForDate.add(event);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving events for date from database: " + e.getMessage());
        }

        return eventsForDate;
    }

    public void addContactToEvent(Event event, Contact contact) {
        String insertSQL = "INSERT INTO event_participants (event_id, contact_id) VALUES (?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setInt(1, event.getId());
            preparedStatement.setInt(2, contact.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error adding contact to event: " + e.getMessage());
        }
    }

    public void removeContactFromEvent(Event event, Contact contact) {
        String deleteSQL = "DELETE FROM event_participants WHERE event_id = ? AND contact_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL);
            preparedStatement.setInt(1, event.getId());
            preparedStatement.setInt(2, contact.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error removing contact from event: " + e.getMessage());
        }
    }
    
    public void setEvents(List<Event> events) {
        this.events.clear(); // Wyczyść aktualną listę wydarzeń
        this.events.addAll(events); // Dodaj nowe wydarzenia
    }
}
