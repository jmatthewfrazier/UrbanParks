package model;

import exceptions.*;

import java.util.HashMap;

public final class Volunteer extends User {

private HashMap<JobID, Job> jobsCurrentlyRegisteredForMap;

	/**
	 * A default minimum number of calendar days after the current date that a job begins and a volunteer may sign up 
	 * for.
	 */
	public static final int MINIMUM_SIGNUP_DAYS_OUT = 2;
	/** Collection of jobs that the volunteer has signed up for. */
	private JobCollection myJobs;
	/** Flag indicates whether or not the Volunteer had job signed up. */
	private boolean myIsSignedUp;
  
	/**
	 * Constructor for Volunteer class.
	 * 
	 */

    public Volunteer(JobCollection paramJobMap) {
        this("Test", "Volunteer",
                new UserID("volunteer_default"));
        myJobs = paramJobMap;
		    myIsSignedUp = true;
        jobsCurrentlyRegisteredForMap = new HashMap<>();
    }

    /**
	 * This would be the constructor for Volunteer that has no 
	 * jobs signed up before.
	 * 
	 * a new Volunteer Object. 
	 * @param firstName the First name of the Volunteer.
	 * @param lastName the Last name of the Volunteer.
	 * @param userID the User ID of the Volunteer.
	 */
    public Volunteer(String firstName, String lastName, UserID userID) {
        super(firstName, lastName, UserRole.VOLUNTEER, userID);

        jobsCurrentlyRegisteredForMap = new HashMap<>();

    }

    /**
	 * This is the constructor for Volunteer that has jobs signed
	 * up already.
	 * 
	 * a new Volunteer Object. 
	 * @param firstName the First name of the Volunteer.
	 * @param lastName the Last name of the Volunteer.
	 * @param userID the User ID of the Volunteer.
	 * @param theJobs the jobs that volunteer signed up already.
	 */
   public Volunteer(String firstName, String lastName, UserID userID,
		   				JobCollection theJobs) {
       super(firstName, lastName, UserRole.VOLUNTEER, userID);
       myJobs = theJobs;
       myIsSignedUp = false;
   }

    public void registerForJobInCollection(final Job jobToRegisterFor)
            throws VolunteerJobRegistrationException {

        try { //let's see if we can sign up for this job
            jobToRegisterFor.addVolunteerToThisJob(this);
        }
        catch(VolunteerDailyJobLimitException e ) {
            throw new VolunteerJobRegistrationException(e.getMsgString());
        }
        catch(VolunteerSignUpStartDateException e) {
            throw new VolunteerJobRegistrationException(e.getMsgString());
        }

        //this should not really ever happen, because if a job already has this
        //userId associated with it, then it should never display to the Volunteer
        //object in the first place, right?
        catch (DuplicateVolunteerUserIDException e) {
            throw new VolunteerJobRegistrationException(e.getMsgString());
        }
        //ok, by now all the checks should have been performed
        //that tells me the Volunteer has successfully registered for the Job
        //now we need to track this Job for comparisons in the future
        //so let's get some info from the system's job map:

        jobsCurrentlyRegisteredForMap.put(jobToRegisterFor.getID(), jobToRegisterFor);

    }

    public HashMap<JobID, Job> getJobsCurrentlyRegisteredForMap() {
        return jobsCurrentlyRegisteredForMap;
    }

    /**
     public void signupForNewJob(Job theCandidateJob)
     throws SignupForNewJobException{
     if (myIsSignedUp){
     try{
     isJobSameDay(theCandidateJob);
     }
     catch(JobInSameDayException e){
     throw new SignupForNewJobException(e.getMsgString());
     }
     try{
     isStartAtEndDate(theCandidateJob);
     }
     catch(JobStartAtEndDateException e){
     throw new SignupForNewJobException(e.getMsgString());
     }
     try{
     isEndAtStartDate(theCandidateJob);
     }
     catch(JobEndAtStartDateException e){
     throw new SignupForNewJobException(e.getMsgString());
     }
     try{
     isMoreThanMinimumDaysOut(theCandidateJob);
     }
     catch(LessThanMinimumDaysAwayException e){
     throw new SignupForNewJobException(e.getMsgString());
     }
     }

     myJobs.addJob(theCandidateJob);//Should we change this?
     //Otherwise it would go through the exceptions
     //again.
     }*/

    //end of Volunteer class
}
