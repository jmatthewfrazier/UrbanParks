package model;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Represents a collection of Users.
 */
public final class UserCollection extends HashMap<UserID, User>
        implements Serializable{

    /**
     * Maps a UserID to a User if the UserID is not already contained within
     * this map's key set.
     *
     * Pre: UserID does not exist within the collection.
     */
    @Override
    public final User put(UserID userID, User user) {
        if (!this.containsKey(userID)) {
            super.put(userID, user);
        }
        return user;
    }

}
