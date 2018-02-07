package model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Job implements Serializable {

    /************************************************************************
     * STATIC FIELDS ********************************************************
     ************************************************************************/

    /**
     * The max number of days away from the current date that the end of a
     * new job can be specified.
     */
    public static int MAX_NUM_DAYS_FROM_TODAY = 75;

    public Long maxJobLengthInDays = (long) 3;

    /**
     * unique identifier for object serialization
     */
    private static final long serialVersionUID = 8341912696713916150L;

    /************************************************************************
     * NON-STATIC FIELDS ****************************************************
     ************************************************************************/

    public String jobName;

    public LocalDateTime beginDateTime;

    public LocalDateTime endDateTime;

	public Park jobLocation;

//	public UrbanParksSystemUserInterface ui;

    public Job(String jobName,  Park jobPark,  LocalDateTime beginDate,
               LocalDateTime endDate) {
        this.jobName = jobName;
        this.beginDateTime = beginDate;
        this.endDateTime = endDate;
		this.jobLocation = jobPark;
    }

    /************************************************************************
     * STATIC METHODS *******************************************************
     ************************************************************************/

    /************************************************************************
     * NON-STATIC METHODS ***************************************************
     ************************************************************************/


    public String createJobCollectionMapKey() {
        String retStr = null;
        //whatever fields will be used to create a unique key go here
        return retStr;
    }

    // TODO: finish comment
    /**
     *
     * @return
     */
    public boolean submitNewJob() {
        boolean retBool = false;
        if (isNewJobValid()) {
            retBool = submitValidatedJob();
        }

        return retBool;
    }
    /**
     * Examine job attributes and return whether the job fits the
     * specified validation criteria.  Additional criteria can be
     * added as needed.
     *
     * TODO
     * @return
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

        return retBool;
    }

    /**
     * Accessor method to check if the length of a proposed new job is within
     * the maximum allowable time limit.
     *
     * If the proposed job length is longer than the max allowable time, will
     * return false.
     *
     * TODO
     * @return
     */
    public boolean isNewJobLengthValid() {
        boolean retBool = false;
        LocalDateTime maxValidJobEndDate = beginDateTime.plusDays(maxJobLengthInDays);
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

    // TODO
    /**
     *
     * @return
     */
    public boolean submitValidatedJob() {
        boolean retBool = false;

        return retBool;
    }

}

