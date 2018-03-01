package model;

import java.io.Serializable;
import java.time.LocalDateTime;

public final class Job implements Serializable {

    public static final int MAX_NUM_DAYS_FROM_TODAY = 75;
    public static final int MAX_JOB_LENGTH_IN_DAYS = 3;

//	private List<Volunteer> volunteerList;
    private String name;
    private Park park;
    private JobID ID;
    private LocalDateTime beginDateTime;
    private LocalDateTime endDateTime;
    private String description;
//    private ParkManager jobCreator;

    public Job(final String name, final Park park, final JobID ID,
               final LocalDateTime beginDate,
               final LocalDateTime endDate, final String description/*,
               final ParkManager jobCreator*/) {
//    	volunteerList = new ArrayList<>();
        this.name = name;
        this.park = park;
        this.ID = ID;
        this.beginDateTime = beginDate;
        this.endDateTime = endDate;
        this.description = description;
//        this.jobCreator = jobCreator;
    }

    public static int getMaximumValidDayRangeFromToday() {
        return MAX_NUM_DAYS_FROM_TODAY;
    }

//    public void addVolunteer(final Volunteer volunteer)
//		    throws VolunteerDailyJobLimitException {
//
//    	for (Job job : volunteer.getJobList()) {
//    		for (LocalDateTime date = job.getBeginDateTime(); date.compareTo
//				    (job.endDateTime) <= 0; date = date.plusDays(1)) {
//    			if (this.getBeginDateTime().equals(date) || this
//					    .getEndDateTime().equals(date)) {
//    				throw new VolunteerDailyJobLimitException();
//			    }
//		    }
//	    }
//
//        this.volunteerList.add(volunteer);
//    }

    /**
     * A method to determine if this Job spans an acceptable range of days.
     * The maximum range of days is determined by client business rules.
     * LocalDateTime will add n days to the current date with the current date
     * being counted as 0 not 1.  For example, 12/31/1999 plus 3 days will result in
     * 1/3/2000.  Valid in this context means the length of time this Job covers is
     * not in violation of proscribed business rules.
     *
     * @return true if day count of this Job does not violate any business rules
     * pertaining to the number of days the job spans.
     */
    public boolean isJobLengthValid() {
	    LocalDateTime maxValidJobEndDate =
			    beginDateTime.plusDays(MAX_JOB_LENGTH_IN_DAYS);
        return maxValidJobEndDate.isAfter(this.endDateTime);
    }

    /**
     * A method to determine if this Job's scheduled event time is within the
     * allowable range of future dates.  The range of future dates is currently
     * set by client business rules.
     *
     * @return true if date of this Job is within the acceptable date range.
     * The date range is determined by client business rules.
     */
    public boolean isJobWithinValidDateRange() {
        return endDateTime.minusDays(MAX_NUM_DAYS_FROM_TODAY).compareTo
                (LocalDateTime.now()) <= 0;
    }
    
	/**
	 * Checks if theCandidateJob starts at the end date of this job.
	 * 
	 * @param theCandidateJob the Job that the Volunteer is trying to sign up.
	 * @return boolean value indicates whether or not the Volunteer can get the job.
	 */
	public boolean isStartAtEndDate(Job theCandidateJob){
		return this.endDateTime.toLocalDate().equals(
				theCandidateJob.beginDateTime.toLocalDate());
	}
	
	/**
	 * Checks if theCandidateJob ends at the start date of this job.
	 * 
	 * @param theCandidateJob the Job that the Volunteer is trying to sign up.
	 * @return boolean value indicates whether or not the Volunteer can get the job.
	 */
	public boolean isEndAtStartDate(Job theCandidateJob) {
		return this.beginDateTime.toLocalDate().equals(
				theCandidateJob.endDateTime.toLocalDate());
	}
	
	/**
	 * Checks if theCandidateJob's start and end dates overlaps with this job's 
	 * start and end dates.
	 * 
	 * @param theCandidateJob is the job to compare dates with.
	 * @return False iff the dates of theCandidateJob do not extend across any 
	 * of the days of this job.
	 */
	public boolean isOverlapping(Job theCandidateJob) {

	    return (this.endDateTime.isAfter(theCandidateJob.beginDateTime) && 
	    	this.beginDateTime.isBefore(theCandidateJob.beginDateTime)) ||
	    	(this.beginDateTime.isBefore(theCandidateJob.endDateTime) && 
	    	this.endDateTime.isAfter(theCandidateJob.endDateTime)) || 
	    	(this.endDateTime.isBefore(theCandidateJob.endDateTime) && 
	    	this.beginDateTime.isAfter(theCandidateJob.beginDateTime));
	}
    
    /**
     * Determines if the start date of this job is after or equal to the date
     * specified.
     * 
     * @param date is the date to compare the job start date to.
     * @return true iff the job start date is after or equal to the date 
     * specified.
     */
    public boolean isJobStartAfterEqualDate(LocalDateTime date) {
    	return beginDateTime.isAfter(date) || beginDateTime.isEqual(date);
    }
    
    /**
     * Determines if the end date of this job is before or equal to the date
     * specified.
     * 
     * @param date is the date to compare the job start date to.
     * @return true iff the job end date is before or equal to the date 
     * specified.
     */
    public boolean isJobEndBeforeEqualDate(LocalDateTime date) {
    	return endDateTime.isBefore(date) || endDateTime.isEqual(date);
    }

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
    
    public String getDescription() {
    	return description;
    }

//    public void setName(final String name) {
//        this.name = name;
//    }

    public void setPark(final Park park) {
        this.park = park;
    }

//    public void setBeginDateTime(final LocalDateTime time) {
//        this.beginDateTime = time;
//    }
//
//    public void setEndDateTime(final LocalDateTime time) {
//        this.endDateTime = time;
//    }
    
    public void setDescription(final String description) {
    	this.description = description;
    }

//    public ParkManager getJobCreator() {
//    	return this.jobCreator;
//    }

//    public ParkManager getJobCreator() {
//	    return jobCreator;
//    }

//    public ArrayList<UserID> getVolunteerUserIDList() {
//	    ArrayList<UserID> volunteerUserIDList = new ArrayList<>();
//	    for (Volunteer v : volunteerList) {
//	        volunteerUserIDList.add(v.getID());
//        }
//	    return volunteerUserIDList;
//    }
}

