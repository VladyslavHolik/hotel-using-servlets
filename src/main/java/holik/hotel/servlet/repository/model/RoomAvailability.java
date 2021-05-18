package holik.hotel.servlet.repository.model;

/**
 * Enumeration of room availability.
 */
public enum RoomAvailability {
	AVAILABLE(1), UNAVAILABLE(2);
	
	private final int id;
	
	RoomAvailability(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public static RoomAvailability getStatusById(int id) {
		RoomAvailability status = null;
		for (RoomAvailability roomStatus : RoomAvailability.values()) {
			if (roomStatus.getId() == id) {
				status = roomStatus;
			}
		}
		return status;
	}
}
