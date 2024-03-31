import javax.swing.*;
import java.io.*;

/**
 * Binary tree of contacts
 * @author Sam Watts, Jonah Phillipson-Masters, Harrison Reed
 */
public class Tree {
    private Contact root;
    private Boolean sortedAlphabetically;
    //Tree tree = new Tree();

    /**
     * Constructor for the tree
     * @param alphabetical whether the tree should be sorted alphabetically (UNUSED)
     */
    public Tree(Boolean alphabetical) {
        this.root = null;
        this.sortedAlphabetically = alphabetical;
    }

    /**
     * Adds a contact to the tree
     * @param contactName name of the contact
     * @param contactNumber phone number of the contact
     * @param ID - unique ID of the contact
     */
    public void add(String contactName, String contactNumber, int ID) {
        root = addRecursive(root, new Contact(contactName, contactNumber, ID));

    }

    /**
     * Adds a contact to the tree, with a profile picture
     * @param pathToProfilePic - the file path to the profile picture of the contact
     * @param contactName - name of the contact
     * @param contactNumber - phone number of the contact
     * @param ID - unique ID of the contact
     */
    public void add(String pathToProfilePic, String contactName, String contactNumber, int ID) {
        root = addRecursive(root, new Contact(pathToProfilePic, contactName, contactNumber, ID));

    }

    /**
     * Finds the highest ID in the tree, used to generate new IDs
     * @param root - the root of the tree
     * @return the highest ID in the tree
     */
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

    /**
     * Adds a contact to the tree
     * @param contactToAdd - the contact to add
     */
    public void add(Contact contactToAdd) {
        root = addRecursive(root, contactToAdd);

    }

    /**
     * Recursively adds a contact to the tree
     * @param current - the current contact
     * @param contactToAdd - the contact to add
     * @return the current contact
     */
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

    /**
     * Deletes a contact from the tree
     * @param contactName - the name of the contact to delete
     */
    public void deleteContact(String contactName) {
        Contact contactToDelete = search(contactName);
        root = deleteContactRecursive(root,  contactToDelete);
    }

    /**
     * Recursively deletes a contact from the tree
     * @param current - the current contact
     * @param contactToDelete - the contact to delete
     * @return the current contact
     */
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

    /**
     * Finds the contact with the lowest ID in the tree, used for deleting contacts
     * @param root - the root of the tree
     * @return the contact with the lowest ID
     */
    public Contact findLowestID(Contact root) {
        while (root.getLeft() != null) {
            root = root.getLeft();
        }
        return root;
    }

    /**
     * Searches for a contact in the tree
     * @param contactName - the name of the contact to search for
     * @return the contact, or null
     */
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

    /**
     * Initialises test contacts
     */
    public void initialiseContacts() {
        //Tree tree = new Tree();
        this.add("Sam", "07123456789", 0);
        this.add("Jonah", "07987654321", 1);
        this.add("Harrison", "01312345678", 2);
        this.add("Ruairidh", "78247434", 3);
        this.add("Linus Torvalds", "555444668899", 4);
    }

    /**
     * Gets the root of the tree
     * @return the root of the tree
     */
    public Contact getRoot() {
        return root;
    }

    /**
     * Adds contacts to the contact bar in order
     * @param current - the current contact
     * @param contactBar - the contact bar
     * @param frame - the window to add the contacts to
     * @param profile - the profile of the user
     */
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

    /**
     * Saves the contacts to a file
     */
    public void saveToFile() {
        try {
            PrintWriter printWriter1 = new PrintWriter(new FileOutputStream("files/contacts.txt", false));
            saveToFileRecursive(root, printWriter1);
            printWriter1.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Recursively saves the contacts to a file
     * @param root - the root of the tree
     * @param printWriter - the print writer being used
     */
    private void saveToFileRecursive(Contact root, PrintWriter printWriter){
        if (root != null) {
            saveToFileRecursive(root.getLeft(), printWriter);
            String regex = "%% 101010CONTACTSPLIT010101 %%";
            printWriter.println(root.getPathToProfilePic()+ regex + root.getName()
                    + regex + root.getNumber()+ regex + root.getID() + regex + root.getIsDefaultProfilePic());
            root.getMessages().saveLogToFile();
            saveToFileRecursive(root.getRight(), printWriter);
        }
    }

    /**
     * Loads contacts from a file and saves them to the tree
     * if empty, uses test contacts
     */
    public void loadFromFile() {
        FileReader fileReader;
        BufferedReader bufferedReader = null;
        String nextLine;
        try {
            fileReader = new FileReader("files/contacts.txt");
            bufferedReader = new BufferedReader(fileReader);

            while ((nextLine = bufferedReader.readLine()) != null) {
                String[] contactInfo = nextLine.split("%% 101010CONTACTSPLIT010101 %%");
                if (contactInfo.length == 5) {
                    if (contactInfo[4].equals("true")) {
                        this.add(contactInfo[1], contactInfo[2], Integer.parseInt(contactInfo[3]));
                    } else {
                        this.add(contactInfo[0], contactInfo[1], contactInfo[2], Integer.parseInt(contactInfo[3]));
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("There was a problem opening contacts file, using test contacts.");
            initialiseContacts();
        }

    }

    /**
     * Gets the contacts in the tree in array form
     * @return the contacts in the tree
     */
    public Contact[] getContacts() {
        Contact[] contacts = getContactsRecursive(root);
        return contacts;
    }

    /**
     * Recursively gets the contacts in the tree in array form
     * @param current - the current contact
     * @return the contacts in the tree in array form
     */
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