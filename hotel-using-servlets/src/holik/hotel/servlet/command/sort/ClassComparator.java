package holik.hotel.servlet.command.sort;

import java.util.Comparator;

import holik.hotel.servlet.model.Room;

public class ClassComparator implements Comparator<Room> {

	@Override
	public int compare(Room firstRoom, Room secondRoom) {
		int result = 1;
		int classOfFirst = firstRoom.getRoomClass().getId();
		int classOfSecond = secondRoom.getRoomClass().getId();
		if (classOfFirst < classOfSecond) {
			result = -1;
		} else if (classOfFirst == classOfSecond) {
			result = 0;
		}
		return result;
	}
}
