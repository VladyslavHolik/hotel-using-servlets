package holik.hotel.servlet.web.command.sort;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import holik.hotel.servlet.repository.model.Room;

/**
 * Enumeration for sorting methods.
 */
public enum SortMethod {
	PRICE(new PriceComparator()), SPACE(new SpaceComparator()),
	CLASS(new ClassComparator()), STATUS(new StatusComparator());
	
	private final Comparator<Room> comparator;
	
	SortMethod(Comparator<Room> comparator) {
		this.comparator = comparator;
	}
	
	public static SortMethod getMethod(String method) {
		if (!isValidMethod(method)) {
			throw new IllegalArgumentException("method is invalid");
		}
		
		SortMethod result = null;
		for (SortMethod sortMethod : SortMethod.values()) {
			if (sortMethod.name().equals(method.toUpperCase())) {
				result = sortMethod;
				break;
			}
		}
		return result;
	}
	
	public static boolean isValidMethod(String method) {
		boolean result = false;
		for (SortMethod sortMethod : SortMethod.values()) {
			if (sortMethod.name().equals(method.toUpperCase())) {
				result = true;
				break;
			}
		}
		return result;
	}
	
	public void sort(List<Room> rooms) {
		Collections.sort(rooms, comparator);
	}
}
