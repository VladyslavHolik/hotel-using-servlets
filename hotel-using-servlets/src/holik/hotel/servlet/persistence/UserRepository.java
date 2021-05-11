package holik.hotel.servlet.persistence;

import java.util.Optional;

import holik.hotel.servlet.models.User;

public interface UserRepository {
	boolean createUser(User user);
	Optional<User> getUserById(int id);
}
