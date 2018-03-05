package model;

import exceptions.*;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UrbanParksData implements Serializable {

    private JobCollection jobs;
    private UserCollection users;
    private ParkCollection parks;
	private User currentUser;

    public UrbanParksData() {
        jobs = new JobCollection();
        users = new UserCollection();
        parks = new ParkCollection();
        currentUser = User.getNullUser();

        importCollections();
        validate();
    }

    private void validate() {
	    Volunteer vol = new Volunteer("Robert", "Smith",
			    new UserID("robertsmith"));
	    ParkManager pm = new ParkManager("Steve", "Lafore",
			    new UserID("stevelafore"));

	    Park park = new Park("Hyde Park", new ParkID(1));
	    Job job = new Job("Clean Up", park, new JobID(1),
			    LocalDateTime.now().plusDays(5),
			    LocalDateTime.now().plusDays(6), "Clean up the park" +
			    ".", pm);

        if (users.isEmpty()) {
            users.addUser(vol);
            users.addUser(pm);
            users.addUser(new StaffMember("Frederico", "Montoya",
		            new UserID("fred")));

        }
	    if (parks.isEmpty()) {
		    parks.addPark(park);
		    parks.addPark(new Park("Greenwich Lake Park", new ParkID(2)));
	    }

	    if (jobs.isEmpty()) {
		    try {
			    this.addNewJobByParkManager(pm, job);
			    jobs.addJob(new Job("Petting Zoo Clean Up", park,
					    new JobID(2), LocalDateTime.now().plusDays(7),
					    LocalDateTime.now().plusDays(8),
					    "Clean up the petting zoo.", pm));
			    jobs.addJob(new Job("Highway Clean Up", parks.getPark(new
					    ParkID(1)), new JobID(3), LocalDateTime.now()
					    .plusDays(10), LocalDateTime.now().plusDays(11),
					    "Clean up the highway along the park.", pm));
			    jobs.addJob(new Job("Gardening", parks.getPark(new
					    ParkID(1)), new JobID(4), LocalDateTime.now()
					    .plusDays(1), LocalDateTime.now().plusDays(2),
					    "Help plant sunflowers.", pm));
			    jobs.addJob(new Job("Highway Clean Up", parks.getPark(new
					    ParkID(1)), new JobID(5), LocalDateTime.now()
					    .plusDays(30), LocalDateTime.now().plusDays(31),
					    "Clean up the highway along the park.", pm));
			    jobs.addJob(new Job("Fix the Picnic Benches", parks.getPark(new
					    ParkID(2)), new JobID(6), LocalDateTime.now()
					    .plusDays(31), LocalDateTime.now().plusDays(32),
					    "Fix the picnic benches.", pm));
			    jobs.addJob(new Job("Build a Bridge", parks.getPark(new
					    ParkID(2)), new JobID(7), LocalDateTime.now()
					    .plusDays(30), LocalDateTime.now().plusDays(32),
					    "Build a small bridge the crosses the pond.",
					    pm));
			    jobs.addJob(new Job("Weeding", parks.getPark(new
					    ParkID(1)), new JobID(8), LocalDateTime.now()
					    .plusDays(25), LocalDateTime.now().plusDays(26),
					    "Weed the lawn to get rid of thistle.", pm));
			    jobs.addJob(new Job("Rake the Leaves", parks.getPark(new
					    ParkID(1)), new JobID(9), LocalDateTime.now()
					    .plusDays(40), LocalDateTime.now().plusDays(41),
					    "Rake the leaves by the trees.", pm));
			    jobs.addJob(new Job("Tree Planting", parks.getPark(new
					    ParkID(1)), new JobID(10), LocalDateTime.now()
					    .plusDays(29), LocalDateTime.now().plusDays(30),
					    "We'll be planting trees.", pm));
		    } catch (MaxPendingJobsException | JobCollectionDuplicateKeyException
				    | InvalidJobEndDateException | InvalidJobLengthException
				    | UrbanParksSystemOperationException e) {
			    e.printStackTrace();
		    }


		    if (vol.getJobList().size() == 0) {
			    try {
				    assign(vol, job);
			    } catch (Exception e) {
				    e.printStackTrace();
			    }

		    }
	    }
    }

    public void assign(Volunteer volunteer, Job job) throws Exception {
    	job.addVolunteer(volunteer);
    	volunteer.signUpForJob(job);
    }

    public void cancelAssignment(Volunteer volunteer, Job job) {
    	volunteer.removeJobFromMyRegisteredJobs(job);
    	job.removeVolunteer(volunteer);
    }

    //////////////add and remove things ///////////////////////////////////////

    public void unsubmitParkJob(final Job jobToRemove)
		    throws LessThanMinDaysAwayException,
		    JobIDNotFoundInCollectionException, InvalidUserException {
        jobs.removeJobFromCollection(jobToRemove, this.getCurrentUser());
        ((ParkManager) this.getCurrentUser()).removeJob(jobToRemove);
    }

    public List<Job> getAllFutureJobs() {
        return jobs.getAllFutureJobsFromToday();
    }

    public List<Job> getJobsInDateRange(final LocalDateTime start,
    	final LocalDateTime end) {
    	return jobs.getJobsInDateRange(start, end);
    }

    public void addNewJobByParkManager(final ParkManager pm, final Job jobToAdd)
            throws UrbanParksSystemOperationException {
        try {
            jobs.addJob(jobToAdd);
            pm.addJob(jobToAdd);
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
		    InvalidJobCollectionCapacityException, InvalidUserException {
        try {
            jobs.setMaxCapacity(currentUser, capacity);
        } catch (InvalidJobCollectionCapacityException e) {
            throw new InvalidJobCollectionCapacityException();
        } catch (InvalidUserException e) {
        	throw new InvalidUserException("You must be a "
        			+ "Staff Member to do that.");
        }
    }

    public void loginUserID(final UserID userID) {
        if (users.containsUserID(userID)) {
            setCurrentUser(users.getUser(userID));
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

    public <U extends User> U getCurrentUser() {
	    return (U) currentUser;
    }
    
    public int getCurrentMaxJobs() {
    	return jobs.getMaxCapacity();
    }

    //////////////import and export to the collections/////////////////////////

    private void importCollections() {
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
}
