import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

/**
 * Class for button next to messages in messageArea which deletes that button when clicked
 * @author Sam Watts, Jonah Phillipson-Masters, Harrison Reed
 */
public class DeleteButton extends JButton {
    private int message;
    private Contact contact;

    /**
     * Constructor for DeleteButton class
     * @param contact - the contact which the delete button's message is in
     * @param messageID - the messageID of the message which will be deleted when the button is clicked
     * @param window - the window to display the buttons in
     */
    public DeleteButton(Contact contact, int messageID, JFrame window){
        setPreferredSize(new Dimension(30, 30));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, getPreferredSize().height));
        setBackground(new Color(242, 233, 208));

        ImageIcon unscaledImage = new ImageIcon("delete.png");
        Image scaledImage = unscaledImage.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        setIcon( new ImageIcon(scaledImage));

        setBorder(new EmptyBorder(0, 0, 0, 0));
        message = messageID;
        this.addActionListener(new ActionListener() {

            /**
             * Deletes the message and updates message area when pressed
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                contact.getMessages().deleteMessage(messageID);
                MessagePage.createMessageArea(window, contact);
            }
        });
    }



}
