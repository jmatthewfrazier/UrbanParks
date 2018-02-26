package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class StaffMember extends User implements Serializable {

    ArrayList<Park> parkList;

//    private JobCollection administratedJobCollection;

    public StaffMember() {
        this("Test", "Staff Member", new UserID("testSM"));
    }

    public StaffMember(String firstName, String lastName, UserID userID) {
        super(firstName, lastName, UserRole.STAFF_MEMBER, userID);
        parkList = new ArrayList<>();
    }

}
