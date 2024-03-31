import java.io.*;
import java.time.LocalDateTime;
import java.util.Scanner;

/**
 * Linked list of messages between the user and a contact.
 */
public class MessageLog {

    private Message head;
    private Contact contact;

    /**
     * Constructor for MessageLog
     * @param newContact - the contact associated with the message log
     */
    public MessageLog(Contact newContact) {
        contact = newContact;
        loadFromFile();
    }

    /**
     * Inserts a message into the linked list
     * @param message - the message to be added
     */
    public void insert(Message message) {
        Message current = head;
        while (current.getNext() != null) {
            current = current.getNext();
        }
        current.setNext(message);

        if (contact.getContactButton() != null) {
            contact.getContactButton().update();
        }
    }

    /**
     * Gets the message at a specific index
     * @param i - the index
     * @return - the message at the index
     */
    public Message getMessageFromIndex(int i) {
        Message current = head;
        for (int j = 0; j < i; j++) {
            current = current.getNext();
        }
        return current;
    }

    /**
     * Gets the head of the linked list
     * @return - the head of the linked list
     */
    public int getSize() {
        int size = 0;
        Message current = head;
        while (current != null) {
            size++;
            current = current.getNext();
        }
        return size;
    }

    /**
     * prints the contents of the messageLog to the terminal
     */
    public void printLogToTerminal() {
        Message current = head;
        while (current != null) {
            if (current.isSent()) {
                System.out.print(current.getTimeSentFormatted() + "                             ");
                System.out.println(current.getMessageText());
            } else {
                System.out.println(current.getTimeSentFormatted() + " " + current.getMessageText());
            }
            current = current.getNext();
        }
    }

    /**
     * gets the most recent message in the messageLog
     * @return the most recent message
     */
    public Message getMostRecentMessage() {
        Message current = head;
        while (current.getNext() != null) {
            current = current.getNext();
        }

        return current;
    }

    /**
     * deletes a message from the messageLog
     * @param messageIDToBeDeleted - the ID of the message being deleted
     */
    public void deleteMessage(int messageIDToBeDeleted) {

        if (messageIDToBeDeleted < 0 || head == null) {
            System.out.println("Invalid message index.");
            return;
        }

        if (head.getID() == messageIDToBeDeleted) {
            head = head.getNext();
            System.out.println("Message deleted successfully.");
            printLogToTerminal();
            return;
        }

        Message current = head;
        Message prev = null;

        while (current != null && current.getID() != messageIDToBeDeleted) {
            prev = current;
            current = current.getNext();
        }

        if (current == null) {
            System.out.println("Message not found.");
            return;
        }

        prev.setNext(current.getNext());
        System.out.println("Message deleted successfully.");
        printLogToTerminal();
        if (contact.getContactButton() != null){
            contact.getContactButton().update();
        }
    }

    /**
     * gets the messages in the messageLog as an array
     * @return
     */
    public Message[] getMessagesInArray() {
        Message[] messages = new Message[getSize()];
        Message current = head;

        for (int i = 0; i < messages.length; i++) {
            messages[i] = current;
            current = current.getNext();
        }
        return messages;
    }

    /**
     * generates test messages for the messageLog
     */
    public void generateTestMessages() {
        head = new Message("Hello " + contact.getName(), contact, true, 0, 0, 0);
        insert(new Message("Hi!", contact, false, 1, 1, 1));
        insert(new Message("How are you?", contact, true, 2, 2, 2));
        insert(new Message("I'm good thanks!", contact, false, 3, 3, 3));
        insert(new Message("That's good to hear!", contact, true, 4, 4, 4));
        insert(new Message("I'm going to the shops, do you need anything?", contact, false, 5, 5, 5));
        insert(new Message("No, I'm good thanks!", contact, true, 6, 6, 6));
        insert(new Message("Okay, see you later!", contact, false, 7, 7, 7));
        insert(new Message("Goodbye " + contact.getName() + "!", contact, true, 8, 8, (int) (Math.random() * 60)));

    }

    /**
     * saves the messageLog to a file
     */
    public void saveLogToFile() {
        try {
            PrintWriter printWriter = new PrintWriter(new FileOutputStream("files/" + contact.getName() + "_messages.txt", false));
            Message current = head;
            while (current != null) {
                printWriter.println(current.getMessageText() + "%%/SPLITTER/%%"
                        + current.isSent() + "%%/SPLITTER/%%"
                        + current.getHour() + "%%/SPLITTER/%%"
                        + current.getMinute() + "%%/SPLITTER/%%"
                        + current.getSecond() + "%%/SPLITTER/%%"
                        + current.getIsToday() + "%%/SPLITTER/%%"
                        + current.getDay() + "%%/SPLITTER/%%"
                        + current.getMonth() + "%%/SPLITTER/%%"
                        + current.getYear() + "%%/SPLITTER/%%"
                        + current.getID());

                current = current.getNext();
            }
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("DIDNT SAVE TO FILE");
        }

    }

    /**
     * loads the messageLog from a file
     */
    public void loadFromFile() {

        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        String nextLine;
        try {
            fileReader = new FileReader("files/" + contact.getName() + "_messages.txt");
            bufferedReader = new BufferedReader(fileReader);

            while ((nextLine = bufferedReader.readLine()) != null) {
                String[] contactInfo = nextLine.split("%%/SPLITTER/%%");
                if (head == null) {
                    head = new Message(
                            contactInfo[0],//message text
                            contact,//contact
                            Boolean.parseBoolean(contactInfo[1]),//isSent
                            Integer.parseInt(contactInfo[2]),//hour
                            Integer.parseInt(contactInfo[3]),//minute
                            Integer.parseInt(contactInfo[4]),//second
                            Integer.parseInt(contactInfo[5]),//isToday
                            Integer.parseInt(contactInfo[6]),//day
                            Integer.parseInt(contactInfo[7]),//month
                            Integer.parseInt(contactInfo[8]),//year
                            Integer.parseInt(contactInfo[9])//ID
                    );
                } else {
                    this.insert(new Message(
                            contactInfo[0],//message text
                            contact,//contact
                            Boolean.parseBoolean(contactInfo[1]),//isSent
                            Integer.parseInt(contactInfo[2]),//hour
                            Integer.parseInt(contactInfo[3]),//minute
                            Integer.parseInt(contactInfo[4]),//second
                            Integer.parseInt(contactInfo[5]),//isToday
                            Integer.parseInt(contactInfo[6]),//day
                            Integer.parseInt(contactInfo[7]),//month
                            Integer.parseInt(contactInfo[8]),//year
                            Integer.parseInt(contactInfo[9])//ID
                    ));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Messages file not found, using automatically generated messages.");
            generateTestMessages();
        } catch (IOException e) {
            System.out.println("There is a problem opening or reading from the file, using automatically generated messages.");
            generateTestMessages();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    System.out.println("File was not properly opened. Using automatically generated messages.");
                    generateTestMessages();
                }
            }
        }
    }
}
