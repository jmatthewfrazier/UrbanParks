package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public final class UserCollection implements Serializable {

    private Map<UserID, User> userMap;

    public UserCollection() {
        this.userMap = new HashMap<>();
    }

    public final User getUser(final UserID userID) {
        return this.userMap.get(userID);
    }

    public final boolean containsUserID(final UserID newUserID) {
        for (final UserID userID : this.userMap.keySet()) {
            if (userID.equals(newUserID)) {
                return true;
            }
        }
        return false;
    }

    public boolean isEmpty() {
        return userMap.isEmpty();
    }

    /**
     * Maps a UserID to a User if the UserID is not already contained within
     * this map's key set.
     *
     * Pre: UserID does not exist within the collection.
     */
    public final void addUser(final User user) {
        if (!this.userMap.containsKey(user.getID())) {
            this.userMap.put(user.getID(), user);
        }
    }
}
