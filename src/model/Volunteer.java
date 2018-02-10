package model;

import exceptions.*;

/**
 * This is the Volunteer class that extends the user abstract class.
 * 
 * @author Yulin, Eli
 * @version 2/9/2018
 *
 */

public final class Volunteer extends User {

	/**
	 * A default minimum number of calendar days after the current date that a job begins and a volunteer may sign up 
	 * for.
	 */
	public static final int MINIMUM_SIGNUP_DAYS_OUT = 2;
	/** Collection of jobs that the volunteer has signed up for. */
	private JobMap myJobs;
	/** Flag indicates whether or not the Volunteer had job signed up. */
	private boolean myIsSignedUp;
	/**
	 * Constructor for Volunteer class.
	 * 
	 */
    public Volunteer(JobMap paramJobMap) {
        this("Test", "Volunteer",
                new UserID("volunteer_default"));
        myJobs = theParamJobMap;
		myIsSignedUp = true;

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
        myIsSignedUp = false;
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
		   				JobMap theJobs) {
       super(firstName, lastName, UserRole.VOLUNTEER, userID);
       myJobs = theJobs;
       myIsSignedUp = false;
   }
   
   /**
	 * This method would handle the situation when the volunteer is trying 
	 * to sign up for jobs that extend across the job(s) already signed up for.
	 * 
	 * @param theCandidateJob the job that the Volunteer is trying to sign up for.
	 * @return boolean value shows if the Volunteer can sign up for this job.
	 */
	public boolean isJobSameDay(final Job theCandidateJob) 
			throws JobInSameDayException {
		for (Job job: myJobs){
			if (job.isJobOverlapping(theCandidateJob))
				throw new JobInSameDayException("Sorry, the job you " + 
						"are applying extends across the job you have" +
						" signed up already!!");
		}
		return true;
	}
	
	/**
	 * This method checks if the Volunteer is trying to sign up for a
	 * job that starts at the end date of the jobs that the Volunteer
	 * has signed up for.
	 * 
	 * @param theCandidateJob the job that the Volunteer is trying to sign up for.
	 * @return boolean value shows if the Volunteer can sign up for this job.
	 */
	public boolean isStartAtEndDate(Job theCandidateJob) throws
						JobStartAtEndDateException {
		for (Job job: myJobs){
			if (job.isStartAtEndDate(theCandidateJob))
				throw new JobStartAtEndDateException("Sory, " + 
						"the job you are applying starts at " + 
						"the end date of one of your signed up jobs!");
		}
		return true;
	}
	
	/**
	 * This method checks if the Volunteer is trying to sign up for a
	 * job that ends at the start date of the jobs that the Volunteer
	 * has signed up for.
	 * 
	 * @param theCandidateJob the job that the Volunteer is trying to sign up for.
	 * @return boolean value shows if the Volunteer can sign up for this job.
	 */
	public boolean isEndAtStartDate(Job theCandidateJob) throws
					JobEndAtStartDateException {
		for(Job job: myJobs){
			if (job.isEndAtStartDate(theCandidateJob))
				throw new JobEndAtStartDateException("Sorry, " +
						"the job you are applying ends at the " +
						"date of one of your signed up jobs!!" );
		}
		return true;
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
	public boolean isMoreThanMinimumDaysOut(Job candidateJob) throws
						LessThanMinimumDaysAwayException{
		boolean beyondMin = true;
		
		if (candidateJob.dateDifference() < MINIMUM_SIGNUP_DAYS_OUT){
			beyondMin = false;
			throw new LessThanMinimumDaysAwayException("Sorry, " +
					"the job you are applying is less than the " +
					"minimum days away from one of your signed up jobs!!");
		}		
		return beyondMin;
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

    public void registerForJobInCollection(final Job jobToAdd)
            throws VolunteerJobRegistrationException {
        //String retStr = "";
        try {
            jobToAdd.addNewVolunteer(this);
        }
        catch(VolunteerDailyJobLimitException e ) {
            throw new VolunteerJobRegistrationException(e.getMsgString());
        }
        catch(VolunteerSignUpStartDateException e) {
            throw new VolunteerJobRegistrationException(e.getMsgString());
        }
//        catch (InvalidJobEndDateException e) {
//            throw new VolunteerJobRegistrationException(e.getMsgString());
//        }
        //this should not really ever happen, because if a job already has this
        //userId associated with it, then it should never display to the Volunteer
        //object in the first place, right?
        catch (DuplicateVolunteerUserIDException e) {
            throw new VolunteerJobRegistrationException(e.getMsgString());
        }

    }
    //end of Volunteer class
}
