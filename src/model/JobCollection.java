package model;

import exceptions.InvalidJobEndDateException;
import exceptions.InvalidJobLengthException;
import exceptions.JobCollectionDuplicateKeyException;
import exceptions.MaxPendingJobsException;

import java.io.Serializable;
import java.util.*;

public final class JobCollection implements Serializable {

    private static int MAX_CAPACITY = 20;

    public Map<JobID, Job> jobMap;

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

    public static int getMaxCapacity() {
        return MAX_CAPACITY;
    }

//    public List<Job> getChronologicalList() {
//        Object[] jobArray = jobMap.values().toArray();
//        List<Job> result = new ArrayList<>(Arrays.<>asList((Job[])
//                jobArray));
//        result.sort(getChronologicalJobComparator());
//        return result;
//    }

    public List<Job> getList() {
        return new ArrayList<>(jobMap.values());
    }

    public boolean isAtMaxCapacity() {
        return (jobMap.size() < MAX_CAPACITY);
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
}