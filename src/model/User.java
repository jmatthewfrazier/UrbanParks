package model;

import java.io.Serializable;

/**
 * Represents an Urban Parks user.
 */
public class User implements Serializable {

//    static final long serial

    private String firstName;
    private String lastName;
    private UserRole userRole;
    private UserID userID;

    protected User(final String firstName, final String lastName,
                final UserRole userRole, final UserID userID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userRole = userRole;
        this.userID = userID;
    }

    public static final User getNullUser() {
        return new User("", "", UserRole.NULL_USER,
                new UserID("null_user"));
    }

    public final String getFirstName() {
        return firstName;
    }

    public final String getLastName() { return lastName; }

    public final UserRole getUserRole() {
        return userRole;
    }

    public final String getFullName() {
        return firstName + " " + lastName;
    }

    public final UserID getID() {
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
