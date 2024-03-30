import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class ContactButton extends JButton {
    private Contact contact;

    private JPanel window;
    private Boolean isClicked = false;
    private JPanel buttonPanel;
    private JFrame windowFrame;
    private JLabel recentMessage = new JLabel();
    private JLabel messageTime = new JLabel();
    private Color buttonColour;
    public ContactButton(JFrame frame, JPanel newWindow, Contact newContact, Tree tree, Profile profile){
        window = newWindow;
        contact = newContact;
        windowFrame = frame;
        setPreferredSize(new Dimension(300, 100));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, getPreferredSize().height));
        setBorder(BorderFactory.createLineBorder(Color.gray));
        setBackground(new Color(242, 233, 208));

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(242, 233, 208));
        buttonPanel = panel;
        GridBagConstraints constraints = new GridBagConstraints();

        JLabel contactName = new JLabel(contact.getName());
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.gridwidth = 1;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weighty = 0.33;
        constraints.weightx = 1;
        contactName.setFont(new Font("SansSerif",Font.PLAIN,20));
        panel.add(contactName, constraints);

        constraints.gridwidth = 1;

        recentMessage = new JLabel(contact.getMostRecentChat());
        constraints.anchor = GridBagConstraints.LINE_START;
        constraints.gridwidth = 1;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weighty = 0.33;
        constraints.weightx = 1;
        recentMessage.setFont(new Font("SansSerif",Font.PLAIN,20));
        panel.add(recentMessage, constraints);

        messageTime = new JLabel(contact.getMessages().getMostRecentMessage().getTimeSentFormatted());
        constraints.anchor = GridBagConstraints.LAST_LINE_START;
        constraints.gridwidth = 1;
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.weighty = 0.33;
        constraints.weightx = 1;
        recentMessage.setFont(new Font("SansSerif",Font.PLAIN,20));
        panel.add(messageTime, constraints);

        this.add(panel);

        this.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                if (!isClicked) {
                    isClicked = true;
                    panel.setBackground(new Color(200, 191, 166));
                    setButtonColour(new Color(200, 191, 166));
                    for (Component component : window.getComponents()) {
                        if (component instanceof ContactButton) {
                            ContactButton contactButton = (ContactButton) component;
                            if (contactButton != ContactButton.this) {
                                contactButton.isClicked = false;
                                contactButton.getButtonPanel().setBackground(new Color(242, 233, 208));
                                contactButton.setBackground(new Color(242, 233, 208));
                            }
                        }
                    }
                    openMessagePageWithContact(contact, frame, tree, profile);
                    }
                }
            @Override
            public void mouseEntered(MouseEvent e) {
                //System.out.println("Mouse entered");
            }
        });





    }
    public void setButtonColour(Color color){
        this.buttonColour = color;
        setBackground(color);
    }
    public Color getButtonColour(){
        return buttonColour;
    }


    public void openMessagePageWithContact(Contact contact, JFrame frame, Tree tree, Profile profile){

        if (frame.isVisible() && frame.getName().equals("landingPage")){
            frame.setVisible(false);
            MessagePage messagePage = new MessagePage(tree, profile);
            messagePage.setVisible(true);
            messagePage.createMessageArea(messagePage, contact);
        } else {
            MessagePage.createMessageArea(frame, contact);
        }
        frame.revalidate();
        frame.repaint();
    }

    public Contact getContact() {
        return contact;
    }
    public void update(){
        recentMessage.setText(contact.getMostRecentChat());
        messageTime.setText(contact.getMessages().getMostRecentMessage().getTimeSentFormatted());

    }
    public JPanel getButtonPanel() {
        return buttonPanel;
    }
    public void setButtonPanelColour(Color color){
        window.setBackground(color);
    }
}
