package tests;

import exceptions.InvalidJobEndDateException;
import exceptions.InvalidJobLengthException;
import exceptions.JobCollectionDuplicateKeyException;
import exceptions.MaxPendingJobsException;
import model.*;

import java.time.LocalDateTime;

/**
 * Created by dave on 2/17/18.
 */
public class MockJobCollection {

    private final JobCollection jobWallet;

    private final ParkCollection parks;

    public UserID uidOne = new UserID("InternetFamous");
    private String pmTwo = "TrollFeeder";
    private String pmThree = "FakeBake";

    public MockJobCollection() {
        jobWallet = new JobCollection();
        parks = new ParkCollection();

        populateJobCollection();
    }

    private void populateJobCollection() {
        try {
            jobWallet.addJob(new Job("Dock Maintenance",
                    parks.getPark(new ParkID(2)), new JobID(14),
                    LocalDateTime.now().plusDays(20),
                    LocalDateTime.now().plusDays(21),
                    "Replacing boards on the dock at the lake.",
                    uidOne));
        } catch (MaxPendingJobsException | JobCollectionDuplicateKeyException |
                InvalidJobEndDateException | InvalidJobLengthException e) {
            e.printStackTrace();
        }

    }

    public JobCollection getJobWallet() {
        return jobWallet;
    }

    //end MockJobCollection
}
