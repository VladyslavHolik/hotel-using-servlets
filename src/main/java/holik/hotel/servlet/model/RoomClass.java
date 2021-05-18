package holik.hotel.servlet.model;

/**
 * Enumeration of room classes.
 */
public enum RoomClass {
	Apartment(1), Balcony(2), ConnectedRooms(3), Business(4), Bedroom(5), DeLuxe(6),
	Duplex(7), FamilyRoom(8), HoneymoonRoom(9), President(10), Standart(11);
	
	private final int id;
	
	RoomClass(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public static RoomClass getRoomClassFromId(int id) {
		RoomClass result = null;
		RoomClass[] values = RoomClass.values();
		for (RoomClass roomClass : values) {
			if (roomClass.getId() == id) {
				result = roomClass;
				break;
			}
		}
		return result;
	}
}
