package holik.hotel.servlet.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;

import holik.hotel.servlet.repository.model.Room;
import holik.hotel.servlet.repository.model.RoomClass;
import holik.hotel.servlet.repository.model.RoomAvailability;
import holik.hotel.servlet.repository.RoomRepository;
import holik.hotel.servlet.repository.db.DBManager;

/**
 * Default realization of room repository.
 */
public class DefaultRoomRepository implements RoomRepository {
	private static final Logger LOG = Logger.getLogger(DefaultRoomRepository.class);

	@Override
	public List<Room> getAllRooms() {
		List<Room> result = new ArrayList<>();
		Connection connection = null;
		try {
			connection = DBManager.getConnection();
			String sql = "select * from rooms;";
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				Room room = new Room();
				room.setId(resultSet.getInt("id"));
				room.setNumber(resultSet.getString("number"));
				room.setPrice(resultSet.getInt("price"));
				room.setSpace(resultSet.getInt("space"));
				room.setRoomClass(RoomClass.getRoomClassFromId(resultSet.getInt("class")));
				room.setAvailability(RoomAvailability.getStatusById(resultSet.getInt("status")));
				result.add(room);
			}
		} catch (SQLException exception) {
			String message = exception.getLocalizedMessage();
			LOG.error("SQL exception occurred: " + message);
		} finally {
			DBManager.closeConnection(connection);
		}
		return result;
	}

	@Override
	public Optional<Room> getRoomById(int id) {
		Room room = null;

		Connection connection = DBManager.getConnection();
		try {
			String sql = "select * from rooms where id=?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				room = new Room();
				room.setId(resultSet.getInt("id"));
				room.setNumber(resultSet.getString("number"));
				room.setPrice(resultSet.getInt("price"));
				room.setSpace(resultSet.getInt("space"));
				room.setRoomClass(RoomClass.getRoomClassFromId(resultSet.getInt("class")));
			}
		} catch (SQLException exception) {
			String message = exception.getLocalizedMessage();
			LOG.error("SQL exception occurred: " + message);
		} finally {
			DBManager.closeConnection(connection);
		}

		return Optional.ofNullable(room);
	}

	@Override
	public List<Room> getSpecificRooms(int classId, int space, int status) {
		List<Room> result = new ArrayList<>();
		Connection connection = null;
		try {
			connection = DBManager.getConnection();
			String sql = "select * from rooms where class=? and space=? and status=?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, classId);
			statement.setInt(2, space);
			statement.setInt(3, status);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Room room = new Room();
				room.setId(resultSet.getInt("id"));
				room.setNumber(resultSet.getString("number"));
				room.setPrice(resultSet.getInt("price"));
				room.setSpace(resultSet.getInt("space"));
				room.setRoomClass(RoomClass.getRoomClassFromId(resultSet.getInt("class")));
				room.setAvailability(RoomAvailability.getStatusById(resultSet.getInt("status")));
				result.add(room);
			}
		} catch (SQLException exception) {
			String message = exception.getLocalizedMessage();
			LOG.error("SQL exception occurred: " + message);
		} finally {
			DBManager.closeConnection(connection);
		}
		return result;
	}

	@Override
	public boolean updateRoom(Room room) {
		boolean result = false;
		Optional<Room> storedRoom = getRoomById(room.getId());
		if (storedRoom.isPresent()) {
			Connection connection = DBManager.getConnection();
			try {
				String sql = "UPDATE Rooms SET number=?, price=?, space=?, class=?, status=? WHERE id=?";
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setString(1, room.getNumber());
				statement.setInt(2, room.getPrice());
				statement.setInt(3, room.getSpace());
				statement.setInt(4, room.getRoomClass().getId());
				statement.setObject(5, room.getAvailability().getId());
				statement.setInt(6, room.getId());
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
