import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.Scanner;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

public class ToDo {

    private String toDo;
    private static final String DATA_FILE = "data/";

    /**
     * @param toDo To-Do item
     */
    public ToDo(String toDo) {
        this.toDo = toDo;
    }

    /**
     * @param toDo To-Do item
     */
    public void setToDo(String toDo) {
        this.toDo = toDo;
    }

    /**
     * @return toDo To-Do item
     */
    public String getToDo() {
        return toDo;
    }

    /**
     * Loads to-do items.
     * @param model DefaultListModel
     * @param username User's username.
     */
    @SuppressWarnings("unchecked")
    public static void loadToDo(DefaultListModel model, String username) {
        File f = new File(DATA_FILE + username + "/todo.dat");
        try (Scanner s = new Scanner(f)) {
            while (s.hasNextLine()) {
                String line = s.nextLine();
                model.addElement(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Couldn't load your to-do list.", "Error",
                                          JOptionPane.ERROR);
        }
    }

    /**
     * Saves to-do items.
     * @param model DefaultListModel
     * @param username User's username.
     * @throws IOException Possible IO Exception, since we are dealing with files.
     */
    public static void saveToDo(DefaultListModel model, String username) throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter(DATA_FILE + username + "/todo.dat", false));
        for (int i = 0; i < model.size(); i++) {
            writer.println(model.get(i));
        }
        writer.close();
    }

    /**
     * Adds new item to the to-do list.
     * @param item To-Do item
     * @param model DefaultListModel
     * @param username User's username.
     */
    public void addToDo(String item, DefaultListModel model, String username) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(DATA_FILE + username + "/todo.dat", true));
            writer.println(item);
            writer.close();
            model.addElement(item);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return object as string
     */
    @Override
    public String toString() {
        return toDo;
    }

}
