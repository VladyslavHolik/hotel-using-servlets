package holik.hotel.servlet.persistence;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import holik.hotel.servlet.model.Room;

public interface RoomRepository {
	List<Room> getAllRooms() throws SQLException;
	Optional<Room> getRoomById(int id) throws SQLException;
}
