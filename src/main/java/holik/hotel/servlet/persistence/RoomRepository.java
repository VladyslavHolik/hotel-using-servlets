package holik.hotel.servlet.persistence;

import java.util.List;
import java.util.Optional;

import holik.hotel.servlet.model.Room;

/**
 * Interface for room repository.
 */
public interface RoomRepository {
	List<Room> getAllRooms();
	Optional<Room> getRoomById(int id);
	List<Room> getSpecificRooms(int classId, int space, int status);
	boolean updateRoom(Room room);
}