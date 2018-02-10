package model;

import exceptions.DuplicateVolunteerUserIDException;
import exceptions.VolunteerDailyJobLimitException;
import exceptions.VolunteerSignUpStartDateException;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;

public class Job implements Serializable {

//    /**
//     * The max number of days away from the current date that the end of a
//     * new job can be specified.
//     */
//    //TODO-is there a reason we are using "static" here? why not
//    // setters/getters?
    private static final int MAX_NUM_DAYS_FROM_TODAY = 75;
//
//    private static final int MAX_JOB_LENGTH_IN_DAYS = 3;

    /**
     * unique identifier for object serialization
     */
    private static final long serialVersionUID = 8341912696713916150L;

    private String name;

    private JobID ID;

    private LocalDateTime beginDateTime;

    private LocalDateTime endDateTime;

    private Park park;

    private UserCollection usersRegistered;


    public Job(final String jobName,
               final Park jobPark,
               final LocalDateTime beginDate,
               final LocalDateTime endDate) {
        this.name = jobName;
        this.beginDateTime = beginDate;
        this.endDateTime = endDate;
		this.park = jobPark;

		usersRegistered = new UserCollection();
    }

    public static final int getMaximumValidDayRangeFromToday() {

        return MAX_NUM_DAYS_FROM_TODAY;
    }


//    public int getThisJobLengthInDays() {
//        int jobLength = this.endDateTime.
//        return MAX_JOB_LENGTH_IN_DAYS;
//    }

    public void addVolunteerToThisJob(final Volunteer volToAdd) throws
            VolunteerSignUpStartDateException, VolunteerDailyJobLimitException,
            DuplicateVolunteerUserIDException {
        //TODO-again, this is incomplete logic, just a few example lines!!
        if (isUserJobOverlapping(volToAdd)) {
            throw new VolunteerSignUpStartDateException("Sorry bro," +
                    "only one job per day!");
        }

    }

    /**
     * Check if the job the Volunteer is applying is extend across with
     * the jobs that he already signed up.
     *

     * @return boolean value indicates whether or not the Volunteer can get the job.
     */
    public boolean isUserJobOverlapping(final Volunteer volToCheck){
        boolean retBool = false;
        //check all the jobs this person is signed up for
        for (Job j : volToCheck.getJobsCurrentlyRegisteredForMap().values()) {
            //TODO-this logic is not adewquate!!! this is only meant to be an example!
            if (j.beginDateTime.toLocalDate().equals(this.getEndDateTime().toLocalDate())) {
                //that only checks one day out of the entire job span
                retBool = true;
                break;
            }
        }
        return retBool;
    }

    //other access methods here
    //do the checking like from Yulin's

    public String getName() {
        return name;
    }

    public Park getPark() {
        return park;
    }

    public JobID getID() {
        return ID;
    }

    public LocalDateTime getBeginDateTime() {
        return beginDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setPark(final Park park) {
        this.park = park;
    }

    public void setBeginDateTime(final LocalDateTime time) {
        this.beginDateTime = time;
    }

    public void setEndDateTime(final LocalDateTime time) {
        this.endDateTime = time;
    }


}

