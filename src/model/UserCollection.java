package model;

import java.util.HashMap;

/**
 * Represents the collection of Users.
 *
 * @author Eli Shafer
 * @date 2/6/18
 */
public class UserCollection extends HashMap<UserID, User> {

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

}
