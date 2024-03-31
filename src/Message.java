import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * used to store information about the messages
 * @author Sam Watts, Jonah Phillipson-Masters, Harrison Reed
 */
public class Message {

    private Message next;

    private String messageText;
    private int messageID;
    private Contact reciever;
    private boolean isSent;

    private LocalDateTime timeSent;

    private int isToday;

    /**
     * constructor for the message class
     * @param text - the contents of the message
     * @param contact - the contact the message is being sent to/from
     * @param sent - whether the message was sent or received
     * @param ID - the ID of the message
     */
    public Message(String text, Contact contact, boolean sent, int ID){
        messageText = text;
        reciever = contact;
        isSent = sent;
        messageID = ID;
        int hour = (int)(Math.random()*24);
        int min = (int)(Math.random()*60);
        int sec = (int)(Math.random()*60);
        isToday = (int)(Math.random()*5);
        //isToday = 3;
        if (isToday == 3){
            timeSent = LocalDateTime.of(LocalDateTime.now().getYear(),LocalDateTime.now().getMonth(),LocalDateTime.now().getDayOfMonth(),hour,min,sec);
        }else{
            int day = (int)(Math.random()*26)+1;
            int month = (int)(Math.random()*11)+1;
            int year = 23;
            //timeSent = LocalDateTime.of(hour,min,sec);
            timeSent = LocalDateTime.of(year,month,day,hour,min,sec);
        }
    }

    /**
     * constructor for the message class
     * @param text - the contents of the message
     * @param contact - the contact the message is being sent to/from
     * @param sent - whether the message was sent or received
     * @param ID - the ID of the message
     * @param newHour - the hour the message was sent
     * @param newMin - the minute the message was sent
     */
    public Message(String text, Contact contact, boolean sent, int ID, int newHour, int newMin){
        messageText = text;
        reciever = contact;
        isSent = sent;
        messageID = ID;
        int hour = newHour;
        int min = newMin;
        int sec = (int)(Math.random()*60);
        isToday = (int)(Math.random()*4);
        if (isToday == 1){
            timeSent = LocalDateTime.of(LocalDateTime.now().getYear(),LocalDateTime.now().getMonth(),LocalDateTime.now().getDayOfMonth(),hour,min,sec);
        }else{
            int day = (int)(Math.random()*26)+1;
            int month = (int)(Math.random()*11)+1;
            int year = 23;
            //timeSent = LocalDateTime.of(hour,min,sec);
            timeSent = LocalDateTime.of(year,month,day,hour,min,sec);
        }
    }

    /**
     * constructor for the message class, used when loading from a file
     * @param text - the contents of the message
     * @param contact - the contact the message is being sent to/from
     * @param sent - whether the message was sent or received
     * @param newHour - the hour the message was sent
     * @param newMin - the minute the message was sent
     * @param newSec - the second the message was sent
     * @param newIsToday - whether the message was sent today or not
     * @param newDay - the day the message was sent
     * @param newMonth - the month the message was sent
     * @param newYear - the year the message was sent
     * @param ID - the ID of the message
     */
    public Message(String text, Contact contact, boolean sent, int newHour, int newMin, int newSec, int newIsToday, int newDay, int newMonth, int newYear, int ID){
        messageText = text;
        reciever = contact;
        isSent = sent;
        messageID = ID;
        int hour = newHour;
        int min = newMin;
        int sec = newSec;
        isToday = newIsToday;
        int day = newDay;
        int month = newMonth;
        int year = newYear;

        timeSent = LocalDateTime.of(year,month,day,hour,min,sec);

    }

    /**
     * constructor for the message class
     * @param text - the contents of the message
     * @param contact - the contact the message is being sent to/from
     * @param sent - whether the message was sent or received
     * @param time - the time the message was sent
     * @param ID - the ID of the message
     */
    public Message(String text, Contact contact, boolean sent, LocalDateTime time, int ID){
        messageText = text;
        reciever = contact;
        isSent = sent;
        timeSent = time;
        messageID = ID;
    }

    /**
     * gets the ID of the message
     * @return - the ID of the message
     */
    public int getID(){
        return messageID;
    }

    /**
     * gets the contents of the message
     * @return - the contents of the message
     */
    public String getMessageText() {
        return messageText;
    }

    /**
     * gets whether the message was sent or received
     * @return - whether the message was sent or received
     */
    public boolean isSent() {
        return isSent;
    }

    /**
     * gets the time the message was sent
     * @return - the time the message was sent
     */
    public LocalDateTime getTimeSent(){
        return timeSent;
    }

    /**
     * gets if the message is sent today or not
     * @return - if the message is sent today or not
     */
    public int getIsToday(){
        return isToday;
    }

    /**
     * sets the next message in the linked list
     * @param newNext - the next message in the linked list
     */
    public void setNext(Message newNext){
        next = newNext;
    }

    /**
     * gets the next message in the linked list
     * @return - the next message in the linked list
     */
    public Message getNext(){
        return next;
    }

    /**
     * gets the time the message was sent in a nice format
     * @return - the time the message was sent in a nice format
     */
    public String getTimeSentFormatted(){
        String formattedTime;
        if (isToday == 1 || timeSent.isAfter(LocalDateTime.now().minusDays(1))){
            formattedTime = timeSent.format(DateTimeFormatter.ofPattern("HH:mm"));
        }else{
            formattedTime = timeSent.format(DateTimeFormatter.ofPattern("dd/MM/yy HH:mm"));
        }
        return formattedTime;
    }

    /**
     * gets the message ID
     * @return the message ID
     */
    public int getMessageID() {
        return messageID;
    }

    /**
     * gets the hour the message was sent
     * @return the hour the message was sent
     */
    public String getHour() {
        return timeSent.format(DateTimeFormatter.ofPattern("HH"));
    }

    /**
     * gets the minute the message was sent
     * @return the minute the message was sent
     */
    public String getMinute() {
        return timeSent.format(DateTimeFormatter.ofPattern("mm"));
    }

    /**
     * gets the second the message was sent
     * @return the second the message was sent
     */
    public String getSecond() {
        return timeSent.format(DateTimeFormatter.ofPattern("ss"));
    }

    /**
     * gets the day the message was sent
     * @return the day the message was sent
     */
    public String getDay() {
        return timeSent.format(DateTimeFormatter.ofPattern("dd"));
    }

    /**
     * gets the month the message was sent
     * @return the month the message was sent
     */
    public String getMonth() {
        return timeSent.format(DateTimeFormatter.ofPattern("MM"));
    }

    /**
     * gets the year the message was sent
     * @return the year the message was sent
     */
    public String getYear() {
        return timeSent.format(DateTimeFormatter.ofPattern("yy"));
    }
}
