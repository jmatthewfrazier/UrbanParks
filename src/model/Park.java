package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public final class Park implements Serializable {

    private String name;

    private int houseNumber;

    private String street;

    private String city;

    private String state;

    private int zipCode;

    private ParkID ID;

    private List<Job> jobs;

    public Park() {
        this("Default name", 1000, "Apple St.",
                "Seattle", "WA", 98105);
    }

    public Park(String name, int houseNumber, String street, String city,
                String state, int zipCode) {
        this.name = name;
        this.houseNumber = houseNumber;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.jobs = new ArrayList<>();
    }

    public ParkID getID() {
        return ID;
    }

    public String getName() {
        return name;
    }
}
