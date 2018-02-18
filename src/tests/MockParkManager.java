package tests;

import model.ParkManager;
import model.UserID;

/**
 * Created by dave on 2/17/18.
 */
public class MockParkManager {

    private final ParkManager mockPM;

    public MockParkManager() {
        mockPM = new ParkManager("Carlos", "Danger",
                new UserID("InternetFamous"));
    }

    public ParkManager getMockPM() {
        return mockPM;
    }
}
