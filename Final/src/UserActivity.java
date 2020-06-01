import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class UserActivity extends BasicInformation {

    private String date;
    private String time;
    private double bmi;
    ArrayList<UserActivity> activityList = new ArrayList<>();
    private static final String DATA_FILE = "data/";

    /**
     * The constructor of the class.
     * @param date Current date
     * @param time Current time
     * @param name User's name 
     * @param surname User's surname
     * @param bmi User's BMI
     */
    public UserActivity(String date, String time, String name, String surname, double bmi) {
        super(name, surname);
        this.date = date;
        this.time = time;
        this.bmi = bmi;
    }

    /**
     * @param date Current date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /** 
     * @return date Current date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param time Current time
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * @return Current time
     */
    public String getTime() {
        return time;
    }

    /**
     * @param bmi User's BMI
     */
    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    /**
     * @return bmi User's BMI
     */
    public double getBmi() {
        return bmi;
    }

    /**
     * Saves user's results to the activity.dat file.
     * @param information User's results from the calculator
     * @param username User's username
     */
    public void saveToFile(String information, String username) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(DATA_FILE + username + "/activity.dat", true));
            writer.println(information);
            writer.close(); // close file to make it available
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Loads all data to the table from the activity.dat file.
     * @param model JTable's model
     * @param username User's username
     */
    public static void loadDataToTable(DefaultTableModel model, String username) {
        try {
            File f = new File(DATA_FILE  + username + "/activity.dat");
            BufferedReader br = new BufferedReader(new FileReader(f));
            Object[] tableLines = br.lines().toArray();
            for (int i = 0; i < tableLines.length; i++) {
                String line = tableLines[i].toString().trim();
                String[] dataRow = line.split("/");
                model.addRow(dataRow);
            }
            br.close(); // close buffered reader
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This function is used to save any modification to the activity history table.
     * @param activityTable Activity Table
     * @param toAppend Overwrite everything or not
     * @param username User's username
     */
    public static void saveActivityChanges(JTable activityTable, boolean toAppend, String username) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(DATA_FILE  + username + "/activity.dat", toAppend));
            for (int row = 0; row < activityTable.getRowCount(); row++) {
                for (int col = 0; col < activityTable.getColumnCount(); col++) {
                    if (activityTable.getColumnCount() - col == 1) { // Check if it is the last object in row
                        writer.write(activityTable.getValueAt(row, col).toString());
                    } else {
                        writer.write(activityTable.getValueAt(row, col).toString() + "/");
                    }
                }
                writer.write("\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates the columns for the model of JTable.
     * @param model DefaultTableModel
     */
    public static void populateModel(DefaultTableModel model) {
        model.addColumn("#");
        model.addColumn("Name");
        model.addColumn("Date");
        model.addColumn("Time");
        model.addColumn("Weight (kg)");
        model.addColumn("BMI");
        model.addColumn("Category");
    }

    /**
     * Generates the string saved to the activity.dat file.
     * @param weight User's weight
     * @param category User's category (Normal, Overweight etc...)
     * @param number Counting entries
     * @return String to be saved in specific format.
     */
    public String generateStringToSave(double weight, Status category, int number) {
        return number + "/" + super.getName() + " " + super.getSurname() + "/" + date + "/" + time + "/" + weight +
               "/" + bmi + "/" + category;
    }

}
