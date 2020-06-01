public class BasicInformation {

    private String name;
    private String surname;

    /**
     * @param name Name of user.
     * @param surname Surname of user.
     */
    public BasicInformation(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    /**
     * @param name Name of user.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return name Name of user.
     */
    public String getName() {
        return name;
    }

    /**
     * @param surname Surname of user.
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * @return surname Surname of user.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * @return object to string
     */
    @Override 
    public String toString() {
        return name + " | " + surname;
    }

}