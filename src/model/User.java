package model;

import java.io.Serializable;

enum UserRole {
    PARK_MANAGER, VOLUNTEER
}

/**
 * Represents an Urban Parks user.
 */
public abstract class User implements Serializable {

//    static final long serial

    private String firstName;
    private String lastName;
    private UserRole userRole;
    private UserID userID;

    public User(final String firstName, final String lastName,
                final UserRole userRole, final UserID userID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userRole = userRole;
        this.userID = userID;
//        setupUser();
    }

//    private void setupUser() {
//        //TODO-validate a user name, etc in the User class?
//        //TODO-yes, how we do it in Job class also
//        add all user role types to the set
//        populateUserRolesSet();
//        verifyUserSystemName();
//        assignUserRole();
//    }

    public final String getFirstName() {
        return firstName;
    }

    public final String getLastName() { return lastName; }

    public final UserRole getUserRole() {
        return userRole;
    }

    public final UserID getUserSystemName() {
        return userID;
    }

    public final void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public final void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public final void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public final void setUserSystemName(UserID userID) {
        this.userID = userID;
    }

}
