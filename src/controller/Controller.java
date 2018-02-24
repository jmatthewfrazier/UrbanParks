package controller;

import model.*;
import view.UrbanParksGUI;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The wiring hub of the system.  An instance of this class will
 * be passed into User class instances to give users access to needed
 * collections without violating the LOD.
 *
 * @Created by Chad on 2/24/18.
 */
public class Controller {

    private JobCollection jobs;

    private UserCollection users;

    private ParkCollection parks;

    //private UrbanParksGUI systemGUI;

    private User currentUser;

    public Controller() {
        jobs = new JobCollection();
        users = new UserCollection();
        parks = new ParkCollection();
        setupController();


    }

    private void setupController() {
        loadCollectionsFromFile();
    }

    public void unsubmitParkJob(final Job jobToRemove) {
        //jobs.removeJobFromCollection(jobToRemove);
    }

    private void loadCollectionsFromFile() {
        //when the system is frist firing up
        FileInputStream fileIn;
        File f = new File("./data.bin");

        if (!f.isFile()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            fileIn = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(fileIn);

            List<Object> woi = (ArrayList<Object>) ois.readObject();

            jobs = (JobCollection) woi.get(0);
            users = (UserCollection) woi.get(1);
            parks = (ParkCollection) woi.get(2);
            fileIn.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        } catch (IOException e) {
            // e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println(("File is invalid."));
            e.printStackTrace();
        }
    }

    /**
     * Writes all modified data to the stored collections.
     */
    private void exportCollections() {
        try {
            FileOutputStream out = new FileOutputStream("./data.bin");
            ObjectOutputStream oos = new ObjectOutputStream(out);

            List<Object> collections = new ArrayList<>(); // look at this later
            collections.add(jobs);
            collections.add(users);
            collections.add(parks);
            oos.writeObject(collections);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //end of Controller class
}
