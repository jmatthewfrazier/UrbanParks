package model;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {

    private final String firstName;
    private final String lastName;
    private final UserRole userRole;
    private final UserID userID;

    protected User(final String firstName, final String lastName,
                final UserRole userRole, final UserID userID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userRole = userRole;
        this.userID = userID;
    }

    public static User getNullUser() {
        return new User("", "", UserRole.NULL_USER,
                new UserID("null_user"));
    }

    public final String getFirstName() {
        return this.firstName;
    }

    public final String getLastName() {
        return this.lastName;
    }

    public final UserRole getUserRole() {
        return this.userRole;
    }

    public final String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    public final UserID getID() {
        return this.userID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (this == null) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        final User other = (User) o;
        return this.firstName.equals(other.firstName)
                && this.lastName.equals(other.lastName)
                && this.userRole.equals(other.userRole)
                && this.userID.equals(other.userID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, userRole, userID);
    }
}
