package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class JobCollection implements Serializable {

    private Map<JobID, Job> jobMap;

    private static final int MAX_CAPACITY = 20;

    public JobCollection() {
        jobMap = new HashMap<>();
    }

    public final void addJob(Job job) {
        if (isAtMaxCapacity() && !jobMap.containsKey(job.getID()))
            jobMap.put(job.getID(), job);
    }

    public final Job getJob(JobID jobID) {
        return jobMap.get(jobID);
    }

    public static int getMaxCapacity() {
        return MAX_CAPACITY;
    }

    public List<Job> getAlphabetizedList() {
        return null;
    }

    public List<Job> getChronologicalList() {

        return null;
    }

    public boolean isAtMaxCapacity() {
        return (jobMap.size() < MAX_CAPACITY);
    }

    public static class AlphabeticalComparator implements  Comparator<Job> {
        @Override
        public int compare(Job o1, Job o2) {
            String s1 = o1.getName();
            String s2 = o2.getName();
            return s1.compareTo(s2);
        }
    }


    public static class ChoronologicalComparator implements Comparator<Job> {
        @Override
        public int compare(Job o1, Job o2) {
            LocalDateTime t1 = o1.getBeginDateTime();
            LocalDateTime t2 = o2.getEndDateTime();
            return t1.compareTo(t2);
        }
    }
}
