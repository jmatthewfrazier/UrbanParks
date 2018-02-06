package model;

import java.util.HashMap;

public class JobCollection extends HashMap<String, Job> {

    public static int MAX_CAPACITY = 20;

//    private Map<String, Job> jobMap;

//    public JobMap() {
//        jobMap = new HashMap<>();
//    }
//
//    public boolean addJobToCollection(Job jobToAdd) {
//        boolean retBool = false;
//        //get key String from jobToAdd
//        //put jobToAdd into hashmap
//        //return true if successful
//        //how to check success?
//        return retBool;
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
//    public void addJob(Job newJob) {
//        if (canAddJob() && !this.containsKey(newJob.jobName))
//            this.put(newJob.jobName, newJob);
//    }

    /**
     * Specifies if the collection is not at full capacity.
     *
     * @return true if collection is not at capacity, false otherwise
     */
    public boolean canAddJob() {
        return (size() < MAX_CAPACITY);
    }

    /**
     * @return the jobHashMap
     */
//    public Map<String, Job> getJobHashMap() {
//        return this;
//    }


    /**
     * @param jobHashMap the jobHashMap to set

    public void setJobHashMap(HashMap<String, Job> jobHashMap) {
    this.jobHashMap = jobHashMap;
    }
     */
}
