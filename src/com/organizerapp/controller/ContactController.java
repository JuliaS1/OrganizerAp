package com.organizerapp.controller;

import com.organizerapp.model.Contact;
import com.organizerapp.model.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactController {
    private Connection connection;
    private List<Contact> contacts;
    
    public ContactController() {
        
   
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            System.err.println("Error connecting to database: " + e.getMessage());
        }
    }

    public void addContact(Contact contact) {
        if (!contactExists(contact)) {
            String insertSQL = "INSERT INTO contacts (name, phone, email, category) VALUES (?, ?, ?, ?)";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
                preparedStatement.setString(1, contact.getName());
                preparedStatement.setString(2, contact.getPhone());
                preparedStatement.setString(3, contact.getEmail());
                preparedStatement.setString(4, contact.getCategory());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.err.println("Error adding contact to database: " + e.getMessage());
            }
        } 
    }

    private boolean contactExists(Contact contact) {
        String selectSQL = "SELECT COUNT(*) AS count FROM contacts WHERE name = ? AND phone = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, contact.getName());
            preparedStatement.setString(2, contact.getPhone());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                return count > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error checking contact existence: " + e.getMessage());
        }
        return false;
    }


    public void editContact(Contact oldContact, Contact newContact) {
        String updateSQL = "UPDATE contacts SET name = ?, phone = ?, email = ?, category = ? WHERE id = ?";

        try {
        	 removeContact(oldContact);
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);
            preparedStatement.setString(1, newContact.getName());
            preparedStatement.setString(2, newContact.getPhone());
            preparedStatement.setString(3, newContact.getEmail());
            preparedStatement.setString(4, newContact.getCategory());
            preparedStatement.setInt(5, oldContact.getId()); // assuming id is the primary key
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating contact in database: " + e.getMessage());
        }
    }

    public void removeContact(Contact contact) {
        String deleteSQL = "DELETE FROM contacts WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL);
            preparedStatement.setInt(1, contact.getId()); // assuming id is the primary key
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting contact from database: " + e.getMessage());
        }
    }

    public List<Contact> getContacts() {
        List<Contact> contacts = new ArrayList<>();
        String selectSQL = "SELECT * FROM contacts";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectSQL);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");
                String category = resultSet.getString("category");

                Contact contact = new Contact(id, name, phone, email, category);
                contacts.add(contact);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving contacts from database: " + e.getMessage());
        }

        return contacts;
    }

    // Metoda finalize do zamykania połączenia z bazą danych
    @Override
    protected void finalize() throws Throwable {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
        super.finalize();
    }
    
    public void setContacts(List<Contact> contacts) {
        this.contacts.clear(); // Wyczyść aktualną listę kontaktów
        this.contacts.addAll(contacts); // Dodaj nowe kontakty
    }
}
