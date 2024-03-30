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
    public Contact(){
        messages = new MessageLog(this);
        name = "";
    }
    public Contact(String newName, String phoneNumber){
        name = newName;
        number = phoneNumber;
        pathToProfilePic = "src/images/defaultProfilePic.png";
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
}
