import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

/**
 * Class representing the message page of the program
 * @author Sam Watts, Jonah Phillipson-Masters, Harrison Reed
 */
public class MessagePage extends JFrame {
    private static Tree tree;
    private Profile profile;

    /**
     * Constructor for the message page
     * @param theTree - the tree of contacts
     * @param theProfile - the profile of the user
     */
    public MessagePage(Tree theTree, Profile theProfile) //Contact contact)
    {
        //this.contact = contact.getContactButton();
        tree = theTree;
        profile = theProfile;
        createMessagePage(this, tree, null, theProfile);
    }

    /**
     * Creates the message page
     * @param messagePage - the frame which the messagePage is displayed on
     * @param tree - the tree of contacts
     * @param contactButton - the contact button used to open the message page
     * @param profile - the profile of the user
     */
    public static void createMessagePage(JFrame messagePage, Tree tree, ContactButton contactButton, Profile profile)
    {
        messagePage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        messagePage.setSize(1300, 792);
        messagePage.setTitle("DunChat message page");
        messagePage.getContentPane().setBackground(new Color(242, 233, 208));
        messagePage.setIconImage(new ImageIcon(MessagePage.class.getResource("iconFixed.png")).getImage());
        messagePage.setLocationRelativeTo(null);
        messagePage.setLayout(new GridBagLayout());

        messagePage.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                if (!Files.exists(Path.of("files"))){
                    try {
                        Files.createDirectory(Path.of("files"));
                    } catch (IOException ex) {
                        System.out.println("Files directory cannot be created");
                    }
                }
                tree.saveToFile();
                //tree.saveContactsMessagesToFile();
                profile.saveProfile();
            }
        });
        //Creates the tree and initialises the contacts

        //Creates the contact bar and message area
        createMenuBar(messagePage, tree, contactButton, profile);
        createContactBar(messagePage, tree, contactButton, profile);
        createMessageArea(messagePage, tree.getRoot());

        //Displays the window
        messagePage.setVisible(true);

    }

    /**
     * Creates the menu bar at the top of the message page, allowing the user to go back to the landing page or edit contacts
     * @param window - the window the menu bar is displayed on
     * @param tree - the tree of contacts
     * @param contactButton - a contactButton
     * @param profile - the profile of the user
     */
    public static void createMenuBar(JFrame window, Tree tree, ContactButton contactButton, Profile profile){
        JMenuBar menuBar = new JMenuBar();
        JButton home = new JButton("Home");
        JMenu contacts = new JMenu("Contacts");
        JMenuItem newContact = new JMenuItem("New Contact");
        JMenuItem deleteContact = new JMenuItem("Delete Contact");
        newContact.addActionListener(new ActionListener() {

            /**
             * When newContact button pressed, calls newContactFrame method
             * @param e - the action event
             */
            public void actionPerformed(ActionEvent e) {
                newContactFrame(window, tree, contactButton, profile);
            }
        });
        deleteContact.addActionListener(new ActionListener() {

            /**
             * When deleteContact button pressed, calls deleteContactFrame method
             * @param e - the action event
             */
            public void actionPerformed(ActionEvent e) {
                deleteContactFrame(window, tree, contactButton, profile);
            }
        });
        home.addActionListener(new ActionListener() {

            /**
             * When home button pressed, deletes the window and creates a new landing page
             * @param e the event to be processed
             */
            public void actionPerformed(ActionEvent e) {
                window.dispose();
                LandingPage.createLandingPage(tree);
            }
        });

        contacts.add(newContact);
        contacts.add(deleteContact);
        menuBar.add(home);
        menuBar.add(contacts);
        window.setJMenuBar(menuBar);

    }

    /**
     * Creates a frame allowing the user to delete a contact
     * @param window - the window the frame is displayed on
     * @param tree - the tree of contacts
     * @param contactButton - a contactButton
     * @param profile - the profile of the user
     */
    public static void deleteContactFrame(JFrame window, Tree tree, ContactButton contactButton, Profile profile){
        JFrame deleteContactFrame = new JFrame();
        deleteContactFrame.setSize(300, 400);
        deleteContactFrame.setTitle("Delete Contact");
        deleteContactFrame.setLocationRelativeTo(null);
        deleteContactFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        deleteContactFrame.getContentPane().setBackground(new Color(242, 233, 208));
        deleteContactFrame.setLayout(null);
        deleteContactFrame.setVisible(true);
        JTextField contactName = new JTextField("Enter contact name...");
        JButton submit = new JButton("Submit");
        submit.addActionListener(new ActionListener() {

            /**
             * when the submit button is pressed, deletes the specified contact from the tree, and updates messagePage
             * @param e the event to be processed
             */
            public void actionPerformed(ActionEvent e) {
                tree.deleteContact(contactName.getText());
                createMessagePage(window, tree, contactButton, profile);
                deleteContactFrame.dispose();
            }
        });
        contactName.setBounds(50, 50, 200, 30);
        submit.setBounds(100, 150, 100, 30);
        deleteContactFrame.add(contactName);
        deleteContactFrame.add(submit);
    }

    /**
     * Creates a frame allowing the user to add a new contact
     * @param window - the window the frame is displayed on
     * @param tree - the tree of contacts
     * @param contactButton - a contactButton
     * @param profile - the profile of the user
     */
    public static void newContactFrame(JFrame window, Tree tree, ContactButton contactButton, Profile profile){
        JFrame newContactFrame = new JFrame();
        newContactFrame.setSize(300, 400);
        newContactFrame.setTitle("New Contact");
        newContactFrame.setLocationRelativeTo(null);
        newContactFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newContactFrame.getContentPane().setBackground(new Color(242, 233, 208));
        newContactFrame.setLayout(null);
        newContactFrame.setVisible(true);
        JTextField contactName = new JTextField("Enter contact name...");
        JTextField contactNumber = new JTextField("Enter contact number...");
        JButton submit = new JButton("Submit");
        submit.addActionListener(new ActionListener() {

            /**
             * when the submit button is pressed, creates a new contact with the specified parameters and adds it to the tree
             * @param e the event to be processed
             */
            public void actionPerformed(ActionEvent e) {
                Contact newContact = new Contact(contactName.getText(), contactNumber.getText(), tree.findHighestID(tree.getRoot()) + 1);
                newContact.getMessages().generateTestMessages();
                tree.add(newContact);
                removeComponent(window.getContentPane(), "contactBar");
                createContactBar(window, tree, contactButton, profile);
                window.revalidate();
                window.repaint();
                window.getContentPane().revalidate();
                window.getContentPane().repaint();
                newContactFrame.dispose();
            }
        });
        contactName.setBounds(50, 50, 200, 30);
        contactNumber.setBounds(50, 100, 200, 30);
        submit.setBounds(100, 150, 100, 30);
        newContactFrame.add(contactName);
        newContactFrame.add(contactNumber);
        newContactFrame.add(submit);



        contactName.setBounds(50, 50, 200, 30);
        contactNumber.setBounds(50, 100, 200, 30);
        submit.setBounds(100, 150, 100, 30);
        newContactFrame.add(contactName);
        newContactFrame.add(contactNumber);
        newContactFrame.add(submit);
    }

    /**
     * Creates the bar of contactButtons on the left side of the message page
     * @param window - the window that the contactBar is displayed on
     * @param tree - the tree of contacts
     * @param contactButton - the contactButton used to open the message page
     * @param profile - the profile of the user
     */
    public static void createContactBar(JFrame window, Tree tree, ContactButton contactButton, Profile profile){
        removeComponent(window.getContentPane(), "contactBar");
        JPanel contactBar = new JPanel();
        contactBar.setName("contactBar");
        contactBar.setLayout(new BoxLayout(contactBar, BoxLayout.Y_AXIS));
        contactBar.setBackground(new Color(242, 233, 208));
        if (contactButton != null) {
            contactButton.setButtonColour(new Color(242, 233, 208));
        }
        tree.addInOrder(tree.getRoot(), contactBar, window, profile);


        JScrollPane scrollPane = new JScrollPane(contactBar);
        scrollPane.setBackground(new Color(242, 233, 208));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.VERTICAL;
        window.add(scrollPane, gbc);
    }

    /**
     * Removes a component with a given name from a container, recursively
     * @param container
     * @param name
     */
    public static void removeComponent(Container container, String name){
        for (Component component : container.getComponents()) {
            if (name.equals(component.getName())) {
                container.remove(component);
                System.out.println("REMOVED");
                container.revalidate();
                container.repaint();
                return;
            } else if (component instanceof Container) {
                removeComponent((Container) component, name);
            }
        }
    }

    /**
     * Creates the message area of the message page
     * @param window - the window the message area is displayed on
     * @param contact - the contact being shown
     */
    public static void createMessageArea(JFrame window, Contact contact) {
        contact.getMessages().printLogToTerminal();
        removeComponent(window.getContentPane(), "messageArea");

        MessagePanel messageArea = new MessagePanel(new BorderLayout(), "messageArea");
        messageArea.setVisible(true);
        messageArea.setBackground(new Color(242, 233, 208));

        JTextField textBox = new JTextField("Enter message here...");
        textBox.setPreferredSize(new Dimension(messageArea.getWidth(), 50));

        JPanel contactInfoArea = new JPanel();
        contactInfoArea.setLayout(new GridBagLayout());
        contactInfoArea.setPreferredSize(new Dimension(messageArea.getWidth(), 50));

        JLabel contactInfoImage = new JLabel();
        contactInfoImage.setIcon(contact.getProfilePicScaled());
        contactInfoImage.setPreferredSize(new Dimension(50, 50));
        contactInfoImage.setMaximumSize(new Dimension(50, 50));
        contactInfoImage.setMinimumSize(new Dimension(50, 50));
        
        JLabel contactInfoLabel = new JLabel(contact.getName() + "   " + contact.getNumber());
        Font contactInfoFont = new Font("Arial", Font.PLAIN, 20);
        contactInfoLabel.setFont(contactInfoFont);
        
        JButton editContact = new JButton("Edit Contact");
        editContact.addActionListener(new ActionListener() {

            /**
             * lets the user edit the contact's details when the button is pressed
             * @param e - the action event
             */
            public void actionPerformed(ActionEvent e) {
                JFrame editContactFrame = new JFrame();
                editContactFrame.setSize(300, 400);
                editContactFrame.setTitle("Edit Contact");
                editContactFrame.setLocationRelativeTo(null);
                editContactFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                editContactFrame.getContentPane().setBackground(new Color(242, 233, 208));
                editContactFrame.setLayout(null);
                editContactFrame.setVisible(true);

                JButton contactPicButton = new JButton("Change Contact Picture");
                JTextField contactName = new JTextField(contact.getName());
                JTextField contactNumber = new JTextField(contact.getNumber());

                JButton submit = new JButton("Submit");
                contactPicButton.addActionListener(new ActionListener() {

                    /**
                     * loads a fileDialog allowing the user to choose a profile picture for the contact
                     * @param e - the action event
                     */
                    public void actionPerformed(ActionEvent e) {
                        try {

                            FileDialog fileDialog = new FileDialog(editContactFrame, "Choose a file", FileDialog.LOAD);
                            fileDialog.setVisible(true);
                            contact.setProfilePic(fileDialog.getDirectory() + fileDialog.getFile());

                        }catch (Exception ex){
                            ex.printStackTrace();
                            contact.setProfilePicToDefault();
                        }
                    }
                });

                submit.addActionListener(new ActionListener() {

                    /**
                     * when the submit button is pressed, updates the contact's details and message area
                     * @param e - the action event
                     */
                    public void actionPerformed(ActionEvent e) {
                        contact.setName(contactName.getText());
                        contact.setNumber(contactNumber.getText());
                        createMessageArea(window, contact);
                        editContactFrame.dispose();
                    }
                });

                contactPicButton.setBounds(50, 50, 200, 30);
                contactName.setBounds(50, 100, 200, 30);
                contactNumber.setBounds(50, 150, 200, 30);
                submit.setBounds(100, 200, 100, 30);

                editContactFrame.add(contactPicButton);
                editContactFrame.add(contactName);
                editContactFrame.add(contactNumber);
                editContactFrame.add(submit);

            }

        });

        GridBagConstraints contactInfoAreaConstraints = new GridBagConstraints();
        contactInfoAreaConstraints.gridx = 0;
        contactInfoAreaConstraints.gridy = 0;
        contactInfoAreaConstraints.fill = HEIGHT;
        contactInfoAreaConstraints.gridwidth = 1;
        contactInfoAreaConstraints.insets = new Insets(10, 10, 10, 10);
        contactInfoArea.add(contactInfoImage, contactInfoAreaConstraints);
        contactInfoAreaConstraints.gridx = 1;
        contactInfoAreaConstraints.weightx = 1;
        contactInfoArea.add(contactInfoLabel, contactInfoAreaConstraints);
        contactInfoAreaConstraints.gridx = 2;
        contactInfoAreaConstraints.weightx = 0;
        contactInfoArea.add(editContact, contactInfoAreaConstraints);
        contactInfoArea.setBackground(new Color(200, 191, 166));
        contactInfoArea.setBorder(BorderFactory.createLineBorder(Color.gray));
        messageArea.add(contactInfoArea, BorderLayout.NORTH);

        JPanel textsPanel = createTexts(contact, window);
        JScrollPane scrollPane = new JScrollPane(textsPanel);
        scrollPane.setPreferredSize(new Dimension(messageArea.getWidth(), messageArea.getHeight()-50));
        textsPanel.setBackground(new Color(242, 233, 208));
        scrollPane.setBackground(new Color(242, 233, 208));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        messageArea.add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(textBox, BorderLayout.CENTER);

        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {

            /**
             * when the send button is pressed, adds the message in the textBox to the message log of the selected contact
             * @param e the event to be processed
             */
            public void actionPerformed(ActionEvent e) {
                String textToSend = textBox.getText();
                Message newMessage = new Message(textToSend, contact, true, LocalDateTime.now(),contact.getMessages().getMostRecentMessage().getMessageID()+1);
                contact.getMessages().insert(newMessage);
                createMessageArea(window, contact);
            }
        });
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(sendButton);
        inputPanel.add(buttonPanel, BorderLayout.EAST);
        messageArea.add(inputPanel, BorderLayout.SOUTH);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        window.add(messageArea, gbc);
    }

    /**
     * Creates a panel containing the messages of a contact
     * @param contact - the contact whose messages are displayed
     * @param window - the window the panel is displayed on
     * @return a panel containing the messages of the contact
     */
    public static JPanel createTexts(Contact contact, JFrame window){
        JPanel textsPanel = new JPanel();
        textsPanel.setLayout(new BoxLayout(textsPanel, BoxLayout.Y_AXIS));

        MessageLog messageLog = contact.getMessages();

        for (int i = 0; i < messageLog.getSize(); i++) {
            Message message = messageLog.getMessageFromIndex(i);

            JLabel text = new JLabel();
            text.setText(message.getMessageText());
            text.setOpaque(true);
            text.setFont(new Font("SansSerif", Font.PLAIN, 20));

            JPanel textWithPadding = new JPanel();
            textWithPadding.setPreferredSize(new Dimension(textsPanel.getWidth(), 50));
            textWithPadding.setMinimumSize(new Dimension(textsPanel.getWidth(), 50));
            //textWithPadding.setBackground(new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255)));
            textWithPadding.setBackground(new Color(242, 233, 208));

            if (message.isSent()){
                text.setBackground(Color.cyan);
                textWithPadding.setLayout(new FlowLayout(FlowLayout.RIGHT));
                textWithPadding.add(text);
                DeleteButton deleteButton = new DeleteButton(contact, messageLog.getMessageFromIndex(i).getMessageID(), window);
                textWithPadding.add(deleteButton);

            }else{
                DeleteButton deleteButton = new DeleteButton(contact, messageLog.getMessageFromIndex(i).getMessageID(), window);
                textWithPadding.add(deleteButton);
                text.setBackground(Color.lightGray);
                textWithPadding.setLayout(new FlowLayout(FlowLayout.LEFT));
                textWithPadding.add(text);

            }

            text.setBorder(BorderFactory.createLineBorder(Color.gray));
            textsPanel.add(textWithPadding);
        }
        return textsPanel;
    }
}
