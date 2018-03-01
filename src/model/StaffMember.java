package model;

public class StaffMember extends User {

    public StaffMember(String firstName, String lastName, UserID userID) {
        super(firstName, lastName, UserRole.STAFF_MEMBER, userID);
    }
}
