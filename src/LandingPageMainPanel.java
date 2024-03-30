import javax.swing.*;
import java.awt.*;

public class LandingPageMainPanel extends JPanel {

    private String name;

    public LandingPageMainPanel(BorderLayout layout, String name){
        this.name = name;
        setLayout(layout);
    }

    @Override
    public String getName(){
        return name;
    }
}
