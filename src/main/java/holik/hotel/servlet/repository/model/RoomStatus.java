package holik.hotel.servlet.repository.model;

/**
 * Enumeration of room availability.
 */
public enum RoomStatus {
	FREE(1), BOOKED(2), BUSY(3), UNAVAILABLE(4);
	
	private final int id;
	
	RoomStatus(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public static RoomStatus getStatusById(int id) {
		RoomStatus status = null;
		for (RoomStatus roomStatus : RoomStatus.values()) {
			if (roomStatus.getId() == id) {
				status = roomStatus;
			}
		}
		return status;
	}
}
