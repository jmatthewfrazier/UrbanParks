package model;

import java.io.Serializable;
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
public class ParkManager extends User implements Serializable {

    //store of all parks this user manages / is authorized to add jobs for
    private ArrayList<Park> managedParkList  = new ArrayList<Park>();


    public ParkManager() {
       // super();

    }




    //end of Park Manager class
}
