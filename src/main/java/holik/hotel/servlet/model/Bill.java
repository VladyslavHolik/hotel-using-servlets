package holik.hotel.servlet.model;

/**
 * Bill model.
 */
public final class Bill {
	private Room room;
	private Application application;
	private long price;

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}
}