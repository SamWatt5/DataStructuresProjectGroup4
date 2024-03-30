import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
public class MessagePage extends JFrame

{
    public static ContactButton selectedContact;
    private static Tree tree;
    public ContactButton currentContactButton;

    public MessagePage(Tree theTree) //Contact selectedContact)
    {
        //this.selectedContact = selectedContact.getContactButton();
        tree = theTree;
        createMessagePage(this, tree, null);
    }
    public static void createMessagePage(JFrame messagePage, Tree tree, ContactButton contactButton)
    {
        //Creates and sets up the window
        messagePage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        messagePage.setSize(1300, 792);
        messagePage.setTitle("DunChat message page");
        messagePage.getContentPane().setBackground(new Color(242, 233, 208));
        messagePage.setIconImage(new ImageIcon(MessagePage.class.getResource("images/iconFixed.png")).getImage());
        messagePage.setLocationRelativeTo(null);
        messagePage.setLayout(new GridBagLayout());

        messagePage.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                tree.saveToFile();
                //tree.saveContactsMessagesToFile();
                //profile.saveProfile();
            }
        });
        //Creates the tree and initialises the contacts

        //Creates the contact bar and message area
        createMenuBar(messagePage, tree, contactButton);
        createContactBar(messagePage, tree, contactButton);
        createMessageArea(messagePage, tree.getRoot(), contactButton);

        //Displays the window
        messagePage.setVisible(true);

    }

    public static void createMenuBar(JFrame window, Tree tree, ContactButton contactButton){
        JMenuBar menuBar = new JMenuBar();
        JButton home = new JButton("Home");
        JMenu contacts = new JMenu("Contacts");
        JMenu sort = new JMenu("Sort");
        JMenuItem newContact = new JMenuItem("New Contact");
        JMenuItem deleteContact = new JMenuItem("Delete Contact");
        newContact.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newContactFrame(window, tree, contactButton);
            }
        });
        deleteContact.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteContactFrame(window, tree, contactButton);
            }
        });
        home.addActionListener(new ActionListener() {
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

    public static void deleteContactFrame(JFrame window, Tree tree, ContactButton contactButton){
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
            public void actionPerformed(ActionEvent e) {
                tree.deleteContact(contactName.getText());
                createContactBar(window, tree, contactButton);
                deleteContactFrame.dispose();
            }
        });
        contactName.setBounds(50, 50, 200, 30);
        submit.setBounds(100, 150, 100, 30);
        deleteContactFrame.add(contactName);
        deleteContactFrame.add(submit);
    }

    public static void newContactFrame(JFrame window, Tree tree, ContactButton contactButton){
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
                Contact newContact = new Contact(contactName.getText(), contactNumber.getText());
                newContact.getMessages().generateTestMessages();
                tree.add(newContact);
                removeComponent(window.getContentPane(), "contactBar");
                createContactBar(window, tree, contactButton);
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

    public static void createContactBar(JFrame window, Tree tree, ContactButton contactButton){
        removeComponent(window.getContentPane(), "contactBar");
        JPanel contactBar = new JPanel();
        contactBar.setName("contactBar");
        contactBar.setLayout(new BoxLayout(contactBar, BoxLayout.Y_AXIS));
        contactBar.setBackground(new Color(242, 233, 208));
        if (contactButton != null) {
            contactButton.setButtonColour(new Color(242, 233, 208));
        }
        tree.addInOrder(tree.getRoot(), contactBar, window);


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

    public static void createMessageArea(JFrame window, Contact selectedContact, ContactButton contactButton) {
        selectedContact.getMessages().printLogToTerminal();
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
        contactInfoImage.setIcon(selectedContact.getProfilePicScaled());
        contactInfoImage.setPreferredSize(new Dimension(50, 50));
        contactInfoImage.setMaximumSize(new Dimension(50, 50));
        contactInfoImage.setMinimumSize(new Dimension(50, 50));
        JLabel contactInfoLabel = new JLabel(selectedContact.getName() + "   " + selectedContact.getNumber());
        Font contactInfoFont = new Font("Arial", Font.PLAIN, 20);
        contactInfoLabel.setFont(contactInfoFont);

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
        contactInfoArea.setBackground(new Color(200, 191, 166));
        contactInfoArea.setBorder(BorderFactory.createLineBorder(Color.gray));
        messageArea.add(contactInfoArea, BorderLayout.NORTH);

        JPanel textsPanel = createTexts(selectedContact, window, contactButton);
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
            public void actionPerformed(ActionEvent e) {
                String textToSend = textBox.getText();
                Message newMessage = new Message(textToSend, selectedContact, true, LocalDateTime.now(),selectedContact.getMessages().getMostRecentMessage().getMessageID()+1);
                selectedContact.getMessages().insert(newMessage);
                createMessageArea(window, selectedContact, contactButton);
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


    public static JPanel createTexts(Contact contact, JFrame window, ContactButton contactButton){

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
                DeleteButton deleteButton = new DeleteButton(contact, messageLog.getMessageFromIndex(i).getMessageID(), window, contactButton);
                textWithPadding.add(deleteButton);
            }else{
                DeleteButton deleteButton = new DeleteButton(contact, messageLog.getMessageFromIndex(i).getMessageID(), window, contactButton);
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
