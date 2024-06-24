package com.organizerapp.view;

import com.organizerapp.controller.ContactController;
import com.organizerapp.model.Contact;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddContactDialog extends JDialog {
    private JTextField nameField;
    private JTextField phoneField;
    private JTextField emailField;
    private JComboBox<String> categoryComboBox;
    private boolean confirmed;

    public AddContactDialog(Frame owner) {
        super(owner, "Add/Edit Contact", true);
        getContentPane().setBackground(new Color(255, 128, 192));
        initialize(owner);
    }

    private void initialize(Frame owner) {
        getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField(30); // Większa szerokość pola
        nameField.setBackground(new Color(252, 201, 227));

        gbc.gridx = 0;
        gbc.gridy = 0;
        getContentPane().add(nameLabel, gbc);
        gbc.gridx = 1;
        getContentPane().add(nameField, gbc);

        JLabel phoneLabel = new JLabel("Phone:");
        phoneField = new JTextField(30); // Większa szerokość pola
        phoneField.setBackground(new Color(252, 201, 227));

        gbc.gridx = 0;
        gbc.gridy = 1;
        getContentPane().add(phoneLabel, gbc);
        gbc.gridx = 1;
        getContentPane().add(phoneField, gbc);

        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(30); // Większa szerokość pola
        emailField.setBackground(new Color(252, 201, 227));
        emailField.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 2;
        getContentPane().add(emailLabel, gbc);
        gbc.gridx = 1;
        getContentPane().add(emailField, gbc);

        JLabel categoryLabel = new JLabel("Category:");
        categoryComboBox = new JComboBox<>(new String[]{"Business", "Private"});
        categoryComboBox.setBackground(new Color(252, 201, 227));

        gbc.gridx = 0;
        gbc.gridy = 3;
        getContentPane().add(categoryLabel, gbc);
        gbc.gridx = 1;
        getContentPane().add(categoryComboBox, gbc);

        JButton okButton = new JButton("OK");
        okButton.setBackground(new Color(245, 99, 162));
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBackground(new Color(245, 99, 162));

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        getContentPane().add(okButton, gbc);
        gbc.gridx = 1;
        getContentPane().add(cancelButton, gbc);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateInput()) {
                    // Tworzenie obiektu Contact z polami z formularza
                    String name = getNameInput();
                    String phone = getPhoneInput();
                    String email = getEmailInput();
                    String category = getCategoryInput();

                    Contact newContact = new Contact(name, phone, email, category);

                    // Dodanie kontaktu do bazy danych
                    ContactController contactController = new ContactController();
                    contactController.addContact(newContact);

                    confirmed = true;
                    setVisible(false);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmed = false;
                setVisible(false);
            }
        });

        pack();
        setSize(new Dimension(600, 400)); // Ustawienie rozmiaru okna
        setLocationRelativeTo(owner); // Ustawienie okna na środku ekranu
    }

    private boolean validateInput() {
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();

        if (phone.length() != 9 || !phone.matches("\\d{9}")) {
            JOptionPane.showMessageDialog(this, "Phone number must be 9 digits.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-z]{2,}$")) {
            JOptionPane.showMessageDialog(this, "Invalid email format.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public JTextField getNameField() {
        return nameField;
    }

    public JTextField getPhoneField() {
        return phoneField;
    }

    public JTextField getEmailField() {
        return emailField;
    }

    public JComboBox<String> getCategoryComboBox() {
        return categoryComboBox;
    }

    public String getNameInput() {
        return nameField.getText().trim();
    }

    public String getPhoneInput() {
        return phoneField.getText().trim();
    }

    public String getEmailInput() {
        return emailField.getText().trim();
    }

    public String getCategoryInput() {
        return (String) categoryComboBox.getSelectedItem();
    }
}
