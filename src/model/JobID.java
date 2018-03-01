package model;

import java.io.Serializable;
import java.util.Objects;

public final class JobID implements Serializable {

    private final int jobIDNumber;

    public JobID(int jobID) {
        this.jobIDNumber = jobID;
    }

    public final int getJobIDNumber() {
        return jobIDNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof JobID)) {
            return false;
        }
        final JobID other = (JobID) o;
        return this.getJobIDNumber() == other.getJobIDNumber();
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobIDNumber);
    }
}