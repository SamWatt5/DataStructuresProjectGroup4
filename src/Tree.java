import javax.swing.*;
import java.io.*;

public class Tree {
    private Contact root;
    private Boolean sortedAlphabetically;
    //Tree tree = new Tree();

    public Tree(Boolean alphabetical) {
        this.root = null;
        this.sortedAlphabetically = alphabetical;
    }

    public void add(String contactName, String contactNumber, int ID) {
        root = addRecursive(root, new Contact(contactName, contactNumber, ID));

    }
    public void add(String pathToProfilePic, String contactName, String contactNumber, int ID) {
        root = addRecursive(root, new Contact(pathToProfilePic, contactName, contactNumber, ID));

    }

    public int findHighestID(Contact root) {
        boolean found = false;
        Contact current = root;
        do {
            if (current.getRight() != null){
                current = current.getRight();
            }else {
                found = true;
            }
        }while (!found);
        return current.getID();
    }

    public void add(Contact contactToAdd) {
        root = addRecursive(root, contactToAdd);

    }


    public Contact addRecursive(Contact current, Contact contactToAdd) {
        if (current == null) {
            return contactToAdd;
        }
        if (contactToAdd.getID() < current.getID()) {
            current.setLeft(addRecursive(current.getLeft(), contactToAdd));
        } else if (contactToAdd.getID() > current.getID()) {
            current.setRight(addRecursive(current.getRight(), contactToAdd));
        } else {
            return current;
        }
        return current;
    }

    public void deleteContact(String contactName) {
        Contact contactToDelete = search(contactName);
        root = deleteContactRecursive(root,  contactToDelete);
    }

    private Contact deleteContactRecursive(Contact current, Contact contactToDelete) {
        if (current == null) {
            return null;
        }
        if (contactToDelete.getID() < current.getID()) {
            current.setLeft(deleteContactRecursive(current.getLeft(), contactToDelete));
        } else if (contactToDelete.getID() > current.getID()) {
            current.setRight(deleteContactRecursive(current.getRight(), contactToDelete));
        }else {

            if (current.getLeft() == null && current.getRight() == null){
                return null;
            }else if (current.getLeft() == null){
                return current.getRight();
            }else if(current.getRight() == null){
                return current.getLeft();
            }
            Contact inorderSuccessor = findLowestID(current.getRight());
            current.setName(inorderSuccessor.getName());
            current.setNumber(inorderSuccessor.getNumber());
            current.setID(inorderSuccessor.getID());
            current.setMessages(inorderSuccessor.getMessages());
            current.setRight(deleteContactRecursive(current.getRight(), inorderSuccessor));
        }
        return current;
    }

    public Contact findLowestID(Contact root) {
        while (root.getLeft() != null) {
            root = root.getLeft();
        }
        return root;
    }

    public Contact search(String contactName) {
        return searchRecursive(root, contactName);
    }

    private Contact searchRecursive(Contact current, String contactName) {
        if (current == null) {
            return null;
        }
        if (current.getName().toUpperCase().equals(contactName.toUpperCase())) {
            return current;
        }
        Contact leftSearch = searchRecursive(current.getLeft(), contactName);
        if (leftSearch != null) {
            return leftSearch;
        }
        return searchRecursive(current.getRight(), contactName);
    }

    public void initialiseContacts() {
        //Tree tree = new Tree();
        this.add("Sam", "07123456789", 0);
        this.add("Jonah", "07987654321", 1);
        this.add("Harrison", "01312345678", 2);
        this.add("Ruairidh", "666", 3);
        this.add("Jesus", "8008135", 4);
    }
    public Contact getRoot() {
        return root;
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
            String regex = "%% 101010CONTACTSPLIT010101 %%";
            printWriter.println(root.getPathToProfilePic()+ regex + root.getName() + regex + root.getNumber()+ regex + root.getID());
            root.getMessages().saveLogToFile();
            saveToFileRecursive(root.right, printWriter);
        }
    }

    public void loadFromFile() {
        FileReader fileReader;
        BufferedReader bufferedReader = null;
        String nextLine;
        try {
            fileReader = new FileReader("files/contacts.txt");
            bufferedReader = new BufferedReader(fileReader);

            while ((nextLine = bufferedReader.readLine()) != null) {
                String[] contactInfo = nextLine.split("%% 101010CONTACTSPLIT010101 %%");
                this.add(contactInfo[0], contactInfo[1], contactInfo[2], Integer.parseInt(contactInfo[3]));
            }

        } catch (Exception e) {
            System.out.println("There was a problem opening contacts file, using test contacts.");
            initialiseContacts();
        }

    }

    public Contact[] getContacts() {
        Contact[] contacts = getContactsRecursive(root);
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

}