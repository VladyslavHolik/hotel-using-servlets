package holik.hotel.servlet.web.command;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import holik.hotel.servlet.web.command.constant.Pages;
import holik.hotel.servlet.web.command.sort.SortMethod;
import holik.hotel.servlet.web.dto.RoomsContent;
import holik.hotel.servlet.repository.model.Application;
import holik.hotel.servlet.repository.model.ApplicationStatus;
import holik.hotel.servlet.repository.model.Room;
import holik.hotel.servlet.repository.model.RoomAvailability;
import holik.hotel.servlet.repository.model.RoomStatus;
import holik.hotel.servlet.service.ApplicationService;
import holik.hotel.servlet.service.RoomService;
import holik.hotel.servlet.service.impl.DefaultApplicationService;
import holik.hotel.servlet.service.impl.DefaultRoomService;

/**
 * Command that forward user to rooms page.
 */
public class RoomsCommand implements Command {
	private static final Logger LOG = Logger.getLogger(RoomsCommand.class);
	private final RoomService roomService;
	private final ApplicationService applicationService;

	public RoomsCommand() {
		roomService = new DefaultRoomService();
		applicationService = new DefaultApplicationService();
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String pageNumberString = request.getParameter("page");
		if (pageNumberString == null) {
			pageNumberString = "1";
		}
		int pageNumber = Integer.parseInt(pageNumberString);

		String errorMessage = null;
		String forward = Pages.PAGE_ERROR_PAGE;

		List<Room> allRooms = roomService.getAllRooms();
		if (allRooms == null) {
			errorMessage = "Something went wrong";
			request.setAttribute("errorMessage", errorMessage);
			LOG.error("Nothing returned while getting all rooms");
			return forward;
		}
		
		List<Room> availableRooms = getAvailableRooms(allRooms);
		
		SortMethod method = getSortMethod(request);
		sort(availableRooms, method);

		int numberOfPages = (int) Math.ceil(availableRooms.size() / 4.0);

		if (pageNumber > numberOfPages || pageNumber < 1) {
			errorMessage = "Incorrect page number";
			request.setAttribute("errorMessage", errorMessage);
			return forward;
		}

		RoomsContent roomsContent = getRoomsContent(pageNumber, numberOfPages);
		List<Room> roomsOnPage = getRoomsOnPage(pageNumber, availableRooms);

		roomsContent.setRooms(roomsOnPage);
		request.setAttribute("roomsContent", roomsContent);
		return Pages.PAGE_ROOMS;
	}

	private List<Room> getRoomsOnPage(int pageNumber, List<Room> availableRooms) {
		int startRoomIndex = (pageNumber - 1) * 4;
		int endRoomIndex = startRoomIndex + 3 > availableRooms.size() - 1 ? availableRooms.size() - 1 : startRoomIndex + 3;
		List<Room> roomsOnPage = new ArrayList<>();
		for (int i = startRoomIndex; i <= endRoomIndex; i++) {
			roomsOnPage.add(availableRooms.get(i));
		}
		return roomsOnPage;
	}

	private RoomsContent getRoomsContent(int pageNumber, int numberOfPages) {
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

	private SortMethod getSortMethod(HttpServletRequest request) {
		SortMethod method;
		HttpSession session = request.getSession();
		Object methodObject = session.getAttribute("sort");
		if (methodObject == null) {
			method = SortMethod.CLASS;
		} else {
			method = (SortMethod) methodObject;
		}
		return method;
	}

	private List<Room> getAvailableRooms(List<Room> allRooms) {
		List<Room> availableRooms = new ArrayList<>();
		for (Room room : allRooms) {
			if (RoomAvailability.AVAILABLE.equals(room.getAvailability())) {
				room.setRoomStatus(getRoomStatus(room));
				availableRooms.add(room);
			}
		}
		return availableRooms;
	}

	private void sort(List<Room> rooms, SortMethod method) {
		method.sort(rooms);
	}
	
	private RoomStatus getRoomStatus(Room room) {
		RoomStatus result = RoomStatus.FREE;
		int roomId = room.getId();
		List<Application> allApplications = applicationService.getAllApplications();
		LocalDateTime now = LocalDateTime.now();
		for (Application application : allApplications) {
			if (application.getRoomId() == roomId && now.isAfter(application.getDatetimeOfArrival())
					&& now.isBefore(application.getDatetimeOfLeaving())) {
				if (application.getDatetimeOfBooking() != null) {
					if (ApplicationStatus.BOOKED.equals(application.getStatus())) {
						result = RoomStatus.BOOKED;
					} else {
						result = RoomStatus.BUSY;
					}
					break;
				}
			}
		}
		return result;
	}

	@Override
	public String toString() {
		return "RoomsCommand";
	}
}
