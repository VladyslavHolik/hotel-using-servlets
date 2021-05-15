package holik.hotel.servlet.model;

public enum ApplicationStatus {
	REQUESTED(1), APPROVED(2), DECLINED(3), BOOKED(4), PAID(5);
	
	private int id;
	
	private ApplicationStatus(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public static ApplicationStatus getStatusById(int id) {
		ApplicationStatus status = null;
		for (ApplicationStatus applicationStatus : ApplicationStatus.values()) {
			if (applicationStatus.getId() == id) {
				status = applicationStatus;
			}
		}
		return status;
	}
}
