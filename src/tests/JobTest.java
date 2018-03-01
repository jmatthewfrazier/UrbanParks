package tests;

import model.Job;
import model.JobID;
import model.Park;
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

    @Before
    public void setUp() {
        beginDateTime = LocalDateTime.now();
    	testJob = new Job("Test", parkEastSide, new JobID(1),
    			beginDateTime, beginDateTime.plusDays(1), "test", null);
		parkWestSide = new Park();
		parkEastSide = new Park();
    }
    
    
    @Test
    public void isStartAtEndDate_StartAtEndDate_True(){
    	final Job job = new Job(jobNameFoo, parkWestSide, new JobID(1),
    			beginDateTime, beginDateTime.plusDays(
    					Job.MAX_JOB_LENGTH_IN_DAYS), "test job", null);
    	
    	final Job test_Job = new Job(jobNameBar, parkEastSide, new JobID(2),
    			beginDateTime.plusDays(Job.MAX_JOB_LENGTH_IN_DAYS), 
    			beginDateTime.plusDays(2*Job.MAX_JOB_LENGTH_IN_DAYS), 
    			"test job2", null);
    	
    	assertTrue("Volunteer cannot sign up for this job!!",
    			job.isStartAtEndDate(test_Job));
    }
    
    @Test
    public void isStartAtEndDate_NotStartAtEndDate_False(){
    	final Job job = new Job(jobNameFoo, parkWestSide, new JobID(1),
    			beginDateTime, beginDateTime.plusDays(
    					Job.MAX_JOB_LENGTH_IN_DAYS), "test job", null);
    	
    	final Job test_Job = new Job(jobNameBar, parkEastSide, new JobID(2),
    			beginDateTime.plusDays(Job.MAX_JOB_LENGTH_IN_DAYS-1), 
    			beginDateTime.plusDays(2*Job.MAX_JOB_LENGTH_IN_DAYS-1), 
    			"test job2", null);
    	
    	assertFalse("Volunteer can sign up for this job!!",
    			job.isStartAtEndDate(test_Job));
    }
    
    @Test
    public void isEndAtStartDate_EndAtStartDate_True(){
    	final Job job = new Job(jobNameBar, parkEastSide, new JobID(2),
    			beginDateTime.plusDays(Job.MAX_JOB_LENGTH_IN_DAYS + 2),
    			beginDateTime.plusDays(2 * Job.MAX_JOB_LENGTH_IN_DAYS + 2),
    			"test job2", null);
    	
    	final Job test_Job = new Job(jobNameFoo, parkWestSide, new JobID(1),
    			beginDateTime, beginDateTime.plusDays(
    					Job.MAX_JOB_LENGTH_IN_DAYS), "test job", null);
    	
    	assertFalse("Volunteer cannot sign up for this job!!",
    			job.isEndAtStartDate(test_Job));
    }
    
    @Test
    public void isEndAtStartDate_NotEndAtStartDate_False(){
    	final Job job = new Job(jobNameBar, parkEastSide, new JobID(2),
    			beginDateTime.plusDays(Job.MAX_JOB_LENGTH_IN_DAYS - 1),
    			beginDateTime.plusDays(2 * Job.MAX_JOB_LENGTH_IN_DAYS - 1), 
    			"test job2", null);
    	
    	final Job test_Job = new Job(jobNameFoo, parkWestSide, new JobID(1),
    			beginDateTime, beginDateTime.plusDays(
    					Job.MAX_JOB_LENGTH_IN_DAYS), "test job", null);
    	
    	assertFalse("Volunteer can sign up for this job!!",
    			job.isEndAtStartDate(test_Job));
    }
    
    @Test
    public void isOverlapping_Overlapping_True(){
    	final Job job = new Job(jobNameFoo, parkWestSide, new JobID(1),
    			beginDateTime, beginDateTime.plusDays(
    					Job.MAX_JOB_LENGTH_IN_DAYS), "test job", null);
    	
    	final Job test_Job = new Job(jobNameBar, parkEastSide, new JobID(2),
    			beginDateTime.plusDays(Job.MAX_JOB_LENGTH_IN_DAYS).minusHours(
    			Job.MAX_JOB_LENGTH_IN_DAYS), beginDateTime.plusDays(
    			2*Job.MAX_JOB_LENGTH_IN_DAYS).minusHours(
    			Job.MAX_JOB_LENGTH_IN_DAYS), "test job2", null);
    	
    	assertTrue("Volunteer cannot sign up for this job!!",
    			job.isOverlapping(test_Job));
    }
    
    @Test
    public void isOverlapping_NotOverlapping_False(){
    	final Job job = new Job(jobNameFoo, parkWestSide, new JobID(1),
    			beginDateTime, beginDateTime.plusDays(
    			Job.MAX_JOB_LENGTH_IN_DAYS), "test job", null);
    	
    	final Job test_Job = new Job(jobNameBar, parkEastSide, new JobID(2),
    			beginDateTime.plusDays(Job.MAX_JOB_LENGTH_IN_DAYS).plusHours(
    			Job.MAX_JOB_LENGTH_IN_DAYS), beginDateTime.plusDays(
    			2*Job.MAX_JOB_LENGTH_IN_DAYS).plusHours(
    			Job.MAX_JOB_LENGTH_IN_DAYS), "test job2", null);
    	
    	assertFalse("Volunteer can sign up for this job!!",
    			job.isOverlapping(test_Job));
    }

	@Test
	public void testJobConstructor_SameDatesNameFooWestside_NotNullTrue() {
		assertNotNull(new Job(jobNameFoo, parkWestSide, new JobID(1),
				beginDateTime, beginDateTime, "test job", null));
		//fail("Not yet implemented");
	}

	@Test
	public void testIsNewJobLengthValid_JobMaxDaysMinusOne_True() {
		LocalDateTime dayMaxDaysMinusOneDay =
				beginDateTime.plusDays((Job.MAX_JOB_LENGTH_IN_DAYS - 2));
		Job jobMaxDaysMinusOne = new Job(jobNameFoo, parkWestSide, new JobID(1),
                beginDateTime, dayMaxDaysMinusOneDay, "test job", null);
		assertTrue(jobMaxDaysMinusOne.isJobLengthValid());
	}

	@Test
	public void testIsNewJobLengthValid_JobMaxDaysExactly_True() {
		LocalDateTime dayMaxDaysExactly =
				beginDateTime.plusDays((Job.MAX_JOB_LENGTH_IN_DAYS - 1));
		Job jobMaxDaysExactly = new Job(jobNameFoo, parkWestSide, new JobID(1),
                beginDateTime, dayMaxDaysExactly, "test job", null);
		assertTrue(jobMaxDaysExactly.isJobLengthValid());
	}

	@Test
	public void testIsNewJobLengthValid_JobMaxDaysPlusOne_False() {
		LocalDateTime dayMaxDaysPlusOneDay =
				beginDateTime.plusDays((Job.MAX_JOB_LENGTH_IN_DAYS));
		Job jobMaxDaysPlusOne = new Job(jobNameFoo, parkWestSide, new JobID(1),
                beginDateTime, dayMaxDaysPlusOneDay, "test job", null);
		assertFalse(jobMaxDaysPlusOne.isJobLengthValid());
	}

    @Test
    public void isJobWithinValidDateRange_JobEndDateOneFewerThanMax_True() {
        Job test_Job = new Job("Test Job", parkEastSide, new JobID(1),
		        LocalDateTime.now()
				        .plusDays(Job.getMaximumValidDayRangeFromToday() - 2),
		        LocalDateTime.now()
				        .plusDays(Job.getMaximumValidDayRangeFromToday() - 1),
		        "test job", null);
        assertTrue(test_Job.isJobWithinValidDateRange());
    }

    @Test
    public void isJobWithinValidDateRange_JobEndDateEqualsMax_True() {
        Job test_Job = new Job("Test Job", parkEastSide, new JobID(1),
		        LocalDateTime.now()
				        .plusDays(Job.getMaximumValidDayRangeFromToday() - 1),
		        LocalDateTime.now()
                        .plusDays(Job.getMaximumValidDayRangeFromToday()),
		        "test job", null);
        assertTrue(test_Job.isJobWithinValidDateRange());
    }

    @Test
    public void isJobWithinValidDateRange_JobEndDateOneGreaterThanMax_False() {
        Job test_Job = new Job("Test Job", parkEastSide, new JobID(1),
		        LocalDateTime.now()
				        .plusDays(Job.getMaximumValidDayRangeFromToday() + 1),
		        LocalDateTime.now()
                        .plusDays(Job.getMaximumValidDayRangeFromToday() + 2),
                "test job", null);
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
    //end of Job Test class
}
