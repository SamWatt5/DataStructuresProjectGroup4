import javax.swing.*;
import java.time.LocalDateTime;
import java.util.Scanner;
public class Tree {
    Contact root;
    Boolean sortedAlphabetically;
    //Tree tree = new Tree();

    public Tree(Boolean alphabetical) {
        this.root = null;
        this.sortedAlphabetically = alphabetical;
    }

    public void add(String contactName, String contactNumber) {
        root = addRecursive(root, new Contact(contactName, contactNumber));

    }

    public void add(Contact contactToAdd) {
        root = addRecursive(root, contactToAdd);

    }


    public Contact addRecursive(Contact current, Contact contactToAdd) {
        if (current == null) {
            return contactToAdd;
        }
        //System.out.println(contactToAdd.getMessages().getMostRecentMessage().getTimeSent() + " " + contactToAdd.getName());
        if (contactToAdd.getMessages().getMostRecentMessage().getTimeSent().isAfter(current.getMessages().getMostRecentMessage().getTimeSent())) {
            current.setLeft(addRecursive(current.getLeft(), contactToAdd));
        }else if (contactToAdd.getMessages().getMostRecentMessage().getTimeSent().isBefore(current.getMessages().getMostRecentMessage().getTimeSent())) {
            current.setRight(addRecursive(current.getRight(), contactToAdd));
        }else {
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
        this.add("Sam","07123456789");
        this.add("Jonah","07987654321");
        this.add("Harrison","01312345678");
        this.add("Ruairidh","666");
        this.add("Jesus","8008135");
    }

    public void addNewContact(){
        String contactName = getContactName();
        String contactNumber = getContactNumber();

        this.add(contactName,contactNumber);

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
    public Contact getRoot(){
        return root;
    }

    public void printInOrder(Contact current, JPanel contactBar, JFrame frame) {
        if (current != null) {
            printInOrder(current.getLeft(), contactBar, frame);
            System.out.println(current.getName() + " " + current.getMessages().contact.getMessages().getMostRecentMessage().getTimeSentFormatted());

            ContactButton contactButton = new ContactButton(frame, contactBar, current);
            current.setContactButton(contactButton);
            contactBar.add(contactButton);
            printInOrder(current.getRight(), contactBar, frame);
        }
    }

    public void addContactsTocontactBar(Contact contact, JPanel contactBar, JFrame frame){
        if (contact != null){
            addContactsTocontactBar(contact.right, contactBar, frame);
            ContactButton contactButton = new ContactButton(frame, contactBar, contact);
            contact.setContactButton(contactButton);
            contactBar.add(contactButton);
            addContactsTocontactBar(contact.left, contactBar, frame);
        }
    }

}
