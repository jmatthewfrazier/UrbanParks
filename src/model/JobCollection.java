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
        if (mapSizeEnd != (mapSizeBegin + 1)) {
            throw new UrbanParksSystemOperationException("Job was not removed from collection?");
        }
        // all volunteers signed up for this job should have it removed from their lists as well
        for (UserID uid : jobToRemove.getVolunteerUserIDList()) {

        }
    }


}