package model;

import exceptions.VolunteerDailyJobLimitException;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class Job implements Serializable {

    private static final int MAX_NUM_DAYS_FROM_TODAY = 60;
    private static final int MAX_JOB_LENGTH_IN_DAYS = 4;

	private List<Volunteer> volunteerList;
    private String name;
    private Park park;
    private JobID ID;
    private LocalDateTime beginDateTime;
    private LocalDateTime endDateTime;
    private String description;
    private ParkManager jobCreator;

    public Job(final String name, final Park park, JobID jobID,
               final LocalDateTime beginDate, final LocalDateTime endDate,
               final String description, final ParkManager jobCreator) {
    	this.volunteerList = new ArrayList<>();
        this.name = name;
        this.park = park;
        this.ID = jobID;
        this.beginDateTime = beginDate;
        this.endDateTime = endDate;
        this.description = description;
        this.jobCreator = jobCreator;
    }

    public static int getMaxJobLengthInDays() {
    	return MAX_JOB_LENGTH_IN_DAYS;
    }

    public static int getMaximumValidDayRangeFromToday() {
        return MAX_NUM_DAYS_FROM_TODAY;
    }

	public ParkManager getJobCreator() {
		return jobCreator;
	}

	/**
     * Checks if a Volunteer is already signed up for a job that has dates that
     * conflict with this job.
     * @param volunteer is the volunteer to check for conflicting jobs in.
     * @throws VolunteerDailyJobLimitException iff the volunteer has a job with
     * dates that conflict with thie job's dates.
     */
    public final void addVolunteer(final Volunteer volunteer)
		    throws VolunteerDailyJobLimitException {
    	boolean canAdd = true;

	    if (this.getBeginDateTime().isBefore(LocalDateTime
			    .now().plusDays(Volunteer.getMinDaysAwaySignUp()))) {
		    canAdd = false;
	    }

    	for (Job job : volunteer.getJobList()) {
    		for (LocalDateTime date = job.getBeginDateTime(); date.compareTo
				    (job.endDateTime) <= 0; date = date.plusDays(1)) {
    			if (this.getBeginDateTime().equals(date)
					    || this.getEndDateTime().equals(date)) {
    				canAdd = false;
			    }
		    }
	    }

		if (canAdd) {
    		this.volunteerList.add(volunteer);
		} else {
			throw new VolunteerDailyJobLimitException();
		}
    }

    public final boolean hasVolunteer(Volunteer volunteer) {
    	return this.volunteerList.contains(volunteer);
    }

    public final void removeVolunteer(Volunteer volunteer) {
    	this.volunteerList.remove(volunteer);
    }

    /**
     * Determines if this Job's length is <= MAX_JOB_LENGTH_IN_DAYS.
     *
     * @return true iff day count of this Job is <= MAX_JOB_LENGTH_IN_DAYS.
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

    public void setPark(final Park park) {
        this.park = park;
    }
}

