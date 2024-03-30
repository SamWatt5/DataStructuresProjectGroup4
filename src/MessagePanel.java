import javax.swing.*;
import java.awt.*;

public class MessagePanel extends JPanel {
    private String name;

    public MessagePanel(BorderLayout layout, String name){
        this.name = name;
        setLayout(layout);
    }

    @Override
    public String getName(){
        return name;
    }
}
