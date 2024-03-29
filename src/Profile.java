import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Profile {
    String name;
    String number;
    ImageIcon profilePic;

    private ImageIcon profilePicScaled;

    public Profile(String name, String number, ImageIcon profilePic) {
        this.name = name;
        this.number = number;
        this.profilePic = profilePic;
        Image scaledImage = profilePic.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        this.profilePicScaled = new ImageIcon(scaledImage);
    }
    public Profile(String name, String number) {
        this.name = name;
        this.number = number;
        this.profilePic = new ImageIcon("src/images/defaultProfilePic.png");
        Image scaledImage = profilePic.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
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

    public void setDefaultProfile(){
        this.name = "John Doe";
        this.number = "1234567890";
        this.profilePic = new ImageIcon("src/images/defaultProfilePic.png");
        Image scaledImage = profilePic.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        this.profilePicScaled = new ImageIcon(scaledImage);
    }

    public void setProfilePicScaled(ImageIcon profilePicScaled) {
        this.profilePicScaled = profilePicScaled;
    }

    public void saveProfile(){
        try{
            PrintWriter printWriter = new PrintWriter(new FileOutputStream( "files/profile.txt"));
            printWriter.println(name + "%% 101010PROFILESPLIT010101 %%" + number);
            printWriter.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void loadProfile() {
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        String nextLine;
        try {
            fileReader = new FileReader("files/profile.txt");
            bufferedReader = new BufferedReader(fileReader);
            while ((nextLine = bufferedReader.readLine()) != null) {
                String[] contactInfo = nextLine.split("%% 101010PROFILESPLIT010101 %%");
                this.name = contactInfo[0];
                this.number = contactInfo[1];
            }
        } catch (FileNotFoundException e) {
            System.out.println("Profile file not found, using default profile.");
            setDefaultProfile();

        } catch (IOException e) {
            System.out.println("There is a problem opening or reading from the file, using default profile.");
            setDefaultProfile();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    System.out.println("File was not properly opened. Using default profile.");
                    setDefaultProfile();

                }
            }
        }
    }
}
