import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.nio.file.Files;

import java.nio.file.Paths;

import javax.swing.DefaultListModel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Notes {

    private String noteTitle;
    private static final String DATA_PATH = "data/";

    /**
     * @param noteTitle Title of the note.
     */
    public Notes(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    /**
     * @param noteTitle Title of the note.
     */
    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    /**
     * @return Title Title of the note.
     */
    public String getNoteTitle() {
        return noteTitle;
    }

    /**
     * Gets all files from the notes directory and lists them.
     * @param model This is the DefaultListModel.
     * @param counter TextField to show number of notes.
     * @param username User's username.
     */
    @SuppressWarnings("unchecked")
    public static void populateNotes(DefaultListModel model, JTextField counter, String username) {
        File directoryPath = new File(DATA_PATH + username + "/notes/");
        String notes[] = directoryPath.list();
        for (String note : notes) {
            model.addElement(note);
        }
        counter.setText("Notes: " + model.size());
    }

    /**
     * Loads selected note (file) in the text area.
     * @param title Title of the note.
     * @param textArea TextArea in which the user edits his/her notes.
     * @param username User's username.
     */
    public static void loadNote(String title, JTextArea textArea, String username) {
        File directoryPath = new File(DATA_PATH + username + "/notes/");
        String notes[] = directoryPath.list();
        for (String note : notes) {
            if (note.equalsIgnoreCase(title)) { // if note found
                try {
                    String data = "";
                    data = new String(Files.readAllBytes(Paths.get(DATA_PATH + username + "/notes/" + title))); // read all data from file
                    textArea.setText(data);
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @param username User's username.
     * @param model DefaultListModel
     * @return 0 if note with same title exists, 1 if note created, 2 if something went wrong.
     */
    public int createNote(DefaultListModel model, String username) {
        try {
            String finalTitle = noteTitle + ".txt";
            for (int i = 0; i < model.size(); i++) {
                if (finalTitle.equals(model.getElementAt(i))) {
                    return 0; // Note with same title exists
                }
            }
            PrintWriter writer = new PrintWriter(DATA_PATH + username + "/notes/" + noteTitle + ".txt", "UTF-8");
            writer.close();
            return 1; // Note created
        } catch (IOException e) {
            e.printStackTrace();
            return 2; // Something went wrong
        }
    }

    /**
     * Deletes selected note.
     * @param title Title of the note.
     * @param username User's username.
     */
    public static void deleteNote(String title, String username) {
        try {
            Files.deleteIfExists(Paths.get(DATA_PATH + username + "/notes/" + title)); // delete note
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves note if "save note" button is pressed.
     * @param title Title of the note.
     * @param content Content of the note to save.
     * @param username User's username.
     */
    public static void saveNote(String title, String content, String username) {
        File directoryPath = new File(DATA_PATH + username + "/notes/");
        String notes[] = directoryPath.list();
        for (String note : notes) {
            if (note.equalsIgnoreCase(title)) {
                try {
                    PrintWriter writer = new PrintWriter(new FileWriter(DATA_PATH + username + "/notes/" + title, false));
                    writer.println(content);
                    writer.close();
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
