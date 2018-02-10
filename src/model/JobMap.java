package model;

import exceptions.InvalidJobEndDateException;
import exceptions.InvalidJobLengthException;
import exceptions.JobCollectionDuplicateKeyException;
import exceptions.MaxPendingJobsException;
import exceptions.JobCollectionDuplicateKeyException;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class JobMap extends HashMap<JobID, Job> implements Serializable {

    /**
     * The max number of days away from the current date that the end of a
     * new job can be specified.
     */
    private int MAX_NUM_DAYS_FROM_TODAY = 75;

    private int MAX_JOB_LENGTH_IN_DAYS = 3;

    private int MAX_CAPACITY = 20;

    //these still need setters/getters
    private LocalDateTime beginDateTime;

    private LocalDateTime endDateTime;

    private Map<JobID, Job> jobMap;
    
    public JobMap() {
        super();
        //jobMap = new HashMap<>();
    }

//    /**
//     * Adds a Job to the collection.
//     *
//     * Pre: Collection must contain less than max jobs and the Job name must
//     * be unique from other Jobs.
//     * @param jobToAdd the Job that will be added
//     */
//    //no need to override the put from hashmap
//    public void addJob(final JobID jobIDToAdd, final Job jobToAdd) throws MaxPendingJobsException,
//            InvalidJobLengthException, InvalidJobEndDateException,
//            JobCollectionDuplicateKeyException {
//        //check if the job is at capacity first
//        //this needs to be in the park manager class
//        if (isJobMapAtMaxCapacity()) {
//            //jobmap is at capacity, let the user know it is so
//            throw new MaxPendingJobsException("Sorry, the pending job list " +
//                    "is at maximum capacity, please add your job at a later date");
//        } else if (!isNewJobLengthValid()) { //check to see if the job length is valid
//            throw new InvalidJobLengthException("Sorry, the length of this job" +
//                    "is too many days.");
//
//        } else if (!isJobWithinValidDateRange()) { //check to see if the job's end date is acceptable
//            throw new InvalidJobEndDateException("Sorry, the end date of that" +
//                    "job is too far in the future");
//        } else if (jobMap.containsKey(jobToAdd.getID())) { //check to see if the job is already present in the map
//            throw new JobCollectionDuplicateKeyException("A Job matching that" +
//                    "key value is already present in the Job collection.");
//        } else { //all checks passed, add the job to the JobMap
//            this.put(jobIDToAdd, jobToAdd);
//        }
//
//    }
//
//    /**
//     * Specifies if the collection is at full capacity.
//     *
//     *
//     * @return true if collection is at capacity
//     */
//    public boolean isJobMapAtMaxCapacity() {
//
//        return (jobMap.size() >= MAX_CAPACITY);
//    }
//
//    /*
//     * Accessor method to check if the length of a proposed new job is within
//     * the maximum allowable time limit.
//     *
//     * If the proposed job length is longer than the max allowable time, will
//     * return false.
//     */
//    public boolean isNewJobLengthValid() {
//        boolean retBool = false;
//        LocalDateTime maxValidJobEndDate =
//                beginDateTime.plusDays(MAX_JOB_LENGTH_IN_DAYS);
//        if (maxValidJobEndDate.isAfter(this.endDateTime)) {
//            retBool = true;
//        }
//
//        return retBool;
//    }
//
//    /**
//     * Specifies if this Job is within a valid date range.
//     *
//     * @return true if the Job is within the valid date range, false otherwise
//     */
//    public boolean isJobWithinValidDateRange() {
//        return endDateTime.minusDays(MAX_NUM_DAYS_FROM_TODAY).compareTo
//                (LocalDateTime.now()) <= 0;
//    }
//
//    /**
//     * @return the jobHashMap
//     */
//    public Map<JobID, Job> getJobMap() {
//        return jobMap;
//    }
//
////    public JobID getJobIDForJob(final Job jobToGetIDFor) {
////        for (Job j in this.) {
////
////        }
////    }
//
//
//    /** TODO - not sure we need this, we have a method to add things to it,
//     * TODO we will probably have to write another method with some logic
//     * TODO checks for deleting jobs too, so let's scrap this one?
//     * @param jobHashMap the jobHashMap to set
//
//    public void setJobHashMap(HashMap<String, Job> jobHashMap) {
//    this.jobHashMap = jobHashMap;
//    }
//     */
}
