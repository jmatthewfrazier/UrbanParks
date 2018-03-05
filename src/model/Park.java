package model;

import java.io.Serializable;

public final class Park implements Serializable {

    private final String name;
    private final ParkID ID;


    public Park(final String name, ParkID parkID) {
        this.name = name;
        this.ID = parkID;
    }

    public final ParkID getID() {
        return this.ID;
    }

    @Override
    public final String toString() {
        return this.name;
    }
}
