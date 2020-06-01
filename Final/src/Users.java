import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;

import java.util.Scanner;

import javax.swing.JOptionPane;

public class Users {

    private String username;
    private String password;
    private String email;
    private static String currentUser;
    private static final String DATA_FILE = "data/users.dat";
    private static final String USER_DIR = "data/";

    /**
     * @param username User's username
     * @param password User's password
     * @param email User's email
     */
    public Users(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    /**
     * @param username User's username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return username User's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param password User's password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return password User's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param email User's email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return email User's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param passUser username to pass to other frames
     */
    public static void setCurrentUser(String passUser) {
        Users.currentUser = passUser;
    }

    /**
     * @return username of user
     */
    public static String getCurrentUser() {
        return currentUser;
    }

    /**
     * Checks if the credentials are correct.
     * @param username User's username
     * @param password User's password
     * @return true if user exists, false if not.
     */
    public static boolean checkCredentials(String username, String password) {
        try {
            File f = new File(DATA_FILE);
            boolean found = false;
            try (Scanner s = new Scanner(f)) {
                while (s.hasNextLine()) {
                    String line = s.nextLine();
                    line = line.replaceAll("\"", "");
                    String[] row = line.split(",");
                    if (row[0].equals(username) && row[1].equals(password)) {
                        found = true;
                        break; // if found, break loop
                    }
                }
            }
            return found;
        } catch (FileNotFoundException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Adds new users.
     * @param userInfo Final string to write to users.dat file.
     * @param username User's username.
     */
    public static void createUser(String userInfo, String username) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(DATA_FILE, true));
            writer.println(userInfo);
            writer.close();
            File newUser = new File(USER_DIR + username);
            newUser.mkdir(); // create user folder
            JOptionPane.showMessageDialog(null, "Account created successfuly!", "Success",
                                          JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Something went wrong!", "Error", JOptionPane.ERROR);
        }
    }

    /**
     * Creates all files for the user's account.
     * Creates a file which contains the account creation date and time.
     * @param username The username selected by the user.
     */
    public static void createUserFiles(String username) {
        try {
            File activity = new File(USER_DIR + username + "/activity.dat");
            File todo = new File(USER_DIR + username + "/todo.dat");
            File accountCreated = new File(USER_DIR + username + "/created.dat");
            File notes = new File(USER_DIR + username + "/notes");
            activity.createNewFile();
            todo.createNewFile();

            PrintWriter writer = new PrintWriter(new FileWriter(accountCreated, true));
            writer.println(StatsCalculator.findDate() + " | " +
                           StatsCalculator.findTime()); // Generate time and date account was created.
            writer.close();

            notes.mkdir(); // Create notes folder
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Something went wrong!", "Error", JOptionPane.ERROR);
        }
    }

    /**
     * Checks if username is available.
     * @param username User's username
     * @return true if available, false if not.
     */
    public static boolean checkIfUsernameExists(String username) {
        try {
            boolean found = false;
            File f = new File(DATA_FILE);
            try (Scanner s = new Scanner(f)) {
                while (s.hasNextLine()) {
                    String line = s.nextLine();
                    line = line.replaceAll("\"", "");
                    String[] row = line.split(","); // store line to array
                    if (row[0].equals(username)) { // if username exists (row[0] holds the username)
                        found = true;
                        break; // if found, break loop
                    }
                }
            }

            return found;
        } catch (FileNotFoundException e) {
            return false;
        }
    }

    /**
     * Checks if users directory exists.
     * Checks if users.dat file exists in that directory.
     */
    public static void checkIfFileExists() {
        File userDir = new File("data");
        File userAcc = new File("data/users.dat");
        if (!userDir.exists()) { // if folder doesn't exist. Create it.
            userDir.mkdir(); // create directory
            if (!userAcc.exists()) {
                try {
                    userAcc.createNewFile(); // create file to store accounts/users
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Checks if the password is decent or not.
     * @param password User's password
     * @return true if password is ok, false if not
     */
    public static boolean isDecentPassword(String password) {
        boolean isDecent = false;
        if (password.length() >= 6) {
            char[] passArr = password.toCharArray();
            for (char pass : passArr) {
                if (Character.isDigit(pass)) {
                    isDecent = true;
                }
            }
            
            return isDecent;
        } else {
            return false;
        }
    }

    /**
     * @return User's information
     */
    @Override
    public String toString() {
        if (!email.isEmpty()) {
            return username + "," + password + "," + email;    
        } else {
            return username + "," + password + ",unknown";
        }
    }
}
