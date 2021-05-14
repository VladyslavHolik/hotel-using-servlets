package holik.hotel.servlet.model;

public final class Room {
	private int id;
	private String number;
	private int price;
	private int space;
	private RoomClass roomClass;
	private RoomStatus status;
	
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
	
	public RoomStatus getStatus() {
		return status;
	}
	
	public void setStatus(RoomStatus status) {
		this.status = status;
	}
	
	public String getPreview() {
		return "./images/rooms/" + id  + "/1.jpg";
	}
}
