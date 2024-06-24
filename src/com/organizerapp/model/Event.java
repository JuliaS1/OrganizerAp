package com.organizerapp.model;

import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Event {
    private int id;
    private String name;
    private String description;
    private Date date;
    private String category;
    private List<Contact> participants;

    public Event(int id, String name, String description, Date date, String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.category = category;
        this.participants = new ArrayList<>();
    }
    public Event(String name, String description, Date date, String category) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.category = category;
        this.participants = new ArrayList<>();
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Contact> getParticipants() {
        return participants;
    }

    public void addParticipant(Contact contact) {
        participants.add(contact);
    }

    public void removeParticipant(Contact contact) {
        participants.remove(contact);
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = sdf.format(date);
        return name + " (" + description + ") " + formattedDate ;
    }
}
