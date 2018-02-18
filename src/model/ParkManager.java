package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public final class ParkManager extends User implements Serializable {

    //store of all parks this user manages / is authorized to add jobs for
    //TODO-actually this probably belongs in aPark, not here
    private List<Park> parkList;

    public ParkManager(String firstName, String lastName, UserID userID) {
       super(firstName, lastName, UserRole.PARK_MANAGER, userID);
        parkList = new ArrayList<>();
    }

    //view a list of future jobs, with options to update, create, delete jobs
        //list of all future jobs?
        //or just jobs created by this park manager id?
    //select a job from the list of future jobs
    //tell app to remove that job from the list
    //app confirms if you want to remove that job from list, removal will be permanent
    //remove that job from the list
    //confirm to user that job was just removed from list
    //view the list of future jobs with that job removed

}
