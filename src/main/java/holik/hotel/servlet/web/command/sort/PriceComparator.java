package holik.hotel.servlet.web.command.sort;

import java.util.Comparator;

import holik.hotel.servlet.repository.model.Room;

/**
 * Comparator that is used for sorting by room price.
 */
public class PriceComparator implements Comparator<Room> {

	@Override
	public int compare(Room firstRoom, Room secondRoom) {
		int result = 1;
		int priceOfFirst = firstRoom.getPrice();
		int priceOfSecond = secondRoom.getPrice();
		if (priceOfFirst < priceOfSecond) {
			result = -1;
		} else if (priceOfFirst == priceOfSecond) {
			result = 0;
		}
		return result;
	}
}
