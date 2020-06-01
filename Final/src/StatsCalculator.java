import java.io.FileWriter;
import java.io.PrintWriter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class StatsCalculator extends BasicInformation {

    private int height;
    private double weight;
    private int age;
    private String gender;
    private Status userStatus;

    /**
     * The constructor of the class.
     * @param height User's height
     * @param weight User's weight
     * @param age User's age
     * @param gender User's gender
     * @param name User's name
     * @param surname User's surname
     */
    public StatsCalculator(int height, double weight, int age, String gender, String name, String surname) {
        super(name, surname);
        this.height = height;
        this.weight = weight;
        this.age = age;
        this.gender = gender;
    }

    /**
     * Calculates and returns the BMI.
     * @return BMI result
     */
    public double calculateBMI() {
        return Math.round((100 * 100 * weight) / (height * height) * 100.0) / 100.0;
    }

    /**
     * Calculates and returns the Ideal Weight.
     * @return specific results for each gender.
     */
    public double calculateIdealWeight() {
        if (gender.equals("Male")) {
            return 50 + 0.9 * (height - 152);
        } else {
            return 45.5 + 0.9 * (height - 152);
        }
    }
    
    /**
     * Calculates and returns the calories.
     * @return specific results for each gender.
     */
    public double calculateCalories() {
        if (gender.equals("Male")) {
            return (10 * weight) + (6.25 * height) - (5 * age) + 5;
        } else {
            return (10 * weight) + (6.25 * height) - (5 * age) + 161;
        }
    }

    /**
     * Calculates and returns the average BMI and Weight.
     * @param activityTable Activity history table
     * @return Average BMI and Weight.
     */
    public static String calculateStats(JTable activityTable) { // BMI and Weight average
        double bmiSum = 0;
        double weightSum = 0;
        if (activityTable.getRowCount() > 0) {
            for (int i = 0; i < activityTable.getRowCount(); i++) {
                weightSum += Double.parseDouble(activityTable.getValueAt(i, 4).toString());
                bmiSum += Double.parseDouble(activityTable.getValueAt(i, 5).toString());
            }

            double finalBMI = Math.round(bmiSum / activityTable.getRowCount() * 100.0) / 100.0;
            double finalWeight = Math.round(weightSum / activityTable.getRowCount() * 100.0) / 100.0;

            return "Average BMI: " + finalBMI + "\nAverage weight: " + finalWeight + " kg";
        } else {
            return "There is no activity to calculate the average BMI and Weight!";
        }
    }
    
    /**
     * Returns the category in which the user belongs to.
     * @param bmi User's Body Mass Index (BMI)
     * @return User category based on on BMI result.
     */
    public Status myStatus(double bmi) {
        if (bmi < 18.5D) {
            userStatus = Status.UNDERWEIGHT;
        } else if (bmi >= 18.5D && bmi <= 24.9D) {
            userStatus = Status.NORMAL;
        } else if (bmi >= 25 && bmi <= 29.9D) {
            userStatus = Status.OVERWEIGHT;
        } else {
            userStatus = Status.OBESE;
        }
        return userStatus;
    }

    /**
     * Gets current time.
     * @return current time
     */
    public static String findTime() {
        String time;
        LocalTime myTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm a");
        time = myTime.format(formatter);
        return time;
    }

    /**
     * Gets current date.
     * @return current date
     */
    public static String findDate() {
        String date;
        LocalDate myDate = LocalDate.now();
        date = myDate.toString();
        return date;
    }
    
    /**
     * Exports the results to a user specified directory.
     * @param name Provided name
     * @param surname Provided surname
     * @param bmi BMI Result
     * @param category Category result
     * @param ideal Ideal Weight result
     * @param calories Calories per day results
     */
    public static void exportResults(String name, String surname, String bmi, String category, String ideal, String calories) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // Set selection to directories only
        if (!bmi.equals("BMI Result: ~")) { // Check if user has used the calculator
            int retrival = chooser.showSaveDialog(null);
            if (retrival == JFileChooser.APPROVE_OPTION) {
                try {
                    PrintWriter writer = new PrintWriter(new FileWriter(chooser.getSelectedFile() + "/" + StatsCalculator.findDate() + ".txt", true));
                    writer.println("Report for " + name + " " + surname + "\n");
                    writer.println(bmi + "\n" + category + "\n" + ideal + "\n" + calories);
                    writer.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please use the tool first to export the results!", "Information",
                                          JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    @Override
    public String toString() {
        return height + " | " + weight + " | " + age + " | " + gender + " | " + super.getName() + " " + super.getSurname();
    }

}
