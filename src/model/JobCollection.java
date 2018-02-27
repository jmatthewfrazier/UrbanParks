package model;

import exceptions.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

public final class JobCollection implements Serializable {

    private int MAX_CAPACITY = 20;

    public final static int MIN_DAYS_REMOVAL_BUFFER = 2;

    private Map<JobID, Job> jobMap;

    public JobCollection() {

        jobMap = new HashMap<>();
    }

    //////////////info and settings for Job collection class///////////////////

    public void setMaxCapacity(User user, int capacity)
            throws InvalidJobCollectionCapacityException {
        if (user.getUserRole() == UserRole.STAFF_MEMBER) {
            if (isValidCapacity(capacity)) {
                MAX_CAPACITY = capacity;
            } else {
                throw new InvalidJobCollectionCapacityException("Capacity " +
                        "must be an Integer that is equal to or greater than " +
                        "0.");
            }
        }
    }

    /**
     * Pre: capacity must be an Integer that is greater than or equal to 0.
     */
    public final boolean isValidCapacity(Number capacity) {
        return capacity.getClass().equals(Integer.class)
                && capacity.intValue() > 0;
    }

    public int size() {
        return jobMap.size();
    }

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

    public static Comparator<Job> getChronologicalJobComparator() {
        class ChronologicalComparator implements  Comparator<Job> {
            @Override
            public int compare(Job o1, Job o2) {
                String s1 = o1.getName();
                String s2 = o2.getName();
                return s1.compareTo(s2);
            }
        }
        return new ChronologicalComparator();
    }

    /////////////fetch a collection of jobs //////////////////////////////////////

    public List<Job> getList() {
        return new ArrayList<>(jobMap.values());
    }

    public ArrayList<Job> getJobArrayListFilterByUserID(final UserID paramUserID) {
        ArrayList<Job> jobsFilteredByUserID = new ArrayList<>();
            for (Job j : this.getJobMap().values()) {
                if (j.getJobCreatorUserID().equals(paramUserID)) {
                    jobsFilteredByUserID.add(j);
                }
        } return jobsFilteredByUserID;
    }

    private Map<JobID, Job> getJobMap() {
        return jobMap;
    }
    /**
     * Creates and returns a list of all jobs in a specified date range.
     *
     * @param start is the start of the date range.
     * @param end is the end of the date range.
     * @return a list of all jobs that fall within the date range.
     */
    public List<Job> getJobsInDateRange(final LocalDateTime start,
    		final LocalDateTime end) {
    	ArrayList<Job> jobs = new ArrayList<Job>();

    	for (Job job : this.getList()) {
    		if (job.isJobStartAfterEqualDate(start) &&
    				job.isJobEndBeforeEqualDate(end)) jobs.add(job);
    	}

    	return jobs;
    }

    public List<Job> getAllFutureJobsFromToday() {
        return getJobsInDateRange(LocalDateTime.now(),
                LocalDateTime.now().plusDays(Job.MAX_NUM_DAYS_FROM_TODAY));
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
     * @pre-condition This method expects this JobCollection class holds Job
     * objects which in turn posses a JobID object and a UserID object.
     * These two ID objects will be used to find a Job to remove from this
     * collection, permanently.
     * This method verifies the Job being deleted was also created by the
     * same user.  A user may only delete jobs they created, this ensures
     * security and appropriate user roles.
     * @post-condition This method will either have thrown an excepetion due
     * to encountering illegal behavior or it will have removed one Job and
     * be 1 Job smaller in size.  Exception behavior is detailed below
     * @param jobToRemove //
     * @param jobRemoverUserID //
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
                                        final UserID jobRemoverUserID)
            throws LessThanMinDaysAwayException, UserNotFoundException,
            JobIDNotFoundInCollectionException {

        JobID jobToRemoveID = jobToRemove.getID();

        if (!jobMap.containsKey(jobToRemoveID)) { // check the job is in the collection
            throw new JobIDNotFoundInCollectionException("JobID not found in collection");
        //check if the user is the one who submitted the job
        } else if (!jobToRemove.getJobCreatorUserID().equals(jobRemoverUserID)){
            throw new UserNotFoundException("\nThis user did not originally " +
                    "submit this job.\nUser not authorized to unsubmit this " +
                    "job.\nJob not removed.\n");
        }
        LocalDateTime currentDateTime = LocalDateTime.now();
        // job has to be a min number of days in the future to be cancelled
        if (currentDateTime.toLocalDate().isBefore(
                jobToRemove.getBeginDateTime().toLocalDate().plusDays(MIN_DAYS_REMOVAL_BUFFER))) {
            //Job is too near in the future, not enough buffer time, can't be removed
            throw new LessThanMinDaysAwayException("This job begins soon, it cannot be removed");
        }
        //all conditions passed, job is removed from collection
        jobMap.remove(jobToRemoveID);

        //remove any volunteers who may have signed up for this job also
        removeVolunteersFromDeletedJob(jobToRemove);
    }

    /////////////////////////recycling ////////////////////////////////////////

//    public static void removeJobFromCollectionStatic(final Job jobToRemove) {
//        JobID jobToRemoveID = jobToRemove.getID();
//
//    }

    public void removeVolunteersFromDeletedJob(final Job removedJob) {

    }


    //end Job Collection class
}