package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dave on 2/18/18.
 */
public class StaffMember extends User implements Serializable {

    ArrayList<Park> parkList;

    public StaffMember(String firstName, String lastName, UserID userID) {
        super(firstName, lastName, UserRole.STAFF_MEMBER, userID);
        parkList = new ArrayList<>();
    }

}
