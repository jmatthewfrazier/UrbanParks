package model;

import java.io.Serializable;
import java.util.HashMap;

public final class JobCollection extends HashMap<JobID, Job>
        implements Serializable {

    private static final int MAX_CAPACITY = 20;

    public JobCollection() {
        super();
    }

    public static int getMaxCapacity() {
        return MAX_CAPACITY;
    }

//    public static class AlphabeticalComparator implements  Comparator<Job> {
//        @Override
//        public int compare(Job o1, Job o2) {
//            String s1 = o1.getName();
//            String s2 = o2.getName();
//            return s1.compareTo(s2);
//        }
//    }
//
//
//    public static class ChoronologicalComparator implements Comparator<Job> {
//        @Override
//        public int compare(Job o1, Job o2) {
//            LocalDateTime t1 = o1.getBeginDateTime();
//            LocalDateTime t2 = o2.getEndDateTime();
//            return t1.compareTo(t2);
//        }
//    }

    /**
     * Adds a Job to the collection.
     *
     * Pre: Collection must contain less than max jobs and the Job name must
     * be unique from other Jobs.
     * @param key the Job's ID
     * @param value the Job that will be added
     */
    @Override
    public Job put(JobID key, Job value) {
        if (canAddJob() && !this.containsKey(key))
            super.put(key, value);
        return value;
    }

    /**
     * Adds a Job to the collection.
     *
     * Pre: Collection must contain less than max jobs and the Job name must
     * be unique from other Jobs.
     * @param newJob the Job that will be added
     */

    /**
     * Specifies if the collection is not at full capacity.
     *
     * @return true if collection is not at capacity, false otherwise
     */
    public boolean canAddJob() {
        return (size() < MAX_CAPACITY);
    }
}
