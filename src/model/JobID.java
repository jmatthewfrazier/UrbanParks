package model;

import java.io.Serializable;
import java.util.Objects;

public final class JobID implements Serializable {
    private final int jobID;

    public JobID(int jobID) {
        this.jobID = jobID;
    }

    public final int getJobID() {
        return jobID;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof JobID)) {
            return false;
        }
        JobID other = (JobID) o;
        return this.getJobID() == other.getJobID();
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobID);
    }
}