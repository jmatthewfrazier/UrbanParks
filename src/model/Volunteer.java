package model;

import exceptions.LessThanMinDaysAwayException;
import exceptions.VolunteerDailyJobLimitException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class Volunteer extends User {

	/**
	 * A default minimum number of calendar days after the current date that a
	 * job begins and a volunteer may sign up for.
	 */
	public static int MINIMUM_SIGN_UP_DAYS_OUT = 2;

	private List<Job> jobList;

    public Volunteer(String firstName, String lastName, UserID userID) {
        super(firstName, lastName, UserRole.VOLUNTEER, userID);
	    jobList = new ArrayList<>();
    }
    
    /**
     * Adds newJob to the Volunteer's list of jobs and the Volunteer to 
     * newJob's list of volunteers if the preconditions are met.
     * 
     * @param newJob is the job the volunteer is attempting to sign up for.
     * @throws VolunteerDailyJobLimitException iff the volunteer has an 
     * existing job on a same calendar day as newJob.
     * @throws LessThanMinDaysAwayException iff the job is less than the 
     * MINIMUM_SIGN_UP_DAYS_OUT.
     */
    public void signUpForJob(Job newJob) throws VolunteerDailyJobLimitException,
		    LessThanMinDaysAwayException {

   	    if (newJob.getBeginDateTime().isBefore(LocalDateTime
		        .now().plusDays(getMinDaysAwaySignUp()))) {
   	    	throw new LessThanMinDaysAwayException("Job begins too soon");
        }

		for (Job job : jobList) {
			if (job.isStartAtEndDate(newJob)) {
				throw new VolunteerDailyJobLimitException("Candidate job " +
						"begins on a date of a job that the volunteer is " +
						"currently signed up for.");
			} else if (job.isEndAtStartDate(newJob)) {
				throw new VolunteerDailyJobLimitException("Candidate job " +
						"ends on a date of a job that the volunteer is " +
						"currently signed up for.");
			} else if (job.isOverlapping(newJob)) {
				throw new VolunteerDailyJobLimitException("Candidate job " +
						"is extend across with one of the jobs you have " +
						"signed up already.");
			}
		}

//		newJob.addVolunteer(this);
		jobList.add(newJob);
    }
    
    /**
     * Removes jobToRemove from this volunteer's jobList.
     * 
     * @param jobToRemove is the job being unvolunteered for.
     */
    public void removeJobFromMyRegisteredJobs(final Job jobToRemove) {
    	if (jobList.contains(jobToRemove)) {
    		jobList.remove(jobToRemove);
		}
	}
    
    /**
     * Removes jobs from jobList that are no longer on the list of all jobs.
     * 
     * @param masterJobs is the current master job list.
     */
    public void updateJobList(JobCollection masterJobs) {
    	for (Job job : jobList) {
    		if (masterJobs.containsJobID(job.getID())) {
    			this.removeJobFromMyRegisteredJobs(job);
    		}
    	}
    }
    
    public int getMinDaysAwaySignUp() {
    	return MINIMUM_SIGN_UP_DAYS_OUT;
    }
    
    public void setMinDaysAwaySignUp(int newValue) {
    	MINIMUM_SIGN_UP_DAYS_OUT = newValue;
    }

    public List<Job> getJobList() {
   	    return new ArrayList<>(jobList);
    }

//    public List<Job> getChronologicalJobList() {
//   	    List<Job> chronologicalList = new ArrayList<>(jobList);
//   	    chronologicalList.sort(JobCollection.getChronologicalJobComparator());
//   	    return chronologicalList;
//    }

    //end Volunteer class
}
