import java.io.*;
import java.time.LocalDateTime;
import java.util.Scanner;

public class MessageLog {

    private Message head;
    private Contact contact;


    public MessageLog(Contact newContact){
        contact = newContact;
        generateTestMessages();

    }

    public void insert(Message message){
        Message current = head;
        while (current.getNext() != null){
            current = current.getNext();
        }
        current.setNext(message);

        if (contact.getContactButton() != null) {
            contact.getContactButton().update();
        }
    }

    public Message getMessageFromIndex(int i){
        Message current = head;
        for (int j = 0; j < i; j++){
            current = current.getNext();
        }
        return current;
    }

    public int getSize(){
        int size = 0;
        Message current = head;
        while (current != null){
            size++;
            current = current.getNext();
        }
        return size;
    }

    public void printLogToTerminal(){
        Message current = head;
        while (current != null){
            if (current.isSent()) {
                System.out.print(current.getTimeSentFormatted() + "                             ");
                System.out.println(current.getMessageText());
            }else{
                System.out.println(current.getTimeSentFormatted() + " " + current.getMessageText());
            }
            current = current.getNext();
        }
    }
    public Message getMostRecentMessage(){
        Message current = head;
        while (current.getNext() != null){
            current = current.getNext();
        }
        return current;
    }

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

        // Remove the message from the linked list
        prev.setNext(current.getNext());
        System.out.println("Message deleted successfully.");
        printLogToTerminal();
    }

    public Message[] getMessagesInArray(){
        Message[] messages = new Message[getSize()];
        Message current = head;
        for (int i = 0; i < messages.length; i++){
            messages[i] = current;
            current = current.getNext();
        }
        return messages;
    }


    public void generateTestMessages(){

        //GENERATE EXAMPLE MESSAGES
        head = new Message("Hello " + contact.getName(), contact,true,0,0,0);
        insert(new Message("Hi!", contact, false,1,1,1));
        insert(new Message("How are you?", contact, true,2,2,2));
        insert(new Message("I'm good thanks!", contact,false,3,3,3));
        insert(new Message("That's good to hear!", contact,true,4,4,4));
        insert(new Message("I'm going to the shops, do you need anything?", contact,false,5,5,5));
        insert(new Message("No, I'm good thanks!", contact,true,6,6,6));
        insert(new Message("Okay, see you later!", contact,false,7,7,7));
        insert(new Message("Goodbye " + contact.getName() + "!", contact,true,8,8,(int)(Math.random()*60)));

    }

    public void saveLogToFile() {
        try{
            File file = new File("files/" + contact.getName() + "_messages.txt");
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            PrintWriter printWriter = new PrintWriter(new FileOutputStream( "files/" + contact.getName() + "_messages.txt", false));
            Message current = head;
            while (current != null){
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
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("DIDNT SAVE TO FILE");
        }

    }
    public void loadFromFile() {

        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        String nextLine;
        try {
            fileReader = new FileReader("files/" + contact.getName() + "_messages.txt");
            bufferedReader = new BufferedReader(fileReader);

            while ((nextLine = bufferedReader.readLine()) != null) {
                String[] contactInfo = nextLine.split("%%/SPLITTER/%%");
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
