import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;

import static java.awt.GridBagConstraints.*;

public class LandingPage extends JFrame {

    static Profile profile;
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
    public LandingPage(){

    }

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
        tree.initialiseContacts();
        profile = new Profile("John Doe", "1234567890");

        createContactBar(window, tree);
        createButtons(window, tree);

        window.setVisible(true);
    }

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

        createContactBar(window, tree);
        createButtons(window, tree);

        window.setVisible(true);
    }



    public static void createButtons(JFrame window, Tree tree){
        LandingPageMainPanel mainArea = new LandingPageMainPanel(new BorderLayout(), "mainArea");
        //mainArea.setLayout(new GridBagLayout());
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
        profileAreaConstraints.fill = HEIGHT;
        profileAreaConstraints.gridwidth = 1;
        profileAreaConstraints.insets = new Insets(10, 10, 10, 10);
        profileArea.add(profileImage, profileAreaConstraints);
        profileAreaConstraints.gridx = 1;
        profileAreaConstraints.weightx = 1;
        profileArea.add(profileLabel, profileAreaConstraints);
        profileArea.setBackground(new Color(200, 191, 166));
        profileArea.setBorder(BorderFactory.createLineBorder(Color.gray));
        //profileArea.setPreferredSize(new Dimension(mainArea.getWidth(), 50));


        JPanel buttonArea = new JPanel();
        buttonArea.setLayout(new GridBagLayout());
        buttonArea.setBackground(new Color(242, 233, 208));


        JButton viewProfileButton = new JButton("View/Edit Profile");
        viewProfileButton.setPreferredSize(new Dimension(300, 100));
        viewProfileButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, viewProfileButton.getPreferredSize().height));
        viewProfileButton.setBorder(BorderFactory.createLineBorder(Color.gray));
        viewProfileButton.setBackground(new Color(242, 233, 208));
        viewProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame profileFrame = new JFrame();
                profileFrame.setSize(300, 400);
                profileFrame.setTitle("New Contact");
                profileFrame.setLocationRelativeTo(null);
                profileFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                profileFrame.getContentPane().setBackground(new Color(242, 233, 208));
                profileFrame.setLayout(null);
                profileFrame.setVisible(true);

                JTextField contactName = new JTextField(profile.getName());
                JTextField contactNumber = new JTextField(profile.getNumber());

                JButton submit = new JButton("Submit");
                submit.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        profile.setName(contactName.getText());
                        profile.setNumber(contactNumber.getText());
                        //createButtons(window, tree);
                        profileFrame.dispose();
                    }
                });

                contactName.setBounds(50, 50, 200, 30);
                contactNumber.setBounds(50, 100, 200, 30);
                submit.setBounds(100, 150, 100, 30);

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
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        //mainArea.add(newContactButton, BorderLayout.CENTER);

        JButton newChatButton = new JButton("New Chat");
        newChatButton.setPreferredSize(new Dimension(300, 100));
        newChatButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, newChatButton.getPreferredSize().height));
        newChatButton.setBorder(BorderFactory.createLineBorder(Color.gray));
        newChatButton.setBackground(new Color(242, 233, 208));
        newChatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

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
        buttonArea.add(newChatButton, buttonAreaContstraints);


        GridBagConstraints mainAreaConstraints = new GridBagConstraints();
        mainAreaConstraints.gridx = 0;
        mainAreaConstraints.gridy = 0;
        mainAreaConstraints.fill = HORIZONTAL;
        mainAreaConstraints.gridwidth = 3;
        mainAreaConstraints.weightx = 1;
        //mainAreaConstraints.weighty = 1;
        mainArea.add(profileArea, BorderLayout.NORTH);
        mainAreaConstraints.gridx = 1;
        mainAreaConstraints.gridy = 1;
        mainAreaConstraints.fill = NONE;
        mainAreaConstraints.gridwidth = 1;
        mainAreaConstraints.weightx = 0;
        mainAreaConstraints.weighty = 0;
        mainArea.add(buttonArea, BorderLayout.CENTER);


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        window.add(mainArea, gbc);
        

    }


    public static void createContactBar(JFrame window, Tree tree){
        removeComponent(window.getContentPane(), "contactBar");
        JPanel contactBar = new JPanel();
        contactBar.setName("contactBar");
        contactBar.setLayout(new BoxLayout(contactBar, BoxLayout.Y_AXIS));
        contactBar.setBackground(new Color(242, 233, 208));

        tree.printInOrder(tree.root, contactBar, window);


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


}
