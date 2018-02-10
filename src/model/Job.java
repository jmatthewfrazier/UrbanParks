package model;

import exceptions.DuplicateVolunteerUserIDException;
import exceptions.VolunteerDailyJobLimitException;
import exceptions.VolunteerSignUpStartDateException;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;

public class Job implements Serializable {

//    /**
//     * The max number of days away from the current date that the end of a
//     * new job can be specified.
//     */
//    //TODO-is there a reason we are using "static" here? why not
//    // setters/getters?
    private static final int MAX_NUM_DAYS_FROM_TODAY = 75;
//
//    private static final int MAX_JOB_LENGTH_IN_DAYS = 3;

    /**
     * unique identifier for object serialization
     */
    private static final long serialVersionUID = 8341912696713916150L;

    private String name;

    private JobID ID;

    private LocalDateTime beginDateTime;

    private LocalDateTime endDateTime;

    private Park park;

    private UserCollection usersRegistered;


    public Job(final String jobName,
               final Park jobPark,
               final LocalDateTime beginDate,
               final LocalDateTime endDate) {
        this.name = jobName;
        this.beginDateTime = beginDate;
        this.endDateTime = endDate;
		this.park = jobPark;

		usersRegistered = new UserCollection();
    }

    public static final int getMaximumValidDayRangeFromToday() {

        return MAX_NUM_DAYS_FROM_TODAY;
    }


//    public int getThisJobLengthInDays() {
//        int jobLength = this.endDateTime.
//        return MAX_JOB_LENGTH_IN_DAYS;
//    }

    public void addUserToThisJob(final User userToAdd) throws
            VolunteerSignUpStartDateException, VolunteerDailyJobLimitException,
            DuplicateVolunteerUserIDException {

        //blah blah add a user to this job
    }
    //other access methods here
    //do the checking like from Yulin's

    public String getName() {
        return name;
    }

    public Park getPark() {
        return park;
    }

    public JobID getID() {
        return ID;
    }

    public LocalDateTime getBeginDateTime() {
        return beginDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setPark(final Park park) {
        this.park = park;
    }

    public void setBeginDateTime(final LocalDateTime time) {
        this.beginDateTime = time;
    }

    public void setEndDateTime(final LocalDateTime time) {
        this.endDateTime = time;
    }

//    /**
//     * A method that checks all business rules a new instance of job is expected
//     * to follow.
//     * TODO - we need to decide what happens if a user fails a business
//     * rule.  Do we throw an exception?  That seems kind of extreme but it may
//     * be the easiest way to go about things for now?
//     *
//     */
//    //throws InvalidJobException
//    private void validateJobVariables()  {
//
//    }
//
//
//    public String createJobCollectionMapKey() {
//        String retStr = null;
//        //whatever fields will be used to create a unique key go here
//        return retStr;
//    }

//    /** TODO-here do we signal a successful add to a User in the job class or
//     * in the JobMap class?  I think the JobMap class since it will be the
//     * one actually adding the new Job.
//     *
//     */
//    public boolean submitNewJob() {
//        boolean retBool = false;
//        //signal it went wrong either here?
//        if (isNewJobValid()) {
//            //or here for adds that aren't successful?
//            //or the JobMap class instead?
//            retBool = submitValidatedJob();
//        }
//        return retBool;
//    }
//    /*
//     * Examine job attributes and return whether the job fits the
//     * specified validation criteria.  Additional criteria can be
//     * added as needed.
//     * TODO-this is obviously not right logic, i think we first need to figure
//     * out how to manage failed business rule criteria.
//     * Exceptions?
//     * Only console messages?
//     * ???
//     */
//    public boolean isNewJobValid() {
//        boolean retBool = false;
//        //validate business case: job must be =< maxDays days in total length
//        //job length is (maxDays - 1)
//        //job length is (maxDays)
//        //job length is (maxDays + 1)
//        if (isNewJobLengthValid()) {
//            retBool = true;
//        }
//        if (isJobWithinValidDateRange()) {
//            retBool = true;
//        }
//        return retBool;
//    }





//    //TODO-need to figure out how to couple this to the JobMap instance.
//
//    /**
//     *
//     * @return was the validated job successfully added or not?
//     */
//    public boolean submitValidatedJob() {
//        boolean retBool = false;
//        //talk with the JobMap here, but how?
//        //get back some input about adding this Job instance
//        return retBool;
//    }

}

