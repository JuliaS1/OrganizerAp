/*package com.organizerapp.util;

import com.organizerapp.model.Contact;
import com.organizerapp.model.Event;
import java.util.List;
import java.io.File;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


public class XMLExporter {

    public static void exportEventsToXml(List<Event> events, String filePath) {
        try {
            JAXBContext context = JAXBContext.newInstance(EventListWrapper.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Obudowanie listy wydarzeń dla serializacji
            EventListWrapper wrapper = new EventListWrapper();
            wrapper.setEvents(events);

            // Zapis do pliku XML
            File file = new File(filePath);
            marshaller.marshal(wrapper, file);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public static void exportContactsToXml(List<Contact> contacts, String filePath) {
        try {
            JAXBContext context = JAXBContext.newInstance(ContactListWrapper.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Obudowanie listy kontaktów dla serializacji
            ContactListWrapper wrapper = new ContactListWrapper();
            wrapper.setContacts(contacts);

            // Zapis do pliku XML
            File file = new File(filePath);
            marshaller.marshal(wrapper, file);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}*/
