package model;

import java.io.Serializable;

/**
 * Created by dave on 2/9/18.
 */
public class JobID implements Serializable {

    private String jobID;

    // TODO LATER ON: PASSWORD

    public JobID(String paramJobID) {

        this.jobID = paramJobID;
    }

    public final String getUserID() {

        return this.jobID;
    }
}
