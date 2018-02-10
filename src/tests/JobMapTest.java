package tests;

import model.Job;
import model.Park;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class JobMapTest {

    private JobCollection jobCollection;

    @Before
    public void setUp() {
        jobCollection = new JobCollection();
    }

    /**
     * This tests that a Job can be added only when there is below the
     * maximum number of Jobs already contained in the system.
     * Specifically, it tests the result when there are many fewer Jobs than
     * maximum in the system.
     */
    @Test
    public void canAddJob_ManyFewerThanMaxJobsExist_True() {
        assertTrue(jobCollection.canAddJob());
    }

    /**
     * This tests that a Job can be added only when there is below the
     * maximum number of Jobs already contained in the system.
     * Specifically, it tests the result when there is one fewer Jobs than
     * maximum in the system.
     */
    @Test
    public void canAddJob_OneFewerJobsThanMaxExists_True() {
        for (int i = 0; i < JobCollection.getMaxCapacity() - 1; i++) {
            jobCollection.put("Job " + i, new Job("Job " + i,
                    new Park(), LocalDateTime.now().plusDays(i * 2),
                    LocalDateTime.now().plusDays(i * 2 + 2)));
        }
        assertTrue(jobCollection.canAddJob());
    }

    /**
     * This tests that a Job can be added only when there is below the
     * maximum number of Jobs already contained in the system.
     * Specifically, it tests the result when there is the maximum number of
     * Jobs in the system.
     */
    @Test
    public void canAddJob_MaxJobsExist_False() {
        for (int i = 0; i < JobCollection.getMaxCapacity(); i++) {
            jobCollection.put("Job " + i, new Job("Job " + i,
                    new Park(), LocalDateTime.now().plusDays(i * 2),
                    LocalDateTime.now().plusDays(i * 2 + 2)));
        }
        assertFalse(jobCollection.canAddJob());
    }

    @After
    public void tearDown() {
    }
}
