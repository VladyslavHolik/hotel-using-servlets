package holik.hotel.servlet.service.impl;

import java.util.List;
import java.util.Optional;

import holik.hotel.servlet.model.Room;
import holik.hotel.servlet.persistence.RoomRepository;
import holik.hotel.servlet.persistence.impl.DefaultRoomRepository;
import holik.hotel.servlet.service.RoomService;

/**
 * Default realization of room service.
 */
public class DefaultRoomService implements RoomService {
	private final RoomRepository roomRepository;
	
	public DefaultRoomService() {
		roomRepository = new DefaultRoomRepository();
	}
	
	@Override
	public List<Room> getAllRooms() {
		return roomRepository.getAllRooms();
	}

	@Override
	public Optional<Room> getRoomById(int id) {
		return roomRepository.getRoomById(id);
	}

	@Override
	public List<Room> getSpecificRooms(int classId, int space, int status) {
		return roomRepository.getSpecificRooms(classId, space, status);
	}

	@Override
	public boolean updateRoom(Room room) {
		return roomRepository.updateRoom(room);
	}
}