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
	public static int MINIMUM_SIGN_UP_DAYS_OUT = 3;

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
    public void signUpForJob(Job newJob) throws Exception {
    	boolean canAdd = true;
    	Exception e = new Exception();

   	    if (newJob.getBeginDateTime().isBefore(LocalDateTime
		        .now().plusDays(getMinDaysAwaySignUp()))) {
   	    	canAdd = false;
   	    	e = new LessThanMinDaysAwayException("Job begins too soon");
        }

		for (Job job : jobList) {
			if (job.isStartAtEndDate(newJob)) {
				canAdd = false;
				e = new VolunteerDailyJobLimitException("Candidate job " +
						"begins on a date of a job that the volunteer is " +
						"currently signed up for.");
			} else if (job.isEndAtStartDate(newJob)) {
				canAdd = false;
				e = new VolunteerDailyJobLimitException("Candidate job " +
						"ends on a date of a job that the volunteer is " +
						"currently signed up for.");
			} else if (job.isOverlapping(newJob)) {
				canAdd = false;
				e = new VolunteerDailyJobLimitException("Candidate job " +
						"is extend across with one of the jobs you have " +
						"signed up already.");
			}
		}
		System.out.println(canAdd);
		if (canAdd) {
   	    	jobList.add(newJob);
		} else {
   	    	throw e;
		}
	}

    /**
     * Tests if the dates of newJob will conflict with any job the Volunteer is
     * currently signed up for and if the job is far enough in the future.
     * @param newJob is the job that the Volunteer is interested in signing up
     * for.
     * @return true iff the volunteer won't break any business rules by signing
     * up for newJob.
     */
	public boolean canSignUpForJob(Job newJob) {
		
		for (Job job : getJobList()) {
    		for (LocalDateTime date = job.getBeginDateTime(); date.compareTo
				    (job.getEndDateTime()) <= 0; date = date.plusDays(1)) {
    			if (newJob.getBeginDateTime().equals(date) || newJob
					    .getEndDateTime().equals(date)) {
    				return false;
			    }
		    }
	    }

		if (newJob.getBeginDateTime().isBefore(LocalDateTime
		    .now().plusDays(getMinDaysAwaySignUp()))) {
		    return false;
		}
	
		for (Job job : jobList) {
			if (job.isStartAtEndDate(newJob)) {
				return false;
			} else if (job.isEndAtStartDate(newJob)) {
				return false;
			} else if (job.isOverlapping(newJob)) {
				return false;
			}
		}
		return true;
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

    public int getMinDaysAwaySignUp() {
    	return MINIMUM_SIGN_UP_DAYS_OUT;
    }


    public List<Job> getJobList() {
   	    return new ArrayList<>(jobList);
    }
}
