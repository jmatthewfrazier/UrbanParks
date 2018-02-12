package tests;

import exceptions.InvalidJobEndDateException;
import exceptions.InvalidJobLengthException;
import exceptions.JobCollectionDuplicateKeyException;
import exceptions.MaxPendingJobsException;
import model.Job;
import model.JobCollection;
import model.JobID;
import model.Park;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class JobCollectionTest {

    private JobCollection jobCollection;

    @Before
    public void setUp() {
        jobCollection = new JobCollection();
    }

    @Test
    public void isAtMaxCapacity_ManyFewerThanMaxJobsExist_False() {
        assertFalse(jobCollection.isAtMaxCapacity());
    }

    @Test
    public void isAtMaxCapacity_OneFewerJobsThanMaxExists_False() {
        for (int i = 0; i < JobCollection.getMaxCapacity() - 1; i++) {
            try {
                jobCollection.addJob(new Job("Job " + i, new Park(),
                        new JobID(i), LocalDateTime.now().plusDays(i * 2),
                        LocalDateTime.now().plusDays(i * 2 + 2),
                        "test job"));
            } catch (MaxPendingJobsException | JobCollectionDuplicateKeyException
                    | InvalidJobEndDateException | InvalidJobLengthException e) {
                e.printStackTrace();
            }

        }
        assertFalse(jobCollection.isAtMaxCapacity());
    }

    @Test
    public void isAtMaxCapacity_MaxJobsExist_True() {
        for (int i = 0; i < JobCollection.getMaxCapacity(); i++) {
            try {
                jobCollection.addJob(new Job("Job " + i, new Park(),
                        new JobID(i), LocalDateTime.now().plusDays(i * 2),
                        LocalDateTime.now().plusDays(i * 2 + 2),
                        "test job"));
            } catch (MaxPendingJobsException | JobCollectionDuplicateKeyException
                    | InvalidJobEndDateException | InvalidJobLengthException e) {
                e.printStackTrace();
            }
        }
        assertTrue(jobCollection.isAtMaxCapacity());
    }
}
