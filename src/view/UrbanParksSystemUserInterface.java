package view;

import model.JobMap;

import java.util.HashMap;

public class UrbanParksSystemUserInterface {


    //instance to for job storage while the user is interacting with the program
    public JobMap systemJobCollection;

    //what about taking in Date fields that are not a String?
    private HashMap<String, Object> newJobInfoMap = new HashMap<String, Object>();

    /**
     * @return the newJobInfoMap
     */
    public HashMap<String, Object> getNewJobInfoMap() {
        return newJobInfoMap;
    }

    //tighter coupling than is ideal but an easy way to get going on the project.
    public UrbanParksSystemUserInterface(JobMap mainSystemJobCollection) {
        systemJobCollection = mainSystemJobCollection;
        setupNewJobHashMap();
    }

    public void setupNewJobHashMap() {
        //new jobs have 5 params so far
        //first param: starting date
        newJobInfoMap.put("job begin date", "1/1/1"); //replace with actual date object
        newJobInfoMap.put("job end date", "2/2/2"); //replace with actual date object
        newJobInfoMap.put("job name", "job_name_here");
        newJobInfoMap.put("job park", "job_park_here");  //replace with actual park object
        //newJobInfoMap.put("system UI", "this"); //is this still needed?
        //now that it is setup, if a new job is submitted it can replace the
        //values of the corresponding keys

    }

    public void editNewJobHashMap(String keyStringValue, Object newValueObject) {
        newJobInfoMap.replace(keyStringValue, newValueObject);
    }

    /**
     * @return the newJobInfoMap
     */
    public void populateNewJobFromMap(HashMap<String, Object> populatedJobInfoMap) {
//        Date startDate = populatedJobInfoMap.get("job begin date");
    }


    /*
     * this is where the magic happens, probably a do/while loop
     * that will take menu input an dperform some action based on that input
     *
     */
    public void runInterface() {
        //while loop that takes input and executes actions based on user input
        //will have to look and check the user role, then display role's menu options
        //for submitting a new job = systemJobCollection.addJobToCollection(jobToAdd);
    }
}
