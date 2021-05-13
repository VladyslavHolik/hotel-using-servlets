package holik.hotel.servlet.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import holik.hotel.servlet.models.Room;

public interface RoomService {
	List<Room> getAllRooms() throws SQLException;
	Optional<Room> getRoomById(int id) throws SQLException;
}
