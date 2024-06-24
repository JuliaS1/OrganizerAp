/*package com.organizerapp.util;

import com.organizerapp.model.Contact;
import com.organizerapp.model.Event;
import java.util.List;
/*import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


public class XMLImporter {

    public static List<Event> importEventsFromXml(String filePath) {
        try {
            JAXBContext context = JAXBContext.newInstance(EventListWrapper.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            // Odczyt z pliku XML
            File file = new File(filePath);
            EventListWrapper wrapper = (EventListWrapper) unmarshaller.unmarshal(file);

            return wrapper.getEvents();

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Contact> importContactsFromXml(String filePath) {
        try {
            JAXBContext context = JAXBContext.newInstance(ContactListWrapper.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            // Odczyt z pliku XML
            File file = new File(filePath);
            ContactListWrapper wrapper = (ContactListWrapper) unmarshaller.unmarshal(file);

            return wrapper.getContacts();

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }
}*/
