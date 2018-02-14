package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public final class ParkManager extends User implements Serializable {

    //store of all parks this user manages / is authorized to add jobs for
    //TODO-actually this probably belongs in aPark, not here
    private List<Park> parkList;

    public ParkManager(String firstName, String lastName, UserID userID) {
       super(firstName, lastName, UserRole.PARK_MANAGER, userID);
        parkList = new ArrayList<>();
    }

}
