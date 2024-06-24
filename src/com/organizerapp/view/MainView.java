package com.organizerapp.view;

import com.organizerapp.controller.ContactController;
import com.organizerapp.controller.EventController;
import com.organizerapp.model.Contact;
import com.organizerapp.model.Event;
import java.util.List; 
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

public class MainView extends JFrame {
    private ContactController contactController;
    private EventController eventController;
    private JPanel calendarPanel;
    private JLabel monthYearLabel;
    private boolean contactSortAscending = true;
    private boolean eventSortAscending = true; 

    private JPanel contactListPanel;
    private DefaultListModel<Contact> contactListModel;
    private JList<Contact> contactList;

    private JPanel eventListPanel;
    private DefaultListModel<Event> eventListModel;
    private JList<Event> eventList;

    private Calendar currentCalendar;
    
    private Map<String, Color> eventCategoryColors;

    public MainView(ContactController contactController, EventController eventController) {
    	getContentPane().setForeground(new Color(255, 128, 192));
    	setBackground(new Color(255, 128, 192));
        this.contactController = contactController;
        this.eventController = eventController;
        this.currentCalendar = Calendar.getInstance(); 
        initialize();
    }

    private void initialize() {
    	eventCategoryColors = new HashMap<>();
    	eventCategoryColors.put("Business", new Color(147, 112, 219)); 
    	eventCategoryColors.put("Private", new Color(173, 216, 230));  

       
        setTitle("Organizer App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        getContentPane().setBackground(Color.PINK);
        getContentPane().setLayout(new BorderLayout());

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(255, 193, 224));
        setJMenuBar(menuBar);

        // Menu File
        JMenu mnFile = new JMenu("File");
        mnFile.setForeground(new Color(255, 83, 169));
        mnFile.setBackground(new Color(255, 83, 169));
        menuBar.add(mnFile);

        JMenuItem mntmAddContact = new JMenuItem("Add Contact");
        mntmAddContact.setForeground(new Color(255, 83, 169));
        mntmAddContact.setBackground(new Color(255, 255, 255));
        mnFile.add(mntmAddContact);
        mntmAddContact.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddContactDialog();
            }
        });

        JMenuItem mntmAddEvent = new JMenuItem("Add Event");
        mntmAddEvent.setForeground(new Color(255, 83, 169));
        mntmAddEvent.setBackground(new Color(255, 255, 255));
        mnFile.add(mntmAddEvent);
        mntmAddEvent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddEventDialog();
            }
        });

        // Menu List
        JMenu mnList = new JMenu("List");
        mnList.setForeground(new Color(255, 83, 169));
        menuBar.add(mnList);

        JMenuItem mntmContactList = new JMenuItem("Contact List");
        mntmContactList.setForeground(new Color(255, 83, 169));
        mnList.add(mntmContactList);
        mntmContactList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showContactList();
            }
        });

        JMenuItem mntmEventList = new JMenuItem("Event List");
        mntmEventList.setForeground(new Color(255, 83, 169));
        mnList.add(mntmEventList);
        
                // Menu Help
                JMenu mnHelp = new JMenu("Help");
                mnHelp.setForeground(new Color(255, 83, 169));
                menuBar.add(mnHelp);
                
                        JMenuItem mntmAbout = new JMenuItem("About");
                        mntmAbout.setForeground(new Color(255, 83, 169));
                        mnHelp.add(mntmAbout);
                        mntmAbout.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                showAboutDialog();
                            }
                        });
        mntmEventList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showEventList();
            }
        });
        
    /* // Inside initialize() method or constructor of MainView class
        JMenuItem mntmImport = new JMenuItem("Import");
        mntmImport.setForeground(new Color(255, 83, 169));
        mntmImport.setBackground(new Color(255, 255, 255));
        mnFile.add(mntmImport);
        mntmImport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                importFromFile();
            }
        });

        JMenuItem mntmExport = new JMenuItem("Export");
        mntmExport.setForeground(new Color(255, 83, 169));
        mntmExport.setBackground(new Color(255, 255, 255));
        mnFile.add(mntmExport);
        mntmExport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportToFile();
            }
        });*/


        // Panel nawigacyjny
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.setBackground(new Color(255, 128, 192));
        JButton prevMonthButton = new JButton("<< Prev");
        prevMonthButton.setBackground(new Color(255, 255, 255));
        prevMonthButton.setForeground(new Color(198, 0, 99));
        JButton nextMonthButton = new JButton("Next >>");
        nextMonthButton.setBackground(new Color(255, 255, 255));
        nextMonthButton.setForeground(new Color(198, 0, 99));
        monthYearLabel = new JLabel("", JLabel.CENTER);
        monthYearLabel.setForeground(new Color(198, 0, 99));
        topPanel.add(prevMonthButton);
        topPanel.add(monthYearLabel);
        topPanel.add(nextMonthButton);
        getContentPane().add(topPanel, BorderLayout.NORTH);

        // Obsługa przycisków
        prevMonthButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentCalendar.add(Calendar.MONTH, -1);
                displayMonth(currentCalendar.get(Calendar.YEAR), currentCalendar.get(Calendar.MONTH));
            }
        });

        nextMonthButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentCalendar.add(Calendar.MONTH, 1);
                displayMonth(currentCalendar.get(Calendar.YEAR), currentCalendar.get(Calendar.MONTH));
            }
        });

        // Panel kalendarza
        calendarPanel = new JPanel();
        calendarPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(255, 98, 176), new Color(255, 191, 223), new Color(255, 215, 235), new Color(255, 128, 192)));
        calendarPanel.setBackground(new Color(255, 193, 224));
        calendarPanel.setLayout(new GridLayout(0, 7)); 
        getContentPane().add(calendarPanel, BorderLayout.CENTER);

        // Panel listy kontaktów
        contactListPanel = new JPanel(new BorderLayout());
        contactListModel = new DefaultListModel<>();
        contactList = new JList<>(contactListModel);
        contactList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane contactScrollPane = new JScrollPane(contactList);
        contactListPanel.add(contactScrollPane, BorderLayout.CENTER);
        
        JPanel contactButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton editContactButton = new JButton("Edit");
        JButton removeContactButton = new JButton("Remove");
        contactButtonPanel.add(editContactButton);
        contactButtonPanel.add(removeContactButton);
        contactListPanel.add(contactButtonPanel, BorderLayout.SOUTH);
        
    
        JButton sortContactsButton = new JButton("Sort by Name");
        sortContactsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleContactSortOrder(); 
                sortContactList(); 
            }
        });
        contactButtonPanel.add(sortContactsButton); 
        
     // Dodanie ListSelectionListener do listy kontaktów
        contactList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && contactList.getSelectedValue() != null) {
                    showContactDetails(contactList.getSelectedValue());
                }
            }
        });

        editContactButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editSelectedContact();
            }
        });

        removeContactButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeSelectedContact();
            }
        });

        // Panel listy wydarzeń
        eventListPanel = new JPanel(new BorderLayout());
        eventListModel = new DefaultListModel<>();
        eventList = new JList<>(eventListModel);
        eventList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane eventScrollPane = new JScrollPane(eventList);
        eventListPanel.add(eventScrollPane, BorderLayout.CENTER);
        
        JPanel eventButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton editEventButton = new JButton("Edit");
        JButton removeEventButton = new JButton("Remove");
        eventButtonPanel.add(editEventButton);
        eventButtonPanel.add(removeEventButton);
        eventListPanel.add(eventButtonPanel, BorderLayout.SOUTH);
        
        JButton sortEventsButton = new JButton("Sort Events");
        sortEventsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleEventSortOrder(); 
                sortEventList();     
                sortEventsButton.setText(eventSortAscending ? "Sort Events Oldest First" : "Sort Events Newest First");
            }
        });
        eventButtonPanel.add(sortEventsButton);
        
     // Dodanie ListSelectionListener do listy wydarzeń
        eventList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && eventList.getSelectedValue() != null) {
                    showEventDetails(eventList.getSelectedValue());
                }
            }
        });

        editEventButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editSelectedEvent();
            }
        });

        removeEventButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeSelectedEvent();
            }
        });

        // Wyświetl aktualny miesiąc
        displayMonth(currentCalendar.get(Calendar.YEAR), currentCalendar.get(Calendar.MONTH));
    }

    private void displayMonth(int year, int month) {
        updateMonthYearLabel(year, month);

        createCalendarPanel(year, month);

        calendarPanel.revalidate();
        calendarPanel.repaint();
    }

    private void createCalendarPanel(int year, int month) {
        calendarPanel.removeAll();
        String[] daysOfWeek = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (String day : daysOfWeek) {
            calendarPanel.add(new JLabel(day, SwingConstants.CENTER));
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1);
        int firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int i = 1; i < firstDayOfWeek; i++) {
            calendarPanel.add(new JLabel(""));
        }

        for (int day = 1; day <= daysInMonth; day++) {
            final int eventDay = day;
            JButton dayButton = new JButton(String.valueOf(day));
            Color dayColor = getColorForEventsOfDay(day, month, year);
            dayButton.setBackground(dayColor);
            dayButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showEventsForDay(eventDay, month, year);
                }
            });
            calendarPanel.add(dayButton);
        }
    }
    private Color getColorForEventsOfDay(int day, int month, int year) {
        List<Event> events = eventController.getEventsForDate(year, month, day);
        for (Event event : events) {
            Color categoryColor = eventCategoryColors.get(event.getCategory());
            if (categoryColor != null) {
                return categoryColor;
            }
        }
        return new Color(255, 192, 203); 

    }
   
    private void updateMonthYearLabel(int year, int month) {
        // Tablica z nazwami miesięcy bez odmiany
        String[] monthNames = {
            "styczeń", "luty", "marzec", "kwiecień", "maj", "czerwiec",
            "lipiec", "sierpień", "wrzesień", "październik", "listopad", "grudzień"
        };
        // Ustawienie etykiety na nazwę miesiąca i rok
        monthYearLabel.setText(monthNames[month] + " " + year);
    }

    private void showAddContactDialog() {
        AddContactDialog dialog = new AddContactDialog(this);
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            String name = dialog.getNameInput();
            String phone = dialog.getPhoneInput();
            String email = dialog.getEmailInput();
            String category = dialog.getCategoryInput();

            Contact newContact = new Contact(name, phone, email, category);
            contactController.addContact(newContact);

            JOptionPane.showMessageDialog(this, "Contact added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }


    private void showAddEventDialog() {
        AddEventDialog dialog = new AddEventDialog(this);
        dialog.setCategories(new String[]{"Business", "Private"});
        
        
        List<Contact> allContacts = contactController.getContacts();
        dialog.setParticipantsList(allContacts.toArray(new Contact[0]));

        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            String name = dialog.getNameInput();
            String description = dialog.getDescriptionInput();
            Date date = dialog.getDateInput();
            Contact participant = dialog.getParticipant();
            String category = dialog.getSelectedCategory(); 

            Event newEvent = new Event(name, description, date, category);
            newEvent.addParticipant(participant);
             eventController.addEvent(newEvent); // zakładając, że masz kontroler zdarzeń

            // Odśwież widok kalendarza
            displayMonth(currentCalendar.get(Calendar.YEAR), currentCalendar.get(Calendar.MONTH));

            JOptionPane.showMessageDialog(this, "Event added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }


    private void showAboutDialog() {
        JOptionPane.showMessageDialog(this, "Kalendarz na zajęcia\n Karolina Bil 38036\n Julia Sapko 38044", "About", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showContactList() {
        // Wyczyść aktualną listę modelu kontaktów
        contactListModel.clear();

        // Pobierz listę kontaktów z kontrolera
        List<Contact> contacts = contactController.getContacts();

        // Dodaj pobrane kontakty do listy modelu
        for (Contact contact : contacts) {
            contactListModel.addElement(contact);
        }

        // Ustaw renderowanie listy kontaktów z kolorowaniem
        contactList.setCellRenderer(new ColoredContactListRenderer());

        // Wyświetl panel z listą kontaktów bez użycia JOptionPane
        showListPanel(contactListPanel, "Contact List");
    }


    private void showEventList() {
        eventListModel.clear();
        List<Event> events = eventController.getEvents();
        for (Event event : events) {
            eventListModel.addElement(event);
        }
        showListPanel(eventListPanel, "Event List");
    }

    private void showListPanel(JPanel listPanel, String title) {
    	JOptionPane.showMessageDialog(this, listPanel, title, JOptionPane.PLAIN_MESSAGE);
    }

    private void showEventsForDay(int day, int month, int year) {
        eventListModel.clear();
        Calendar selectedDate = Calendar.getInstance();
        selectedDate.set(year, month, day);
        List<Event> events = eventController.getEvents();
        for (Event event : events) {
            Calendar eventDate = Calendar.getInstance();
            eventDate.setTime(event.getDate());
            if (eventDate.get(Calendar.YEAR) == year &&
                    eventDate.get(Calendar.MONTH) == month &&
                    eventDate.get(Calendar.DAY_OF_MONTH) == day) {
                eventListModel.addElement(event);
            }
        }

        showListPanel(eventListPanel, "Events on " + (month + 1) + "/" + day + "/" + year);
    }
    
    private void editSelectedContact() {
        Contact selectedContact = contactList.getSelectedValue();
        if (selectedContact != null) {
            AddContactDialog dialog = new AddContactDialog(this);
            dialog.getNameField().setText(selectedContact.getName());
            dialog.getPhoneField().setText(selectedContact.getPhone());
            dialog.getEmailField().setText(selectedContact.getEmail());
            dialog.getCategoryComboBox().setSelectedItem(selectedContact.getCategory());

            dialog.setVisible(true);

            if (dialog.isConfirmed()) {
                String newName = dialog.getNameInput();
                String newPhone = dialog.getPhoneInput();
                String newEmail = dialog.getEmailInput();
                String newCategory = dialog.getCategoryInput();

                if (!newName.isEmpty()) {
                    Contact newContact = new Contact(newName, newPhone, newEmail, newCategory);
                    contactController.editContact(selectedContact, newContact);
                    contactListModel.set(contactList.getSelectedIndex(), newContact);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a contact to edit.", "Edit Contact", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void removeSelectedContact() {
        Contact selectedContact = contactList.getSelectedValue();
        if (selectedContact != null) {
            int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this contact?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                contactController.removeContact(selectedContact);
                contactListModel.removeElement(selectedContact);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a contact to remove.", "Remove Contact", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void editSelectedEvent() {
        Event selectedEvent = eventList.getSelectedValue();
        if (selectedEvent != null) {
            AddEventDialog dialog = new AddEventDialog(this);
            dialog.setName(selectedEvent.getName());
            dialog.setDescription(selectedEvent.getDescription());
            dialog.setDate(selectedEvent.getDate());
            dialog.setParticipantsList(contactController.getContacts().toArray(new Contact[0]));
            dialog.setCategories(new String[]{"Private", "Business"}); // Dodaj odpowiednie kategorie

            dialog.setParticipant(selectedEvent.getParticipants().isEmpty() ? null : selectedEvent.getParticipants().get(0)); // Zakładając, że jest tylko jeden uczestnik na event

            dialog.setVisible(true);

            if (dialog.isConfirmed()) {
                String name = dialog.getNameInput();
                String description = dialog.getDescriptionInput();
                Date date = dialog.getDateInput();
                String category = dialog.getSelectedCategory();
                Contact participant = dialog.getParticipant();

                selectedEvent.setName(name);
                selectedEvent.setDescription(description);
                selectedEvent.setDate(date);
                selectedEvent.setCategory(category);

                selectedEvent.getParticipants().clear();
                if (participant != null) {
                    selectedEvent.addParticipant(participant);
                }

                eventListModel.set(eventList.getSelectedIndex(), selectedEvent);
                eventController.editEvent(selectedEvent, selectedEvent);
                displayMonth(currentCalendar.get(Calendar.YEAR), currentCalendar.get(Calendar.MONTH));

                JOptionPane.showMessageDialog(this, "Event edited successfully!", "Edit Event", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an event to edit.", "Edit Event", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void removeSelectedEvent() {
        Event selectedEvent = eventList.getSelectedValue();
        if (selectedEvent != null) {
            int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this event?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                eventController.removeEvent(selectedEvent);
                eventListModel.removeElement(selectedEvent);
                displayMonth(currentCalendar.get(Calendar.YEAR), currentCalendar.get(Calendar.MONTH));
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an event to remove.", "Remove Event", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void showContactDetails(Contact contact) {
        // Example implementation: Display details in a JOptionPane
        String details = "Name: " + contact.getName() + "\n"
                       + "Phone: " + contact.getPhone() + "\n"
                       + "Email: " + contact.getEmail();

        JOptionPane.showMessageDialog(this, details, "Contact Details", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showEventDetails(Event event) {
        // Example implementation: Display details in a JOptionPane
        String details = "Name: " + event.getName() + "\n"
                       + "Description: " + event.getDescription() + "\n"
                       + "Date: " + event.getDate().toString();  // Adjust date formatting as needed

        JOptionPane.showMessageDialog(this, details, "Event Details", JOptionPane.INFORMATION_MESSAGE);
    }

    private void manageParticipants(Event event) {
        // Okno dialogowe do zarządzania uczestnikami zdarzenia
        JPanel panel = new JPanel(new BorderLayout());
        DefaultListModel<Contact> participantListModel = new DefaultListModel<>();
        JList<Contact> participantList = new JList<>(participantListModel);

        // Dodaj istniejących uczestników do listy
        for (Contact participant : event.getParticipants()) {
            participantListModel.addElement(participant);
        }

        JScrollPane scrollPane = new JScrollPane(participantList);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Przyciski do dodawania i usuwania uczestników
        JPanel buttonPanel = new JPanel();
        JButton addParticipantButton = new JButton("Add Participant");
        JButton removeParticipantButton = new JButton("Remove Participant");

        addParticipantButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Pokaż okno dialogowe do wyboru nowego uczestnika
                Contact[] allContacts = contactController.getContacts().toArray(new Contact[0]);
                Contact selectedContact = (Contact) JOptionPane.showInputDialog(
                        MainView.this,
                        "Select a contact:",
                        "Add Participant",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        allContacts,
                        null);

                if (selectedContact != null) {
                    event.addParticipant(selectedContact);
                    participantListModel.addElement(selectedContact);
                }
            }
        });

        removeParticipantButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Usuń wybranego uczestnika z listy
                Contact selectedParticipant = participantList.getSelectedValue();
                if (selectedParticipant != null) {
                    event.removeParticipant(selectedParticipant);
                    participantListModel.removeElement(selectedParticipant);
                }
            }
        });

        buttonPanel.add(addParticipantButton);
        buttonPanel.add(removeParticipantButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Pokaż okno dialogowe
        JOptionPane.showMessageDialog(MainView.this, panel, "Manage Participants", JOptionPane.PLAIN_MESSAGE);
    }

    public ContactController getContactController() {
        return contactController; // Assuming contactController is the instance variable of type ContactController
    }
    private Color getColorForContactCategory(String category) {
        // Tutaj zwracamy kolor na podstawie kategorii kontaktu
        if ("Business".equals(category)) {
            return Color.PINK; // Kolor dla kategorii "Business"
        } else if ("Private".equals(category)) {
            return new Color(148, 0, 211); // Kolor dla kategorii "Private"
        } else {
            return Color.BLACK; // Domyślny kolor
        }
    }
    private class ColoredContactListRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Component rendererComponent = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof Contact) {
                Contact contact = (Contact) value;
                Color nameColor = getColorForContactCategory(contact.getCategory());
                rendererComponent.setForeground(nameColor);
            }
            return rendererComponent;
        }
    }
    private void sortContactListByName() {
        List<Contact> contacts = new ArrayList<>();
        Enumeration<Contact> enumeration = contactListModel.elements();
        while (enumeration.hasMoreElements()) {
            contacts.add(enumeration.nextElement());
        }
        contacts.sort(Comparator.comparing(Contact::getName)); // Sortowanie kontaktów po nazwie

        // Wyczyszczenie listy i dodanie posortowanych kontaktów
        contactListModel.clear();
        for (Contact contact : contacts) {
            contactListModel.addElement(contact);
        }
    }
    private void toggleContactSortOrder() {
        contactSortAscending = !contactSortAscending; // Zmień kierunek sortowania
    }
    
    private void toggleEventSortOrder() {
        eventSortAscending = !eventSortAscending; // Zmień kierunek sortowania wydarzeń
    }
    
    private void sortContactList() {
        List<Contact> contacts = contactController.getContacts();

        // Sortowanie kontaktów
        Collections.sort(contacts, new Comparator<Contact>() {
            @Override
            public int compare(Contact c1, Contact c2) {
                if (contactSortAscending) {
                    return c1.getName().compareToIgnoreCase(c2.getName()); // Rosnąco
                } else {
                    return c2.getName().compareToIgnoreCase(c1.getName()); // Malejąco
                }
            }
        });

        // Wyczyść listę i dodaj posortowane kontakty
        contactListModel.clear();
        for (Contact contact : contacts) {
            contactListModel.addElement(contact);
        }
    }
    
    private void sortEventList() {
        List<Event> events = eventController.getEvents();

        // Sortowanie wydarzeń
        Collections.sort(events, new Comparator<Event>() {
            @Override
            public int compare(Event e1, Event e2) {
                if (eventSortAscending) {
                    return e1.getDate().compareTo(e2.getDate()); // Od najstarszej do najnowszej
                } else {
                    return e2.getDate().compareTo(e1.getDate()); // Od najnowszej do najstarszej
                }
            }
        });

        // Wyczyść listę i dodaj posortowane wydarzenia
        eventListModel.clear();
        for (Event event : events) {
            eventListModel.addElement(event);
        }
    }
    
    private void importFromFile() {
        try {
            // Load contacts
            List<Contact> importedContacts = readContactsFromFile("contacts.txt");
            contactController.setContacts(importedContacts);

            // Load events
            List<Event> importedEvents = readEventsFromFile("events.txt");
            eventController.setEvents(importedEvents);

            JOptionPane.showMessageDialog(this, "Data imported successfully!", "Import", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error importing data: " + e.getMessage(), "Import Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private List<Contact> readContactsFromFile(String filename) throws IOException {
        List<Contact> contacts = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                // Parse each line to create Contact objects and add them to contacts list
                // Example: Contact contact = parseContactFromLine(line);
                // contacts.add(contact);
            }
        }
        return contacts;
    }

    private List<Event> readEventsFromFile(String filename) throws IOException {
        List<Event> events = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                // Parse each line to create Event objects and add them to events list
                // Example: Event event = parseEventFromLine(line);
                // events.add(event);
            }
        }
        return events;
    }
    
 

    private void saveContactsToFile(String filename, List<Contact> contacts) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Contact contact : contacts) {
                // Example: writer.write(contact.toString());
                // Ensure proper formatting for reading back (e.g., CSV format or JSON)
                // writer.newLine();
            }
        }
    }

    private void saveEventsToFile(String filename, List<Event> events) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Event event : events) {
                // Example: writer.write(event.toString());
                // Ensure proper formatting for reading back (e.g., CSV format or JSON)
                // writer.newLine();
            }
        }
    }
        
        private void exportToFile() {
            try {
                // Save contacts
                saveContactsToFile("contacts.txt", contactController.getContacts());

                // Save events
                saveEventsToFile("events.txt", eventController.getEvents());

                JOptionPane.showMessageDialog(this, "Data exported successfully!", "Export", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error exporting data: " + e.getMessage(), "Export Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    

       
        

 }







    
