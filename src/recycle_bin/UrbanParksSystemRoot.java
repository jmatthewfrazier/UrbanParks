//package recycle_bin;
//
///**
// * Created by dave on 2/18/18.
// */
//
//import model.JobCollection;
//import model.ParkCollection;
//import model.User;
//import model.UserCollection;
//import view.UrbanParksGUI;
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * This class should serve as the root of then entire application.
// * can invoke the UP GUI class which serves as a frame
// * then pop in the appropriate panel
// *
// */
//public class UrbanParksSystemRoot {
//
//    private JobCollection jobs;
//
//    private UserCollection users;
//
//    private ParkCollection parks;
//
//    private UrbanParksGUI systemGUI;
//
//    private User currentUser;
//
//    public UrbanParksSystemRoot() {
//
//        jobs = new JobCollection();
//        users = new UserCollection();
//        parks = new ParkCollection();
//
//
//    }
//
//    private void loadCollectionsFromFile() {
//        //when the system is first firing up
//        FileInputStream fileIn;
//        File f = new File("./data.bin");
//
//        if (!f.isFile()) {
//            try {
//                f.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        try {
//            fileIn = new FileInputStream(f);
//            ObjectInputStream ois = new ObjectInputStream(fileIn);
//
//            List<Object> woi = (ArrayList<Object>) ois.readObject();
//
//            jobs = (JobCollection) woi.get(0);
//            users = (UserCollection) woi.get(1);
//            parks = (ParkCollection) woi.get(2);
//            fileIn.close();
//        } catch (FileNotFoundException e) {
//            System.out.println("File not found.");
//        } catch (IOException e) {
//            // e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            System.out.println(("File is invalid."));
//            e.printStackTrace();
//        }
//    }
//
//    public void runSystem() {
//        //populate all the collections
//        loadCollectionsFromFile();
//
//        //pass in all the populated collections to the GUI
////        systemGUI = new UrbanParksGUI(jobs, users, parks);
//
//        //system just began so start with the login panel
//        //don't think this class needs to be aware of who the user is
//        //this class just needs to populate things and get the gui started
////        systemGUI.displayLoginPanel("Welcome to Urban Parks");
//        }
//
//
//    //end UbranParksSystemRootClass
//}
