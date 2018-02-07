package model;

import java.util.HashSet;

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
public abstract class User {

    //sprivate Enum pmRole = new Enum("Park Manager");

    private String firstName = "";
    private String lastName = "";

    private String userRole = ""; //should probably use Enums for roles.
    private String userSystemName = ""; //their login name

    private HashSet<String> possibleUserRolesSet;

    public User(final String fName, final String lName
                , final String uRole
                , final String uLogin) {

        possibleUserRolesSet = new HashSet<>();
        firstName = fName;
        lastName = lName;
        userRole = uRole;
        userSystemName = uLogin;

        setupUser();

    }

    private void setupUser() {
        //TODO-validate a user name, etc in the User class?
        //TODO-yes, how we do it in Job class also
        
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
    public String getUserRole() {
        return userRole;
    }
    /**
     * @param userRole the userRole to set
     */
    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
    /**
     * @return the userSystemName
     */
    public String getUserSystemName() {
        return userSystemName;
    }
    /**
     * @param userSystemName the userSystemName to set
     */
    public void setUserSystemName(String userSystemName) {
        this.userSystemName = userSystemName;
    }
}
