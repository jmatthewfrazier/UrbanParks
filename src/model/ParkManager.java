package model;

import exceptions.InvalidJobEndDateException;
import exceptions.InvalidJobLengthException;
import exceptions.JobCollectionDuplicateKeyException;
import exceptions.MaxPendingJobsException;

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

    private JobMap jobCollection;

    public ParkManager(JobMap paramJobMap) {
        this("Test", "Park Manager",
                new UserID("park_manager_default"));
        jobCollection = paramJobMap;
        //aren't we supposed to call super somewhere in here?
    }

    public ParkManager(String firstName, String lastName, UserID userID) {
       super(firstName, lastName, UserRole.PARK_MANAGER, userID);
    }

    //return type may need to be a boolean instead.
    public String addNewJobToCollection(final Job jobToAdd) {
        String retStr = "";
            try {
                jobCollection.addJob(jobToAdd);
            }
            catch(MaxPendingJobsException e) {
                retStr = e.getMsgString();
            }
            catch(InvalidJobLengthException e) {
                retStr = e.getMsgString();
            }
            catch (InvalidJobEndDateException e) {
                retStr = e.getMsgString();
            }
            catch (JobCollectionDuplicateKeyException e) {
                retStr = e.getMsgString();
            }
            //by here it has made it through all exceptions,
            //so let's add the job and signal success
        retStr = getAddJobSuccessfulMsg(jobToAdd);

        return retStr;
    }

    public String getAddJobSuccessfulMsg(final Job addedJob) {
        StringBuilder sb = new StringBuilder();
        sb.append("\nYay! ");
        sb.append(this.getFirstName() + " ");
        sb.append(this.getLastName() + " ");
        sb.append("your Urban Parks Job List added the following job:\n ");
        sb.append("Job Name: " + addedJob.getJobName() + "\n");//job name
        sb.append("Park: " + addedJob.getJobLocation() + "\n\n");//park name
        sb.append("Starting on Date: " + addedJob.getBeginDateTime().getDayOfWeek()
                + ", ");//job date
        sb.append(addedJob.getBeginDateTime().getMonth() + ", ");
        sb.append(addedJob.getBeginDateTime().getDayOfMonth() + ", ");
        sb.append(addedJob.getBeginDateTime().getYear() + "\n");
        sb.append("Beginning at Time: ");
        sb.append(addedJob.getBeginDateTime().getHour() + ":");
        sb.append(addedJob.getBeginDateTime().getMinute() + "\n\n");

        sb.append("Ending on Date: " + addedJob.getEndDateTime().getDayOfWeek()
                + ", ");//job date
        sb.append(addedJob.getEndDateTime().getMonth() + ", ");
        sb.append(addedJob.getEndDateTime().getDayOfMonth() + ", ");
        sb.append(addedJob.getEndDateTime().getYear() + "\n");
        sb.append("Ending at Time: \n");
        sb.append(addedJob.getEndDateTime().getHour() + ":");
        sb.append(addedJob.getEndDateTime().getMinute() + "\n\n");
        sb.append("Thank you for using Urban Parks " +
                "registration management system!\n");
        //successfully
        String addJobSuccessfulMsg = sb.toString();
        return addJobSuccessfulMsg;
    }

    //end of Park Manager class
}
