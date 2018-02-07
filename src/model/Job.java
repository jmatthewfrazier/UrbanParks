package model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Job implements Serializable {

    /**
     * The max number of days away from the current date that the end of a
     * new job can be specified.
     */
    //TODO-is there a reason we are using "static" here? why not
    // setters/getters?
    private static int MAX_NUM_DAYS_FROM_TODAY = 75;

    private static int MAX_JOB_LENGTH_IN_DAYS = 3;

    /**
     * unique identifier for object serialization
     */
    private static final long serialVersionUID = 8341912696713916150L;

    public String jobName;

    public LocalDateTime beginDateTime;

    public LocalDateTime endDateTime;

	public Park jobLocation;

//	public UrbanParksSystemUserInterface ui;

    public Job(final String jobName, final Park jobPark
            , final LocalDateTime beginDate
            , final LocalDateTime endDate) {
        this.jobName = jobName;
        this.beginDateTime = beginDate;
        this.endDateTime = endDate;
		this.jobLocation = jobPark;

		validateJobVariables();
    }

    public int getMaximumValidDayRangeFromToday() {
        return MAX_NUM_DAYS_FROM_TODAY;
    }

    public int getMaxJobLengthInDays() {
        return MAX_JOB_LENGTH_IN_DAYS;
    }

    /**
     * A method that checks all business rules a new instance of job is expected
     * to follow.
     * TODO - we need to decide what happens if a user fails a business
     * rule.  Do we throw an exception?  That seems kind of extreme but it may
     * be the easiest way to go about things for now?
     *
     */
    //throws InvalidJobException
    private void validateJobVariables()  {

    }


    public String createJobCollectionMapKey() {
        String retStr = null;
        //whatever fields will be used to create a unique key go here
        return retStr;
    }

    /** TODO-here do we signal a successful add to a User in the job class or
     * in the JobMap class?  I think the JobMap class since it will be the
     * one actually adding the new Job.
     *
     */
    public boolean submitNewJob() {
        boolean retBool = false;
        //signal it went wrong either here?
        if (isNewJobValid()) {
            //or here for adds that aren't successful?
            //or the JobMap class instead?
            retBool = submitValidatedJob();
        }
        return retBool;
    }
    /*
     * Examine job attributes and return whether the job fits the
     * specified validation criteria.  Additional criteria can be
     * added as needed.
     * TODO-this is obviously not right logic, i think we first need to figure
     * out how to manage failed business rule criteria.
     * Exceptions?
     * Only console messages?
     * ???
     */
    public boolean isNewJobValid() {
        boolean retBool = false;
        //validate business case: job must be =< maxDays days in total length
        //job length is (maxDays - 1)
        //job length is (maxDays)
        //job length is (maxDays + 1)
        if (isNewJobLengthValid()) {
            retBool = true;
        }
        if (isJobWithinValidDateRange()) {
            retBool = true;
        }
        return retBool;
    }

    /*
     * Accessor method to check if the length of a proposed new job is within
     * the maximum allowable time limit.
     *
     * If the proposed job length is longer than the max allowable time, will
     * return false.
     */
    public boolean isNewJobLengthValid() {
        boolean retBool = false;
        LocalDateTime maxValidJobEndDate =
                beginDateTime.plusDays(MAX_JOB_LENGTH_IN_DAYS);
        if (maxValidJobEndDate.isAfter(this.endDateTime)) {
            retBool = true;
        }

        return retBool;
    }

    /**
     * Specifies if this Job is within a valid date range.
     *
     * @return true if the Job is within the valid date range, false otherwise
     */
    public boolean isJobWithinValidDateRange() {
        return endDateTime.minusDays(MAX_NUM_DAYS_FROM_TODAY).compareTo
                (LocalDateTime.now()) <= 0;
    }

    //TODO-need to figure out how to couple this to the JobMap instance.

    /**
     *
     * @return was the validated job successfully added or not?
     */
    public boolean submitValidatedJob() {
        boolean retBool = false;
        //talk with the JobMap here, but how?
        //get back some input about adding this Job instance
        return retBool;
    }

}

