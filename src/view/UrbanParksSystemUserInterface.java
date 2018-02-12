package view;

import exceptions.*;
import model.*;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class UrbanParksSystemUserInterface {
	
	private Scanner console;
	
	private DateTimeFormatter formatter;
	
	private User currentUser;

	private JobCollection jobs;

    private UserCollection users;

    private ParkCollection parks;
	
	private static final String BREAK = "=============================URBAN " +
			"PARKS=============================\n";

    //what about taking in Date fields that are not a String?
    private HashMap<String, Object> newJobInfoMap = 
    		new HashMap<String, Object>();

    UrbanParksSystemUserInterface() {
	    console = new Scanner(System.in);
	    formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        jobs = new JobCollection();
        users = new UserCollection();
        parks = new ParkCollection();
    }

    /**
     * Retrieves the stored data and creates program accessible collections.
     */
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

    /**
     * Starts the user interface with the welcome screen.
     */
	public void runInterface() {
		importCollections();

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
						.plusDays(4), LocalDateTime.now().plusDays(6),
						"Help plant sunflowers."));
				jobs.addJob(new Job("Highway Clean Up", parks.getPark(new
						ParkID(1)), new JobID(5), LocalDateTime.now()
						.plusDays(30), LocalDateTime.now().plusDays(31),
						"Clean up the highway along the park."));
				jobs.addJob(new Job("Fix the Picnic Benches", parks.getPark(new
						ParkID(2)), new JobID(6), LocalDateTime.now()
						.plusDays(15), LocalDateTime.now().plusDays(17),
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
			} catch (MaxPendingJobsException e) {
				e.printStackTrace();
			} catch (InvalidJobLengthException e) {
				e.printStackTrace();
			} catch (InvalidJobEndDateException e) {
				e.printStackTrace();
			} catch (JobCollectionDuplicateKeyException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Welcome to Urban Parks.");
		System.out.println("\nPress Enter to proceed.");
		if (console.hasNextLine()) {
			console.nextLine();
			signIn();
		}
		
		console.close();
	}
	
	/**
	 * The sign in screen that receives a user ID and establishes the current
	 * user for this session.
	 */
	private void signIn() {
		System.out.println(BREAK);
		System.out.println("Urban Parks - Sign In");
		System.out.println();

		do {
			System.out.print("Please enter your user ID: ");
			if (console.hasNextLine()) {
				String s = console.nextLine();
				UserID id = new UserID(s);
				if (users.containsUserID(id)) {
					currentUser = users.getUser(id);
				} else {
					System.out.print("That user ID was not found. " +
							"Please try again.");
					console.nextLine();
				}
			}
		} while(currentUser == null);

		welcomeMessage();
	}
	
	/**
	 * A welcome message letting the user know they successfully signed in.
	 */
	private void welcomeMessage() {
		System.out.println(BREAK);
		System.out.println("Welcome " + currentUser.getFirstName() + " " + 
				currentUser.getLastName() + " (" + 
				currentUser.getID().getUserID() + ")!");
		System.out.println("You have been logged in as a " + 
				currentUser.getUserRole() +	".\n");
		
		if (currentUser.getUserRole() == UserRole.PARK_MANAGER) {
			parkManagerMenu();
		} else if (currentUser.getUserRole() == UserRole.VOLUNTEER) {
			volunteerMenu();
		}
	}
	
	/**
	 * Displays the program options for a Volunteer type user and gets their 
	 * option selection.
	 */
	private void volunteerMenu() {
		int choice = 0;
		
		System.out.println(BREAK);
		System.out.println("Volunteer Menu for " + 
				currentUser.getFullName());
		System.out.println();
		System.out.println("1. Sign up for a job");
		System.out.println("2. See jobs you are signed up for");
		System.out.println("3. Log out");
		
		do {
			System.out.println();
			System.out.println("Please enter a number from the menu: ");
			if (console.hasNextInt()) {
				choice = console.nextInt();
				if (choice < 1 || choice > 3) {
					System.out.println("\nYou entered an incorrect value.");
				}
			} else {
				System.out.println("\nYou entered an incorrect value.");
			}
		}while(choice < 1 || choice > 3);

		if (choice == 1) {
			displayOpenJobs();
		} else if (choice == 2) {
			displayYourJobs();
		} else if (choice == 3) {
			logout();
		}
	}
	
	/**
	 * Displays all the jobs that the user is allowed to sign up for. Doesn't 
	 * display the jobs that violate business rules. Provides options to go 
	 * back or view a specific job details.
	 */
	private void displayOpenJobs() {
		boolean jobSelected = false;
		ArrayList<Job> availableJobs = new ArrayList<Job>();
		int count = 1;
		
		System.out.println(BREAK);
		System.out.println("Jobs available for you to sign up for:");
		System.out.println();
		
		System.out.printf("|%-9s|%-50s|%-20s|%-10s|%-10s|\n",
				"OPTION", "JOB NAME", "PARK", "START DATE", "END DATE");
		for (int i = 1; i <= 105; i++) {
			System.out.print("-");
		}
		System.out.println();
		
		for (Job job : jobs.getList()) {
			if (!((Volunteer) currentUser).getJobList().contains(job)) {
				System.out.printf("|%-9s|", count);
				System.out.printf("%-50s|", job.getName());
				System.out.printf("%-20s|", job.getPark().getName());
				System.out.printf("%-10s|", 
						job.getBeginDateTime().format(formatter));
				System.out.printf("%-10s|\n", 
						job.getEndDateTime().format(formatter));
				availableJobs.add(job);
				count++;
			}
		}
		
		System.out.println("\n\n\n\n");
		System.out.println("Please enter the number from a job you want " + 
		"to see more details for or " + count + " to go back: ");
		
		do {
			if (console.hasNextInt()) { 
				int jobNumber = console.nextInt();
				if (jobNumber == count) {
					jobSelected = true;
					volunteerMenu();
				} else if (jobNumber >= 1 || jobNumber < count) {
					jobSelected = true;
					displayJobInfo(availableJobs.get(jobNumber - 1));
				} else {
					System.out.println("You entered an invalid number.");
				}
			} else {
				System.out.println("You entered an invalid character. Please" + 
						" enter a number only.");
			}
		}while(jobSelected == false);
	}
	
	/**
	 * Displays the detailed job information for the job the user selected and 
	 * provides the options to go back or sign up as a volunteer.
	 * 
	 * @param job is the job the user wants to view.
	 */
	private void displayJobInfo(Job job) {
		System.out.println(BREAK);
		System.out.println("User: " + currentUser.getFullName());
		System.out.println();
		
		displayJobInfo(job.getName(), job.getID(), job.getBeginDateTime(), 
				job.getEndDateTime(), job.getPark(), job.getDescription());
		
		System.out.println();
		System.out.println("Would you like to sign up for this job?");
		
		String option;
		do {
			System.out.println("Enter Y for yes or N for no: ");
			option = console.next();
			if (option.equalsIgnoreCase("y")) {
				try {
					((Volunteer) currentUser).signUpForJob(job);
				} catch (LessThanMinDaysAwayException e) {
					// TODO
					e.printStackTrace();
				} catch (VolunteerDailyJobLimitException e) {
					// TODO
					e.printStackTrace();
				} finally {
					System.out.printf("Congradulations, you have sign up for " + 
							"the job %s \n\n", job.getName());
					volunteerMenu();
					
				}
			} else if (option.equalsIgnoreCase("n")) {
				displayOpenJobs();
			} else {
				System.out.println("\nYou entered an invalid input.");
			}
		} while(!option.equalsIgnoreCase("y") || !option.equalsIgnoreCase("n"));
	}
	
	/**
	 * Displays the jobs the Volunteer is currently signed up for.
	 */
	private void displayYourJobs() {
		System.out.println(BREAK);
		System.out.println("Jobs you are signed up for: ");
		System.out.println();
		
		System.out.printf("|%-50s|%-20s|%-10s|%-10s|\n", "JOB NAME", "PARK", 
				"START DATE", "END DATE");
		for (int i = 1; i <= 95; i++) {
			System.out.print("-");
		}
		System.out.println();
		
		Volunteer temp = (Volunteer) currentUser;
		for (Job job : temp.getChronologicalJobList()) {
			System.out.printf("%-50s|", job.getName());
			System.out.printf("%-20s|", job.getPark().getName());
			System.out.printf("%-10s|", 
					job.getBeginDateTime().format(formatter));
			System.out.printf("%-10s|\n", 
					job.getEndDateTime().format(formatter));
		}
		
		System.out.println("Press Enter to go back to the Volunteer Menu.");
		System.out.println("\n\n\n\n");
		if (console.hasNextLine()) {
			volunteerMenu();
		}
	}
	
	/**
	 * Displays the program options for a Park Manager type user and gets their 
	 * option selection.
	 */
	private void parkManagerMenu() {
		int choice = 0;
		
		System.out.println(BREAK);
		System.out.println("Park Manager Menu for " + 
				currentUser.getFullName());
		System.out.println();
		System.out.println("1. Submit a new job");
		System.out.println("2. Log out");
		
		do {
			System.out.println();
			System.out.print("Please enter a number from the menu: ");
			if (console.hasNextInt()) {
				choice = console.nextInt();
				if (choice < 1 || choice > 2) {
					System.out.println("\nYou entered an incorrect value.");
				}
				if (choice == 1 && jobs.isAtMaxCapacity()) {
					System.out.println("There is already the max number of " +
							"jobs in the system. Please try again later.");
					choice = 0;
				}
			} else {
				System.out.println("\nYou entered an incorrect value.");
			}
		}while(choice < 1 || choice > 2);
		console.nextLine();
		
		if (choice == 1) {
			createJob();
		} else if (choice == 2) {
			logout();
		}
	}
	
	/**
	 * Sequentially asks a Park Manager for all the required fields needed to 
	 * create and add a new job.
	 */
	private void createJob() {
		String title, description;
		Park park = null;
		JobID id = null;
		LocalDateTime startDate, endDate;
		boolean flag = false;
		
		// TO DO - CREATE ID (USER OR AUTO-GENERATED?)
		
		System.out.println(BREAK);
		System.out.println("Park Manager: " + currentUser.getFullName());
		System.out.println();
		System.out.println("PLEASE ENTER THE NEW JOB INFORMATION"); 
		System.out.println();
		
		System.out.print("Please enter a job title: "); 
		title = console.nextLine();
		System.out.println();
		
		do {
			System.out.print("Please enter a job ID number (up to 5 digits): ");
			if (console.hasNextInt()) {
				int number = console.nextInt();
				if (String.valueOf(number).length() <= 5) {
					id = new JobID(number);
					if (jobs.containsJobID(id)) {
						System.out.println("That job already exists. Please " + 
								"try a different ID.");
					} else {
						flag = true;
					}
				}
			} else {
				System.out.println("Please enter a number up to 5 digits " + 
						"for the job ID.");
			}
		}while(!flag);
		console.nextLine();
		flag = false;
		System.out.println();
		
		do {
			startDate = getDate("start");
			endDate = getDate("finish");

//			Job newJob = new Job(title, park, id, startDate, endDate,
//					description);

			flag = true;
//			if (jobs.isNewJobLengthValid()) {
//				System.out.println("The job specified is too long. Please " +
//					"enter a smaller date range.");
//				flag = false;
//			}
//			if (jobs.isJobWithinValidDateRange()) {
//				System.out.println("The job end date is too far away " +
//					"from the current date. Please enter a closer date.");
//				flag = false;
//			}
		} while(!flag);
		flag = false;
		System.out.println();
		
		do {
			System.out.print("Please enter the Park ID: "); 
			int parkID = console.nextInt();
			if (parks.containsParkID(new ParkID(parkID))) {
				park = parks.getPark(new ParkID(parkID));
			} else {
				System.out.println("Sorry, that's not a valid Park ID. " + 
						"Please Try again.");
			}
		} while(!flag);
		flag = false;

		System.out.println();
		System.out.println("Please enter a description for the job: ");
		description = console.nextLine();
		System.out.println();
		
		displayJobInfo(title, id, startDate, endDate, park, description);
		System.out.println();
		System.out.println("Would you like to submit this job?");
		
		String option;
		do {
			System.out.println("Enter Y for yes or N for no: ");
			option = console.next();
			if (option.equalsIgnoreCase("y")) {
				Job newJob = new Job(title, park, id, startDate, endDate,
						description);
				try {
					jobs.addJob(newJob);
					flag = true;
				} catch(MaxPendingJobsException e) {
					System.out.println("There is already the max number of " +
							"jobs in the system. Please try again later.");
					flag = false;
				} catch(InvalidJobLengthException e) {
					System.out.println("The job specified is too long." + 
							"Please enter a smaller date range.");
					flag = false;
				} catch(InvalidJobEndDateException e) {
					System.out.println("The job end date is too far away " + 
							"from the current date. Please enter a closer " + 
							"date.");
					flag = false;
				} catch(JobCollectionDuplicateKeyException e) {
					System.out.println("That job already exists. Please " + 
							"try entering a different job.");
					flag = false;
				}		
			} else if (option.equalsIgnoreCase("n")) {
				flag = true;
				parkManagerMenu();
			} else {
				System.out.println("\nYou entered an invalid input.");
			}
		} while(!flag);
		
		System.out.println();
		System.out.println("You successfully added a new job!");
		System.out.println("Press Enter to proceed.");
		if (console.hasNextLine()) {
			parkManagerMenu();
		}
	}
	
	/**
	 * Receives the 
	 * 
	 * @param description is the date qualifier to add into the prompt string
	 * (typically: "start" or "finish").
	 * @return the date the user entered.
	 */
	private LocalDateTime getDate(String description) {
		boolean valid = false;
		LocalDateTime date = null;
		String in;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
				"yyyy-MM-dd HH:mm");
		
		do {
			System.out.print("Please enter a " + description + " date and " +
					"time for the job (in the form YYYY-MM-DD HH:MM): "); 
			in = console.nextLine();
			try {
				date = LocalDateTime.parse(in, formatter);
				valid = true;
			}catch (DateTimeParseException e) {
				System.out.println("\nYou entered an invalid date.");
			}
			System.out.println();
		}while (!valid);
		
		return date;
	}
	
	private void displayJobInfo(String jobName, JobID jobID, 
			LocalDateTime start, LocalDateTime finish, Park park, 
			String description) {
		String formatDate;
		
		System.out.println("JOB DETAILS:"); 
		System.out.println();
		System.out.println("Job Name:\t" + jobName); 	
		System.out.println("Job ID:\t" + jobID.getJobID());
		formatDate = start.format(formatter);
		System.out.println("Start Date:\t" + formatDate); 
		formatDate = finish.format(formatter);
		System.out.println("End Date:\t" + formatDate);
		System.out.println("Location:\t" + park.getName());
		System.out.println("Description:\t" + description);
	}
	
	/**
	 * Logs the current user out so a different user can log in.
	 */
	private void logout() {
		exportCollections();
		currentUser = null;
		signIn();
	}
}
