package model;

import exceptions.LessThanMinDaysAwayException;
import exceptions.UnvolunteerPriorTimeException;
import exceptions.VolunteerDailyJobLimitException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class Volunteer extends User {

	/**
	 * A default minimum number of calendar days after the current date that a
	 * job begins and a volunteer may sign up for.
	 */
	public static final int MINIMUM_SIGN_UP_DAYS_OUT = 3;

	private List<Job> jobList;

    public Volunteer(String firstName, String lastName, UserID userID) {
        super(firstName, lastName, UserRole.VOLUNTEER, userID);
	    jobList = new ArrayList<>();
    }

    public int getMinDaysAwaySignUp() {
    	return MINIMUM_SIGN_UP_DAYS_OUT;
    }

    public List<Job> getJobList() {
   	    return new ArrayList<>(jobList);
    }

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

		newJob.addVolunteer(this);
		jobList.add(newJob);
    }
    
    public void unSignupAJob(Job theJob)
    			throws UnvolunteerPriorTimeException {
    	
    	if (theJob.isJobBeforeCurrentDate(theJob)){
    		throw new UnvolunteerPriorTimeException("The Job you want to unvolunteer is "
    				+ "too close to the current date. You may not be able to cancel it now!");
    	}
    	else{
    		jobList.remove(theJob);
    		theJob.removeVolunteer(this);
    	}
    				
    			
    }

    public List<Job> getChronologicalJobList() {
   	    List<Job> chronologicalList = new ArrayList<>(jobList);
   	    chronologicalList.sort(JobCollection.getChronologicalJobComparator());
   	    return chronologicalList;
    }
}
