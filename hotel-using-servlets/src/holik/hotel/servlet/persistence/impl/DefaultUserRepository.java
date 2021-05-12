package holik.hotel.servlet.persistence.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import holik.hotel.servlet.models.Role;
import holik.hotel.servlet.models.User;
import holik.hotel.servlet.persistence.UserRepository;
import holik.hotel.servlet.persistence.db.DBManager;

public class DefaultUserRepository implements UserRepository {

	@Override
	public boolean createUser(User user) {
		boolean result = false;

		Connection connection = DBManager.getConnection();
		if (connection != null) {
			try {
				String sql = "INSERT INTO Users (first_name, last_name, phone, email, role_id, salt, password_hash) VALUES(?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setString(1, user.getFirstName());
				statement.setString(2, user.getLastName());
				statement.setString(3, user.getPhone());
				statement.setString(4, user.getEmail());
				statement.setInt(5, user.getRole().getId());
				statement.setString(6, user.getSalt());
				statement.setString(7, user.getPasswordHash());

				result = statement.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (connection != null) {
					DBManager.commitAndClose(connection);
				}
			}
		}
		return result;
	}

	@Override
	public Optional<User> getUserById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<User> getUserByEmail(String email) {
		User user = null;

		Connection connection = DBManager.getConnection();
		if (connection != null) {
			try {
				String sql = "SELECT * FROM Users WHERE email=?";
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setString(1, email);

				ResultSet resultSet = statement.executeQuery();
				if (resultSet.next()) {
					user = new User();
					user.setId(resultSet.getInt("id"));
					user.setFirstName(resultSet.getString("first_name"));
					user.setLastName(resultSet.getString("last_name"));
					user.setPhone(resultSet.getString("phone"));
					user.setEmail(email);
					user.setRole(Role.getRole(resultSet.getInt("role_id")));
					user.setSalt(resultSet.getString("salt"));
					user.setPasswordHash(resultSet.getString("password_hash"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (connection != null) {
					DBManager.commitAndClose(connection);
				}
			}
		}

		return Optional.ofNullable(user);
	}

}
