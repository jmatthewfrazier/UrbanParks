package model;

import java.util.ArrayList;

/*
 * Class representing a Park Manager type of User class.
 * Extends User class.
 * Currently should be able to submit a new Job to
 * Urban Parks' Job List
 *
 * @date Feb. 4 2018
 * @author Chad Chapman
 *
 *
 */
public class ParkManager extends User {

    //store of all parks this user manages / is authorized to add jobs for
    private ArrayList<Park> managedParkList  = new ArrayList<Park>();


    public ParkManager() {
        this("Park", "Manager", new UserID("park_manager_default"));
    }

    public ParkManager(String firstName, String lastName, UserID userID) {
        super(firstName, lastName, UserRole.PARKMANAGER, userID);
    }




    //end of Park Manager class
}
