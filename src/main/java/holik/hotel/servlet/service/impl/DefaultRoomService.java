package holik.hotel.servlet.service.impl;

import holik.hotel.servlet.repository.RoomRepository;
import holik.hotel.servlet.repository.model.Room;
import holik.hotel.servlet.service.RoomService;
import holik.hotel.servlet.web.command.sort.SortMethod;
import holik.hotel.servlet.web.dto.RoomsContent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Default realization of room service.
 */
public class DefaultRoomService implements RoomService {
	private final RoomRepository roomRepository;
	
	public DefaultRoomService(RoomRepository roomRepository) {
		this.roomRepository = roomRepository;
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

	@Override
	public List<Room> getAvailableRooms() {
		return roomRepository.getAvailableRooms();
	}

	public RoomsContent getRoomsContent(int pageNumber, int numberOfPages) {
		RoomsContent roomsContent = new RoomsContent();
		List<RoomsContent.Page> pages = new ArrayList<>();
		for (int currentPage = 1; currentPage <= numberOfPages; currentPage++) {
			RoomsContent.Page page = new RoomsContent.Page();
			page.setName("" + currentPage);
			String pageClass = "page-item";
			if (currentPage == pageNumber) {
				pageClass += " active";
			}
			page.setPageClass(pageClass);
			pages.add(page);
		}
		roomsContent.setPages(pages);
		return roomsContent;
	}

	@Override
	public int getNumberOfPages() {
		List<Room> rooms = getAllRooms();
		return (int) Math.ceil((double) rooms.size() / RoomsContent.NUMBER_OF_ROOMS_ON_PAGE);
	}

	@Override
	public List<Room> getRoomsOnPage(int pageNumber, SortMethod method) {
		int offset = (pageNumber - 1) * RoomsContent.NUMBER_OF_ROOMS_ON_PAGE;
		int limit = RoomsContent.NUMBER_OF_ROOMS_ON_PAGE;
		List<Room> rooms;
		if (SortMethod.PRICE.equals(method)) {
			rooms = roomRepository.getRoomsOrderedByPrice(limit, offset);
		} else if (SortMethod.SPACE.equals(method)) {
			rooms = roomRepository.getRoomsOrderedBySpace(limit, offset);
		} else if (SortMethod.CLASS.equals(method)) {
			rooms = roomRepository.getRoomsOrderedByClass(limit, offset);
		} else {
			rooms = roomRepository.getRoomsOrderedByStatus(limit, offset);
		}
		return rooms;
	}
}
