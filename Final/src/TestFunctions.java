public class TestFunctions {
    public static void main(String[] args) {
        String invalidPassword = "mypass";
        String validPassword = "mypass1234";
        
        System.out.println("Good password: " + Users.isDecentPassword(invalidPassword));
        System.out.println("Good password: " + Users.isDecentPassword(validPassword));
        
        StatsCalculator stats = new StatsCalculator(175, 70.0D, 25, "Female", "Test", "Name");
        System.out.println(stats.toString());
        System.out.println(stats.calculateBMI());
        System.out.println(stats.calculateIdealWeight());
        System.out.println(stats.calculateCalories());
        System.out.println(stats.myStatus(stats.calculateBMI()));
    }
}