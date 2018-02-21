package model;

import exceptions.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

public final class JobCollection implements Serializable {

    public final static int MAX_CAPACITY = 20;

    public final static int MIN_DAYS_REMOVAL_BUFFER = 2;

    private Map<JobID, Job> jobMap;

    public JobCollection() {

        jobMap = new HashMap<>();
    }

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

    public int size() {
        return jobMap.size();
    }

    public static int getMaxCapacity() {
        return MAX_CAPACITY;
    }


    public List<Job> getList() {
        return new ArrayList<>(jobMap.values());
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

    public List<Job> listAllJobsByParkManager(final ParkManager pm) {

        return new ArrayList<>(jobMap.values());
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
     * @param jobID object with unique identifier of this Job object
     * @param userID object with unique identifier of person attempting to
     *               delete this job
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
    public void removeJobFromCollection(final JobID jobID, final UserID userID)
            throws LessThanMinDaysAwayException, UserNotFoundException,
            JobIDNotFoundInCollectionException, UrbanParksSystemOperationException {
        Job jobToRemove;
        if (!jobMap.containsKey(jobID)) { // check the job is in the collection
            throw new JobIDNotFoundInCollectionException("JobID not found in collection");
        } else {
            jobToRemove = jobMap.get(jobID);
        }
        UserID jobCreatorID = jobToRemove.getJobCreatorID();
        LocalDateTime currentDateTime = LocalDateTime.now();
        if (jobCreatorID.compareTo(userID) != 0) { // check the userid is allowed to remove this job
            //this user was not the creator of this job, can't remove it
            throw new UserNotFoundException("This UserID does not match the creator of this job");
        }
        // job has to be a min number of days in the future to be cancelled
        if (currentDateTime.toLocalDate().isBefore(
                jobToRemove.getBeginDateTime().toLocalDate().plusDays(MIN_DAYS_REMOVAL_BUFFER))) {
            //Job is too near in the future, not enough buffer time, can't be removed
            throw new LessThanMinDaysAwayException("This job begins soon, it cannot be removed");
        }
        //all conditions passed, job is removed from collection
        int mapSizeBegin = jobMap.values().size();
        jobMap.remove(jobID);
        int mapSizeEnd = jobMap.values().size();
        if (mapSizeEnd != (mapSizeBegin + 1)) { //post condition wasn't met for some reason
            throw new UrbanParksSystemOperationException("Job was not removed from collection?");
        }
        // all volunteers signed up for this job should have it removed from their lists as well
        removeVolunteersFromDeletedJob(jobToRemove);
    }

    public void removeVolunteersFromDeletedJob(final Job removedJob) {

    }
}