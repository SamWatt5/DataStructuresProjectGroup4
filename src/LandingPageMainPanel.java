import javax.swing.*;
import java.awt.*;

/**
 * Class that creates a JPanel with a name
 * @author Sam Watts, Jonah Phillipson-Masters, Harrison Reed
 */
public class LandingPageMainPanel extends JPanel {

    private String name;

    /**
     * Constructor for the LandingPageMainPanel class
     * @param layout - the layout of the panel
     * @param name - the name of the panel
     */
    public LandingPageMainPanel(BorderLayout layout, String name){
        this.name = name;
        setLayout(layout);
    }

    @Override
    public String getName(){
        return name;
    }
}
