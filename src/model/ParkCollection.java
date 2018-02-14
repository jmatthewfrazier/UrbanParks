package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ParkCollection implements Serializable {

	private Map<ParkID, Park> parkMap;

	public ParkCollection() {
	    parkMap = new HashMap<>();
    }

	/**
	 * Maps a ParkID to a Park if the ParkID is not already contained within
	 * this map's key set.
	 *
	 * Pre: ParkID does not exist within the collection.
	 */
	public final void addPark(Park park) {
		if (!parkMap.containsKey(park.getID())) {
			parkMap.put(park.getID(), park);
		}
	}

	public boolean isEmpty() {
		return parkMap.isEmpty();
	}

	public boolean containsParkID(ParkID ID) {
		return parkMap.containsKey(ID);
	}

    public final Park getPark(ParkID parkID) {
	    return parkMap.get(parkID);
    }
}
