package holik.hotel.servlet.services.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import holik.hotel.servlet.models.Room;
import holik.hotel.servlet.persistence.RoomRepository;
import holik.hotel.servlet.persistence.impl.DefaultRoomRepository;
import holik.hotel.servlet.services.RoomService;

public class DefaultRoomService implements RoomService {

	private RoomRepository roomRepository;
	
	public DefaultRoomService() {
		roomRepository = new DefaultRoomRepository();
	}
	
	@Override
	public List<Room> getAllRooms() throws SQLException {
		return roomRepository.getAllRooms();
	}

	@Override
	public Optional<Room> getRoomById(int id) throws SQLException{
		return roomRepository.getRoomById(id);
	}

}
