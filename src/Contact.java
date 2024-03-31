import javax.swing.*;
import java.awt.*;

/**
 * Contact class
 * @author Sam Watts, Jonah Phillipson-Masters, Harrison Reed
 */
public class Contact {
    private MessageLog messages;
    private String name;
    private String number;
    private Contact left;
    private Contact right;
    private ContactButton contactButton;
    private ImageIcon profilePic;
    private ImageIcon profilePicScaled;
    private String pathToProfilePic;
    private int ID;

    /**
     * Constructor for the contact
     * @param newName - the name of the contact
     * @param phoneNumber - the phone number of the contact
     * @param newID - the unique ID of the contact
     */
    public Contact(String newName, String phoneNumber, int newID){
        name = newName;
        number = phoneNumber;
        ID = newID;
        pathToProfilePic = "src/images/defaultProfilePic.png";
        setProfilePic(pathToProfilePic);
        messages = new MessageLog(this);
    }

    /**
     * Constructor for the contact
     * @param pathToProfilePic - the file path to the profile picture of the contact
     * @param newName - the name of the contact
     * @param phoneNumber - the phone number of the contact
     * @param newID - the unique ID of the contact
     */
    public Contact(String pathToProfilePic,String newName, String phoneNumber, int newID){
        name = newName;
        number = phoneNumber;
        ID = newID;
        this.pathToProfilePic = pathToProfilePic;
        setProfilePic(pathToProfilePic);
        messages = new MessageLog(this);
    }

    /**
     * Sets the profile picture of the contact, given a file path, and scales it to the correct size
     * @param newPathToProfilePic - the file path to the new profile picture
     */
    public void setProfilePic(String newPathToProfilePic) {
        this.pathToProfilePic = newPathToProfilePic;
        ImageIcon unscaledImage = new ImageIcon(pathToProfilePic);
        Image scaledImage = unscaledImage.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        this.profilePic = new ImageIcon(scaledImage);
    }

    public void setProfilePicToDefault() {
        ImageIcon unscaledImage = new ImageIcon(LandingPage.class.getResource("defaultProfilePic.png"));
        Image scaledImage = unscaledImage.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        this.profilePic = new ImageIcon(scaledImage);
    }


    /**
     * gets the file path to the profile picture
     * @return the file path to the profile picture
     */
    public String getPathToProfilePic() {
        return pathToProfilePic;
    }

    /**
     * gets the profile picture
     * @return the profile picture
     */
    public ImageIcon getProfilePicScaled() {
        Image scaledImage = profilePic.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        this.profilePicScaled = new ImageIcon(scaledImage);
        return profilePicScaled;
    }

    /**
     * gets the name of the contact
     * @return the name of the contact
     */
    public String getName(){
        return name;
    }

    /**
     * gets the ID of the contact
     * @return the ID of the contact
     */
    public int getID(){
        return ID;
    }

    /**
     * sets the ID of the contact
     * @param newID - the new ID of the contact
     */
    public void setID(int newID){
        ID = newID;
    }

    /**
     * sets the contact button
     * @param contactButton - the contact button
     */
    public void setContactButton(ContactButton contactButton) {
        this.contactButton = contactButton;
    }

    /**
     * gets the contact button
     * @return the contact button
     */
    public ContactButton getContactButton() {
        return contactButton;
    }

    /**
     * gets the phone number of the contact
     * @return the phone number of the contact
     */
    public String getNumber(){
        return number;
    }

    /**
     * gets the most recent message's text
     * @return the most recent message's text
     */
    public String getMostRecentChat(){
        return messages.getMostRecentMessage().getMessageText();
    }

    /**
     * the messageLog of the contact
     * @return - the messageLog of the contact
     */
    public MessageLog getMessages() {
        return messages;
    }

    /**
     * sets the messageLog of the contact
     * @param newMessages - the new messageLog
     */
    public void setMessages(MessageLog newMessages){
        messages = newMessages;
    }

    /**
     * sets the left contact node in the tree
     * @param contact - the contact to set as the left node
     */
    public void setLeft(Contact contact) {
        left = contact;
    }

    /**
     * sets the right contact node in the tree
     * @param contact - the contact to set as the right node
     */
    public void setRight(Contact contact) {
        right = contact;
    }

    /**
     * gets the right contact node in the tree
     * @return - the right contact node in the tree
     */
    public Contact getRight() {
        return right;
    }

    /**
     * gets the left contact node in the tree
     * @return - the left contact node in the tree
     */
    public Contact getLeft() {
        return left;
    }

    /**
     * sets the name of the contact
     * @param newName - the new name of the contact
     */
    public void setName(String newName) {
        name = newName;
    }

    /**
     * sets the phone number of the contact
     * @param newNumber - the new phone number of the contact
     */
    public void setNumber(String newNumber) {
        number = newNumber;
    }
}
