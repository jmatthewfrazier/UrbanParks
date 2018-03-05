package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public final class Park implements Serializable {

    private final String name;
    private final ParkID ID;
    private final List<Job> jobs;

    public Park(final String name, ParkID parkID) {
        this.name = name;
        this.ID = parkID;
        this.jobs = new ArrayList<>();
    }

    public final ParkID getID() {
        return this.ID;
    }

    @Override
    public final String toString() {
        return this.name;
    }
}
