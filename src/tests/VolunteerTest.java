package tests;

import exceptions.LessThanMinDaysAwayException;
import exceptions.VolunteerDailyJobLimitException;
import model.*;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertTrue;

public class VolunteerTest {
	
	private Park parkEastSide;
	private ParkManager pm;
	private Volunteer testVolunteer = new Volunteer("first", "last",
		new UserID("VolTest"));
	
	private LocalDateTime beginDateTime = LocalDateTime.now().plusDays(7);
	
	private LocalDateTime endDateTime = beginDateTime.plusDays(1);
	
	private Job testJobInVolList;
	
	@Before
	public void setup() throws Exception {
		pm = new ParkManager("Test", "PM", new UserID("testpm"));
		testVolunteer.signUpForJob(testJobInVolList);
		testJobInVolList = new Job("Test", parkEastSide, new JobID
				(1), beginDateTime, endDateTime, "test", pm);
	}
	
	@Test(expected = LessThanMinDaysAwayException.class)
	public void signUpForJob_jobLessThanMinDaysInFuture_throwsException() 
		throws Exception {
		
		LocalDateTime beginDateLessThanMinDaysAway = LocalDateTime.now()
			.plusDays(testVolunteer.getMinDaysAwaySignUp() - 1);
		Job jobLessThanMinDaysAway = new Job("Test1", parkEastSide, 
			new JobID(2), beginDateLessThanMinDaysAway, 
			beginDateLessThanMinDaysAway.plusDays(1), "test", pm);

		testVolunteer.signUpForJob(jobLessThanMinDaysAway);
	}
	
	@Test(expected = VolunteerDailyJobLimitException.class)
	public void signUpForJob_jobBeginsOnJobEndDateInVolList_throwsException() 
		throws Exception {
		
		LocalDateTime beginDateOnJobEndDateInVolList = endDateTime;
		Job jobBeginsOnJobEndDateInVolList = new Job("Test2", parkEastSide, 
			new JobID(2), beginDateOnJobEndDateInVolList, 
			beginDateOnJobEndDateInVolList.plusDays(2), "test", pm);
		
		testVolunteer.signUpForJob(jobBeginsOnJobEndDateInVolList);
	}
	
	@Test(expected = VolunteerDailyJobLimitException.class)
	public void signUpForJob_jobEndsOnJobStartDateInVolList_throwsException() 
		throws Exception {
		
		LocalDateTime endDateOnJobStartDateInVolList = beginDateTime;
		Job jobEndsOnJobStartDateInVolList = new Job("Test3", parkEastSide, 
			new JobID(2), endDateOnJobStartDateInVolList.minusDays(1), 
			endDateOnJobStartDateInVolList, "test", pm);
		
		testVolunteer.signUpForJob(jobEndsOnJobStartDateInVolList);
	}
	
	@Test(expected = VolunteerDailyJobLimitException.class)
	public void signUpForJob_jobOverlapsJobDateInVolList_throwsException() 
		throws Exception {
		
		Job jobOverlapsJobDateInVolList = new Job("Test4", parkEastSide, 
			new JobID(2), beginDateTime.minusDays(1), endDateTime.plusDays(1), 
			"test", pm);
		
		testVolunteer.signUpForJob(jobOverlapsJobDateInVolList);
		
	}
	
	@Test
	public void removeJobFromMyRegisteredJobs_jobListIsEmpty_True() {
		testVolunteer.removeJobFromMyRegisteredJobs(testJobInVolList);
		assertTrue(testVolunteer.getJobList().isEmpty());
	}

}
