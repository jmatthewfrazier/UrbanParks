package model;

import java.io.Serializable;
import java.util.Objects;

public final class UserID implements Serializable,  Comparable<UserID> {

    private String userID;

    // TODO LATER ON: PASSWORD

    public UserID(String userID) {

        this.userID = userID;
    }

    public final String getUserID() {

        return this.userID;
    }

    public int compareTo(UserID other) {
        return this.userID.compareTo(other.userID);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
	        return true;
        }
        if (!(other instanceof UserID)) {
	        return false;
        }
        return this.userID.equals(((UserID) other).userID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID);
    }
}