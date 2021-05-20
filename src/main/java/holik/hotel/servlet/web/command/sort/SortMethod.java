package holik.hotel.servlet.web.command.sort;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import holik.hotel.servlet.repository.model.Room;

/**
 * Enumeration for sorting methods.
 */
public enum SortMethod {
	PRICE, SPACE, CLASS, STATUS;
	
	public static SortMethod getMethod(String method) {
		if (isMethodInvalid(method)) {
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
	
	public static boolean isMethodInvalid(String method) {
		boolean result = false;
		for (SortMethod sortMethod : SortMethod.values()) {
			if (sortMethod.name().equals(method.toUpperCase())) {
				result = true;
				break;
			}
		}
		return !result;
	}
}
