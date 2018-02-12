package model;

import exceptions.DuplicateVolunteerUserIDException;
import exceptions.JobInSameDayException;
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

    /**
     * This method would handle the situation when the volunteer is trying
     * to sign up for jobs that extend across the job(s) already signed up for.
     *
     * @param theCandidateJob the job that the Volunteer is trying to sign up for.
     * @return boolean value shows if the Volunteer can sign up for this job.
     */
    public boolean isJobSameDay(final Job theCandidateJob)
            throws JobInSameDayException {
        for (Job job: myJobs){
            if (job.isJobOverlapping(theCandidateJob))
                throw new JobInSameDayException("Sorry, the job you " +
                        "are applying extends across the job you have" +
                        " signed up already!!");
        }
        return true;
    }

    /**
     * This method checks if the Volunteer is trying to sign up for a
     * job that starts at the end date of the jobs that the Volunteer
     * has signed up for.
     *
     * @param theCandidateJob the job that the Volunteer is trying to sign up for.
     * @return boolean value shows if the Volunteer can sign up for this job.
     */
    public boolean isStartAtEndDate(Job theCandidateJob) throws
            JobStartAtEndDateException {
        for (Job job: myJobs){
            if (job.isStartAtEndDate(theCandidateJob))
                throw new JobStartAtEndDateException("Sory, " +
                        "the job you are applying starts at " +
                        "the end date of one of your signed up jobs!");
        }
        return true;
    }

    /**
     * This method checks if the Volunteer is trying to sign up for a
     * job that ends at the start date of the jobs that the Volunteer
     * has signed up for.
     *
     * @param theCandidateJob the job that the Volunteer is trying to sign up for.
     * @return boolean value shows if the Volunteer can sign up for this job.
     */
    public boolean isEndAtStartDate(Job theCandidateJob) throws
            JobEndAtStartDateException {
        for(Job job: myJobs){
            if (job.isEndAtStartDate(theCandidateJob))
                throw new JobEndAtStartDateException("Sorry, " +
                        "the job you are applying ends at the " +
                        "date of one of your signed up jobs!!" );
        }
        return true;
    }

    /**
     * Returns true if the job begins more than or equal to the minimum number of days allowed away from the current
     * date, false otherwise.
     *
     * @param candidateJob is the job that the volunteer is attempting to sign up for.
     *
     * @return true if the job start date is more than or equal to the minimum number of calendar days out that the
     * volunteer is allowed to sign up for, false otherwise.
     */
    public boolean isMoreThanMinimumDaysOut(Job candidateJob) throws
            LessThanMinimumDaysAwayException{
        boolean beyondMin = true;

        if (candidateJob.dateDifference() < MINIMUM_SIGNUP_DAYS_OUT){
            beyondMin = false;
            throw new LessThanMinimumDaysAwayException("Sorry, " +
                    "the job you are applying is less than the " +
                    "minimum days away from one of your signed up jobs!!");
        }
        return beyondMin;
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

