import javax.swing.*;
import java.awt.*;

import static java.awt.GridBagConstraints.HORIZONTAL;
import static java.awt.GridBagConstraints.REMAINDER;

public class LandingPage extends JFrame {
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                runProgram();
            }
        });

    }
    public static void runProgram(){
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

        createContactBar(window, tree);
        createButtons(window, tree);

        window.setVisible(true);
    }

    public static void createButtons(JFrame window, Tree tree){
        LandingPageMainPanel mainArea = new LandingPageMainPanel(new BorderLayout(), "mainArea");
        mainArea.setLayout(new GridBagLayout());
        mainArea.setBackground(new Color(242, 233, 208));

        JPanel buttonArea = new JPanel();
        buttonArea.setLayout(new GridBagLayout());
        buttonArea.setBackground(new Color(242, 233, 208));


        JButton viewProfileButton = new JButton("View/Edit Profile");
        viewProfileButton.setPreferredSize(new Dimension(300, 100));
        viewProfileButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, viewProfileButton.getPreferredSize().height));
        viewProfileButton.setBorder(BorderFactory.createLineBorder(Color.gray));
        viewProfileButton.setBackground(new Color(242, 233, 208));
        viewProfileButton.addActionListener(e -> {

        });
        //mainArea.add(viewProfileButton, BorderLayout.CENTER);

        JButton newContactButton = new JButton("New Contact");
        newContactButton.setPreferredSize(new Dimension(300, 100));
        newContactButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, newContactButton.getPreferredSize().height));
        newContactButton.setBorder(BorderFactory.createLineBorder(Color.gray));
        newContactButton.setBackground(new Color(242, 233, 208));
        newContactButton.addActionListener(e -> {

        });
        //mainArea.add(newContactButton, BorderLayout.CENTER);

        JButton newChatButton = new JButton("New Chat");
        newChatButton.setPreferredSize(new Dimension(300, 100));
        newChatButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, newChatButton.getPreferredSize().height));
        newChatButton.setBorder(BorderFactory.createLineBorder(Color.gray));
        newChatButton.setBackground(new Color(242, 233, 208));
        newChatButton.addActionListener(e -> {

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
        mainAreaConstraints.gridx = 1;
        mainAreaConstraints.gridy = 1;
        mainArea.add(buttonArea, mainAreaConstraints);

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
