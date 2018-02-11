package view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import model.Job;
import model.JobCollection;
import model.Park;
import model.ParkCollection;
import model.User;
import model.UserCollection;
import model.UserID;
import model.UserRole;

public class UrbanParksSystemUserInterface {
	
	private Scanner console = new Scanner(System.in);
	
	private User currentUser = null;

	private JobCollection jobs;

    private UserCollection users;

    private ParkCollection parks;
	
	private final String BREAK = "=============================URBAN PARKS" + 
		"=============================\n";

    //what about taking in Date fields that are not a String?
    private HashMap<String, Object> newJobInfoMap = 
    		new HashMap<String, Object>();

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

    /**
     * Starts the user interface with the welcome screen.
     */
	public void runInterface() {
		
		System.out.println("Welcome to Urban Parks.");
		System.out.println("\nPress Enter to proceed.");
		if (console.hasNextLine()) {
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
		System.out.println("Urban Parks - Sign In\n");
		System.out.print("Please enter your user ID: ");
		
		do {
			if (console.hasNextLine()) {
				UserID id = new UserID(console.nextLine());

				// TO DO - TRY FIND USER IN COLLECTION
				// TO DO - CATCH USER NOT FOUND EXCEPTION
				// 		   "That user name was not found. Please try again."
				
			}
		}while(currentUser == null);	
		
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
		
		if (console.hasNextLine()) {
			if (currentUser.getUserRole() == UserRole.PARK_MANAGER) {
				parkManagerMenu();
			} else if (currentUser.getUserRole() == UserRole.VOLUNTEER) {
				volunteerMenu();
			}
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
				currentUser.getID()); 
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
		
		System.out.println(BREAK);
		System.out.println("Jobs available for " + 
				currentUser.getID() + "to sign up for:"); 
		System.out.println();
		
		// TO DO - SHOW LIST OF JOBS
		// TO DO - REMOVE JOBS WITH DATE CONFLICTS (OR ERROR MESSAGE ON SIGN UP)
		// TO DO - PROVIDE OPTION TO GO BACK
		
		System.out.println();
		System.out.println("Please enter the number from a job you want " + 
		"to see more details for: ");
		
		do {
			if (console.hasNextInt()) { 
				int jobNumber = console.nextInt();
				
				// TO DO - GET JOB IDENTIFIER FROM LIST
				
				jobSelected = true;

			} else {
				System.out.println("You entered an invalid character. Please " + 
						"enter a number only.");
			}
		}while(jobSelected == false);
		displayJobInfo(/*JOB*/);
	}
	
	/**
	 * Displays the detailed job information for the job the user selected and 
	 * provides the options to go back or sign up as a volunteer.
	 * 
	 * @param job is the job the user wants to view.
	 */
	private void displayJobInfo(Job job) {
		DateTimeFormatter formatter = 
				DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formatDate;
		
		System.out.println(BREAK);
		System.out.println("User: " + currentUser.getID());
		System.out.println();
		System.out.println("JOB DETAILS:"); 
		System.out.println();
		System.out.println("Job Name:\t" + job.getName()); 		
		formatDate = job.getBeginDateTime().format(formatter);
		System.out.println("Start Date:\t" + formatDate); 
		formatDate = job.getEndDateTime().format(formatter);
		System.out.println("End Date:\t" + formatDate);
		System.out.println("Location:\t" + job.getPark().getName());
		
		System.out.println();
		System.out.println("Would you like to sign up for this job?");
		
		String option;
		do {
			System.out.println("Enter Y for yes or N for no: ");
			option = console.next();
			if (option.equalsIgnoreCase("y")) {
				
				// TO DO - ADD VOLUNTEER TO JOB/JOB TO VOLUNTEER
				
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
		System.out.println("Jobs " + currentUser.getID() + 
				" is signed up for: ");
		System.out.println();
		
		// TO DO - SHOW JOBS
		
		System.out.println("Press Enter to go back to Volunteer Menu.");
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
				currentUser.getID()); 
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
			} else {
				System.out.println("\nYou entered an incorrect value.");
			}
		}while(choice < 1 || choice > 2);
		
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
		Park park;
		LocalDateTime startDate, endDate;
		
		System.out.println(BREAK);
		System.out.println("Park Manager: " + currentUser.getID()); 
		System.out.println();
		System.out.println("PLEASE ENTER THE NEW JOB INFORMATION"); 
		System.out.println();
		
		System.out.print("Please enter a job title: "); 
		title = console.nextLine();
		System.out.println();
		
		startDate = getDate("start");
		endDate = getDate("finish");
		
		// TO DO - TEST FOR DATE CONFLICTS
		
		System.out.print("Please enter the Park name: "); 
		String parkName = console.nextLine();
		
		// TO DO - FIND PARK IN COLLECTION
		
		System.out.println();
		
		System.out.println("Please enter a description for the job: ");
		description = console.nextLine();
		System.out.println();
		
		displayJobInfo(title, startDate, endDate, park, description);
		System.out.println();
		System.out.println("Would you like to submit this job?");
		
		String option;
		do {
			System.out.println("Enter Y for yes or N for no: ");
			option = console.next();
			if (option.equalsIgnoreCase("y")) {
				
				// TO DO - ADD JOB TO COLLECTION
				
			} else if (option.equalsIgnoreCase("n")) {
				parkManagerMenu();
			} else {
				System.out.println("\nYou entered an invalid input.");
			}
		} while(!option.equalsIgnoreCase("y") || !option.equalsIgnoreCase("n"));
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
		DateTimeFormatter formatter = DateTimeFormatter
				.ofPattern("MM-dd-uuuu", Locale.US)
				.withResolverStyle(ResolverStyle.STRICT);
		
		do {
			System.out.print("Please enter a " + description + " date for " + 
					"the job (in the form MM-DD-YYYY): "); 
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
	
	private void displayJobInfo(String jobName, LocalDateTime start, 
			LocalDateTime finish, Park park, String description) {
		DateTimeFormatter formatter = 
				DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formatDate;
		
		System.out.println("JOB DETAILS:"); 
		System.out.println();
		System.out.println("Job Name:\t" + jobName); 		
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
		currentUser = null;
		signIn();
		
		// TO DO - RESET COLLECTIONS?
	}
}
