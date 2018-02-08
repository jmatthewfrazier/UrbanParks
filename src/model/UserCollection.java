package model;

import exceptions.UserCollectionDuplicateKeyException;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Represents the collection of Users.
 *
 * @author Eli Shafer, Chad Chapman
 * @date 2/6/18
 */
public class UserCollection extends HashMap<UserID, User> implements Serializable {

    private String responseStatusMessage = "";
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
        //check the login
        try {
            if (this.containsKey(userID)) {
                throw new UserCollectionDuplicateKeyException();
            }
            else {
                super.put(userID, user);
            }
        } catch (UserCollectionDuplicateKeyException ex) {
            //if that didn't work for some reason, throw exception
            responseStatusMessage = ex.getMessage();
            //maybe put a toString from the user param in here too?
        }
        return user;
    }

    public String getResponseStatusMessage() {
        return responseStatusMessage;
    }

//    public void setResponseStatusMessage(String responseStatusMessage) {
//        this.responseStatusMessage = responseStatusMessage;
//    }

}
