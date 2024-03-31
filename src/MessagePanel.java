import javax.swing.*;
import java.awt.*;

/**
 * The message panel, containing a name
 * @author Sam Watts, Jonah Phillipson-Masters, Harrison Reed
 */
public class MessagePanel extends JPanel {
    private String name;

    /**
     * Constructor for the message panel
     * @param layout - the layout of the panel
     * @param name - the name of the panel
     */
    public MessagePanel(BorderLayout layout, String name){
        this.name = name;
        setLayout(layout);
    }

    /**
     * Get the name of the panel
     * @return the name of the panel
     */
    @Override
    public String getName(){
        return name;
    }
}
