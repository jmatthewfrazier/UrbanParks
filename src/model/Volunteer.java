package model;


import exceptions.*;

import java.util.HashMap;

public class Volunteer extends User {

    private JobMap volunteerJobMap;

    private HashMap<JobID, Job> jobsCurrentlyRegisteredForMap;

    public Volunteer(JobMap paramJobMap) {
        this("Test", "Volunteer",
                new UserID("volunteer_default"));

        volunteerJobMap = paramJobMap;

        jobsCurrentlyRegisteredForMap = new HashMap<>();
    }

    public Volunteer(String firstName, String lastName, UserID userID) {
        super(firstName, lastName, UserRole.VOLUNTEER, userID);
        jobsCurrentlyRegisteredForMap = new JobMap();
    }

    public void registerForJobInCollection(final Job jobToRegisterFor)
            throws VolunteerJobRegistrationException {
        //String retStr = "";
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
    //end of Volunteer class
}
