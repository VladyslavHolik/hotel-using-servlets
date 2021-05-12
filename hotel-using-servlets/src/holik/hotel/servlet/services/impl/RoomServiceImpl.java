package holik.hotel.servlet.services.impl;

import java.util.List;

import holik.hotel.servlet.models.Room;
import holik.hotel.servlet.persistence.RoomRepository;
import holik.hotel.servlet.persistence.impl.RoomRepositoryImpl;
import holik.hotel.servlet.services.RoomService;

public class RoomServiceImpl implements RoomService {

	private RoomRepository roomRepository;
	
	public RoomServiceImpl() {
		roomRepository = new RoomRepositoryImpl();
	}
	
	@Override
	public List<Room> getAllRooms() {
		return roomRepository.getAllRooms();
	}

}
