import javax.swing.*;
import java.awt.*;

public class Profile {
    String name;
    String number;
    ImageIcon profilePic;

    ImageIcon profilePicScaled;

    public Profile(String name, String number, ImageIcon profilePic) {
        this.name = name;
        this.number = number;
        this.profilePic = profilePic;
        Image scaledImage = profilePic.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        this.profilePicScaled = new ImageIcon(scaledImage);
    }
    public Profile(String name, String number) {
        this.name = name;
        this.number = number;
        this.profilePic = new ImageIcon("images/defaultProfilePic.png");
        Image scaledImage = profilePic.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        this.profilePicScaled = new ImageIcon(scaledImage);
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public ImageIcon getProfilePic() {
        return profilePic;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setProfilePic(ImageIcon profilePic) {
        this.profilePic = profilePic;
    }

    public ImageIcon getProfilePicScaled() {
        return profilePicScaled;
    }

    public void setProfilePicScaled(ImageIcon profilePicScaled) {
        this.profilePicScaled = profilePicScaled;
    }
}
