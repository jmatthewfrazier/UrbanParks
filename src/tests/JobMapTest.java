package tests;

import model.Job;
import model.JobMap;
import model.Park;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class JobMapTest {

    private JobMap jobMap;

    @Before
    public void setUp() {
        jobMap = new JobMap();
    }

    /**
     * This tests that a Job can be added only when there is below the
     * maximum number of Jobs already contained in the system.
     * Specifically, it tests the result when there are many fewer Jobs than
     * maximum in the system.
     */
    @Test
    public void isNotAtCapacity_ManyFewerThanMaxJobsExist_True() {
        assertTrue(jobMap.isNotAtCapacity());
    }

    /**
     * This tests that a Job can be added only when there is below the
     * maximum number of Jobs already contained in the system.
     * Specifically, it tests the result when there is one fewer Jobs than
     * maximum in the system.
     */
    @Test
    public void isNotAtCapacity_OneFewerJobsThanMaxExists_True() {
        for (int i = 0; i < JobMap.MAX_CAPACITY - 1; i++) {
            jobMap.addJob(new Job("Job " + i, new Park(),
                    LocalDateTime.now().plusDays(i * 2),
                    LocalDateTime.now().plusDays(i * 2 + 2)));
        }
        assertTrue(jobMap.isNotAtCapacity());
    }

    /**
     * This tests that a Job can be added only when there is below the
     * maximum number of Jobs already contained in the system.
     * Specifically, it tests the result when there is the maximum number of
     * Jobs in the system.
     */
    @Test
    public void isNotAtCapacity_MaxJobsExist_False() {
        for (int i = 0; i < JobMap.MAX_CAPACITY; i++) {
            jobMap.addJob(new Job("Job " + i, new Park(),
                    LocalDateTime.now().plusDays(i * 2),
                    LocalDateTime.now().plusDays(i * 2 + 2)));
        }
        assertFalse(jobMap.isNotAtCapacity());
    }

    @After
    public void tearDown() {
    }
}
