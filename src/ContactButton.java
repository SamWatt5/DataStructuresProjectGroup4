import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * The contact button class is used to create a button containing information about contacts and the most recently sent messages with them
 * @author Sam Watts, Jonah Phillipson-Masters, Harrison Reed
 */
public class ContactButton extends JButton {
    private Contact contact;

    private JPanel window;
    private Boolean isClicked = false;
    private JPanel buttonPanel;
    private JFrame windowFrame;
    private JLabel recentMessage = new JLabel();
    private JLabel messageTime = new JLabel();
    private Color buttonColour;

    /**
     * Constructor for ContactButton class
     * @param frame - the frame that the button is being displayed on
     * @param newWindow - the panel that the buttons are being added to
     * @param newContact - the contact in the button
     * @param tree - the tree of contacts
     * @param profile - the profile of the user
     */
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

            /**
             * when the button is clicked, changes the colour of the button, and updates the messageArea
             * @param evt - the mouse event
             */
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

    /**
     * sets the colour of the button
     * @param color - the colour to set the button to
     */
    public void setButtonColour(Color color){
        this.buttonColour = color;
        setBackground(color);
    }

    /**
     * gets the colour of the button
     * @return the colour of the button
     */
    public Color getButtonColour(){
        return buttonColour;
    }

    /**
     * updates the messagePage to reflect the chosen contact
     * @param contact - the contact being processed
     * @param frame - the JFrame in which the contactButton is
     * @param tree - the tree of contacts
     * @param profile - the profile of the user
     */
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

    /**
     * gets the contact of button
     * @return the contact of the button
     */
    public Contact getContact() {
        return contact;
    }

    /**
     * changes the button's text to reflect changes in messages
     */
    public void update(){
        recentMessage.setText(contact.getMostRecentChat());
        messageTime.setText(contact.getMessages().getMostRecentMessage().getTimeSentFormatted());

    }

    /**
     * gets the panel that the button is in
     * @return
     */
    public JPanel getButtonPanel() {
        return buttonPanel;
    }
}
