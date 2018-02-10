package model;

import exceptions.*;

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
public final class ParkManager extends User implements Serializable {

    //store of all parks this user manages / is authorized to add jobs for
    //TODO-actually this probably belongs in aPark, not here
    private ArrayList<Park> managedParkList  = new ArrayList<Park>();

    private JobCollection jobMap;

    public ParkManager(JobCollection paramJobMap) {
        this("Test", "Park Manager",
                new UserID("park_manager_default"));
        jobMap = paramJobMap;

    }

    public ParkManager(String firstName, String lastName, UserID userID) {
       super(firstName, lastName, UserRole.PARK_MANAGER, userID);
    }


    public void addNewJobToCollection(final Job jobToAdd)
            throws AddNewParkJobException{
        //String retStr = "";
            try {
                jobMap.addJob(jobToAdd);
            }
            catch(MaxPendingJobsException e) {
                throw new AddNewParkJobException(e.getMsgString());
            }
            catch(InvalidJobLengthException e) {
                throw new AddNewParkJobException(e.getMsgString());
            }
            catch (InvalidJobEndDateException e) {
                throw new AddNewParkJobException(e.getMsgString());
            }
            catch (JobCollectionDuplicateKeyException e) {
                throw new AddNewParkJobException(e.getMsgString());
            }

    }

    //TODO-ok, this could go to the UI and save some time there I think
//    public String getAddJobSuccessfulMsg(final Job addedJob) {
//        StringBuilder sb = new StringBuilder();
//        sb.append("\nYay! ");
//        sb.append(this.getFirstName() + " ");
//        sb.append(this.getLastName() + " ");
//        sb.append("your Urban Parks Job List added the following job:\n ");
//        sb.append("Job Name: " + addedJob.getJobName() + "\n");//job name
//        sb.append("Park: " + addedJob.getJobLocation() + "\n\n");//park name
//        sb.append("Starting on Date: " + addedJob.getBeginDateTime().getDayOfWeek()
//                + ", ");//job date
//        sb.append(addedJob.getBeginDateTime().getMonth() + ", ");
//        sb.append(addedJob.getBeginDateTime().getDayOfMonth() + ", ");
//        sb.append(addedJob.getBeginDateTime().getYear() + "\n");
//        sb.append("Beginning at Time: ");
//        sb.append(addedJob.getBeginDateTime().getHour() + ":");
//        sb.append(addedJob.getBeginDateTime().getMinute() + "\n\n");
//
//        sb.append("Ending on Date: " + addedJob.getEndDateTime().getDayOfWeek()
//                + ", ");//job date
//        sb.append(addedJob.getEndDateTime().getMonth() + ", ");
//        sb.append(addedJob.getEndDateTime().getDayOfMonth() + ", ");
//        sb.append(addedJob.getEndDateTime().getYear() + "\n");
//        sb.append("Ending at Time: \n");
//        sb.append(addedJob.getEndDateTime().getHour() + ":");
//        sb.append(addedJob.getEndDateTime().getMinute() + "\n\n");
//        sb.append("Thank you for using Urban Parks " +
//                "registration management system!\n");
//        //successfully
//        String addJobSuccessfulMsg = sb.toString();
//        return addJobSuccessfulMsg;
//    }

    //end of Park Manager class
}
