package model;

import exceptions.VolunteerDailyJobLimitException;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class Job implements Serializable {

    private static final int MAX_NUM_DAYS_FROM_TODAY = 75;
    private static final int MAX_JOB_LENGTH_IN_DAYS = 3;

	private List<Volunteer> volunteerList;
    private String name;
    private Park park;
    private JobID ID;
    private LocalDateTime beginDateTime;
    private LocalDateTime endDateTime;
    private String description;

    public Job(final String name, final Park park, final JobID ID,
               final LocalDateTime beginDate,
               final LocalDateTime endDate, final String description) {
    	volunteerList = new ArrayList<>();
        this.name = name;
        this.park = park;
        this.ID = ID;
        this.beginDateTime = beginDate;
        this.endDateTime = endDate;
        this.description = description;
    }

    public static int getMaximumValidDayRangeFromToday() {
        return MAX_NUM_DAYS_FROM_TODAY;
    }

    public void addVolunteer(final Volunteer volunteer)
		    throws VolunteerDailyJobLimitException {

    	for (Job job : volunteer.getJobList()) {
    		for (LocalDateTime date = job.getBeginDateTime(); date.compareTo
				    (job.endDateTime) <= 0; date = date.plusDays(1)) {
    			if (this.getBeginDateTime().equals(date) || this
					    .getEndDateTime().equals(date)) {
    				throw new VolunteerDailyJobLimitException();
			    }
		    }
	    }

        this.volunteerList.add(volunteer);
    }

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

    public String getName() {
        return name;
    }

    public Park getPark() {
        return park;
    }

    public JobID getID() {
        return ID;
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
		return this.endDateTime.equals(theCandidateJob.beginDateTime);
	}
	
	/**
	 * Check if the job the Volunteer is applying ends at the start date
	 * of the jobs that he already signed up.
	 * 
	 * @param theCandidateJob the Job that the Volunteer is trying to sign up.
	 * @return boolean value indicates whether or not the Volunteer can get the job.
	 */
	public boolean isEndAtStartDate(Job theCandidateJob) {
		return this.beginDateTime.equals(theCandidateJob.endDateTime);
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

	public static Comparator<Job> getChronologicalJobComparator() {
		class ChronologicalComparator implements  Comparator<Job> {
			@Override
			public int compare(Job o1, Job o2) {
				String s1 = o1.getName();
				String s2 = o2.getName();
				return s1.compareTo(s2);
			}
		}
		return new ChronologicalComparator();
	}
}

