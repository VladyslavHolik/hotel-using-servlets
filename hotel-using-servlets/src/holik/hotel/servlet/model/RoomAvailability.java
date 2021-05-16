package holik.hotel.servlet.model;

public enum RoomAvailability {
	AVAILABLE(1), UNAVAILABLE(2);
	
	private int id;
	
	private RoomAvailability(int id) {
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
