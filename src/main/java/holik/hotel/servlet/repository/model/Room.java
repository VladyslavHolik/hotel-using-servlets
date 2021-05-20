package holik.hotel.servlet.repository.model;

/**
 * Room model.
 */
public class Room {
	private int id;
	private String number;
	private int price;
	private int space;
	private RoomClass roomClass;
	private RoomAvailability availability;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNumber() {
		return number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	public int getSpace() {
		return space;
	}
	
	public void setSpace(int space) {
		this.space = space;
	}
	
	public RoomClass getRoomClass() {
		return roomClass;
	}
	
	public void setRoomClass(RoomClass roomClass) {
		this.roomClass = roomClass;
	}
	
	public RoomAvailability getAvailability() {
		return availability;
	}
	
	public void setAvailability(RoomAvailability availability) {
		this.availability = availability;
	}
	
	public String getPreview() {
		return "/image?type=rooms&id=" + id;
	}
}
