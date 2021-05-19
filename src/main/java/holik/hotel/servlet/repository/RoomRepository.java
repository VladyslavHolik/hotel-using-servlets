package holik.hotel.servlet.repository;

import java.util.List;
import java.util.Optional;

import holik.hotel.servlet.repository.model.Application;
import holik.hotel.servlet.repository.model.Room;

/**
 * Interface for room repository.
 */
public interface RoomRepository {
	List<Room> getAllRooms();
	Optional<Room> getRoomById(int id);
	List<Room> getSpecificRooms(int classId, int space, int status);
	boolean updateRoom(Room room);
	List<Room> getAvailableRooms();
    List<Room> getRoomsOrderedByPrice(int limit, int offset);
	List<Room> getRoomsOrderedBySpace(int limit, int offset);
	List<Room> getRoomsOrderedByClass(int limit, int offset);
	List<Room> getRoomsOrderedByStatus(int limit, int offset);
}
