package holik.hotel.servlet.web.command.sort;

import java.util.Comparator;
import holik.hotel.servlet.repository.model.Room;

/**
 * Comparator that is used for sorting by room status.
 */
public class StatusComparator implements Comparator<Room> {

	@Override
	public int compare(Room firstRoom, Room secondRoom) {
		int result = 1;
		int statusOfFirst = firstRoom.getRoomStatus().getId();
		int statusOfSecond = secondRoom.getRoomStatus().getId();
		if (statusOfFirst < statusOfSecond) {
			result = -1;
		} else if (statusOfFirst == statusOfSecond) {
			result = 0;
		}
		return result;
	}
}
