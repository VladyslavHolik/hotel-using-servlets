package holik.hotel.servlet.model;

import java.time.LocalDateTime;

public final class Application {
	private int id;
	private int userId;
	private int space;
	private int roomId;
	private RoomClass roomClass;
	private ApplicationStatus status;
	private LocalDateTime datetimeOfArrival;
	private LocalDateTime datetimeOfLeaving;
	private LocalDateTime datetimeOfBooking;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public ApplicationStatus getStatus() {
		return status;
	}

	public void setStatus(ApplicationStatus status) {
		this.status = status;
	}

	public LocalDateTime getDatetimeOfBooking() {
		return datetimeOfBooking;
	}

	public void setDatetimeOfBooking(LocalDateTime datetimeOfBooking) {
		this.datetimeOfBooking = datetimeOfBooking;
	}
	
	@Override
	public boolean equals(Object secondApplication) {
		boolean result = false;
		if (secondApplication instanceof Application) {
			Application second = (Application) secondApplication;
			result = id == second.getId();
		}
		return result;
	}
}
