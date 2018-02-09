package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import model.Job;
import model.User;

public class UrbanParksSystemUserInterface {
	
	private BufferedReader input = 
			new BufferedReader(new InputStreamReader(System.in));
	
	private Scanner console = new Scanner(System.in);
	
	private User currentUser;
	
	private final String BREAK = "=============================URBAN PARKS" + 
		"=============================\n";

	public void runInterface() {
		
		System.out.println("Welcome to Urban Parks.");
		System.out.println("\nPress Enter to proceed.");
		if (console.hasNextLine()) {
			signIn();
		}
		
		console.close();
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
		System.out.println("Park Manager Menu for " + 
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
	
	private void parkManagerMenu() {
		// TO DO
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
		
		try {
			String option = input.readLine();
			if (option.equalsIgnoreCase("y")) {
				// TO DO - ADD JOB TO USER'S LIST/ADD USER TO JOB'S LIST
			} else if (option.equalsIgnoreCase("n")) {
				displayOpenJobs();
			} else {
				System.out.println("You entered an invalid input.");
				System.out.println(" Pleae Enter Y for yes or N for no: ");
				
				// TO DO - JUMP BACK TO GET INPUT (MAKE AN INPUT METHOD?)
			}
		} catch (IOException e) {
			System.out.println("Input error");
		}
	}
	
	private void displayYourJobs() {
		// TO DO
	}
	
	private void logout() {
		// TO DO
	}
}
