package model;

public class Park {

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
}
