package model;

import exceptions.UserNotFoundException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a collection of Users.
 */
public final class UserCollection implements Serializable {

    private Map<UserID, User> userMap;

    public UserCollection() {
        userMap = new HashMap<>();
    }

    public final User getUser(UserID userID) {
        return userMap.get(userID);
    }

    public final boolean containsUserID(UserID newUserID) {
        boolean result = false;
        for (UserID userID : userMap.keySet()) {
            if (userID.equals(newUserID)) {
                result = true;
            }
        }
        return result;
    }

    /**
     * Maps a UserID to a User if the UserID is not already contained within
     * this map's key set.
     *
     * Pre: UserID does not exist within the collection.
     */
    public final void addUser(User user) {
        if (!userMap.containsKey(user.getID())) {
            userMap.put(user.getID(), user);
        }
    }

    public final User getUserFromUserID(final UserID userID)
            throws UserNotFoundException{
        User genericUser;
        if (!userMap.containsKey(userID)) {
            throw new UserNotFoundException("No user associated with that UserID");
        } else {
            genericUser = userMap.get(userID);
        }
        return genericUser;
    }

	public boolean isEmpty() {
		return userMap.isEmpty();
	}
}
