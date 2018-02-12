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
	public static final int MINIMUM_SIGN_UP_DAYS_OUT = 2;

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

   	    if (newJob.getBeginDateTime().compareTo(LocalDateTime.now()) <
		        getMinDaysAwaySignUp()) {
   	    	throw new LessThanMinDaysAwayException();
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

    public List<Job> getChronologicalJobList() {
   	    List<Job> chronologicalList = new ArrayList<>(jobList);
   	    chronologicalList.sort(JobCollection.getChronologicalJobComparator());
   	    return chronologicalList;
    }
}
