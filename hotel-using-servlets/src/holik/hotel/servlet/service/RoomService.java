package holik.hotel.servlet.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import holik.hotel.servlet.model.Room;

public interface RoomService {
	List<Room> getAllRooms() throws SQLException;
	Optional<Room> getRoomById(int id) throws SQLException;
}
