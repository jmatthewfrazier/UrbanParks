package view;

import model.JobCollection;
import model.ParkCollection;
import model.UserCollection;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UrbanParksSystemUserInterface {

    private JobCollection jobs;
    private UserCollection users;
    private ParkCollection parks;

    //instance to for job storage while the user is interacting with the program
//    public JobMap systemJobCollection;

    //what about taking in Date fields that are not a String?
    private HashMap<String, Object> newJobInfoMap = new HashMap<>();

    /**
     * @return the newJobInfoMap
     */
    public HashMap<String, Object> getNewJobInfoMap() {
        return newJobInfoMap;
    }


    UrbanParksSystemUserInterface() {
        jobs = new JobCollection();
        users = new UserCollection();
        parks = new ParkCollection();

//        JobMap systemJobCollection = new JobMap();
        UserCollection systemUserCollection = new UserCollection();
        ParkCollection systemParkSet = new ParkCollection();
    }
    //tighter coupling than is ideal but an easy way to get going on the project.
//    public UrbanParksSystemUserInterface(JobMap mainSystemJobCollection) {
//        systemJobCollection = mainSystemJobCollection;
//        setupNewJobHashMap();
//    }

    // TODO
    private void importCollections() {
        try {
            FileInputStream fileIn = new FileInputStream("data.bin");
            ObjectInputStream ois = new ObjectInputStream(fileIn);

            List<Object> woi = (ArrayList<Object>) ois.readObject();

            jobs = (JobCollection) woi.get(0);
            users = (UserCollection) woi.get(1);
            parks = (ParkCollection) woi.get(2);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // TODO
    private void exportCollections() {
        try {
            FileOutputStream out = new FileOutputStream("data.bin");
            ObjectOutputStream oos = new ObjectOutputStream(out);

            List<Object> collections = new ArrayList<>(); // look at this later
            collections.add(jobs);
            collections.add(users);
            collections.add(parks);
            oos.writeObject(collections);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public UrbanParksSystemUserInterface(JobMap mainSystemJobCollection) {
//        systemJobCollection = mainSystemJobCollection;
//        setupNewJobHashMap();
//    }

    public void setupNewJobHashMap() {
        //new jobs have 5 params so far
        //first param: starting date

        //now that it is setup, if a new job is submitted it can replace the
        //values of the corresponding keys

    }

    public void editNewJobHashMap(String keyStringValue, Object newValueObject) {
        newJobInfoMap.replace(keyStringValue, newValueObject);
    }

    /**
     *  the newJobInfoMap
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
