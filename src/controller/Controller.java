package controller;

import exceptions.*;
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
<<<<<<< HEAD
public class Controller {
=======
public class Controller implements Serializable {
>>>>>>> e2691b3aecaec15d895a32d31b94d95962fd244a

    private JobCollection jobs;

    private UserCollection users;

    private ParkCollection parks;

    private UrbanParksGUI systemGUI;

	private User currentUser;

    public Controller() {
        jobs = new JobCollection();
        users = new UserCollection();
        parks = new ParkCollection();
        currentUser = User.getNullUser();

<<<<<<< HEAD
=======
        setupController();
>>>>>>> e2691b3aecaec15d895a32d31b94d95962fd244a
    }



    private void setupController() {
       loadCollectionsFromFile();
    }

<<<<<<< HEAD
=======
    //////////////add and remove things ///////////////////////////////////////

>>>>>>> e2691b3aecaec15d895a32d31b94d95962fd244a
    public void unsubmitParkJob(final Job jobToRemove)
            throws UrbanParksSystemOperationException{
        ArrayList<UserID> volunteerListOfRemovedJob =
                jobToRemove.getVolunteerUserIDList();
        try {
            jobs.removeJobFromCollection(jobToRemove,
                    this.getCurrentUser().getID());
        } catch (JobIDNotFoundInCollectionException e) {
            throw new UrbanParksSystemOperationException(e.getMsgString());
        } catch (UserNotFoundException e) {
            throw new UrbanParksSystemOperationException(e.getMsgString());
        } catch (LessThanMinDaysAwayException e) {
            throw new UrbanParksSystemOperationException(e.getMsgString());
        }
        //made it to here, no exceptions thrown, notify
<<<<<<< HEAD
        removeVolunteersFromAbandonedJob(volunteerListOfRemovedJob,
                                            jobToRemove);

    }
    
    public void unvolunteerParkJob(final Job jobToRemove)
    		throws UrbanParksSystemOperationException{   	
    	ArrayList<UserID> volunteerListOfRemovedJob =
                jobToRemove.getVolunteerUserIDList();
    	try {
    		jobs.removeSignupJob(jobToRemove);
    	} catch (JobIDNotFoundInCollectionException e) {
            throw new UrbanParksSystemOperationException(e.getMsgString());
        } catch (UnvolunteerPriorTimeException e) {
            throw new UrbanParksSystemOperationException(e.getMsgString());
        }
    	removeVolunteersFromAbandonedJob(volunteerListOfRemovedJob,
                							jobToRemove);
    }

    public void removeVolunteersFromAbandonedJob(final List<UserID> userIDList,
                                                   final Job unsubmittedJob ) {
        for (UserID uid : userIDList) {
            //TODO - casting here is sketchy, what if user id isn't a volunteer?
            Volunteer abandonedJobVolunteer = (Volunteer) users.getUser(uid);
            abandonedJobVolunteer.removeJobFromMyRegisteredJobs(unsubmittedJob);
=======
        removeVolunteersFromUnsubmittedJob(volunteerListOfRemovedJob,
                                            jobToRemove);

    }

    public void removeVolunteersFromUnsubmittedJob(final List<UserID> userIDList,
                                                   final Job unsubmittedJob ) {
        for (UserID uid : userIDList) {
            //TODO - casting here is sketchy, what if user id isn't a volunteer?
            Volunteer unsubmittedJobVolunteer = (Volunteer) users.getUser(uid);
            unsubmittedJobVolunteer.removeJobFromMyRegisteredJobs(unsubmittedJob);
>>>>>>> e2691b3aecaec15d895a32d31b94d95962fd244a

        }
    }

<<<<<<< HEAD
=======
    /////////////////getters and setters //////////////////////////////////////

>>>>>>> e2691b3aecaec15d895a32d31b94d95962fd244a
    public ArrayList<Job> getFutureJobsSubmittedByParkManager
            (final UserID paramUserID) throws UserRoleCategoryException,
            UserNotFoundException {
        ArrayList<Job> jobList;
        if (currentUser.getUserRole() != UserRole.PARK_MANAGER) {
            throw new UserRoleCategoryException("User is not a Park Manager");
        } else if (!users.containsUserID(paramUserID)) {
            throw new UserNotFoundException("User ID was not found in the system");
        } else {
            jobList = jobs.getJobArrayListFilterByUserID(paramUserID);
        }
        return jobList;
    }
<<<<<<< HEAD
    
    public ArrayList<Job> getFutureJobsSignupByVolunteer
    		(final UserID paramUserID) throws UserRoleCategoryException,
    		UserNotFoundException {
    	ArrayList<Job> jobList;
    	if (currentUser.getUserRole() != UserRole.VOLUNTEER) {
    		throw new UserRoleCategoryException("User is not a Volunteer");    		
    	} else if (!users.containsUserID(paramUserID)) {
    		throw new UserNotFoundException("User ID was not found in the system");
    	} else {
    		jobList = jobs.getJobArrayListSignupByVolunteer(paramUserID);
    	}
    	return jobList;
    	
=======

    public ArrayList<Job> getAllFutureJobs() {
        //TODO - do we implement some business rule check here also?
        return jobs.getAllFutureJobsFromToday();
>>>>>>> e2691b3aecaec15d895a32d31b94d95962fd244a
    }

    public void addNewJobByParkManager(final Job jobToAdd)
            throws UrbanParksSystemOperationException {
        try {
            jobs.addJob(jobToAdd);
        } catch (MaxPendingJobsException e) {
            throw new UrbanParksSystemOperationException(e.getMsgString());
        } catch (InvalidJobLengthException e) {
            throw new UrbanParksSystemOperationException(e.getMsgString());
        } catch (InvalidJobEndDateException e) {
            throw new UrbanParksSystemOperationException(e.getMsgString());
        } catch (JobCollectionDuplicateKeyException e) {
            throw new UrbanParksSystemOperationException(e.getMsgString());
        }
    }
<<<<<<< HEAD
    
    public void signupNewJobByVolunteer(final Job jobToAdd)
    		throws UrbanParksSystemOperationException {
    	try {
    		jobs.signupForNewJobVolunteer(jobToAdd);
    	} catch (JobOverlappingException e) {
    		throw new UrbanParksSystemOperationException(e.getMsgString());
    	} catch (LessThanMinDaysAwayException e) {
    		throw new UrbanParksSystemOperationException(e.getMsgString());
    	}
    	// Here should have implementation on how the job class would 
    	// store the Volunteer in the list. Which I have no idea how to do it 
    	// without breaking the law of Demeter
    }
=======
>>>>>>> e2691b3aecaec15d895a32d31b94d95962fd244a

    public void setJobCollectionCapacity(int capacity) {
        try {
            jobs.setMaxCapacity(currentUser, capacity);
        } catch (InvalidJobCollectionCapacityException e) {
            e.printStackTrace();
        }
    }

<<<<<<< HEAD
=======
    public int getJobCollectionCapacity() {
        return jobs.getMaxCapacity();
    }

    public User getUserByUserID (final UserID userIDToSearchFor)
            throws UserNotFoundException {
        if (users.containsUserID(userIDToSearchFor)) {
            return users.getUser(userIDToSearchFor);
        } else {
            throw new UserNotFoundException("That User ID was not found.");
        }
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public JobCollection getJobs() {
        return jobs;
    }

    public ParkCollection getParks() {
        return parks;
    }

    public User getCurrentUser() {
        return currentUser;
    }





    //////////////import and export to the collections/////////////////////////

>>>>>>> e2691b3aecaec15d895a32d31b94d95962fd244a
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
<<<<<<< HEAD
    private void exportCollections() {
=======
    public void storeCollectionsIntoFile() {
>>>>>>> e2691b3aecaec15d895a32d31b94d95962fd244a
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

<<<<<<< HEAD
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public JobCollection getJobs() {
        return jobs;
    }

    public ParkCollection getParks() {
        return parks;
    }

    public User getCurrentUser() {
        return currentUser;
    }
=======
    /////////////////////////recycling ////////////////////////////////////////

>>>>>>> e2691b3aecaec15d895a32d31b94d95962fd244a

    //end of Controller class
}
