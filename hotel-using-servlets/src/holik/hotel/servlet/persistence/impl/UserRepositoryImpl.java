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

public class UserRepositoryImpl implements UserRepository {

	@Override
	public boolean createUser(User user) {
		boolean result = false;

		try {
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://localhost:5432/hotel_servlets";
			String username = "servlet_app";
			String password = "ksdfh3kldsf9";
			Connection connection = DriverManager.getConnection(url, username, password);

			String sql = "INSERT INTO Users (first_name, last_name, email, role_id, salt, password_hash) VALUES(?, ?, ?, ?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, user.getFirstName());
			statement.setString(2, user.getLastName());
			statement.setString(3, user.getEmail());
			statement.setInt(4, user.getRole().getId());
			statement.setString(5, user.getSalt());
			statement.setString(6, user.getPasswordHash());
			
			result = statement.execute();
			connection.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
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
		
		try {
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://localhost:5432/hotel_servlets";
			String username = "servlet_app";
			String password = "ksdfh3kldsf9";
			Connection connection = DriverManager.getConnection(url, username, password);

			String sql = "SELECT * FROM Users WHERE email=?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, email);
			
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				user = new User();
				user.setId(resultSet.getInt("id"));
				user.setFirstName(resultSet.getString("first_name"));
				user.setLastName(resultSet.getString("last_name"));
				user.setEmail(email);
				user.setRole(Role.getRole(resultSet.getInt("role_id")));
				user.setSalt(resultSet.getString("salt"));
				user.setPasswordHash(resultSet.getString("password_hash"));
			}
			connection.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return Optional.ofNullable(user);
	}

}
