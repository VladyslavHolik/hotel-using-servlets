package holik.hotel.servlet.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;

import holik.hotel.servlet.model.Room;
import holik.hotel.servlet.model.RoomClass;
import holik.hotel.servlet.model.RoomStatus;
import holik.hotel.servlet.persistence.RoomRepository;
import holik.hotel.servlet.persistence.db.DBManager;

public class DefaultRoomRepository implements RoomRepository {
	private static final Logger LOG = Logger.getLogger(DefaultRoomRepository.class);

	@Override
	public List<Room> getAllRooms() {
		List<Room> result = new ArrayList<>();
		Connection connection = null;
		try {
			connection = DBManager.getConnection();
			if (connection != null) {
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
					room.setStatus(RoomStatus.getStatusById(resultSet.getInt("status")));
					result.add(room);
				}
			}
		} catch (SQLException exception) {
			String message = exception.getLocalizedMessage();
			LOG.error("SQL exception occurred: " + message);
			DBManager.rollbackAndClose(connection);
		} finally {
			if (connection != null) {
				DBManager.commitAndClose(connection);
			}
		}
		return result;
	}

	@Override
	public Optional<Room> getRoomById(int id) {
		Room room = null;

		Connection connection = DBManager.getConnection();
		if (connection != null) {
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
				DBManager.rollbackAndClose(connection);
			} finally {
				if (connection != null) {
					DBManager.commitAndClose(connection);
				}
			}
		}
		return Optional.ofNullable(room);
	}

}
