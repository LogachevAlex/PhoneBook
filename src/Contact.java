import java.util.ArrayList;
import java.util.List;

public class Contact {
    private String name;
    private List<String> phoneNumbers;

    public Contact(String name) {
        this.name = name;
        this.phoneNumbers = new ArrayList<>();
    }

    public void addPhoneNumber(String number) {
        phoneNumbers.add(number);
    }

    public void removePhoneNumber(String number) {
        phoneNumbers.remove(number);
    }

    public String getName() {
        return name;
    }

    public List<String> getPhoneNumbers() {
        return new ArrayList<>(phoneNumbers);
    }
}
