package model;

import java.util.Objects;

/**
 * Represents the User ID.
 *
 * @author Eli Shafer
 * @date 2/6/18
 */
public final class UserID implements Comparable<UserID> {


    String userID;

//        String password;

    public UserID(String userID) {
        this.userID = userID;
    }

    @Override
    public int compareTo(UserID other) {
        return this.userID.compareTo(other.userID);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this)
            return true;
        if (!(other instanceof UserID))
            return false;
        return this.userID.equals(((UserID) other).userID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID);
    }
}