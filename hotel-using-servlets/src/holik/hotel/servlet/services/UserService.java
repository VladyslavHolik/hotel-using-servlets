package holik.hotel.servlet.services;

import java.util.Optional;

import holik.hotel.servlet.models.User;

public interface UserService {
	boolean createUser(User user);
	Optional<User> getUserById(int id);
	Optional<User> getUserByEmail(String email);
}
