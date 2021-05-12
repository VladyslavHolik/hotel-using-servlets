package holik.hotel.servlet.models;

public enum Role {
	MANAGER(1), USER(2);
	
	private final int id;
	
	private Role(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public static Role getRole(int id) {
		Role role = null;
		switch (id) {
		case 1: 
			role = Role.MANAGER;
			break;
		case 2:
			role = Role.USER;
			break;
		}
		return role;
	}
}