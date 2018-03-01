package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public final class ParkCollection implements Serializable {

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
	public final void addPark(final Park park) {
		if (!parkMap.containsKey(park.getID())) {
			parkMap.put(park.getID(), park);
		}
	}

	public final boolean isEmpty() {
		return this.parkMap.isEmpty();
	}

	public final boolean containsParkID(final ParkID ID) {
		return this.parkMap.containsKey(ID);
	}

    public final Park getPark(final ParkID parkID) {
	    return this.parkMap.get(parkID);
    }
}
