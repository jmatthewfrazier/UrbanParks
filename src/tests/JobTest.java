package tests;

import model.*;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class JobTest {

    private String jobNameFoo = "A Job Named Foo";
    private String jobNameBar = "A Job Named Bar";
    private LocalDateTime beginDateTime;
    private Job testJob;
	private Park parkWestSide;
	private Park parkEastSide;
	private ParkManager creator;

    @Before
    public void setUp() {
        beginDateTime = LocalDateTime.now();
        creator = new ParkManager("Test", "PM",
		        new UserID("testpm"));
    	testJob = new Job("Test", parkEastSide, new JobID(1),
    			beginDateTime, beginDateTime.plusDays(1), "test", creator);
		parkWestSide = new Park("West Side Park", new ParkID(1));
		parkEastSide = new Park("East Side Park", new ParkID(2));
    }
    
    
    @Test
    public void isStartAtEndDate_StartAtEndDate_True(){
    	final Job job = new Job(jobNameFoo, parkWestSide, new JobID(1),
    			beginDateTime, beginDateTime.plusDays(
    					Job.getMaxJobLengthInDays()), "test job", creator);
    	
    	final Job test_Job = new Job(jobNameBar, parkEastSide, new JobID(2),
    			beginDateTime.plusDays(Job.getMaxJobLengthInDays()),
    			beginDateTime.plusDays(2*Job.getMaxJobLengthInDays()),
    			"test job2", creator);
    	
    	assertTrue("Volunteer cannot sign up for this job!!",
    			job.isStartAtEndDate(test_Job));
    }
    
    @Test
    public void isStartAtEndDate_NotStartAtEndDate_False(){
    	final Job job = new Job(jobNameFoo, parkWestSide, new JobID(1),
    			beginDateTime, beginDateTime.plusDays(
    					Job.getMaxJobLengthInDays()), "test job", creator);
    	
    	final Job test_Job = new Job(jobNameBar, parkEastSide, new JobID(2),
    			beginDateTime.plusDays(Job.getMaxJobLengthInDays()-1),
    			beginDateTime.plusDays(2*Job.getMaxJobLengthInDays()-1),
    			"test job2", creator);
    	
    	assertFalse("Volunteer can sign up for this job!!",
    			job.isStartAtEndDate(test_Job));
    }
    
    @Test
    public void isEndAtStartDate_EndAtStartDate_True(){
    	final Job job = new Job(jobNameBar, parkEastSide, new JobID(2),
    			beginDateTime.plusDays(Job.getMaxJobLengthInDays() + 2),
    			beginDateTime.plusDays(2 * Job.getMaxJobLengthInDays() + 2),
    			"test job2", creator);
    	
    	final Job test_Job = new Job(jobNameFoo, parkWestSide, new JobID(1),
    			beginDateTime, beginDateTime.plusDays(
    					Job.getMaxJobLengthInDays()), "test job", creator);
    	
    	assertFalse("Volunteer cannot sign up for this job!!",
    			job.isEndAtStartDate(test_Job));
    }
    
    @Test
    public void isEndAtStartDate_NotEndAtStartDate_False(){
    	final Job job = new Job(jobNameBar, parkEastSide, new JobID(2),
    			beginDateTime.plusDays(Job.getMaxJobLengthInDays() - 1),
    			beginDateTime.plusDays(2 * Job.getMaxJobLengthInDays() - 1),
    			"test job2", creator);
    	
    	final Job test_Job = new Job(jobNameFoo, parkWestSide, new JobID(1),
    			beginDateTime, beginDateTime.plusDays(
    					Job.getMaxJobLengthInDays()), "test job", creator);
    	
    	assertFalse("Volunteer can sign up for this job!!",
    			job.isEndAtStartDate(test_Job));
    }
    
    @Test
    public void isOverlapping_Overlapping_True(){
    	final Job job = new Job(jobNameFoo, parkWestSide, new JobID(1),
    			beginDateTime, beginDateTime.plusDays(
    					Job.getMaxJobLengthInDays()), "test job", creator);
    	
    	final Job test_Job = new Job(jobNameBar, parkEastSide, new JobID(2),
    			beginDateTime.plusDays(Job.getMaxJobLengthInDays()).minusHours(
    			Job.getMaxJobLengthInDays()), beginDateTime.plusDays(
    			2*Job.getMaxJobLengthInDays()).minusHours(
    			Job.getMaxJobLengthInDays()), "test job2", creator);
    	
    	assertTrue("Volunteer cannot sign up for this job!!",
    			job.isOverlapping(test_Job));
    }
    
    @Test
    public void isOverlapping_NotOverlapping_False(){
    	final Job job = new Job(jobNameFoo, parkWestSide, new JobID(1),
    			beginDateTime, beginDateTime.plusDays(
    			Job.getMaxJobLengthInDays()), "test job", creator);
    	
    	final Job test_Job = new Job(jobNameBar, parkEastSide, new JobID(2),
    			beginDateTime.plusDays(Job.getMaxJobLengthInDays()).plusHours(
    			Job.getMaxJobLengthInDays()), beginDateTime.plusDays(
    			2*Job.getMaxJobLengthInDays()).plusHours(
    			Job.getMaxJobLengthInDays()), "test job2", creator);
    	
    	assertFalse("Volunteer can sign up for this job!!",
    			job.isOverlapping(test_Job));
    }

	@Test
	public void testJobConstructor_SameDatesNameFooWestside_NotNullTrue() {
		Job job = new Job(jobNameFoo, parkWestSide, new JobID(1),
				beginDateTime, beginDateTime, "test job", creator);
		assertNotNull(job);
	}

	@Test
	public void testIsNewJobLengthValid_JobMaxDaysMinusOne_True() {
		LocalDateTime dayMaxDaysMinusOneDay =
				beginDateTime.plusDays((Job.getMaxJobLengthInDays() - 2));
		Job jobMaxDaysMinusOne = new Job(jobNameFoo, parkWestSide, new JobID(1),
                beginDateTime, dayMaxDaysMinusOneDay, "test job", creator);
		assertTrue(jobMaxDaysMinusOne.isJobLengthValid());
	}

	@Test
	public void testIsNewJobLengthValid_JobMaxDaysExactly_True() {
		LocalDateTime dayMaxDaysExactly =
				beginDateTime.plusDays((Job.getMaxJobLengthInDays() - 1));
		Job jobMaxDaysExactly = new Job(jobNameFoo, parkWestSide, new JobID(1),
                beginDateTime, dayMaxDaysExactly, "test job", creator);
		assertTrue(jobMaxDaysExactly.isJobLengthValid());
	}

	@Test
	public void testIsNewJobLengthValid_JobMaxDaysPlusOne_False() {
		LocalDateTime dayMaxDaysPlusOneDay =
				beginDateTime.plusDays((Job.getMaxJobLengthInDays()));
		Job jobMaxDaysPlusOne = new Job(jobNameFoo, parkWestSide, new JobID(1),
                beginDateTime, dayMaxDaysPlusOneDay, "test job", creator);
		assertFalse(jobMaxDaysPlusOne.isJobLengthValid());
	}

    @Test
    public void isJobWithinValidDateRange_JobEndDateOneFewerThanMax_True() {
        Job test_Job = new Job("Test Job", parkEastSide, new JobID(1),
		        LocalDateTime.now()
				        .plusDays(Job.getMaximumValidDayRangeFromToday() - 2),
		        LocalDateTime.now()
				        .plusDays(Job.getMaximumValidDayRangeFromToday() - 1),
		        "test job", creator);
        assertTrue(test_Job.isJobWithinValidDateRange());
    }

    @Test
    public void isJobWithinValidDateRange_JobEndDateEqualsMax_True() {
        Job test_Job = new Job("Test Job", parkEastSide, new JobID(1),
		        LocalDateTime.now()
				        .plusDays(Job.getMaximumValidDayRangeFromToday() - 1),
		        LocalDateTime.now()
                        .plusDays(Job.getMaximumValidDayRangeFromToday()),
		        "test job", creator);
        assertTrue(test_Job.isJobWithinValidDateRange());
    }

    @Test
    public void isJobWithinValidDateRange_JobEndDateOneGreaterThanMax_False() {
        Job test_Job = new Job("Test Job", parkEastSide, new JobID(1),
		        LocalDateTime.now()
				        .plusDays(Job.getMaximumValidDayRangeFromToday() + 1),
		        LocalDateTime.now()
                        .plusDays(Job.getMaximumValidDayRangeFromToday() + 2),
                "test job", creator);
        assertFalse(test_Job.isJobWithinValidDateRange());
    }
    
    @Test
    public void isJobStartAfterEqualDate_JobStartBeforeDate_False() {
    	LocalDateTime testDate = testJob.getBeginDateTime().plusDays(1);
        assertFalse(testJob.isJobStartAfterEqualDate(testDate));
    }
    
    @Test
    public void isJobStartAfterEqualDate_JobStartAfterDate_True() {
    	LocalDateTime testDate = testJob.getBeginDateTime().minusDays(1);
        assertTrue(testJob.isJobStartAfterEqualDate(testDate));
    }
    
    @Test
    public void isJobStartAfterEqualDate_JobStartEqualDate_True() {
    	LocalDateTime testDate = testJob.getBeginDateTime();
        assertTrue(testJob.isJobStartAfterEqualDate(testDate));
    }
    
    @Test
    public void isJobEndBeforeEqualDate_JobEndAfterDate_False() {
    	LocalDateTime testDate = testJob.getEndDateTime().minusDays(1);
        assertFalse(testJob.isJobEndBeforeEqualDate(testDate));
    }
    
    @Test
    public void isJobEndBeforeEqualDate_JobEndBeforeDate_True() {
    	LocalDateTime testDate = testJob.getEndDateTime().plusDays(1);
        assertTrue(testJob.isJobEndBeforeEqualDate(testDate));
    }
    
    @Test
    public void isJobEndBeforeEqualDate_JobEndEqualDate_True() {
    	LocalDateTime testDate =testJob.getEndDateTime();
        assertTrue(testJob.isJobEndBeforeEqualDate(testDate));
    }
}
