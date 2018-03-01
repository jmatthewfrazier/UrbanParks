package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class ParkManager extends User {

	private final List<Job> myJobs;

    public ParkManager(final String firstName, final String lastName,
                       final UserID userID) {
        super(firstName, lastName, UserRole.PARK_MANAGER, userID);
        this.myJobs = new ArrayList<>();
    }

    public final List<Job> getMyJobs() {
        return this.myJobs;
    }

    public final List<Job> getMyFutureJobs() {
        final List<Job> futureJobs = new ArrayList<>();
        final LocalDateTime currentDate = LocalDateTime.now();

        for (Job job : this.myJobs) {
            if (job.getBeginDateTime().isAfter(currentDate)) {
                futureJobs.add(job);
            }
        }

        return futureJobs;
    }

    public final void addJob(final Job job) {
        this.myJobs.add(job);
    }

    public final void removeJob(final Job job) {
        this.myJobs.remove(job);
    }
}
