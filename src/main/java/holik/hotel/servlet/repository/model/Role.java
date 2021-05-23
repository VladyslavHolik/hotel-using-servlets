package holik.hotel.servlet.repository.model;

/**
 * Enumeration of user roles.
 */
public enum Role {
	MANAGER(1), USER(2);
	
	private final int id;
	
	Role(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public static Role getRole(int id) {
		Role result = null;
		for (Role role : Role.values()) {
			if (role.getId() == id) {
				result = role;
				break;
			}
		}
		return result;
	}
}
