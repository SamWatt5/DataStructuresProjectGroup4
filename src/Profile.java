import javax.swing.*;
import java.awt.*;
import java.io.*;

/**
 * the class used to store the user's profile information
 * @author Sam Watts, Jonah Phillipson-Masters, Harrison Reed
 */
public class Profile {
    private String name;
    private String number;
    private ImageIcon profilePic;

    private ImageIcon profilePicScaled;
    private String pathToProfilePic;

    /**
     * constructor for the profile class
     * @param name - the name of the user
     * @param number - the phone number of the user
     */
    public Profile(String name, String number) {
        this.name = name;
        this.number = number;
        this.pathToProfilePic = "defaultProfilePic.png";
        setProfilePic(pathToProfilePic);
    }

    /**
     * gets the name of the user
     * @return the name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * gets the phone number of the user
     * @return the phone number of the user
     */
    public String getNumber() {
        return number;
    }

    /**
     * gets the profile picture of the user
     * @return the profile picture of the user
     */
    public ImageIcon getProfilePic() {
        return profilePic;
    }

    /**
     * sets the name of the user
     * @param name - the name of the user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * sets the phone number of the user
     * @param number - the phone number of the user
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * sets the profile picture of the user
     * @param newPathToProfilePic - the file path to the new profile picture
     */
    public void setProfilePic(String newPathToProfilePic) {
        this.pathToProfilePic = newPathToProfilePic;
        ImageIcon unscaledImage = new ImageIcon(LandingPage.class.getResource(pathToProfilePic));
        Image scaledImage = unscaledImage.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        this.profilePic = new ImageIcon(scaledImage);
    }

    /**
     * gets the file path to the profile picture
     * @return the file path to the profile picture
     */
    public String getPathToProfilePic() {
        return pathToProfilePic;
    }

    /**
     * get the scaled profile picture imageIcon
     * @return
     */
    public ImageIcon getProfilePicScaled() {
        Image scaledImage = profilePic.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        this.profilePicScaled = new ImageIcon(scaledImage);
        return profilePicScaled;
    }

    /**
     * sets the default profile information
     */
    public void setDefaultProfile(){
        this.name = "John Doe";
        this.number = "1234567890";
        this.pathToProfilePic = "defaultProfilePic.png";
        setProfilePic(pathToProfilePic);
    }

    /**
     * saves the profile to a file
     */
    public void saveProfile(){
        try{
            PrintWriter printWriter = new PrintWriter(new FileOutputStream( "files/profile.txt"));
            printWriter.println(getPathToProfilePic() + "%% 101010PROFILESPLIT010101 %%" + name + "%% 101010PROFILESPLIT010101 %%" + number);
            printWriter.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * loads the profile from a file
     */
    public void loadProfile() {
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        String nextLine;
        try {
            fileReader = new FileReader("files/profile.txt");
            bufferedReader = new BufferedReader(fileReader);
            while ((nextLine = bufferedReader.readLine()) != null) {
                String[] contactInfo = nextLine.split("%% 101010PROFILESPLIT010101 %%");
                this.pathToProfilePic = contactInfo[0];
                setProfilePic(pathToProfilePic);
                this.name = contactInfo[1];
                this.number = contactInfo[2];
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
