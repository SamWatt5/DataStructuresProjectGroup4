import javax.swing.*;
import java.awt.*;

public class Contact {
    MessageLog messages;
    String name;
    String number;
    Contact left;
    Contact right;
    ContactButton contactButton;
    private ImageIcon profilePic;
    private ImageIcon profilePicScaled;
    private String pathToProfilePic;
    private int ID;
    public Contact(){
        messages = new MessageLog(this);
        name = "";
    }
    public Contact(String newName, String phoneNumber, int newID){
        name = newName;
        number = phoneNumber;
        ID = newID;
        pathToProfilePic = "src/images/defaultProfilePic.png";
        setProfilePic(pathToProfilePic);
        messages = new MessageLog(this);
    }
    public Contact(String pathToProfilePic,String newName, String phoneNumber, int newID){
        name = newName;
        number = phoneNumber;
        ID = newID;
        this.pathToProfilePic = pathToProfilePic;
        setProfilePic(pathToProfilePic);
        messages = new MessageLog(this);
    }

    public void setProfilePic(String newPathToProfilePic) {
        this.pathToProfilePic = newPathToProfilePic;
        ImageIcon unscaledImage = new ImageIcon(pathToProfilePic);
        Image scaledImage = unscaledImage.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        this.profilePic = new ImageIcon(scaledImage);
    }

    public String getPathToProfilePic() {
        return pathToProfilePic;
    }

    public ImageIcon getProfilePicScaled() {
        Image scaledImage = profilePic.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        this.profilePicScaled = new ImageIcon(scaledImage);
        return profilePicScaled;
    }

    public String getName(){

        return name;
    }

    public int getID(){
        return ID;
    }
    public void setID(int newID){
        ID = newID;
    }

    public void setContactButton(ContactButton contactButton) {
        this.contactButton = contactButton;
    }

    public ContactButton getContactButton() {
        return contactButton;
    }

    public String getNumber(){
        return number;
    }

    public String getMostRecentChat(){
        return messages.getMostRecentMessage().getMessageText();
    }

    public MessageLog getMessages() {
        return messages;
    }

    public void setMessages(MessageLog newMessages){
        messages = newMessages;
    }

    public void setLeft(Contact contact) {
        left = contact;
    }

    public void setRight(Contact contact) {
        right = contact;
    }

    public Contact getRight() {
        return right;
    }

    public Contact getLeft() {
        return left;
    }

    public void setName(String newName) {
        name = newName;
    }

    public void setNumber(String newNumber) {
        number = newNumber;
    }
}
