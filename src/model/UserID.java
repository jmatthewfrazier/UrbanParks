package model;

import java.io.Serializable;
import java.util.Objects;

public final class UserID implements Serializable,  Comparable<UserID> {

    private final String userID;

    public UserID(String userID) {
        this.userID = userID;
    }

    @Override
    public final String toString() {
        return this.userID;
    }

    @Override
    public int compareTo(UserID other) {
        return this.userID.compareTo(other.userID);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || other instanceof UserID
                && this.userID.equals(((UserID) other).userID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID);
    }
}