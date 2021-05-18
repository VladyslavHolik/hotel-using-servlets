package holik.hotel.servlet.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;

import holik.hotel.servlet.model.Application;
import holik.hotel.servlet.model.ApplicationStatus;
import holik.hotel.servlet.model.RoomClass;
import holik.hotel.servlet.persistence.ApplicationRepository;
import holik.hotel.servlet.persistence.db.DBManager;

/**
 * Default realization of application repository.
 */
public class DefaultApplicationRepository implements ApplicationRepository {
	private static final Logger LOG = Logger.getLogger(DefaultApplicationRepository.class);

	@Override
	public boolean saveApplication(Application application) {
		boolean result = false;

		Connection connection = DBManager.getConnection();
		try {
			String sql = "INSERT INTO Applications (user_id, space, class, arrival, leaving, booked, status) VALUES(?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, application.getUserId());
			statement.setInt(2, application.getSpace());
			statement.setInt(3, application.getRoomClass().getId());
			statement.setObject(4, application.getDatetimeOfArrival());
			statement.setObject(5, application.getDatetimeOfLeaving());
			statement.setObject(6, application.getDatetimeOfBooking());
			statement.setInt(7, application.getStatus().getId());

			result = statement.execute();
		} catch (SQLException exception) {
			String message = exception.getLocalizedMessage();
			LOG.error("SQL exception occurred: " + message);
		} finally {
			DBManager.closeConnection(connection);
		}
		return result;
	}

	@Override
	public Optional<Application> getApplicationById(int id) {
		Application application = null;

		Connection connection = DBManager.getConnection();
		try {
			String sql = "select * from applications where id=?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				application = new Application();
				application.setId(id);
				application.setUserId(resultSet.getInt("user_id"));
				application.setSpace(resultSet.getInt("space"));
				application.setRoomId(resultSet.getInt("room_id"));
				application.setDatetimeOfArrival(resultSet.getObject("arrival", LocalDateTime.class));
				application.setDatetimeOfLeaving(resultSet.getObject("leaving", LocalDateTime.class));
				application.setDatetimeOfBooking(resultSet.getObject("booked", LocalDateTime.class));
				application.setRoomClass(RoomClass.getRoomClassFromId(resultSet.getInt("class")));
				application.setStatus(ApplicationStatus.getStatusById(resultSet.getInt("status")));
			}
		} catch (SQLException exception) {
			String message = exception.getLocalizedMessage();
			LOG.error("SQL exception occurred: " + message);
		} finally {
			DBManager.closeConnection(connection);
		}

		return Optional.ofNullable(application);
	}

	@Override
	public List<Application> getAllApplications() {
		List<Application> list = new ArrayList<>();

		Connection connection = DBManager.getConnection();
		try {
			String sql = "select * from applications";
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				Application application = new Application();
				application.setId(resultSet.getInt("id"));
				application.setUserId(resultSet.getInt("user_id"));
				application.setSpace(resultSet.getInt("space"));
				application.setRoomId(resultSet.getInt("room_id"));
				application.setDatetimeOfArrival(resultSet.getObject("arrival", LocalDateTime.class));
				application.setDatetimeOfLeaving(resultSet.getObject("leaving", LocalDateTime.class));
				application.setDatetimeOfBooking(resultSet.getObject("booked", LocalDateTime.class));
				application.setRoomClass(RoomClass.getRoomClassFromId(resultSet.getInt("class")));
				application.setStatus(ApplicationStatus.getStatusById(resultSet.getInt("status")));
				list.add(application);
			}
		} catch (SQLException exception) {
			String message = exception.getLocalizedMessage();
			LOG.error("SQL exception occurred: " + message);
		} finally {
			DBManager.closeConnection(connection);
		}
		return list;
	}

	@Override
	public boolean updateApplication(Application application) {
		boolean result = false;
		Optional<Application> storedApplication = getApplicationById(application.getId());
		if (storedApplication.isPresent()) {
			Connection connection = DBManager.getConnection();
			try {
				String sql = "UPDATE Applications SET user_id=?, space=?, class=?, room_id=?, arrival=?, leaving=?, booked=?, status=? WHERE id=?";
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setInt(1, application.getUserId());
				statement.setInt(2, application.getSpace());
				statement.setInt(3, application.getRoomClass().getId());
				statement.setInt(4, application.getRoomId());
				statement.setObject(5, application.getDatetimeOfArrival());
				statement.setObject(6, application.getDatetimeOfLeaving());
				statement.setObject(7, application.getDatetimeOfBooking());
				statement.setInt(8, application.getStatus().getId());
				statement.setInt(9, application.getId());
				result = statement.execute();
			} catch (SQLException exception) {
				String message = exception.getLocalizedMessage();
				LOG.error("SQL exception occurred: " + message);
			} finally {
				DBManager.closeConnection(connection);
			}
		}
		return result;
	}
}
