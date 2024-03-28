import java.time.LocalDateTime;
import java.util.Scanner;
public class MessageLog {

    Message head;
    Contact contact;


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
}
