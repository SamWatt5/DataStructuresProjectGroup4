import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class DeleteButton extends JButton {
    private int message;
    private Contact contact;
    public DeleteButton(Contact contact, int messageID, JFrame window, ContactButton contactButton){
        setPreferredSize(new Dimension(30, 30));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, getPreferredSize().height));
        setBackground(new Color(242, 233, 208));
        ImageIcon unscaledImage = new ImageIcon("src/images/delete.png");

        Image scaledImage = unscaledImage.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        setIcon( new ImageIcon(scaledImage));
        setBorder(new EmptyBorder(0, 0, 0, 0));
        message = messageID;
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contact.getMessages().deleteMessage(messageID);
                MessagePage.createMessageArea(window, contact);
            }
        });
    }



}
