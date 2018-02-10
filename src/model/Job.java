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
    private final int MAX_NUM_DAYS_FROM_TODAY = 75;
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
        this.beginDateTime = beginDate.toLocalDate();
        this.park = jobPark;
        this.endDateTime = endDate.toLocalDate();
   }

    public int getMaximumValidDayRangeFromToday() {
        return MAX_NUM_DAYS_FROM_TODAY;
    }


		usersRegistered = new UserCollection();
    }

    public static final int getMaximumValidDayRangeFromToday() {

        return MAX_NUM_DAYS_FROM_TODAY;
    }


//    public int getThisJobLengthInDays() {
//        int jobLength = this.endDateTime.
//        return MAX_JOB_LENGTH_IN_DAYS;
//    }

    public void addUserToThisJob(final User userToAdd) throws
            VolunteerSignUpStartDateException, VolunteerDailyJobLimitException,
            DuplicateVolunteerUserIDException {

        //blah blah add a user to this job
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
    
    // The following would be the methods needed for the acceptance tests
    // for #1
    
    /**
	 * Calculates and returns the number of days between the current date and the start date of this job.
	 * 
	 * @return the number of days between the current date and the start date of this job.
	 */
	public int dateDifference() {
		LocalDateTime now = LocalDate.now();
		return (int) Math.abs(now.until(myStartDate, ChronoUnit.DAYS));
	}
	
	/**
	 * Check if the job the Volunteer is applying is extend across with
	 * the jobs that he already signed up.
	 * 
	 * @param theCandidateJob the Job that the Volunteer is trying to sign up.
	 * @return boolean value indicates whether or not the Volunteer can get the job.
	 */
	public boolean isJobOverlapping(Job theCandidateJob){
		return this.endDateTime.isBefore(theCandidateJob.beginDateTime);
	}
	
	/**
	 * Check if the job the Volunteer is applying starts at the end date
	 * of the jobs that he already signed up.
	 * 
	 * @param theCandidateJob the Job that the Volunteer is trying to sign up.
	 * @return boolean value indicates whether or not the Volunteer can get the job.
	 */
	public boolean isStartAtEndDate(Job theCandidateJob){
		return this.myEndDate.equals(theCandidateJob.myStartDate);
	}
	
	/**
	 * Check if the job the Volunteer is applying ends at the start date
	 * of the jobs that he already signed up.
	 * 
	 * @param theCandidateJob the Job that the Volunteer is trying to sign up.
	 * @return boolean value indicates whether or not the Volunteer can get the job.
	 */
	public boolean isEndAtStartDate(Job theCandidateJob){
		return this.myStartDate.equals(theCandidateJob.myEndDate);
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

