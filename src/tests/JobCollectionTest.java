package tests;

import exceptions.*;
import model.*;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class JobCollectionTest {

    private JobCollection jobCollection;

    private LocalDateTime todayDate;

    @Before
    public void setUp() {
        jobCollection = new JobCollection();
        todayDate = LocalDateTime.now();
    }

    @Test
    public void isAtMaxCapacity_ManyFewerThanMaxJobsExist_False() {
        assertFalse(jobCollection.isAtMaxCapacity());
    }

    @Test
    public void isValidCapacity_CapacitySetToZero_False() {
        assertFalse(jobCollection.isValidCapacity(0));
    }

    @Test
    public void isValidCapacity_CapacityIsNegativeInteger_False() {
        assertFalse(jobCollection.isValidCapacity(-1));
    }

    @Test
    public void isValidCapacity_CapacityIsNonInteger_False() {
        assertFalse(jobCollection.isValidCapacity(1.5));
    }

    @Test
    public void isValidCapacity_CapacityIsPositiveInteger_True() {
        assertTrue(jobCollection.isValidCapacity(1));
    }

    @Test
    public void isAtMaxCapacity_OneFewerJobsThanMaxExists_False() {
        for (int i = 0; i < jobCollection.getMaxCapacity() - 1; i++) {
            try {
                jobCollection.addJob(new Job("Job " + i, new Park(),
                        new JobID(i), LocalDateTime.now().plusDays(i * 2),
                        LocalDateTime.now().plusDays(i * 2 + 2),
                        "test job", new UserID("FredAnderson")));
            } catch (MaxPendingJobsException | JobCollectionDuplicateKeyException
                    | InvalidJobEndDateException | InvalidJobLengthException e) {
                e.getMessage();
            }

        }
        assertFalse(jobCollection.isAtMaxCapacity());
    }

    @Test
    public void isAtMaxCapacity_MaxJobsExist_True() {
        for (int i = 0; i < jobCollection.getMaxCapacity(); i++) {
            try {
                jobCollection.addJob(new Job("Job " + i, new Park(),
                        new JobID(i), LocalDateTime.now().plusDays(i * 2),
                        LocalDateTime.now().plusDays(i * 2 + 2),
                        "test job", new UserID("BadTim")));
            } catch (MaxPendingJobsException | JobCollectionDuplicateKeyException
                    | InvalidJobEndDateException | InvalidJobLengthException e) {
                e.printStackTrace();
            }
        }
        assertTrue(jobCollection.isAtMaxCapacity());
    }

    @Test
    public void removeJobFromCollection_MinNumberOfDaysInFutureCurrentDay_Fail() {
        jobCollection.addJob(new Job("Job " + 1, new Park(),
                new JobID(23), LocalDateTime.now(),
                LocalDateTime.now().plusDays(1 * 2 + 1),
                "test job", new UserID("BadTim")));;
        try {
            jobCollection.removeJobFromCollection(new JobID(23), new UserID("BadTim"));
        } catch (LessThanMinDaysAwayException | UserNotFoundException |
                JobIDNotFoundInCollectionException | UrbanParksSystemOperationException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void removeJobFromCollection_MinNumberOfDaysInFutureMultiDayStartedPreviousDay_Fail() {
        jobCollection.addJob(new Job("Job " + 1, new Park(),
                new JobID(23), LocalDateTime.now(),
                LocalDateTime.now().plusDays(1 * 2 + 1),
                "test job", new UserID("BadTim")));;
        try {
            jobCollection.removeJobFromCollection(new JobID(23), new UserID("BadTim"));
        } catch (LessThanMinDaysAwayException | UserNotFoundException |
                JobIDNotFoundInCollectionException | UrbanParksSystemOperationException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void removeJobFromCollection_MoreThanMinNumberOfDaysInFuture_Pass() {
        jobCollection.addJob(new Job("Job " + 1, new Park(),
                new JobID(23), LocalDateTime.now(),
                LocalDateTime.now().plusDays(1 * 2 + 1),
                "test job", new UserID("BadTim")));;
        try {
            jobCollection.removeJobFromCollection(new JobID(23), new UserID("BadTim"));
        } catch (LessThanMinDaysAwayException | UserNotFoundException |
                JobIDNotFoundInCollectionException | UrbanParksSystemOperationException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void removeJobFromCollection_ExactlyMinNumberOfDaysInFuture_Pass() {
        jobCollection.addJob(new Job("Job " + 1, new Park(),
                new JobID(23), LocalDateTime.now(),
                LocalDateTime.now().plusDays(1 * 2 + 1),
                "test job", new UserID("BadTim")));;
        try {
            jobCollection.removeJobFromCollection(new JobID(23), new UserID("BadTim"));
        } catch (LessThanMinDaysAwayException | UserNotFoundException |
                JobIDNotFoundInCollectionException | UrbanParksSystemOperationException e) {
            e.printStackTrace();
        }
    }


}
