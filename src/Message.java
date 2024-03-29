import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Message {

    private Message next;

    private String messageText;
    private int messageID;
    private Contact reciever;
    private boolean isSent;

    private LocalDateTime timeSent;

    private int isToday;

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

    //NEW MESSAGE FROM FILE
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

    public Message(String text, Contact contact, boolean sent, LocalDateTime time, int ID){
        messageText = text;
        reciever = contact;
        isSent = sent;
        timeSent = time;
        messageID = ID;
    }

    public int getID(){
        return messageID;
    }


    public String getMessageText() {
        return messageText;
    }

    public boolean isSent() {
        return isSent;
    }

    public Contact getReciever(){
        return reciever;
    }
    public LocalDateTime getTimeSent(){
        return timeSent;
    }
    public int getIsToday(){
        return isToday;
    }
    public void setNext(Message newNext){
        next = newNext;
    }
    public Message getNext(){
        return next;
    }


    public String getTimeSentFormatted(){
        String formattedTime;
        if (isToday == 1 || timeSent.isAfter(LocalDateTime.now().minusDays(1))){
            formattedTime = timeSent.format(DateTimeFormatter.ofPattern("HH:mm"));
        }else{
            formattedTime = timeSent.format(DateTimeFormatter.ofPattern("dd/MM/yy HH:mm"));
        }
        return formattedTime;
    }

    public int getMessageID() {
        return messageID;
    }

    public String getHour() {
        return timeSent.format(DateTimeFormatter.ofPattern("HH"));
    }

    public String getMinute() {
        return timeSent.format(DateTimeFormatter.ofPattern("mm"));
    }

    public String getSecond() {
        return timeSent.format(DateTimeFormatter.ofPattern("ss"));
    }

    public String getDay() {
        return timeSent.format(DateTimeFormatter.ofPattern("dd"));
    }

    public String getMonth() {
        return timeSent.format(DateTimeFormatter.ofPattern("MM"));
    }

    public String getYear() {
        return timeSent.format(DateTimeFormatter.ofPattern("yy"));
    }
}
