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
        Job testJobFoo;
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
                        "test job"));
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
                        "test job"));
            } catch (MaxPendingJobsException | JobCollectionDuplicateKeyException
                    | InvalidJobEndDateException | InvalidJobLengthException e) {
                e.printStackTrace();
            }
        }
        assertTrue(jobCollection.isAtMaxCapacity());
    }

    // TODO FIXXXX

    @Test
    public void removeJobFromCollection_MinNumberOfDaysInFutureCurrentDay_Fail() {
        Job testJobFoo = new Job("Job ", new Park(),
                new JobID(23), LocalDateTime.now(),
                LocalDateTime.now().plusDays(1),
                "test job");
        try {
            jobCollection.addJob(testJobFoo);
        } catch (MaxPendingJobsException e) {
            e.printStackTrace();
        } catch (InvalidJobLengthException e) {
            e.printStackTrace();
        } catch (InvalidJobEndDateException e) {
            e.printStackTrace();
        } catch (JobCollectionDuplicateKeyException e) {
            e.printStackTrace();
        }
        try {
            jobCollection.removeJobFromCollection(testJobFoo, new UserID("BadTim"));
        } catch (LessThanMinDaysAwayException | UserNotFoundException |
                JobIDNotFoundInCollectionException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void removeJobFromCollection_MinNumberOfDaysInFutureMultiDayStartedPreviousDay_Fail() {
        try {
            jobCollection.addJob(new Job("Job " + 1, new Park(),
                    new JobID(23), LocalDateTime.now(),
                    LocalDateTime.now().plusDays(1 * 2 + 1),
                    "test job"));
        } catch (MaxPendingJobsException e) {
            e.printStackTrace();
        } catch (InvalidJobLengthException e) {
            e.printStackTrace();
        } catch (InvalidJobEndDateException e) {
            e.printStackTrace();
        } catch (JobCollectionDuplicateKeyException e) {
            e.printStackTrace();
        }
        ;
        try {
            jobCollection.removeJobFromCollection(new JobID(23), new UserID("BadTim"));
        } catch (LessThanMinDaysAwayException | UserNotFoundException |
                JobIDNotFoundInCollectionException | UrbanParksSystemOperationException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void removeJobFromCollection_MoreThanMinNumberOfDaysInFuture_Pass() {
        try {
            jobCollection.addJob(new Job("Job " + 1, new Park(),
                    new JobID(23), LocalDateTime.now(),
                    LocalDateTime.now().plusDays(1 * 2 + 1),
                    "test job"));
        } catch (MaxPendingJobsException e) {
            e.printStackTrace();
        } catch (InvalidJobLengthException e) {
            e.printStackTrace();
        } catch (InvalidJobEndDateException e) {
            e.printStackTrace();
        } catch (JobCollectionDuplicateKeyException e) {
            e.printStackTrace();
        }
        ;
        try {

            //TODO this logic doesn't make sense, we shouldn't remove a new job
            jobCollection.removeJobFromCollection(new JobID(23), new UserID("BadTim"));
        } catch (LessThanMinDaysAwayException | UserNotFoundException |
                JobIDNotFoundInCollectionException | UrbanParksSystemOperationException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void removeJobFromCollection_ExactlyMinNumberOfDaysInFuture_Pass() {
        try {
            jobCollection.addJob(new Job("Job " + 1, new Park(),
                    new JobID(23), LocalDateTime.now(),
                    LocalDateTime.now().plusDays(1 * 2 + 1),
                    "test job"));
        } catch (MaxPendingJobsException e) {
            e.printStackTrace();
        } catch (InvalidJobLengthException e) {
            e.printStackTrace();
        } catch (InvalidJobEndDateException e) {
            e.printStackTrace();
        } catch (JobCollectionDuplicateKeyException e) {
            e.printStackTrace();
        }
        ;
        try {
            jobCollection.removeJobFromCollection(new JobID(23), new UserID("BadTim"));
        } catch (LessThanMinDaysAwayException | UserNotFoundException |
                JobIDNotFoundInCollectionException | UrbanParksSystemOperationException e) {
            e.printStackTrace();
        }
    }


}
