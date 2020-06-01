import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.io.PrintWriter;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.List;

import javax.swing.JOptionPane;


public class Account {

    private static final String DATA_FILE = "data/";

    /**
     * Empty constructor.
     */
    public Account() {
    }

    /**
     * Get creation time and date from created.dat file.
     * @param username Username selected by the user.
     * @return account creation date
     */
    public static String getCreationTime(String username) {
        try {
            String creationDate = new String(Files.readAllBytes(Paths.get(DATA_FILE + username + "/created.dat")));
            return creationDate;
        } catch (IOException e) {
            e.printStackTrace();
            return "An error occured.";
        }
    }

    /**
     * Recursively deletes the user account.
     * Source: https://www.baeldung.com/java-delete-directory
     * @param toBeDeleted Directory to be deleted.
     * @return true if deleted, false if not.
     */
    public static boolean deleteRecursively(File toBeDeleted) {
        try {
            File[] allContents = toBeDeleted.listFiles(); // store content of directory to array
            if (allContents != null) { // check if there's any content
                for (File file : allContents) {
                    deleteRecursively(file);
                }
            }
            return toBeDeleted.delete();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Remove user from users.dat file.
     * @param username Username selected by the user.
     */
    public static void removeUser(String username) {
        try {
            List<String> users = Files.readAllLines(Paths.get(DATA_FILE + "users.dat")); // load users to a list
            int i = 0;
            int indexToRemove = 0;
            boolean found = false;
            for (String user : users) {
                String[] line = user.split(",");
                if (line[0].equals(username)) { // line[0] is the username
                    indexToRemove = i; // store index to variable to avoid ConcurrentModificationException
                    found = true;
                    break;
                }
                i++;
            }

            if (found) {
                users.remove(indexToRemove);
                PrintWriter writer = new PrintWriter(new FileWriter(DATA_FILE + "/users.dat", false));
                for (String user : users) {
                    writer.println(user);
                }
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Completely deletes the user's account and
     * closes the application.
     * @param username Username selected by the user.
     */
    public static void deleteAccount(String username) {
        int userChoice =
            JOptionPane.showConfirmDialog(null,
                                          "Are you sure you want to delete your account? This action is permanent and your data can't be recovered!",
                                          "WARNING", JOptionPane.YES_NO_OPTION);
        if (userChoice == JOptionPane.YES_OPTION) {
            File userAccount = new File(DATA_FILE + username);
            boolean result = deleteRecursively(userAccount);
            if (result) {
                removeUser(username);
                JOptionPane.showMessageDialog(null, "The program will now exit.", "Information",
                                              JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
        }
    }

    /**
     * Deletes the contents of the activity.dat file used
     * to populate the activityTable.
     * @param username Username selected by the user.
     */
    public static void deleteActivity(String username) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(DATA_FILE + username + "/activity.dat", false));
            writer.print("");
            writer.close();
            JOptionPane.showMessageDialog(null, "All activity was deleted!", "Information",
                                          JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Something went wrong!", "ERROR", JOptionPane.ERROR);
            e.printStackTrace();
        }
    }

    /**
     * Changes the password of the account.
     * @param username Username selected by the user.
     * @param password New Password.
     * @param oldPassword Current (old) password.
     */
    public static void changePassword(String username, String password, String oldPassword) {
        try {
            List<String> users = Files.readAllLines(Paths.get(DATA_FILE + "users.dat")); // load users to a list
            int i = 0;
            boolean changed = false;
            for (String user : users) {
                String[] line = user.split(",");
                if (line[0].equals(username)) { // if  user location found
                    if (line[1].equals(oldPassword)) { // if old password is correct
                        line[1] = line[1].replaceAll(line[1], password); // Change password
                        String finalLine = line[0] + "," + line[1] + "," + line[2]; // Create final string (user)
                        users.set(i, finalLine);
                        changed = true; // password changed
                        break;
                    }
                }
                i++;
            }

            if (changed) {
                PrintWriter writer = new PrintWriter(new FileWriter(DATA_FILE + "users.dat", false));
                for (String user : users) {
                    writer.println(user);
                }
                writer.close();
                JOptionPane.showMessageDialog(null, "Password changed!", "Information",
                                              JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Something went wrong :(", "Error", JOptionPane.ERROR);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "An error occured.", "ERROR", JOptionPane.ERROR);
            e.printStackTrace();
        }
    }

}
