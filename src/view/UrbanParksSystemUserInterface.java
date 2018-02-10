package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Locale;
import java.util.Scanner;

import model.Job;
import model.Park;
import model.User;

public class UrbanParksSystemUserInterface {
	
	private BufferedReader input = 
			new BufferedReader(new InputStreamReader(System.in));
	
	private Scanner console = new Scanner(System.in);
	
	private User currentUser;
	
	private final String BREAK = "=============================URBAN PARKS" + 
		"=============================\n";

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


    public UrbanParksSystemUserInterface () {
        JobMap systemJobCollection = new JobMap();
        UserCollection systemUserCollection = new UserCollection();
        ParkSet systemParkSet = new ParkSet();
    }
    //tighter coupling than is ideal but an easy way to get going on the project.
//    public UrbanParksSystemUserInterface(JobMap mainSystemJobCollection) {
//        systemJobCollection = mainSystemJobCollection;
//        setupNewJobHashMap();
//    }

    private void importCollections() {

    }

    private void exportCollections() {

    }

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
     * @return the newJobInfoMap
     */
    public void populateNewJobFromMap(HashMap<String, Object> populatedJobInfoMap) {
//        Date startDate = populatedJobInfoMap.get("job begin date");
    }


	public void runInterface() throws IOException {
		
		System.out.println("Welcome to Urban Parks.");
		System.out.println("\nPress Enter to proceed.");
		if (console.hasNextLine()) {
			signIn();
		}
		
		console.close();
		input.close();
	}
	
	private void signIn() {
		System.out.println(BREAK);
		System.out.println("Urban Parks - Sign In\n");
		System.out.print("Please enter your user ID: ");
		try {
			String userID = input.readLine();
		} catch (IOException e) {
			System.out.println("Input error");
		}		
		
		// TO DO - IDENTIFY USER (SET TO currentUser)
		// TO DO - EXCEPTION MESSAGE
		
		welcomeMessage();
	}
	
	private void welcomeMessage() {
		System.out.println(BREAK);
		System.out.println("Welcome " + currentUser.getFirstName() + " " + 
				currentUser.getLastName() + " (" + 
				currentUser.getUserSystemName() + ")!");
		System.out.println("You have been logged in as a " + 
				currentUser.getUserRole() +	".\n");
		
		if (console.hasNextLine()) {
			if (currentUser.getUserRole() == PARK_MANAGER) {
				parkManagerMenu();
			} else if (currentUser.getUserRole() == VOLUNTEER) {
				volunteerMenu();
			}
		}
	}
	
	private void volunteerMenu() {
		System.out.println(BREAK);
		System.out.println("Volunteer Menu for " + 
				currentUser.getUserSystemName()); 
		System.out.println();
		System.out.println("1. Sign up for a job");
		System.out.println("2. See jobs you are signed up for");
		System.out.println("3. Log out");
		System.out.println();
		System.out.println("Please enter a number from the menu: ");
		
		int choice = console.nextInt();
		if (choice == 1) {
			displayOpenJobs();
		} else if (choice == 2) {
			displayYourJobs();
		} else if (choice == 3) {
			logout();
		}
	}
	
	private void displayOpenJobs() {
		System.out.println(BREAK);
		System.out.println("Jobs available for " + 
				currentUser.getUserSystemName() + "to sign up for:"); 
		System.out.println();
		
		// TO DO - SHOW LIST OF JOBS
		// TO DO - REMOVE JOBS WITH DATE CONFLICTS (OR ERROR MESSAGE ON SIGN UP)
		// TO DO - PROVIDE OPTION TO GO BACK
		
		System.out.println();
		System.out.println("Please enter the number from a job you want " + 
		"to see more details for: ");
		
		if (console.hasNextInt()) { 
			int jobNumber = console.nextInt();
			
			// TO DO - GET JOB IDENTIFIER FROM LIST
			
			displayJobInfo(/*JOB*/);
		} else {
			System.out.println("You entered an invalid character. Please " + 
					"enter a number only.");
		}
	}
	
	private void displayJobInfo(Job job) {
		System.out.println(BREAK);
		System.out.println("User: " + currentUser.getUserSystemName());
		System.out.println();
		System.out.println("JOB DETAILS:"); 
		System.out.println();
		System.out.println("Job Name:\t" + job.jobName); 
		
		// TO DO - NEED TO CONVERT TO STRING START DATE, END DATE, AND LOCATION 
		// TO DO - NEED GETTERS/SETTERS FOR VOLUNTEERS AND DESCRIPTION
		
		System.out.println("Start Date:\t" + job.beginDateTime); 
		System.out.println("End Date:\t" + job.endDateTime);
		System.out.println("Location:\t" + job.jobLocation);
		System.out.println();
		System.out.println("Would you like to sign up for this job?");
		System.out.println("Enter Y for yes or N for no: ");
		
		String option = "";
		do {
			try {
				option = input.readLine();
				if (option.equalsIgnoreCase("y")) {
					
					// TO DO - ADD JOB TO USER'S LIST/ADD USER TO JOB'S LIST
					
					displayOpenJobs();
				} else if (option.equalsIgnoreCase("n")) {
					displayOpenJobs();
				} else {
					System.out.println("You entered an invalid input.");
					System.out.println(" Pleae Enter Y for yes or N for no: ");
				}
			} catch (IOException e) {
				System.out.println("Input error");
			}
		} while(!option.equalsIgnoreCase("y") || !option.equalsIgnoreCase("n"));
	}
	
	private void displayYourJobs() {
		System.out.println(BREAK);
		System.out.println("Jobs " + currentUser.getUserSystemName() + 
				"is signed up for: ");
		System.out.println();
		
		// TO DO - SHOW JOBS
		
		System.out.println("Press Enter to go back to Volunteer Menu.");
		if (console.hasNextLine()) {
			volunteerMenu();
		}
	}
	
	private void parkManagerMenu() {
		int choice = -1;
		
		System.out.println(BREAK);
		System.out.println("Park Manager Menu for " + 
				currentUser.getUserSystemName()); 
		System.out.println();
		System.out.println("1. Submit a new job");
		System.out.println("2. Log out");
		
		do {
			System.out.println();
			System.out.print("Please enter a number from the menu: ");
			if (console.hasNextInt()) {
				choice = console.nextInt();
				if (choice == 1) {
					createJob();
				} else if (choice == 2) {
					logout();
				}
			} else {
				System.out.println();
				System.out.println("You entered an invalid choice.");
				System.out.print("Please enter a number from the menu: ");
			}
		}while(choice < 0);
	}
	
	private void createJob() {
		String title, description, temp;
		Park park;
		LocalDateTime startDate, endDate;
		
		System.out.println(BREAK);
		System.out.println("Park Manager: " + currentUser.getUserSystemName()); 
		System.out.println();
		System.out.println("PLEASE ENTER THE NEW JOB INFORMATION"); 
		System.out.println();
		
		System.out.print("Please enter a job title: "); 
		try {
			title = input.readLine();
		} catch (IOException e) {
			System.out.println("Input error");
		}	
		System.out.println();
		
		startDate = getDate("start");
		endDate = getDate("finish");
		
		
		
		
		
	}
	
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
			try {
				in = input.readLine();
				try {
					date = LocalDateTime.parse(in, formatter);
					if () {
						valid = true;
					}
				}catch (DateTimeParseException e) {
					System.out.println("\nYou entered an invalid date.");
				}
			} catch (IOException e) {
				System.out.println("\nInput error.");
			}	
			System.out.println();
		}while (!valid);
		
		return date;
	}
	
	private void logout() {
		currentUser = null;
		signIn();
	}
}
