import java.io.*;
import java.util.*;

public class PhoneBook {
    private Map<String, Contact> contacts = new HashMap<>();
    private String fileName;

    public PhoneBook(String fileName) {
        this.fileName = fileName;
        loadContacts();
    }

    private void loadContacts() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":", 2);
                if (parts.length == 2) {
                    String name = parts[0].trim();
                    String[] numbers = parts[1].split(",");
                    Contact contact = new Contact(name);
                    for (String number : numbers) {
                        contact.addPhoneNumber(number.trim());
                    }
                    contacts.put(name, contact);
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка при загрузке контактов: " + e.getMessage());
        }
    }

    public void saveContacts() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Contact contact : contacts.values()) {
                writer.write(contact.getName() + ": " + String.join(", ", contact.getPhoneNumbers()));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении контактов: " + e.getMessage());
        }
    }

    public Map<String, Contact> getContacts() {
        return contacts;
    }

    public void addContact(String name) {
        if (!contacts.containsKey(name)) {
            contacts.put(name, new Contact(name));
            saveContacts();
        }
    }

    public void addPhoneNumber(String name, String number) {
        Contact contact = contacts.get(name);
        if (contact != null) {
            contact.addPhoneNumber(number);
            saveContacts();
        }
    }

    public void removePhoneNumber(String name, String number) {
        Contact contact = contacts.get(name);
        if (contact != null) {
            contact.removePhoneNumber(number);
            saveContacts();
        }
    }

    public void removeContact(String name) {
        if (contacts.containsKey(name)) {
            contacts.remove(name);
            saveContacts();
        }
    }

    public void printContacts() {
        contacts.values().stream()
                .sorted((c1, c2) -> Integer.compare(c2.getPhoneNumbers().size(), c1.getPhoneNumbers().size()))
                .forEach(contact ->
                        System.out.println(contact.getName() + ": " + String.join(", ", contact.getPhoneNumbers())));
    }
}
