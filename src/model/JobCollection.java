package model;

import exceptions.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class JobCollection implements Serializable {

    private final static int MIN_DAYS_REMOVAL_BUFFER = 3;

    private Map<JobID, Job> jobMap;
    private int MAX_CAPACITY = 10;

    public JobCollection() {
        this.jobMap = new HashMap<>();
    }

    /**
     * Sets the max number of jobs that can be in the system at once.
     *
     * @param user is the User that is currently logged in to the system.
     * @param capacity is the new job capacity number.
     * @throws InvalidJobCollectionCapacityException iff capacity is not a valid
     * number.
     * @throws InvalidUserException iff user is not of type STAFF_MEMBER.
     */
    public final void setMaxCapacity(final User user, int capacity)
            throws InvalidJobCollectionCapacityException, InvalidUserException {
        if (user.getUserRole() == UserRole.STAFF_MEMBER) {
            if (isValidCapacity(capacity)) {
                MAX_CAPACITY = capacity;
            } else {
                throw new InvalidJobCollectionCapacityException("Capacity " +
                        "must be an Integer that is equal to or greater than " +
                        "0.");
            }
        } else {
            throw new InvalidUserException("Only a staff member may change " +
                    "the job collection capacity.");
        }
    }

    public int getLargestIDNumber() {
        int max = 0;

        for (JobID id : jobMap.keySet()) {
            max = Math.max(max, id.getJobIDNumber());
        }

        return max;
    }

    /**
     * Pre: capacity must be an Integer that is greater than or equal to 0.
     */
    public final boolean isValidCapacity(final Number capacity) {
        return capacity.getClass().equals(Integer.class)
                && capacity.intValue() > 0;
    }

    /////////////fetch a collection of jobs //////////////////////////////////////

    public List<Job> getList() {
        return new ArrayList<>(jobMap.values());
    }
    
    /**
     * Creates and returns a list of all jobs in a specified date range.
     *
     * @param start is the start of the date range (date must be valid).
     * @param end is the end of the date range (date must be valid).
     * @return a list of all jobs that fall within the specified date range.
     */
    public List<Job> getJobsInDateRange(final LocalDateTime start,
    		final LocalDateTime end) {
    	List<Job> jobs = new ArrayList<>();

    	for (Job job : this.getList()) {
    		if (job.isJobStartAfterEqualDate(start) &&
    				job.isJobEndBeforeEqualDate(end)) jobs.add(job);
    	}

    	return jobs;
    }

    public List<Job> getAllFutureJobsFromToday() {
        return getJobsInDateRange(LocalDateTime.now(),
                LocalDateTime.now()
                        .plusDays(Job.getMaximumValidDayRangeFromToday()));
    }

    /////////////add or remove a job /////////////////////////////////////////

    /**
     * Adds a Job to the collection.
     *
     * Pre: Collection must contain less than max jobs and the Job name must
     * be unique from other Jobs.
     * @param jobToAdd the Job that will be added
     */
    public void addJob(final Job jobToAdd) throws MaxPendingJobsException,
            InvalidJobLengthException, InvalidJobEndDateException,
            JobCollectionDuplicateKeyException {

        if (isAtMaxCapacity()) {
            throw new MaxPendingJobsException("Sorry, the pending job list " +
                    "is at maximum capacity, please add your job at a later date");
        } else if (!jobToAdd.isJobLengthValid()) {
            throw new InvalidJobLengthException("Sorry, the length of this job" +
                    "is too many days.");
        } else if (!jobToAdd.isJobWithinValidDateRange()) {
            throw new InvalidJobEndDateException("Sorry, the end date of that" +
                    "job is too far in the future");
        } else if (jobMap.containsKey(jobToAdd.getID())) {
            throw new JobCollectionDuplicateKeyException("A Job matching that" +
                    "key value is already present in the Job collection.");
        } else {
            jobMap.put(jobToAdd.getID(), jobToAdd);
        }

    }


    /**
     * Removes jobToRemove from this job collection.
     * @param jobToRemove is the job that should be removed.
     * @param parkManager is the Park Manager that is attempting to remove
     * jobToRemove.
     * @throws LessThanMinDaysAwayException The job to be deleted was too
     * near in the future, deleting it was not allowed
     * @throws UserNotFoundException The user attempting to delete this Job
     * was not found to be the creator of this job, deleting was not allowed
     * @throws JobIDNotFoundInCollectionException The JobID of the Job to be
     * deleted was not found in this collection, no change was made
     * @throws UrbanParksSystemOperationException The deletion failed for some
     * reason, the post condition of the collection being one smaller in size
     * was not met.  The collection is not of size n-1.
     */
    public void removeJobFromCollection(final Job jobToRemove,
                                        final ParkManager parkManager)
            throws LessThanMinDaysAwayException,
            JobIDNotFoundInCollectionException, InvalidUserException {

        JobID jobToRemoveID = jobToRemove.getID();

        if (!jobMap.containsKey(jobToRemoveID)) { // check the job is in the collection
            throw new JobIDNotFoundInCollectionException("JobID not found in collection");
        }
        //check if the user is the one who submitted the job
        LocalDateTime currentDateTime = LocalDateTime.now();
        // job has to be a min number of days in the future to be cancelled
        if (currentDateTime.plusDays(MIN_DAYS_REMOVAL_BUFFER)
        		.isAfter(jobToRemove.getBeginDateTime())) {
            //Job is too near in the future, not enough buffer time, can't be removed
            throw new LessThanMinDaysAwayException("This job begins soon, it cannot be removed");
        }

        if (!parkManager.equals(jobToRemove.getJobCreator())) {
            throw new InvalidUserException();
        }
        //all conditions passed, job is removed from collection
        jobMap.remove(jobToRemoveID);
    }

    /////////////////////////recycling ////////////////////////////////////////

     public int getMaxCapacity() {
         return MAX_CAPACITY;
     }

     public boolean isAtMaxCapacity() {
         return (jobMap.size() >= MAX_CAPACITY);
     }

     public boolean isEmpty() {
         return jobMap.isEmpty();
     }

     public boolean containsJobID(JobID id) {
         return jobMap.containsKey(id);
     }
}