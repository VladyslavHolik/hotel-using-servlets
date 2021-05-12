package holik.hotel.servlet.persistence;

import java.util.List;

import holik.hotel.servlet.models.Room;

public interface RoomRepository {
	List<Room> getAllRooms();
}
