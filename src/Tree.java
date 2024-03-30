import javax.swing.*;
import java.io.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Stream;

public class Tree {
    private Contact root;
    private Boolean sortedAlphabetically;
    //Tree tree = new Tree();

    public Tree(Boolean alphabetical) {
        this.root = null;
        this.sortedAlphabetically = alphabetical;
    }

    public void add(String contactName, String contactNumber) {
        root = addRecursive(root, new Contact(contactName, contactNumber));

    }
    public void add(String pathToProfilePic, String contactName, String contactNumber) {
        root = addRecursive(root, new Contact(pathToProfilePic, contactName, contactNumber));

    }

    public void add(Contact contactToAdd) {
        root = addRecursive(root, contactToAdd);

    }


    public Contact addRecursive(Contact current, Contact contactToAdd) {
        if (current == null) {
            return contactToAdd;
        }
        if (contactToAdd.getMessages().getMostRecentMessage().getTimeSent().isAfter(current.getMessages().getMostRecentMessage().getTimeSent())) {
            current.setLeft(addRecursive(current.getLeft(), contactToAdd));
        } else if (contactToAdd.getMessages().getMostRecentMessage().getTimeSent().isBefore(current.getMessages().getMostRecentMessage().getTimeSent())) {
            current.setRight(addRecursive(current.getRight(), contactToAdd));
        } else {
            return current;
        }
        return current;
    }

    public void deleteContact(String contactName) {
        root = deleteContact(root, contactName);
    }

    public Contact deleteContact(Contact current, String contactName) {
        if (current == null) {
            return current;
        }
        if (contactName.compareTo(current.getName()) < 0) {
            current.setLeft(deleteContact(current.getLeft(), contactName));
        } else if (contactName.compareTo(current.getName()) > 0) {
            current.setRight(deleteContact(current.getRight(), contactName));
        } else {
            if (current.getLeft() == null) {
                return current.getRight();
            } else if (current.getRight() == null) {
                return current.getLeft();
            }
            current.setRight(minValueContact(current.getRight()));
            current.setRight(deleteContact(current.getRight(), current.getName()));
        }
        return current;
    }

    public Contact minValueContact(Contact current) {
        Contact min = current;
        while (current.getLeft() != null) {
            min = current.getLeft();
            current = current.getLeft();
        }
        return min;
    }

    public void initialiseContacts() {
        //Tree tree = new Tree();
        this.add("Sam", "07123456789");
        this.add("Jonah", "07987654321");
        this.add("Harrison", "01312345678");
        this.add("Ruairidh", "666");
        this.add("Jesus", "8008135");
    }

    public void addNewContact() {
        String contactName = getContactName();
        String contactNumber = getContactNumber();

        this.add(contactName, contactNumber);

    }

    public String getContactName() {
        Scanner s = new Scanner(System.in);
        String contactName = s.nextLine();

        return contactName;
    }

    public String getContactNumber() {
        Scanner s = new Scanner(System.in);
        String contactNum = s.nextLine();

        return contactNum;
    }

    public Contact getRoot() {
        return root;
    }

    public void saveContactsMessagesToFile() {
        saveContactsMessagesToFileRecursive(root);
    }
    public void saveContactsMessagesToFileRecursive(Contact current) {
        if (current != null) {
            saveContactsMessagesToFileRecursive(current.getLeft());
            current.getMessages().saveLogToFile();
            saveContactsMessagesToFileRecursive(current.getRight());
        }

    }

    public void addInOrder(Contact current, JPanel contactBar, JFrame frame, Profile profile) {
        if (current != null) {
            addInOrder(current.getLeft(), contactBar, frame, profile);
            System.out.println(current.getName() + " " + current.getMessages().getMostRecentMessage().getTimeSentFormatted());

            ContactButton contactButton = new ContactButton(frame, contactBar, current, this, profile);
            current.setContactButton(contactButton);
            contactBar.add(contactButton);
            addInOrder(current.getRight(), contactBar, frame, profile);
        }
    }

    public void addContactsTocontactBar(Contact contact, JPanel contactBar, JFrame frame, Profile profile) {
        if (contact != null) {
            addContactsTocontactBar(contact.right, contactBar, frame, profile);
            ContactButton contactButton = new ContactButton(frame, contactBar, contact, this, profile);
            contact.setContactButton(contactButton);
            contactBar.add(contactButton);
            addContactsTocontactBar(contact.left, contactBar, frame, profile);
        }
    }


    public void saveToFile() {
        try {
            PrintWriter printWriter1 = new PrintWriter(new FileOutputStream("files/contacts.txt", false));
            saveToFileRecursive(root, printWriter1);
            printWriter1.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveToFileRecursive(Contact root, PrintWriter printWriter){
        if (root != null) {
            saveToFileRecursive(root.left, printWriter);
            printWriter.println(root.getPathToProfilePic()+ "%% 101010CONTACTSPLIT010101 %%" +root.getName() + "%% 101010CONTACTSPLIT010101 %%" + root.getNumber());
            root.getMessages().saveLogToFile();
            saveToFileRecursive(root.right, printWriter);
        }
    }

    public void loadFromFile() {
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        String nextLine;
        try {
            fileReader = new FileReader("files/contacts.txt");
            bufferedReader = new BufferedReader(fileReader);

            while ((nextLine = bufferedReader.readLine()) != null) {
                String[] contactInfo = nextLine.split("%% 101010CONTACTSPLIT010101 %%");
                this.add(contactInfo[0], contactInfo[1], contactInfo[2]);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Contacts file not found, using test contacts.");
            initialiseContacts();
        } catch (IOException e) {
            System.out.println("There is a problem opening or reading from the file");
            initialiseContacts();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    System.out.println("File was not properly opened. Using test contacts.");
                    initialiseContacts();
                }
            }
        }
    }

    public Contact[] getContacts() {
        Contact contacts[] = getContactsRecursive(root);
        return contacts;
    }

    private Contact[] getContactsRecursive(Contact current) {
        if (current == null){
            return new Contact[0];
        }

        Contact[] leftContacts = getContactsRecursive(current.getLeft());
        Contact[] currentContact = {current};
        Contact[] rightContacts = getContactsRecursive(current.getRight());

        Contact[] result = new Contact[leftContacts.length + currentContact.length + rightContacts.length];
        System.arraycopy(leftContacts, 0, result, 0, leftContacts.length);
        System.arraycopy(currentContact, 0, result, leftContacts.length, currentContact.length);
        System.arraycopy(rightContacts, 0, result, leftContacts.length + currentContact.length, rightContacts.length);

        return result;
    }

    public void loadContactsMessagesFromFile() {
        loadContactsMessagesFromFileRecursive(root);
    }

    private void loadContactsMessagesFromFileRecursive(Contact root) {
        if (root != null) {
            loadContactsMessagesFromFileRecursive(root.left);
            root.getMessages().loadFromFile();
            loadContactsMessagesFromFileRecursive(root.right);
        }
    }
}