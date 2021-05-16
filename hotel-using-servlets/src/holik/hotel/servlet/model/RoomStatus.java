package holik.hotel.servlet.model;

public enum RoomStatus {
	FREE(1), BOOKED(2), BUSY(3);
	
	private int id;
	
	private RoomStatus(int id) {
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
