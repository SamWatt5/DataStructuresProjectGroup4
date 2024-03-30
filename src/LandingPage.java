import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

import static java.awt.GridBagConstraints.*;

/**
 * Landing page for the application
 */
public class LandingPage {

    private static Profile profile;

    /**
     * Main Method, runs the landing page in a new thread
     * @param args
     */
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                createLandingPage();
            }
        });

    }

    /**
     * Creates the landing page for the program
     */
    public static void createLandingPage(){
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(1300, 792);
        window.setTitle("DunChat landing page");
        window.setName("landingPage");
        window.getContentPane().setBackground(new Color(242, 233, 208));

        window.setIconImage(new ImageIcon(MessagePage.class.getResource("images/iconFixed.png")).getImage());
        window.setLocationRelativeTo(null);
        window.setLayout(new GridBagLayout());
        Tree tree = new Tree(false);
        profile = new Profile("John Doe", "1234567890");
        tree.loadFromFile();
        //tree.loadContactsMessagesFromFile();
        profile.loadProfile();
        window.addWindowListener(new WindowAdapter(){

            /**
             * When the window is closed, tree, messages and profile are saved to files
             * @param e the event to be processed
             */
            public void windowClosing(WindowEvent e){
                tree.saveToFile();
                //tree.saveContactsMessagesToFile();
                profile.saveProfile();
            }
        });
        createContactBar(window, tree);
        createButtons(window, tree);

        window.setVisible(true);
    }

    /**
     * Creates the landing page, for use when the going back to landing page from message page
     * @param tree - the binary tree of contacts being used
     */
    public static void createLandingPage(Tree tree){
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(1300, 792);
        window.setTitle("DunChat landing page");
        window.setName("landingPage");
        window.getContentPane().setBackground(new Color(242, 233, 208));
        window.setIconImage(new ImageIcon(MessagePage.class.getResource("images/iconFixed.png")).getImage());
        window.setLocationRelativeTo(null);
        window.setLayout(new GridBagLayout());
        window.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                tree.saveToFile();
                //tree.saveContactsMessagesToFile();
                profile.saveProfile();
            }
        });

        createContactBar(window, tree);
        createButtons(window, tree);

        window.setVisible(true);
    }


    /**
     * creates the buttons in the main area of the landing page
     * @param window - the JFrame that the buttons are being added to
     * @param tree - the contact binary tree being used
     */
    public static void createButtons(JFrame window, Tree tree){
        removeComponent(window.getContentPane(), "mainArea");
        LandingPageMainPanel mainArea = new LandingPageMainPanel(new BorderLayout(), "mainArea");

        mainArea.setLayout(new BorderLayout());
        mainArea.setBackground(new Color(242, 233, 208));


        JPanel profileArea = new JPanel();
        profileArea.setLayout(new GridBagLayout());
        profileArea.setPreferredSize(new Dimension(mainArea.getWidth(), 50));

        JLabel profileImage = new JLabel();
        profileImage.setIcon(profile.getProfilePicScaled());
        profileImage.setPreferredSize(new Dimension(50, 50));
        profileImage.setMaximumSize(new Dimension(50, 50));
        profileImage.setMinimumSize(new Dimension(50, 50));
        JLabel profileLabel = new JLabel(profile.getName() + "   " + profile.getNumber());
        Font profileFont = new Font("Arial", Font.PLAIN, 20);
        profileLabel.setFont(profileFont);

        GridBagConstraints profileAreaConstraints = new GridBagConstraints();
        profileAreaConstraints.gridx = 0;
        profileAreaConstraints.gridy = 0;
        profileAreaConstraints.fill = VERTICAL;
        profileAreaConstraints.gridwidth = 1;
        profileAreaConstraints.insets = new Insets(10, 10, 10, 10);
        profileArea.add(profileImage, profileAreaConstraints);
        profileAreaConstraints.gridx = 1;
        profileAreaConstraints.weightx = 1;
        profileArea.add(profileLabel, profileAreaConstraints);
        profileArea.setBackground(new Color(200, 191, 166));
        profileArea.setBorder(BorderFactory.createLineBorder(Color.gray));

        JPanel buttonArea = new JPanel();
        buttonArea.setLayout(new GridBagLayout());
        buttonArea.setBackground(new Color(242, 233, 208));


        JButton viewProfileButton = new JButton("View/Edit Profile");
        viewProfileButton.setPreferredSize(new Dimension(300, 100));
        viewProfileButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, viewProfileButton.getPreferredSize().height));
        viewProfileButton.setBorder(BorderFactory.createLineBorder(Color.gray));
        viewProfileButton.setBackground(new Color(242, 233, 208));
        viewProfileButton.addActionListener(new ActionListener() {
            /**
             * when viewProfileButton pressed, opens new window allowing the user to edit profile
             * @param e - actionEvent
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame profileFrame = new JFrame();
                profileFrame.setSize(300, 400);
                profileFrame.setTitle("Edit Profile");
                profileFrame.setLocationRelativeTo(null);
                profileFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                profileFrame.getContentPane().setBackground(new Color(242, 233, 208));
                profileFrame.setLayout(null);
                profileFrame.setVisible(true);

                JButton profilePicButton = new JButton("Change Profile Picture");
                JTextField contactName = new JTextField(profile.getName());
                JTextField contactNumber = new JTextField(profile.getNumber());

                JButton submit = new JButton("Submit");
                profilePicButton.addActionListener(new ActionListener() {

                    /**
                     * When the profilePic button is pressed, it opens a file dialog to allow user to choose a new profile picture
                     * @param e
                     */
                    public void actionPerformed(ActionEvent e) {
                        try {

                            FileDialog fileDialog = new FileDialog(profileFrame, "Choose a file", FileDialog.LOAD);
                            fileDialog.setVisible(true);
                            profile.setProfilePic(fileDialog.getDirectory() + fileDialog.getFile());

                        }catch (Exception ex){
                            ex.printStackTrace();
                            profile.setProfilePic("src/images/defaultProfilePic.png");
                        }
                    }
                });

                submit.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        profile.setName(contactName.getText());
                        profile.setNumber(contactNumber.getText());
                        createButtons(window, tree);
                        profileFrame.dispose();
                    }
                });

                profilePicButton.setBounds(50, 50, 200, 30);
                contactName.setBounds(50, 100, 200, 30);
                contactNumber.setBounds(50, 150, 200, 30);
                submit.setBounds(100, 200, 100, 30);

                profileFrame.add(profilePicButton);
                profileFrame.add(contactName);
                profileFrame.add(contactNumber);
                profileFrame.add(submit);

            }

        });
        //mainArea.add(viewProfileButton, BorderLayout.CENTER);

        JButton newContactButton = new JButton("New Contact");
        newContactButton.setPreferredSize(new Dimension(300, 100));
        newContactButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, newContactButton.getPreferredSize().height));
        newContactButton.setBorder(BorderFactory.createLineBorder(Color.gray));
        newContactButton.setBackground(new Color(242, 233, 208));
        newContactButton.addActionListener(new ActionListener() {
            /**
             * When newContactButton pressed, opens new window allowing the user to add a new contact
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                newContactFrame(window, tree);
            }
        });
        //mainArea.add(newContactButton, BorderLayout.CENTER);

        JButton searchButton = new JButton("Search for Key Words");
        searchButton.setPreferredSize(new Dimension(300, 100));
        searchButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, searchButton.getPreferredSize().height));
        searchButton.setBorder(BorderFactory.createLineBorder(Color.gray));
        searchButton.setBackground(new Color(242, 233, 208));
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchForMessage(tree);
            }

        });
            //mainArea.add(newChatButton, BorderLayout.CENTER);
        GridBagConstraints buttonAreaContstraints = new GridBagConstraints();
        buttonAreaContstraints.fill = HORIZONTAL;
        buttonAreaContstraints.insets = new Insets(10, 10, 10, 10);
        buttonAreaContstraints.gridy = 0;
        buttonArea.add(viewProfileButton, buttonAreaContstraints);
        buttonAreaContstraints.gridy = 1;
        buttonArea.add(newContactButton, buttonAreaContstraints);
        buttonAreaContstraints.gridy = 2;
        buttonArea.add(searchButton, buttonAreaContstraints);

        mainArea.add(profileArea, BorderLayout.NORTH);
        JPanel buttonsAndLogo = new JPanel(new GridBagLayout());
        GridBagConstraints BLConstraints = new GridBagConstraints();
        buttonsAndLogo.setBackground(new Color(242, 233, 208));
        BLConstraints.gridx = 1;
        BLConstraints.gridy = 0;
        BLConstraints.fill = HORIZONTAL;
        BLConstraints.weighty = 1;
        ImageIcon unscaledImage = new ImageIcon("src/images/titleTransparent.png");

        Image scaledImage = unscaledImage.getImage().getScaledInstance(270,90, Image.SCALE_SMOOTH);
        JLabel logo = new JLabel(new ImageIcon(scaledImage));
        buttonsAndLogo.add(logo, BLConstraints);
        BLConstraints.weighty = 0;
        BLConstraints.fill = NONE;
        BLConstraints.gridy = 1;
        buttonsAndLogo.add(buttonArea, BLConstraints);
        mainArea.add(buttonsAndLogo, BorderLayout.CENTER);


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        window.add(mainArea, gbc);
        

    }


    /**
     * opens a new window allowing the user to search for a message containg a keyword
     * @param tree - the binary tree being searched through
     */
    public static void searchForMessage(Tree tree){
        JFrame searchFrame = new JFrame();
        searchFrame.setSize(300, 400);
        searchFrame.setTitle("Search for message");
        searchFrame.setLocationRelativeTo(null);
        searchFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        searchFrame.getContentPane().setBackground(new Color(242, 233, 208));
        searchFrame.setLayout(null);
        searchFrame.setVisible(true);
        JTextField searchField = new JTextField("Enter search term...");
        JButton submit = new JButton("Submit");
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String searchTerm = searchField.getText();
                searchFrame.dispose();
                searchResultsFrame(searchTerm, tree);
            }
        });
        searchField.setBounds(50, 50, 200, 30);
        submit.setBounds(100, 100, 100, 30);
        searchFrame.add(searchField);
        searchFrame.add(submit);
    }

    /**
     * opens a new window displaying the search results
     * @param searchTerm - the word being searched for
     * @param tree - The tree being searched
     */
    public static void searchResultsFrame(String searchTerm, Tree tree){
        JFrame searchResultsFrame = new JFrame();
        searchResultsFrame.setSize(300, 400);
        searchResultsFrame.setTitle("Search Results");
        searchResultsFrame.setLocationRelativeTo(null);
        searchResultsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        searchResultsFrame.getContentPane().setBackground(new Color(242, 233, 208));
        searchResultsFrame.setLayout(null);
        searchResultsFrame.setVisible(true);
        int y = 50;
        for (Contact contact : tree.getContacts()){
            if (contact != null) {
                for (Message message : contact.getMessages().getMessagesInArray()) {
                    if (message.getMessageText().toUpperCase().contains(searchTerm.toUpperCase())) {
                        JButton result = new JButton(contact.getName() + ": " + message.getMessageText());
                        result.setBounds(50, y, 200, 30);
                        result.setBackground(new Color(242, 233, 208));
                        result.setBorder(new EmptyBorder(0, 0, 0, 0));
                        result.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                MessagePage messagePage = new MessagePage(tree, profile);
                                messagePage.setVisible(true);
                                messagePage.createMessageArea(messagePage, contact);
                            }
                        });
                        searchResultsFrame.add(result);
                        y += 50;
                    }
                }
            }
        }
    }

    /**
     * Creates the bar of contact buttons on the left of the window.
     * @param window - the window being added to
     * @param tree - the tree of contacts being added
     */
    public static void createContactBar(JFrame window, Tree tree){
        removeComponent(window.getContentPane(), "contactBar");

        JPanel contactBar = new JPanel();
        contactBar.setName("contactBar");
        contactBar.setLayout(new BoxLayout(contactBar, BoxLayout.Y_AXIS));
        contactBar.setBackground(new Color(242, 233, 208));

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
     * Creates a new window allowing the user to add a new contact
     * @param window - the window that created the new window
     * @param tree - the tree of contacts being added to
     */
    public static void newContactFrame(JFrame window, Tree tree){
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
            public void actionPerformed(ActionEvent e) {
                Contact newContact = new Contact(contactName.getText(), contactNumber.getText(), tree.findHighestID(tree.getRoot())+1);
                newContact.getMessages().generateTestMessages();
                tree.add(newContact);
                removeComponent(window.getContentPane(), "contactBar");
                createContactBar(window, tree);
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
    }

    /**
     * recursively removes a component from a window
     * @param container - the window or container being removed from
     * @param name - the name of the item being removed
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





}
