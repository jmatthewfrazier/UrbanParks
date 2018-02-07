package model;

enum UserRole {
    STAFF, PARKMANAGER, VOLUNTEER
}

/**
 * Urban Parks' User class for system user.
 * All roles in Urban Parks' system flow from this class.
 *
 *
 * @date Feb. 6 2018
 * @author Chad Chapman, Eli Shafer
 */
public abstract class User {

    private String firstName;

    private String lastName;

    private UserRole userRole;

    // TODO
    // Decide how to implement this
    private UserID userID;

//    their login name
//    not a good place to store the password I think
//    private String userSystemName;

    public User(String firstName, String lastName, UserRole userRole,
                UserID userID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userRole = userRole;
        this.userID = userID;
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
     * Returns the User's full name
     *
     * @return the User's first and last name
     */
    public String getFullName() { return firstName + " " + lastName; }

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


//    /**
//     * @return the userSystemName
//     */
//    public String getUserSystemName() {
//        return userSystemName;
//    }
//
//    /**
//     * @param userSystemName the userSystemName to set
//     */
//    public void setUserSystemName(String userSystemName) {
//        this.userSystemName = userSystemName;
//    }
}
