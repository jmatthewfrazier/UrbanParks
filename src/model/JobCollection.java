package model;

import java.util.HashMap;

public class JobCollection extends HashMap<String, Job> {

    private static int MAX_CAPACITY = 20;

    public static int getMaxCapacity() {
        return MAX_CAPACITY;
    }

    /**
     * Adds a Job to the collection.
     *
     * Pre: Collection must contain less than max jobs and the Job name must
     * be unique from other Jobs.
     * @param key the Job's ID
     * @param value the Job that will be added
     */
    @Override
    public Job put(String key, Job value) {
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
