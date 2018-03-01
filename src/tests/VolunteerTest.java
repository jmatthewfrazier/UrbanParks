package tests;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import exceptions.LessThanMinDaysAwayException;
import exceptions.VolunteerDailyJobLimitException;
import model.Job;
import model.JobID;
import model.Park;
import model.UserID;
import model.Volunteer;

public class VolunteerTest {
	
	private Park parkEastSide;
	
	private Volunteer testVolunteer = new Volunteer("first", "last",
		new UserID("VolTest"));
	
	private LocalDateTime beginDateTime = LocalDateTime.now().plusDays(7);
	
	private LocalDateTime endDateTime = beginDateTime.plusDays(1);
	
	private Job testJobInVolList = new Job("Test", parkEastSide, new JobID(1), 
		beginDateTime, endDateTime, "test");
	
	@Before
	public void setup() throws VolunteerDailyJobLimitException, 
		LessThanMinDaysAwayException {

		testVolunteer.signUpForJob(testJobInVolList);
	}
	
	@Test(expected = LessThanMinDaysAwayException.class)
	public void signUpForJob_jobLessThanMinDaysInFuture_throwsException() 
		throws LessThanMinDaysAwayException, VolunteerDailyJobLimitException {
		
		LocalDateTime beginDateLessThanMinDaysAway = LocalDateTime.now()
			.plusDays(testVolunteer.getMinDaysAwaySignUp() - 1);
		Job jobLessThanMinDaysAway = new Job("Test1", parkEastSide, 
			new JobID(2), beginDateLessThanMinDaysAway, 
			beginDateLessThanMinDaysAway.plusDays(1), "test");

		testVolunteer.signUpForJob(jobLessThanMinDaysAway);
	}
	
	@Test(expected = VolunteerDailyJobLimitException.class)
	public void signUpForJob_jobBeginsOnJobDateInVolList_throwsException() 
		throws LessThanMinDaysAwayException, VolunteerDailyJobLimitException {
		
		LocalDateTime beginDateOnJobDateInVolList = beginDateTime;
		Job jobBeginsOnJobDateInVolList = new Job("Test2", parkEastSide, 
			new JobID(2), beginDateOnJobDateInVolList, 
			beginDateOnJobDateInVolList.plusDays(2), "test");
		
		testVolunteer.signUpForJob(jobBeginsOnJobDateInVolList);
	}
	
	@Test(expected = VolunteerDailyJobLimitException.class)
	public void signUpForJob_jobEndsOnJobDateInVolList_throwsException() 
		throws LessThanMinDaysAwayException, VolunteerDailyJobLimitException {
		
		LocalDateTime endDateOnJobDateInVolList = endDateTime;
		Job jobEndsOnJobDateInVolList = new Job("Test3", parkEastSide, 
			new JobID(2), endDateOnJobDateInVolList, 
			endDateOnJobDateInVolList.plusDays(1), "test");
		
		testVolunteer.signUpForJob(jobEndsOnJobDateInVolList);
	}
	
	@Test(expected = VolunteerDailyJobLimitException.class)
	public void signUpForJob_jobOverlapsJobDateInVolList_throwsException() 
		throws LessThanMinDaysAwayException, VolunteerDailyJobLimitException {
		
		Job jobOverlapsJobDateInVolList = new Job("Test4", parkEastSide, 
			new JobID(2), beginDateTime.minusDays(1), endDateTime.plusDays(1), 
			"test");
		
		testVolunteer.signUpForJob(jobOverlapsJobDateInVolList);
		
	}
	
	@Test
	public void removeJobFromMyRegisteredJobs_jobListIsEmpty_True() {
		testVolunteer.removeJobFromMyRegisteredJobs(testJobInVolList);
		assertTrue(testVolunteer.getJobList().isEmpty());
	}

}
