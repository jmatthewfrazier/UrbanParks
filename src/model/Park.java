package model;

import java.util.Objects;

public class Park implements Comparable<Park> {

    private String name;

    private int houseNumber;

    private String street;

    private String city;

    private String state;

    private int zipCode;

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
    }

    @Override
    public int compareTo(Park other) {
        return name.compareTo(other.name);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this)
            return true;
        if (!(other instanceof Park))
            return false;
        Park o = (Park) other;
        return this.name.equals(o.name)
                && this.houseNumber == o.houseNumber
                && this.street.equals(o.street)
                && this.city.equals(o.city)
                && this.state.equals(o.state)
                && this.zipCode == o.zipCode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, houseNumber, street, city, state, zipCode);
    }
}
