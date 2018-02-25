package model;

import controller.Controller;
import exceptions.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class ParkManager extends User implements Serializable {

    private Controller systemController;
	//    public ParkManager(String firstName, String lastName, UserID userID) {
	private List<Job> jobsSubmittedByMe;

	private Job newJobToSubmit;


    public ParkManager(final String firstName, final String lastName,
                       final Controller paramSystemController) {
        super(firstName, lastName, UserRole.PARK_MANAGER,
                paramSystemController.getCurrentUser().getID());
        this.systemController = paramSystemController;
        populateJobsSubmittedByMe();
    }

    //populate list of all jobs submitted by this park manager
    public void populateJobsSubmittedByMe() {

        try {
            jobsSubmittedByMe =
                    systemController.getFutureJobsSubmittedByParkManager(this.getID());
        } catch (UserRoleCategoryException e) {
            //TODO - how to handle this in this class?
        } catch (UserNotFoundException e) {
            //TODO - again how to handle in this class?

        }
    }


    //only future jobs submitted by this park manager
    public List<Job> getFutureJobsSubmittedByMe() {
        ArrayList<Job> futureJobsSubmittedByMe = new ArrayList<>();
        LocalDateTime currentDate = LocalDateTime.now();
        for (Job j : jobsSubmittedByMe) {
            if (j.getBeginDateTime().isAfter(currentDate)) {
                futureJobsSubmittedByMe.add(j);
            }
        }
        return futureJobsSubmittedByMe;
    }

    public void unsubmitFutureJobSubmittedByMe(final Job jobToRemove)
            throws UrbanParksSystemOperationException {
        try {
            systemController.unsubmitParkJob(jobToRemove);
        } catch (UrbanParksSystemOperationException e) {
            throw new UrbanParksSystemOperationException(e.getMsgString());
        }
        jobsSubmittedByMe.remove(jobToRemove);
    }

    public void submitNewJob(final String name, final Park park,
                             final JobID ID,
                             final LocalDateTime beginDate,
                             final LocalDateTime endDate,
                             final String description,
                             final UserID paramJobCreatorUserID)
            throws UrbanParksSystemOperationException{
        newJobToSubmit = new Job(name, park, ID, beginDate, endDate,
                description, this.getID());
        try {
            systemController.addNewJobByParkManager(newJobToSubmit);
        } catch (UrbanParksSystemOperationException e) {
            throw new UrbanParksSystemOperationException(e.getMsgString());
        }
        jobsSubmittedByMe.add(newJobToSubmit);
    }


    //view a list of future jobs, with options to update, create, delete jobs
    //list of all future jobs?
    //or just jobs created by this park manager id?
    //select a job from the list of future jobs
    //tell app to remove that job from the list
//    //TODO -
//    public void removeFutureJobFromCollection(final Job jobToRemove) {
//        //TODO-app confirms if you want to remove that job from list, removal will be permanent
//        JobID jobToRemoveID = jobToRemove.getID();
//        try { //remove that job from the list
//            jobCollection.removeJobFromCollection(jobToRemoveID, this.getID());
//        } catch (LessThanMinDaysAwayException e) {
//            //TODO-show these messages somewhere
//            e.getMsgString();
//        } catch (UserNotFoundException e) {
//            e.getMsgString();
//        } catch (JobIDNotFoundInCollectionException e) {
//            e.getMsgString();
//        }
//        //TODO - confirm to user that job was just removed from list
//        //TODO - view the list of future jobs with that job removed?
//    }



    //end of Park Manager class
}
