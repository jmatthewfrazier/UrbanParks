package model;

import exceptions.*;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//import view.UrbanParksGUI;

/**
 * The wiring hub of the system.  An instance of this class will
 * be passed into User class instances to give users access to needed
 * collections without violating the LOD.
 *
 * @Created by Chad on 2/24/18.
 */
public class Controller implements Serializable {

    private JobCollection jobs;

    private UserCollection users;

    private ParkCollection parks;

//    private UrbanParksGUI systemGUI;

	private User currentUser;

    public Controller() {
        jobs = new JobCollection();
        users = new UserCollection();
        parks = new ParkCollection();
        currentUser = User.getNullUser();

        setupController();
    }

    private void setupController() {
       //any thing else add here or just call in consctr
        loadCollectionsFromFile();
    }

    //////////////add and remove things ///////////////////////////////////////

    public void unsubmitParkJob(final Job jobToRemove)
            throws UrbanParksSystemOperationException{
//        ArrayList<UserID> volunteerListOfRemovedJob =
//                jobToRemove.getVolunteerUserIDList();
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
//        removeVolunteersFromUnsubmittedJob(volunteerListOfRemovedJob,
//                                            jobToRemove);

    }

//    public void removeVolunteersFromUnsubmittedJob(final List<UserID> userIDList,
//                                                   final Job unsubmittedJob ) {
//        for (UserID uid : userIDList) {
//            //TODO - casting here is sketchy, what if user id isn't a volunteer?
//            Volunteer unsubmittedJobVolunteer = (Volunteer) users.getUser(uid);
//            unsubmittedJobVolunteer.removeJobFromMyRegisteredJobs(unsubmittedJob);
//
//        }
//    }

    /**
     * Updates Volunteer's job list so that if a job was removed from the
     * master list, it will be removed from the Volunteer's job list.
     */
    public void updateVolunteerList() {
    	if (getCurrentUser().getUserRole() == UserRole.VOLUNTEER) {
    		Volunteer user = (Volunteer) currentUser;
    		user.updateJobList(getJobs());
    	}
    }

    /////////////////getters and setters //////////////////////////////////////

//    public ArrayList<Job> getFutureJobsSubmittedByParkManager
//            (final UserID paramUserID) throws InvalidUserException,
//            UserNotFoundException {
//        ArrayList<Job> jobList;
//        if (currentUser.getUserRole() != UserRole.PARK_MANAGER) {
//            throw new InvalidUserException("User is not a Park Manager");
//        } else if (!users.containsUserID(paramUserID)) {
//            throw new UserNotFoundException("User ID was not found in the system");
//        } else {
//            jobList = jobs.getJobsForUser(paramUserID);
//        }
//        return jobList;
//    }

    public ArrayList<Job> getAllFutureJobs() {
        //TODO - do we implement some business rule check here also?
        return jobs.getAllFutureJobsFromToday();
    }

    public ArrayList<Job> getJobsInDateRange(final LocalDateTime start,
    	final LocalDateTime end) {
    	return jobs.getJobsInDateRange(start, end);
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

    public void setJobCollectionCapacity(int capacity) throws
    		UrbanParksSystemOperationException {
        try {
            jobs.setMaxCapacity(currentUser, capacity);
        } catch (InvalidJobCollectionCapacityException e) {
            e.printStackTrace();
        } catch (InvalidUserException e) {
        	throw new UrbanParksSystemOperationException("You must be a "
        			+ "Staff Member to do that.");
        }
    }

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
    public void storeCollectionsIntoFile() {
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
    
    /////////////////////////load test cases///////////////////////////////////
    
    /**
     * Loads test users, parks, and jobs into the system for testing.
     */
	public void runInterface() {

		if (users.isEmpty()) {
			users.addUser(new Volunteer("Robert", "Smith",
					new UserID("robertsmith")));
			users.addUser(new ParkManager("Steve", "Lafore", new
					UserID("stevelafore")));
		}
		if (parks.isEmpty()) {
			parks.addPark(new Park());
			parks.addPark(new Park("Greenwich Lake Park", 1238, "Fernwood " +
					"Pacific Drive", "Federal Way", "WA", 98001, new
					ParkID(2)));
		}

		if (jobs.isEmpty()) {
			try {
				jobs.addJob(new Job("Clean Up", parks.getPark(new ParkID(1)),
						new JobID(1), LocalDateTime.now().plusDays(5),
						LocalDateTime.now().plusDays(6), "Clean up the park" +
						"."));
				jobs.addJob(new Job("Petting Zoo Clean Up", parks.getPark
						(new ParkID(1)), new JobID(2), LocalDateTime.now()
						.plusDays(7), LocalDateTime.now().plusDays(8),
						"Clean up the petting zoo."));
				jobs.addJob(new Job("Highway Clean Up", parks.getPark(new
						ParkID(1)), new JobID(3), LocalDateTime.now()
						.plusDays(10), LocalDateTime.now().plusDays(11),
						"Clean up the highway along the park."));
				jobs.addJob(new Job("Gardening", parks.getPark(new
						ParkID(1)), new JobID(4), LocalDateTime.now()
						.plusDays(1), LocalDateTime.now().plusDays(2),
						"Help plant sunflowers."));
				jobs.addJob(new Job("Highway Clean Up", parks.getPark(new
						ParkID(1)), new JobID(5), LocalDateTime.now()
						.plusDays(30), LocalDateTime.now().plusDays(31),
						"Clean up the highway along the park."));
				jobs.addJob(new Job("Fix the Picnic Benches", parks.getPark(new
						ParkID(2)), new JobID(6), LocalDateTime.now()
						.plusDays(31), LocalDateTime.now().plusDays(32),
						"Fix the picnic benches."));
				jobs.addJob(new Job("Build a Bridge", parks.getPark(new
						ParkID(2)), new JobID(7), LocalDateTime.now()
						.plusDays(30), LocalDateTime.now().plusDays(32),
						"Build a small bridge the crosses the pond."));
				jobs.addJob(new Job("Weeding", parks.getPark(new
						ParkID(1)), new JobID(8), LocalDateTime.now()
						.plusDays(25), LocalDateTime.now().plusDays(26),
						"Weed the lawn to get rid of thistle."));
				jobs.addJob(new Job("Rake the Leaves", parks.getPark(new
						ParkID(1)), new JobID(9), LocalDateTime.now()
						.plusDays(40), LocalDateTime.now().plusDays(41),
						"Rake the leaves by the trees."));
				jobs.addJob(new Job("Tree Planting", parks.getPark(new
						ParkID(1)), new JobID(10), LocalDateTime.now()
						.plusDays(29), LocalDateTime.now().plusDays(30),
						"We'll be planting trees."));
				jobs.addJob(new Job("Bird House Making", parks.getPark(new
						ParkID(1)), new JobID(11), LocalDateTime.now()
						.plusDays(23), LocalDateTime.now().plusDays(24),
						"Make some bird houses for the swallows."));
				jobs.addJob(new Job("Picnic Bench Cleaning", parks.getPark(new
						ParkID(1)), new JobID(12), LocalDateTime.now()
						.plusDays(13), LocalDateTime.now().plusDays(13),
						"Cleaning picnic benches for the spring."));
				jobs.addJob(new Job("Nature Trail Maintenance", parks.getPark
						(new ParkID(2)), new JobID(13), LocalDateTime.now()
						.plusDays(31), LocalDateTime.now().plusDays(32),
						"Maintenance on the nature trail."));
				jobs.addJob(new Job("Dock Maintenance", parks.getPark(new
						ParkID(2)), new JobID(14), LocalDateTime.now()
						.plusDays(20), LocalDateTime.now().plusDays(21),
						"Replacing boards on the dock at the lake."));
				jobs.addJob(new Job("Bridge Construction", parks.getPark(new
						ParkID(2)), new JobID(15), LocalDateTime.now()
						.plusDays(47), LocalDateTime.now().plusDays(48),
						"Build a bridge for the washed out trail."));
				jobs.addJob(new Job("Remove Weeds", parks.getPark(new
						ParkID(2)), new JobID(16), LocalDateTime.now()
						.plusDays(50), LocalDateTime.now().plusDays(51),
						"Removing hazardous plants."));
				jobs.addJob(new Job("Squirrel Habitat", parks.getPark(new
						ParkID(1)), new JobID(17), LocalDateTime.now()
						.plusDays(60), LocalDateTime.now().plusDays(61),
						"Construct a squirrel habitat for the petting zoo."));
				jobs.addJob(new Job("Ranger Station Landscaping", parks.getPark
						(new ParkID(1)), new JobID(18), LocalDateTime.now()
						.plusDays(38), LocalDateTime.now().plusDays(39),
						"Landscaping around the ranger station."));
				jobs.addJob(new Job("Trail Sign Construction", parks.getPark
						(new ParkID(2)), new JobID(19), LocalDateTime.now()
						.plusDays(42), LocalDateTime.now().plusDays(43),
						"Build new trail markers."));
			} catch (MaxPendingJobsException | JobCollectionDuplicateKeyException |
					InvalidJobEndDateException | InvalidJobLengthException e) {
				e.printStackTrace();
			}
		}
	}
    
    /////////////////////////recycling ////////////////////////////////////////


    //end of Controller class
}
