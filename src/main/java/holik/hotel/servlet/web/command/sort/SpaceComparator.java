package holik.hotel.servlet.web.command.sort;

import java.util.Comparator;

import holik.hotel.servlet.repository.model.Room;

/**
 * Comparator that is used for sorting by room space.
 */
public class SpaceComparator implements Comparator<Room> {

	@Override
	public int compare(Room firstRoom, Room secondRoom) {
		int result = 1;
		int spaceOfFirst = firstRoom.getSpace();
		int spaceOfSecond = secondRoom.getSpace();
		
		if (spaceOfFirst < spaceOfSecond) {
			result = -1;
		} else if (spaceOfFirst == spaceOfSecond) {
			result = 0;
		}
		return result;
	}

}
