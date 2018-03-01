package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public final class Park implements Serializable {

    private final String name;
    private int houseNumber;
    private final String street;
    private final String city;
    private final String state;
    private int zipCode;
    private final ParkID ID;
    private final List<Job> jobs;

    public Park() {
        this("Stanley Park", 1000, "Apple St.",
                "Seattle", "WA", 98105, new ParkID(1));
    }

    public Park(final String name, int houseNumber, final String street,
                final String city, final String state, int zipCode,
                final ParkID parkID) {
        this.name = name;
        this.houseNumber = houseNumber;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
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
