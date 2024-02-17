import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        String fileName = "contacts.txt";
        PhoneBook phoneBook = new PhoneBook(fileName);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nТелефонная книга:");
            System.out.println("1. Показать все контакты");
            System.out.println("2. Добавить контакт");
            System.out.println("3. Добавить номер телефона к контакту");
            System.out.println("4. Удалить контакт");
            System.out.println("5. Удалить номер телефона у контакта");
            System.out.println("0. Выход");
            System.out.print("Выберите действие: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    phoneBook.printContacts();
                    break;
                case "2":
                    System.out.print("Введите имя контакта: ");
                    String name = scanner.nextLine();
                    phoneBook.addContact(name);
                    break;
                case "3":
                    modifyContact(phoneBook, scanner, false, true);
                    break;
                case "4":
                case "5":
                    modifyContact(phoneBook, scanner, choice.equals("4"), false);
                    break;
                case "0":
                    System.out.println("Выход из программы.");
                    return;
                default:
                    System.out.println("Некорректный ввод. Пожалуйста, попробуйте еще раз.");
                    break;
            }
        }
    }

    private static void modifyContact(PhoneBook phoneBook, Scanner scanner, boolean isDeleteContact, boolean isAddPhone) {
        System.out.print("Введите имя контакта: ");
        String inputName = scanner.nextLine();
        List<String> matchedNames = phoneBook.getContacts().keySet().stream()
                .filter(name -> name.toLowerCase().contains(inputName.toLowerCase()))
                .collect(Collectors.toList());

        if (matchedNames.isEmpty()) {
            System.out.println("Контакт не найден.");
            return;
        }

        String exactName = matchedNames.size() == 1 ? matchedNames.get(0) :
                selectContact(matchedNames, scanner);

        if (exactName == null) {
            System.out.println("Некорректный выбор.");
            return;
        }

        if (isDeleteContact) {
            phoneBook.removeContact(exactName);
            System.out.println("Контакт '" + exactName + "' успешно удален.");
        } else if (isAddPhone) {
            System.out.print("Введите номер телефона для " + exactName + ": ");
            String phoneNumber = scanner.nextLine();
            phoneBook.addPhoneNumber(exactName, phoneNumber);
            System.out.println("Номер телефона добавлен к контакту '" + exactName + "'.");
        } else {
            Contact contact = phoneBook.getContacts().get(exactName);
            if (contact.getPhoneNumbers().isEmpty()) {
                System.out.println("У контакта '" + exactName + "' нет номеров телефона.");
                return;
            }

            System.out.println("Номера телефона контакта '" + exactName + "':");
            List<String> numbers = contact.getPhoneNumbers();
            for (int i = 0; i < numbers.size(); i++) {
                System.out.println((i + 1) + ". " + numbers.get(i));
            }
            System.out.print("Выберите номер для удаления: ");
            int index = Integer.parseInt(scanner.nextLine()) - 1;

            if (index >= 0 && index < numbers.size()) {
                phoneBook.removePhoneNumber(exactName, numbers.get(index));
                System.out.println("Номер телефона удален у контакта '" + exactName + "'.");
            } else {
                System.out.println("Некорректный выбор номера.");
            }
        }
    }

    private static String selectContact(List<String> matchedNames, Scanner scanner) {
        System.out.println("Найдено несколько совпадений:");
        for (int i = 0; i < matchedNames.size(); i++) {
            System.out.println((i + 1) + ". " + matchedNames.get(i));
        }
        System.out.print("Выберите номер контакта: ");
        int index = Integer.parseInt(scanner.nextLine()) - 1;
        return (index >= 0 && index < matchedNames.size()) ? matchedNames.get(index) : null;
    }
}
