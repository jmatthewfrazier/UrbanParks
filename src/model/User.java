package model;

import java.io.Serializable;

enum UserRole {
    ADMIN, PARK_MANAGER, VOLUNTEER
}

/*
 * Urban Parks' User class for system user.
 * All roles in Urban Parks' system flow from this class.
 *
 *
 * @date Feb. 4 2018
 * @author Chad Chapman
 *
 *
 */
public class User implements Serializable {

    //sprivate Enum pmRole = new Enum("Park Manager");

    private String firstName;
    private String lastName;

    private UserRole userRole; //should probably use Enums for roles.
    private UserID userID; //their login name

//    private HashSet<String> possibleUserRolesSet;



    public User(final String fName, final String lName
                , final UserRole userRole
                , final UserID userID) {

//        possibleUserRolesSet = new HashSet<>();
        this.firstName = fName;
        this.lastName = lName;
        this.userRole = userRole;
        this.userID = userID;

        setupUser();

    }

    private void setupUser() {
        //TODO-validate a user name, etc in the User class?
        //TODO-yes, how we do it in Job class also
        //add all user role types to the set
//        populateUserRolesSet();
//        verifyUserSystemName();
//        assignUserRole();

    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }
    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }
    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    /**
     * @return the userRole
     */
    public UserRole getUserRole() {
        return userRole;
    }
    /**
     * @param userRole the userRole to set
     */
    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
    /**
     * @return the userSystemName
     */
    public UserID getUserSystemName() {
        return userID;
    }
    /**
     * @param userID the userSystemName to set
     */
    public void setUserSystemName(UserID userID) {
        this.userID = userID;
    }
}
