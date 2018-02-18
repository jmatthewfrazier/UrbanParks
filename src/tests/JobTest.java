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

	private Park parkWestSide;

	private Park parkEastSide;

    @Before
    public void setUp() {
        beginDateTime = LocalDateTime.now();
		parkWestSide = new Park();
		parkEastSide = new Park();
    }
    
    
    @Test
    public void isStartAtEndDate_StartAtEndDate_True(){
    	final Job job = new Job(jobNameFoo, parkWestSide, new JobID(1),
    			beginDateTime, beginDateTime.plusDays(Job.MAX_JOB_LENGTH_IN_DAYS), "test job");
    	
    	final Job testJob = new Job(jobNameBar, parkEastSide, new JobID(2),
    			beginDateTime.plusDays(Job.MAX_JOB_LENGTH_IN_DAYS), 
    			beginDateTime.plusDays(2*Job.MAX_JOB_LENGTH_IN_DAYS), "test job2");
    	
    	assertTrue("Volunteer cannot sign up for this job!!",
    			job.isStartAtEndDate(testJob));
    }
    
    @Test
    public void isStartAtEndDate_NotStartAtEndDate_False(){
    	final Job job = new Job(jobNameFoo, parkWestSide, new JobID(1),
    			beginDateTime, beginDateTime.plusDays(Job.MAX_JOB_LENGTH_IN_DAYS), "test job");
    	
    	final Job testJob = new Job(jobNameBar, parkEastSide, new JobID(2),
    			beginDateTime.plusDays(Job.MAX_JOB_LENGTH_IN_DAYS-1), 
    			beginDateTime.plusDays(2*Job.MAX_JOB_LENGTH_IN_DAYS-1), "test job2");
    	
    	assertFalse("Volunteer can sign up for this job!!",
    			job.isStartAtEndDate(testJob));
    }
    
    @Test
    public void isEndAtStartDate_EndAtStartDate_True(){
    	final Job job = new Job(jobNameBar, parkEastSide, new JobID(2),
    			beginDateTime.plusDays(Job.MAX_JOB_LENGTH_IN_DAYS + 2),
    			beginDateTime.plusDays(2 * Job.MAX_JOB_LENGTH_IN_DAYS + 2), "test job2");
    	
    	final Job testJob = new Job(jobNameFoo, parkWestSide, new JobID(1),
    			beginDateTime, beginDateTime.plusDays(Job.MAX_JOB_LENGTH_IN_DAYS), "test job");
    	
    	assertFalse("Volunteer cannot sign up for this job!!",
    			job.isEndAtStartDate(testJob));
    }
    
    @Test
    public void isEndAtStartDate_NotEndAtStartDate_False(){
    	final Job job = new Job(jobNameBar, parkEastSide, new JobID(2),
    			beginDateTime.plusDays(Job.MAX_JOB_LENGTH_IN_DAYS - 1),
    			beginDateTime.plusDays(2 * Job.MAX_JOB_LENGTH_IN_DAYS - 1), "test job2");
    	
    	final Job testJob = new Job(jobNameFoo, parkWestSide, new JobID(1),
    			beginDateTime, beginDateTime.plusDays(Job.MAX_JOB_LENGTH_IN_DAYS), "test job");
    	
    	assertFalse("Volunteer can sign up for this job!!",
    			job.isEndAtStartDate(testJob));
    }
    
    @Test
    public void isOverlapping_Overlapping_True(){
    	final Job job = new Job(jobNameFoo, parkWestSide, new JobID(1),
    			beginDateTime, beginDateTime.plusDays(Job.MAX_JOB_LENGTH_IN_DAYS), "test job");
    	
    	final Job testJob = new Job(jobNameBar, parkEastSide, new JobID(2),
    			beginDateTime.plusDays(Job.MAX_JOB_LENGTH_IN_DAYS).minusHours(Job.MAX_JOB_LENGTH_IN_DAYS), 
    			beginDateTime.plusDays(2*Job.MAX_JOB_LENGTH_IN_DAYS).minusHours(Job.MAX_JOB_LENGTH_IN_DAYS),
    			"test job2");
    	
    	assertTrue("Volunteer cannot sign up for this job!!",
    			job.isOverlapping(testJob));
    }
    
    @Test
    public void isOverlapping_NotOverlapping_False(){
    	final Job job = new Job(jobNameFoo, parkWestSide, new JobID(1),
    			beginDateTime, beginDateTime.plusDays(Job.MAX_JOB_LENGTH_IN_DAYS), "test job");
    	
    	final Job testJob = new Job(jobNameBar, parkEastSide, new JobID(2),
    			beginDateTime.plusDays(Job.MAX_JOB_LENGTH_IN_DAYS).plusHours(Job.MAX_JOB_LENGTH_IN_DAYS), 
    			beginDateTime.plusDays(2*Job.MAX_JOB_LENGTH_IN_DAYS).plusHours(Job.MAX_JOB_LENGTH_IN_DAYS),
    			"test job2");
    	
    	assertFalse("Volunteer can sign up for this job!!",
    			job.isOverlapping(testJob));
    }

	@Test
	public void testJobConstructor_SameDatesNameFooWestside_NotNullTrue() {
		assertNotNull(new Job(jobNameFoo, parkWestSide, new JobID(1),
				beginDateTime, beginDateTime, "test job"));
		//fail("Not yet implemented");
	}

	@Test
	public void testIsNewJobLengthValid_JobMaxDaysMinusOne_True() {
		LocalDateTime dayMaxDaysMinusOneDay =
				beginDateTime.plusDays((Job.MAX_JOB_LENGTH_IN_DAYS - 2));
		Job jobMaxDaysMinusOne = new Job(jobNameFoo, parkWestSide, new JobID(1),
                beginDateTime, dayMaxDaysMinusOneDay, "test job");
		assertTrue(jobMaxDaysMinusOne.isJobLengthValid());
	}

	@Test
	public void testIsNewJobLengthValid_JobMaxDaysExactly_True() {
		LocalDateTime dayMaxDaysExactly =
				beginDateTime.plusDays((Job.MAX_JOB_LENGTH_IN_DAYS - 1));
		Job jobMaxDaysExactly = new Job(jobNameFoo, parkWestSide, new JobID(1),
                beginDateTime, dayMaxDaysExactly, "test job");
		assertTrue(jobMaxDaysExactly.isJobLengthValid());
	}

	@Test
	public void testIsNewJobLengthValid_JobMaxDaysPlusOne_False() {
		LocalDateTime dayMaxDaysPlusOneDay =
				beginDateTime.plusDays((Job.MAX_JOB_LENGTH_IN_DAYS));
		Job jobMaxDaysPlusOne = new Job(jobNameFoo, parkWestSide, new JobID(1),
                beginDateTime, dayMaxDaysPlusOneDay, "test job");
		assertFalse(jobMaxDaysPlusOne.isJobLengthValid());
	}

    @Test
    public void isJobWithinValidDateRange_JobEndDateOneFewerThanMax_True() {
        Job testJob = new Job("Test Job", parkEastSide, new JobID(1),
		        LocalDateTime.now()
				        .plusDays(Job.getMaximumValidDayRangeFromToday() - 2),
		        LocalDateTime.now()
				        .plusDays(Job.getMaximumValidDayRangeFromToday() - 1),
		        "test job");
        assertTrue(testJob.isJobWithinValidDateRange());
    }

    @Test
    public void isJobWithinValidDateRange_JobEndDateEqualsMax_True() {
        Job testJob = new Job("Test Job", parkEastSide, new JobID(1),
		        LocalDateTime.now()
				        .plusDays(Job.getMaximumValidDayRangeFromToday() - 1),
		        LocalDateTime.now()
                        .plusDays(Job.getMaximumValidDayRangeFromToday()),
		        "test job");
        assertTrue(testJob.isJobWithinValidDateRange());
    }

    @Test
    public void isJobWithinValidDateRange_JobEndDateOneGreaterThanMax_False() {
        Job testJob = new Job("Test Job", parkEastSide, new JobID(1),
		        LocalDateTime.now()
				        .plusDays(Job.getMaximumValidDayRangeFromToday() + 1),
		        LocalDateTime.now()
                        .plusDays(Job.getMaximumValidDayRangeFromToday() + 2),
                "test job");
        assertFalse(testJob.isJobWithinValidDateRange());
    }

    //end of Job Test class
}
