package model;

import exceptions.LessThanMinDaysAwayException;
import exceptions.UnvolunteerPriorTimeException;
import exceptions.UrbanParksSystemOperationException;
import exceptions.UserNotFoundException;
import exceptions.UserRoleCategoryException;
import exceptions.VolunteerDailyJobLimitException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import controller.Controller;

public final class Volunteer extends User {

	/**
	 * A default minimum number of calendar days after the current date that a
	 * job begins and a volunteer may sign up for.
	 */
	public static final int MINIMUM_SIGN_UP_DAYS_OUT = 3;

	private List<Job> jobSignupByMe;
	
	private Controller systemController;

    public Volunteer(final String firstName, final String lastName, 
    					final Controller paramSystemController) {
        super(firstName, lastName, UserRole.VOLUNTEER, 
        			paramSystemController.getCurrentUser().getID());
	    this.systemController = paramSystemController;
        
        populateJobSignupByMe();
    }
    
    public void populateJobSignupByMe(){
    	try {
    		jobSignupByMe = 
    				systemController.getFutureJobsSignupByVolunteer(this.getID());
    	} catch (UserRoleCategoryException e) {
            //TODO - how to handle this in this class?
        } catch (UserNotFoundException e) {
            //TODO - again how to handle in this class?

        }
    }
    
    public List<Job> getFutureJobsSubmittedByMe() {
        ArrayList<Job> futureJobsSignupByMe = new ArrayList<>();
        LocalDateTime currentDate = LocalDateTime.now();
        for (Job j : jobSignupByMe) {
            if (j.getBeginDateTime().isAfter(currentDate)) {
                futureJobsSignupByMe.add(j);
            }
        }
        return futureJobsSignupByMe;
    }

    public int getMinDaysAwaySignUp() {
    	return MINIMUM_SIGN_UP_DAYS_OUT;
    }

    public List<Job> getJobList() {
   	    return new ArrayList<>(jobSignupByMe);
    }
    
    public void unvolunteerFutureJobSignupByMe(final Job jobToRemove)
            throws UrbanParksSystemOperationException {
        try {
            systemController.unvolunteerParkJob(jobToRemove);
        } catch (UrbanParksSystemOperationException e) {
            throw new UrbanParksSystemOperationException(e.getMsgString());
        }
        jobSignupByMe.remove(jobToRemove);
    }
    
    public void signupForJob(final Job jobToAdd) 
    		throws UrbanParksSystemOperationException {
    	try {
    		systemController.signupNewJobByVolunteer(jobToAdd);
    	} catch (UrbanParksSystemOperationException e) {
    		throw new UrbanParksSystemOperationException(e.getMsgString());
    	}
    	jobSignupByMe.add(jobToAdd);
    }

    public List<Job> getChronologicalJobList() {
   	    List<Job> chronologicalList = new ArrayList<>(jobSignupByMe);
   	    chronologicalList.sort(JobCollection.getChronologicalJobComparator());
   	    return chronologicalList;
    }

    public void removeJobFromMyRegisteredJobs(final Job jobToRemove) {
    	if (jobSignupByMe.contains(jobToRemove)) {
    		jobSignupByMe.remove(jobToRemove);
		}
	}
    
    /**
    public void signUpForJob(Job newJob) throws VolunteerDailyJobLimitException,
		    LessThanMinDaysAwayException {

   	    if (newJob.getBeginDateTime().isBefore(LocalDateTime
		        .now().plusDays(getMinDaysAwaySignUp()))) {
   	    	throw new LessThanMinDaysAwayException("Job begins too soon");
        }

		for (Job job : jobSignupByMe) {
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
		jobSignupByMe.add(newJob);
    }
    
    public void unSignupAJob(Job theJob)
    			throws UnvolunteerPriorTimeException {
    	
    	if (theJob.isJobBeforeCurrentDate(theJob)){
    		throw new UnvolunteerPriorTimeException("The Job you want to unvolunteer is "
    				+ "too close to the current date. You may not be able to cancel it now!");
    	}
    	else{
    		jobSignupByMe.remove(theJob);
    		theJob.removeVolunteer(this);
    	}
    				
    			
    }*/

    //end Volunteer class
}
