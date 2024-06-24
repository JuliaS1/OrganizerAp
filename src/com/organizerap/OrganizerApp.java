package com.organizerap;

import com.organizerapp.controller.ContactController;
import com.organizerapp.controller.EventController;
import com.organizerapp.view.MainView;

public class OrganizerApp {
    public static void main(String[] args) {
        ContactController contactController = new ContactController();
        EventController eventController = new EventController();

        MainView mainView = new MainView(contactController, eventController);
        mainView.setVisible(true);
    }
}
 