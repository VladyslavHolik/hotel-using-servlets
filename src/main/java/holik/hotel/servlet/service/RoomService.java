package holik.hotel.servlet.service;

import java.util.List;
import java.util.Optional;

import holik.hotel.servlet.repository.model.Room;
import holik.hotel.servlet.web.command.sort.SortMethod;
import holik.hotel.servlet.web.dto.RoomsContent;

/**
 * Interface for room service.
 */
public interface RoomService {
	List<Room> getAllRooms();
	Optional<Room> getRoomById(int id);
	List<Room> getSpecificRooms(int classId, int space, int status);
	void updateRoom(Room room);
	List<Room> getAvailableRooms();
	List<Room> getAvailableRooms(int classId, int space);
	RoomsContent getRoomsContent(int pageNumber, int numberOfPages);
	int getNumberOfPages();
	List<Room> getRoomsOnPage(int pageNumber, SortMethod method);
}
