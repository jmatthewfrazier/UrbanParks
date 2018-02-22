package model;

import exceptions.JobIDNotFoundInCollectionException;
import exceptions.LessThanMinDaysAwayException;
import exceptions.UserNotFoundException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public final class ParkManager extends User implements Serializable {

    //store of all parks this user manages / is authorized to add jobs for
    //TODO-actually this probably belongs in aPark, not here
    private List<Park> parkList;

    private List<Job> jobsSubmittedByMe;

    JobCollection jobCollection;

    public ParkManager(String firstName, String lastName, UserID userID, JobCollection jobCollection) {
       super(firstName, lastName, UserRole.PARK_MANAGER, userID);
        parkList = new ArrayList<>();
        jobsSubmittedByMe = new ArrayList<>();
        this.jobCollection = jobCollection;
    }

    public List<Job> fetchFutureJobsSubmittedByMe() {
        return jobsSubmittedByMe;
    }
    //view a list of future jobs, with options to update, create, delete jobs
        //list of all future jobs?
        //or just jobs created by this park manager id?
    
    //select a job from the list of future jobs
    //tell app to remove that job from the list
    public void removeFutureJobFromCollection(final Job jobToRemove) {
        //TODO-app confirms if you want to remove that job from list, removal will be permanent
        JobID jobToRemoveID = jobToRemove.getID();
        try { //remove that job from the list
            jobCollection.removeJobFromCollection(jobToRemoveID, this.getID());
        } catch (LessThanMinDaysAwayException e) {
            //TODO-show these messages somewhere
            e.getMsgString();
        } catch (UserNotFoundException e) {
            e.getMsgString();
        } catch (JobIDNotFoundInCollectionException e) {
            e.getMsgString();
        }
        //TODO - confirm to user that job was just removed from list
        //TODO - view the list of future jobs with that job removed?
    }


    public List<Park> getParkList() {
        return parkList;
    }
}
