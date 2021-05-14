package holik.hotel.servlet.model;

import java.time.LocalDateTime;

public final class Application {
	private int id;
	private int space;
	private RoomClass roomClass;
	private LocalDateTime datetimeOfArrival;
	private LocalDateTime datetimeOfLeaving;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public LocalDateTime getDatetimeOfArrival() {
		return datetimeOfArrival;
	}

	public void setDatetimeOfArrival(LocalDateTime datetimeOfArrival) {
		this.datetimeOfArrival = datetimeOfArrival;
	}

	public LocalDateTime getDatetimeOfLeaving() {
		return datetimeOfLeaving;
	}

	public void setDatetimeOfLeaving(LocalDateTime datetimeOfLeaving) {
		this.datetimeOfLeaving = datetimeOfLeaving;
	}
}
