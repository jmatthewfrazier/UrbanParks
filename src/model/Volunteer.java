package model;

import exceptions.*;

public class Volunteer extends User {

    private JobMap volunteerJobMap;

    public Volunteer(JobMap paramJobMap) {
        this("Test", "Volunteer",
                new UserID("volunteer_default"));

        volunteerJobMap = paramJobMap;
    }

    public Volunteer(String firstName, String lastName, UserID userID) {
        super(firstName, lastName, UserRole.VOLUNTEER, userID);
    }

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
