package holik.hotel.servlet.persistence;

import java.sql.SQLException;
import java.util.Optional;

import holik.hotel.servlet.models.User;

public interface UserRepository {
	boolean createUser(User user) throws SQLException;
	Optional<User> getUserById(int id) throws SQLException;
	Optional<User> getUserByEmail(String email) throws SQLException;
}
