package model;

import java.util.HashMap;
import java.util.Objects;

/**
 * Represents the collection of Users.
 *
 * @author Eli Shafer
 * @date 2/6/18
 */
public class UserCollection extends HashMap<UserCollection.UserID, User> {

    /**
     * Maps a UserID to a User if the UserID is not already contained within
     * this map's key set.
     *
     * @param userID the key to the User
     * @param user the User that is being entered into the map
     * @return the user
     */
    @Override
    public User put(UserID userID, User user) {
        if (!this.containsKey(userID))
            super.put(userID, user);
        return user;
    }

    /**
     * Represents the User ID.
     *
     * @author Eli Shafer
     * @date 2/6/18
     */
    public class UserID implements Comparable<UserID> {


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

}
