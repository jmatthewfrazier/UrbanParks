package tests;

import model.Job;
import model.JobID;
import model.Park;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class JobTest {

    /*
     * Maximum number of days a job can last.
     * Default of 3, per documentation, User Story #2, business rule: b.
     *
     * A note for Java's LocalDateTime class, adding 1 day will
     * result in the next day, eg Jan. 5 is becomes Jan. 6.
     * As a result, this value when added to the first day of the job will
     * give a time period of 1 day too many, be sure to account for this.
     *
     * Rather than have a user attempt to account for this 1-off, it is
     * adjusted for in the class under test as well as this test class.
     *
     * LocalDateTime requires it to be of type Long
     */
    private Long maxJobDays = (long) 3;

    public int MAX_NUM_DAYS_FROM_TODAY = 75;

    private String jobNameFoo = "A Job Named Foo";

    private String jobNameBar = "A Job Named Bar";

    LocalDateTime beginDateTime;

    LocalDateTime beginDateTimeMaxLengthMinusOneDay;

    LocalDateTime beginDateTimePlusMaxDays;

	Park parkWestside;

	Park parkEastside;

    @Before
    public void setUp() {

        beginDateTime = LocalDateTime.now();
        //System.out.println(beginDateTime.toString());

		parkWestside = new Park();
		parkEastside = new Park();

    }

	@Test
	public void testJobConstructor_SameDatesNameFooWestside_NotNullTrue() {
		assertNotNull(new Job(jobNameFoo, parkWestside, new JobID(1),
				beginDateTime, beginDateTime, "test job"));
		//fail("Not yet implemented");
	}

	@Test
	public void testIsNewJobLengthValid_JobMaxDaysMinusOne_True() {
		LocalDateTime dayMaxDaysMinusOneDay =
				beginDateTime.plusDays((maxJobDays - 2));
		Job jobMaxDaysMinusOne = new Job(jobNameFoo, parkWestside, new JobID(1),
                beginDateTime, dayMaxDaysMinusOneDay, "test job");
		assertTrue(jobMaxDaysMinusOne.isJobLengthValid());
	}

	@Test
	public void testIsNewJobLengthValid_JobMaxDaysExactly_True() {
		LocalDateTime dayMaxDaysExactly =
				beginDateTime.plusDays((maxJobDays - 1));
		Job jobMaxDaysExactly = new Job(jobNameFoo, parkWestside, new JobID(1),
                beginDateTime, dayMaxDaysExactly, "test job");
		assertTrue(jobMaxDaysExactly.isJobLengthValid());
	}

	@Test
	public void testIsNewJobLengthValid_JobMaxDaysPlusOne_False() {
		LocalDateTime dayMaxDaysPlusOneDay =
				beginDateTime.plusDays((maxJobDays));
		Job jobMaxDaysPlusOne = new Job(jobNameFoo, parkWestside, new JobID(1),
                beginDateTime, dayMaxDaysPlusOneDay, "test job");
		assertFalse(jobMaxDaysPlusOne.isJobLengthValid());
	}

    /**
     * This tests that a Job's end date is within the maximum range.
     * Specifically, it tests the result when a Job's end date is one day
     * before the maximum date away from today.
     */
    @Test
    public void isJobWithinValidDateRange_JobEndDateOneFewerThanMax_True() {
        Job testJob = new Job("Test Job", parkEastside, new JobID(1),
		        LocalDateTime.now()
				        .plusDays(Job.getMaximumValidDayRangeFromToday() - 2),
		        LocalDateTime.now()
				        .plusDays(Job.getMaximumValidDayRangeFromToday() - 1),
		        "test job");
        assertTrue(testJob.isJobWithinValidDateRange());
    }

    /**
     * This tests that a Job's end date is within the maximum range.
     * Specifically, it tests the result when a Job's end date is the same
     * as the maximum date away from today.
     */
    @Test
    public void isJobWithinValidDateRange_JobEndDateEqualsMax_True() {
        Job testJob = new Job("Test Job", parkEastside, new JobID(1),
		        LocalDateTime.now()
				        .plusDays(Job.getMaximumValidDayRangeFromToday() - 1),
		        LocalDateTime.now()
                        .plusDays(Job.getMaximumValidDayRangeFromToday()),
		        "test job");
        assertTrue(testJob.isJobWithinValidDateRange());
    }

    /**
     * This tests that a Job's end date is within the maximum range.
     * Specifically, it tests the result when a Job's end date is one day after
     * the maximum date away from today.
     */
    @Test
    public void isJobWithinValidDateRange_JobEndDateOneGreaterThanMax_False() {
        Job testJob = new Job("Test Job", parkEastside, new JobID(1),
		        LocalDateTime.now()
				        .plusDays(Job.getMaximumValidDayRangeFromToday()),
		        LocalDateTime.now()
                        .plusDays(Job.getMaximumValidDayRangeFromToday() + 1),
                "test job");
        assertTrue(testJob.isJobWithinValidDateRange());
    }


    @After
    public void tearDown() {
    }

	@Test
	public void testIsNewJobValid() {

		//fail("Not yet implemented");
	}


    //end of Job Test class
}
