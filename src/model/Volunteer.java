package model;

import java.util.Collection;

/**
 * This is the Volunteer class that extends the user abstract class.
 * 
 * @author Yulin Wang
 * @version 2/8/2018
 *
 */
public class Volunteer extends User {
	/**
	 * A default minimum number of calendar days after the current date that a job begins and a volunteer may sign up 
	 * for.
	 */
	public static final int MINIMUM_SIGNUP_DAYS_OUT = 2;
	/** Collection of jobs that the volunteer has signed up for. */
	private Collection<Job> myJobs;
	/** Flag indicates whether or not the Volunteer had job signed up. */
	private boolean myIsSignedUp;
	/**
	 * Constructor for Volunteer class.
	 * 
	 */
	public Volunteer(){
		this("Test", "Volunteer",
				new UserID("Volunteer_default"));
	}
	
	/**
	 * This would be the one that use the read in data to instantiate 
	 * a new Volunteer Object. 
	 */
	public Volunteer(){
		super(theFname, theLame, UserRole.VOLUNTEER, userID);
		// TO-DO
		// We should probably have something here to connect the jobs that
		// the Volunteer has signed up for, and a flag indicates whether or
		// not this Volunteer has signed up for jobs. For test 1a.
		// I will assume that they are there for me to use for just now.
	}
	
	/**
	 * This method would handle the situation when the volunteer is trying 
	 * to sign up for jobs that extend across the job(s) already signed up for.
	 * 
	 * @param theCandidateJob the job that the Volunteer is trying to sign up for.
	 * @return boolean value shows if the Volunteer can sign up for this job.
	 */
	public boolean isJobSameDay(Job theCandidateJob){
		// Set flag initially to be false
		boolean isJobSameDay = false;
		// Check if Volunteer has jobs signed up
		if (myIsSignedUp){// If Volunteer has job(s) signed up, do the checking
			for (Job jobs: myJobs){
				isJobSameDay = (jobs.isJobOverlapping(theCandidateJob) ||
						isJobSameDay);
			}
		}
		// Return the flag 
		return isJobSameDay;
	}
	
	/**
	 * This method checks if the Volunteer is trying to sign up for a
	 * job that starts at the end date of the jobs that the Volunteer
	 * has signed up for.
	 * 
	 * @param theCandidateJob the job that the Volunteer is trying to sign up for.
	 * @return boolean value shows if the Volunteer can sign up for this job.
	 */
	public boolean isStartAtEndDate(Job theCandidateJob){
		// Set flag initially to be false
		boolean isJobStartAtEndDate = false;
		// Check if Volunteer has jobs signed up
		if (myIsSignedUp){
			for (Job jobs: myJobs){
				isJobStartAtEndDate = (jobs.isStartAtEndDate(theCandidateJob)
						|| isJobStartAtEndDate);
			}
		}
		// Return the flag 
		return isJobStartAtEndDate;
	}
	
	/**
	 * This method checks if the Volunteer is trying to sign up for a
	 * job that ends at the start date of the jobs that the Volunteer
	 * has signed up for.
	 * 
	 * @param theCandidateJob the job that the Volunteer is trying to sign up for.
	 * @return boolean value shows if the Volunteer can sign up for this job.
	 */
	public boolean isEndAtStartDate(Job theCandidateJob){
		// Set flag initially to be false
				boolean isJobEndAtStartDate = false;
				// Check if Volunteer has jobs signed up
				if (myIsSignedUp){
					for (Job jobs: myJobs){
						isJobEndAtStartDate = (jobs.isEndAtStartDate(theCandidateJob)
								|| isJobEndAtStartDate);
					}
				}
				// Return the flag 
				return isJobEndAtStartDate;
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
	public boolean isMoreThanMinimumDaysOut(Job candidateJob) {
		boolean beyondMin = true;
		
		if (candidateJob.dateDifference() < MINIMUM_SIGNUP_DAYS_OUT)
			beyondMin = false;
		
		return beyondMin;
	}
	
}
