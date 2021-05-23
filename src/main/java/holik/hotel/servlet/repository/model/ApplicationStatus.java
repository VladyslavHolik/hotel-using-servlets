package holik.hotel.servlet.repository.model;

/**
 * Enumeration of application statuses.
 */
public enum ApplicationStatus {
	REQUESTED(1), APPROVED(2), DECLINED(3), BOOKED(4), PAID(5);
	
	private final int id;
	
	ApplicationStatus(int id) {
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
				break;
			}
		}
		return status;
	}
}
