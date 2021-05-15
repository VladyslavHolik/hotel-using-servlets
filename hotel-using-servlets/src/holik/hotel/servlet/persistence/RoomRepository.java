package holik.hotel.servlet.persistence;

import java.util.List;
import java.util.Optional;
import holik.hotel.servlet.model.Room;

public interface RoomRepository {
	List<Room> getAllRooms();
	Optional<Room> getRoomById(int id);
}
