package model;

import java.io.Serializable;
import java.time.LocalDateTime;

public final class Job implements Serializable {

    private static final int MAX_NUM_DAYS_FROM_TODAY = 75;
    private static final int MAX_JOB_LENGTH_IN_DAYS = 3;


    private String name;
    private Park park;
    private JobID ID;
    private LocalDateTime beginDateTime;
    private LocalDateTime endDateTime;
    private String description;

    public Job(final String name, final Park park, final JobID ID,
               final LocalDateTime beginDate,
               final LocalDateTime endDate, final String description) {
        this.name = name;
        this.park = park;
        this.ID = ID;
        this.beginDateTime = beginDate;
//        this.myStartDate = beginDateTime.toLocalDate();
        this.endDateTime = endDate;
//        this.myEndDate = endDateTime.toLocalDate();
        this.description = description;
    }

    public static int getMaximumValidDayRangeFromToday() {
        return MAX_NUM_DAYS_FROM_TODAY;
    }

    public static int getMaxJobLengthInDays() {
        return MAX_JOB_LENGTH_IN_DAYS;
    }

    /**
     * A method that checks all business rules a new instance of job is expected
     * to follow.
     * TODO - we need to decide what happens if a user fails a business
     * rule.  Do we throw an exception?  That seems kind of extreme but it may
     * be the easiest way to go about things for now?
     *
     */
    //throws InvalidJobException
    private void validateJobVariables()  {

    }


//    public String createJobCollectionMapKey() {
//        String retStr = null;
//        //whatever fields will be used to create a unique key go here
//        return retStr;
//    }

    /** TODO-here do we signal a successful add to a User in the job class or
     * in the JobMap class?  I think the JobMap class since it will be the
     * one actually adding the new Job.
     *
     */
//    public boolean submitNewJob() {
//        boolean retBool = false;
        //signal it went wrong either here?
//        if (isNewJobValid()) {
            //or here for adds that aren't successful?
            //or the JobMap class instead?
//            retBool = submitValidatedJob();
//        }
//        return retBool;
//    }

    /*
     * Examine job attributes and return whether the job fits the
     * specified validation criteria.  Additional criteria can be
     * added as needed.
     * TODO-this is obviously not right logic, i think we first need to figure
     * out how to manage failed business rule criteria.
     * Exceptions?
     * Only console messages?
     * ???
     */
//    public boolean isJobValid() {
//        boolean retBool = false;
//        //validate business case: job must be =< maxDays days in total length
//        //job length is (maxDays - 1)
//        //job length is (maxDays)
//        //job length is (maxDays + 1)
//        if (isJobLengthValid()) {
//            retBool = true;
//        }
//        if (isJobWithinValidDateRange()) {
//            retBool = true;
//        }
//        return retBool;
//    }

    /*
     * Accessor method to check if the length of a proposed new job is within
     * the maximum allowable time limit.
     *
     * If the proposed job length is longer than the max allowable time, will
     * return false.
     */
    public boolean isJobLengthValid() {
        boolean retBool = false;
        LocalDateTime maxValidJobEndDate =
                beginDateTime.plusDays(MAX_JOB_LENGTH_IN_DAYS);
        if (maxValidJobEndDate.isAfter(this.endDateTime)) {
            retBool = true;
        }

        return retBool;
    }

    /**
     * Specifies if this Job is within a valid date range.
     *
     * @return true if the Job is within the valid date range, false otherwise
     */
    public boolean isJobWithinValidDateRange() {
        return endDateTime.minusDays(MAX_NUM_DAYS_FROM_TODAY).compareTo
                (LocalDateTime.now()) <= 0;
    }

    //TODO-need to figure out how to couple this to the JobMap instance.

//    /**
//     *
//     * @return was the validated job successfully added or not?
//     */
//    public boolean submitValidatedJob() {
//        boolean retBool = false;
//        //talk with the JobMap here, but how?
//        //get back some input about adding this Job instance
//        return retBool;
//    }

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
//		LocalDateTime now = LocalDate.now();
//		return (int) Math.abs(now.until(myStartDate, ChronoUnit.DAYS));
		return 0;
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
//		return this.myEndDate.equals(theCandidateJob.myStartDate);
		return false;
	}
	
	/**
	 * Check if the job the Volunteer is applying ends at the start date
	 * of the jobs that he already signed up.
	 * 
	 * @param theCandidateJob the Job that the Volunteer is trying to sign up.
	 * @return boolean value indicates whether or not the Volunteer can get the job.
	 */
	public boolean isEndAtStartDate(Job theCandidateJob){
//		return this.myStartDate.equals(theCandidateJob.myEndDate);
		return false;

	}

    public LocalDateTime getBeginDateTime() {
        return beginDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }
    
    public String getDescription() {
    	return description;
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
    
    public void getDescription(final String description) {
    	this.description = description;
    }
}

