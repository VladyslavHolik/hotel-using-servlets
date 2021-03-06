package holik.hotel.servlet.repository.impl;

import holik.hotel.servlet.repository.UserRepository;
import holik.hotel.servlet.repository.db.DBManager;
import holik.hotel.servlet.repository.exception.EntityExistsException;
import holik.hotel.servlet.repository.model.Role;
import holik.hotel.servlet.repository.model.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Default realization of user repository.
 */
public class DefaultUserRepository implements UserRepository {
    private static final int SQL_DUPLICATE_ERROR_CODE = 1062;

    @Override
    public void save(User user) throws EntityExistsException {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "INSERT INTO Users (first_name, last_name, phone, email, role_id, salt, password_hash) VALUES(?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getPhone());
            statement.setString(4, user.getEmail());
            statement.setInt(5, user.getRole().getId());
            statement.setString(6, user.getSalt());
            statement.setString(7, user.getPasswordHash());

            statement.execute();
        } catch (SQLException exception) {
            if (exception.getErrorCode() == SQL_DUPLICATE_ERROR_CODE) {
                throw new EntityExistsException("User with email " + user.getEmail() + " already exists");
            }
            String message = exception.getLocalizedMessage();
            throw new IllegalStateException(message);
        }
    }

    @Override
    public Optional<User> getUserById(int id) {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM Users WHERE id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            return getUser(statement);
        } catch (SQLException exception) {
            String message = exception.getLocalizedMessage();
            throw new IllegalStateException(message);
        }
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM Users WHERE email=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            return getUser(statement);
        } catch (SQLException exception) {
            String message = exception.getLocalizedMessage();
            throw new IllegalStateException(message);
        }
    }

    private Optional<User> getUser(PreparedStatement statement) throws SQLException {
        User user = null;
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            user = new User();
            user.setId(resultSet.getInt("id"));
            user.setFirstName(resultSet.getString("first_name"));
            user.setLastName(resultSet.getString("last_name"));
            user.setPhone(resultSet.getString("phone"));
            user.setEmail(resultSet.getString("email"));
            user.setRole(Role.getRole(resultSet.getInt("role_id")));
            user.setSalt(resultSet.getString("salt"));
            user.setPasswordHash(resultSet.getString("password_hash"));
        }
        return Optional.ofNullable(user);
    }
}
