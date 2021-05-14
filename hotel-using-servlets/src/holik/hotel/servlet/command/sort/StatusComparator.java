package holik.hotel.servlet.command.sort;

import java.util.Comparator;

import holik.hotel.servlet.model.Room;

public class StatusComparator implements Comparator<Room> {

	@Override
	public int compare(Room firstRoom, Room secondRoom) {
		int result = 1;
		int statusOfFirst = firstRoom.getStatus().getId();
		int statusOfSecond = secondRoom.getStatus().getId();
		if (statusOfFirst < statusOfSecond) {
			result = -1;
		} else if (statusOfFirst == statusOfSecond) {
			result = 0;
		}
		return result;
	}

}
