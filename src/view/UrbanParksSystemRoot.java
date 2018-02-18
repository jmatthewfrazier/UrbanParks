package view;

/**
 * Created by dave on 2/18/18.
 */

import model.JobCollection;
import model.ParkCollection;
import model.User;
import model.UserCollection;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class should serve as the root of then entire application.
 * can invoke the UP GUI class which serves as a frame
 * then pop in the appropriate panel
 *
 */
public class UrbanParksSystemRoot {

    private Scanner console;

    private DateTimeFormatter formatter;

    private User currentUser;

    private JobCollection jobs;

    private UserCollection users;

    private ParkCollection parks;

    private UrbanParksGUI systemGUI;


    public UrbanParksSystemRoot() {
        console = new Scanner(System.in);
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        jobs = new JobCollection();
        users = new UserCollection();
        parks = new ParkCollection();


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


    private void storeCollectionsIntoFile() {
        //when the system is preparing to shutdown
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

    public void runSystem() {
        //populate all the collections
        loadCollectionsFromFile();

        //pass in all the populated collections to the GUI
        systemGUI = new UrbanParksGUI(jobs, users, parks);

        //system just began so start with the login panel
        //don't think this class needs to be aware of who the user is
        //this class just needs to populate things and get the gui started
        systemGUI.displayLoginPanel();
        }

    //end UbranParksSystemRootClass
}
