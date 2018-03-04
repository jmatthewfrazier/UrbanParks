package model;

import java.io.Serializable;
import java.util.Objects;

public final class ParkID implements Serializable {

	private int parkID;
	
    public ParkID(int parkID) {
        this.parkID = parkID;
    }

    public int getParkIDNumber() {
    	return parkID;
    }

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if (!(other instanceof ParkID)) {
			return false;
		}
		return this.parkID == ((ParkID) other).parkID;
	}

	@Override
	public int hashCode() {
		return Objects.hash(parkID);
	}

}
