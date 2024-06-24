package com.organizerapp.view;

import com.organizerapp.model.Contact;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

public class AddEventDialog extends JDialog {
    private JTextField nameField;
    private JTextField descriptionField;
    private JComboBox<Integer> dayComboBox;
    private JComboBox<String> monthComboBox;
    private JComboBox<Integer> yearComboBox;
    private JComboBox<Contact> participantComboBox;
    private JComboBox<String> categoryComboBox;
    private boolean confirmed = false;

    public AddEventDialog(JFrame parent) {
        super(parent, "Add Event", true);
        initComponents();
        layoutComponents();
        pack();
        setLocationRelativeTo(null); // Ustawienie okna na środku ekranu
        setMinimumSize(new Dimension(400, 300)); // Minimalny rozmiar okna
    }

    private void initComponents() {
        nameField = new JTextField(20);
        descriptionField = new JTextField(20);

        dayComboBox = new JComboBox<>();
        for (int i = 1; i <= 31; i++) {
            dayComboBox.addItem(i);
        }

        monthComboBox = new JComboBox<>(new String[]{
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        });

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        yearComboBox = new JComboBox<>();
        for (int i = currentYear; i <= currentYear + 10; i++) {
            yearComboBox.addItem(i);
        }

        participantComboBox = new JComboBox<>();
        categoryComboBox = new JComboBox<>();

        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmed = true;
                setVisible(false);
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmed = false;
                setVisible(false);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void layoutComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(255, 204, 255)); // Ustawienie tła panelu na odcień różu
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(descriptionField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Date:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        datePanel.setBackground(new Color(255, 204, 255)); // Ustawienie tła panelu na odcień różu
        datePanel.add(dayComboBox);
        datePanel.add(monthComboBox);
        datePanel.add(yearComboBox);
        panel.add(datePanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Participant:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(participantComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Category:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(categoryComboBox, gbc);

        add(panel, BorderLayout.CENTER);
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public String getNameInput() {
        return nameField.getText();
    }

    public String getDescriptionInput() {
        return descriptionField.getText();
    }

    public Date getDateInput() {
        int day = (Integer) dayComboBox.getSelectedItem();
        int month = monthComboBox.getSelectedIndex();
        int year = (Integer) yearComboBox.getSelectedItem();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public Contact getParticipant() {
        return (Contact) participantComboBox.getSelectedItem();
    }

    public String getSelectedCategory() {
        return (String) categoryComboBox.getSelectedItem();
    }

    public void setName(String name) {
        nameField.setText(name);
    }

    public void setDescription(String description) {
        descriptionField.setText(description);
    }

    public void setDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        dayComboBox.setSelectedItem(calendar.get(Calendar.DAY_OF_MONTH));
        monthComboBox.setSelectedIndex(calendar.get(Calendar.MONTH));
        yearComboBox.setSelectedItem(calendar.get(Calendar.YEAR));
    }

    public void setParticipant(Contact participant) {
        participantComboBox.setSelectedItem(participant);
    }

    public void setParticipantsList(Contact[] participants) {
        participantComboBox.setModel(new DefaultComboBoxModel<>(participants));
    }

    public void setCategories(String[] categories) {
        categoryComboBox.setModel(new DefaultComboBoxModel<>(categories));
    }
}
