package model;

public class Volunteer extends User {

    public Volunteer() {
        this("Test", "Volunteer", new UserID("volunteer_default"));
    }

    public Volunteer(String firstName, String lastName, UserID userID) {
        super(firstName, lastName, UserRole.VOLUNTEER, userID);
    }

}
