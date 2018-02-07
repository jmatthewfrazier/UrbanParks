package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class JobMap implements Serializable {


    public static int MAX_CAPACITY = 20;

    private Map<String, Job> jobMap;

    /**
     * constructor
     */
    public JobMap() {

        jobMap = new HashMap<>();
    }

    /**
     * Adds a Job to the collection.
     *
     * Pre: Collection must contain less than max jobs and the Job name must
     * be unique from other Jobs.
     * @param jobToAdd the Job that will be added
     */
    public void addJob(Job jobToAdd) {
        if (isNotAtCapacity() && !jobMap.containsKey(jobToAdd.jobName))
            jobMap.put(jobToAdd.jobName, jobToAdd);
        //TODO - we need to account for this failing?
          }

    /**
     * Specifies if the collection is not at full capacity.
     *
     * @return true if collection is not at capacity, false otherwise
     */
    public boolean isNotAtCapacity() {

        return (jobMap.size() < MAX_CAPACITY);
    }

    /**
     * @return the jobHashMap
     */
    public Map<String, Job> getJobHashMap() {
        return jobMap;
    }


    /** TODO - not sure we need this, we have a method to add things to it,
     * TODO we will probably have to write another method with some logic
     * TODO checks for deleting jobs too, so let's scrap this one?
     * @param jobHashMap the jobHashMap to set

    public void setJobHashMap(HashMap<String, Job> jobHashMap) {
    this.jobHashMap = jobHashMap;
    }
     */
}
