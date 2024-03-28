public class Contact {
    MessageLog messages;
    String name;
    String number;
    Contact left;
    Contact right;
    ContactButton contactButton;
    public Contact(){
        messages = new MessageLog(this);
        name = "";
    }
    public Contact(String newName, String phoneNumber){
        name = newName;
        number = phoneNumber;
        messages = new MessageLog(this);
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
