package model;

import java.util.HashMap;

public class ParkCollection extends HashMap<ParkID, Park> {

	/**
	 * Maps a ParkID to a Park if the ParkID is not already contained within
	 * this map's key set.
	 *
	 * Pre: ParkID does not exist within the collection.
	 */
	@Override
	public final Park put(ParkID parkID, Park park) {
		if (!this.containsKey(parkID)) {
			super.put(parkID, park);
		}
		return park;
	}


}
