package holik.hotel.servlet.web.command;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import holik.hotel.servlet.web.command.constant.Pages;
import holik.hotel.servlet.repository.model.Application;
import holik.hotel.servlet.repository.model.ApplicationStatus;
import holik.hotel.servlet.repository.model.Room;
import holik.hotel.servlet.repository.model.RoomAvailability;
import holik.hotel.servlet.repository.model.User;
import holik.hotel.servlet.service.ApplicationService;
import holik.hotel.servlet.service.RoomService;
import holik.hotel.servlet.service.UserService;
import holik.hotel.servlet.service.impl.DefaultApplicationService;
import holik.hotel.servlet.service.impl.DefaultRoomService;
import holik.hotel.servlet.service.impl.DefaultUserService;

/**
 * Command that forward manager to page with application,
 * so that manager could decline or set room for it.
 */
public final class GetApplicationFormCommand implements Command {
	private final ApplicationService applicationService;
	private final UserService userService;
	private final RoomService roomService;

	public GetApplicationFormCommand() {
		applicationService = new DefaultApplicationService();
		userService = new DefaultUserService();
		roomService = new DefaultRoomService();
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		int id = Integer.parseInt(request.getParameter("id"));
		Optional<Application> applicationOptional = applicationService.getApplicationById(id);

		String errorMessage = null;
		String forward = Pages.PAGE_ERROR_PAGE;

		if (applicationOptional.isEmpty()) {
			errorMessage = "Application with this id doesn't exist";
			request.setAttribute("errorMessage", errorMessage);
			return forward;
		}

		Application application = applicationOptional.get();
		Optional<User> userOptional = userService.getUserById(application.getUserId());

		if (userOptional.isEmpty()) {
			errorMessage = "User with this id doesn't exist";
			request.setAttribute("errorMessage", errorMessage);
			return forward;
		}

		List<Room> availableRooms = getAvailableRooms(application);

		User user = userOptional.get();
		request.setAttribute("user", user);
		request.setAttribute("application", application);
		request.setAttribute("rooms", availableRooms);
		return Pages.PAGE_APPLICATION_FORM;
	}

	private List<Room> getAvailableRooms(Application application) {
		List<Room> allRooms = roomService.getAllRooms();
		List<Room> availableRooms = new ArrayList<>();

		LocalDateTime datetimeOfLeaving = application.getDatetimeOfLeaving();
		LocalDateTime now = LocalDateTime.now();
		
		if (!datetimeOfLeaving.isBefore(now)) {
			for (Room room : allRooms) {
				if (RoomAvailability.AVAILABLE.equals(room.getAvailability()) && room.getRoomClass().equals(application.getRoomClass())
						&& room.getSpace() == application.getSpace() && isAvailable(room, application)) {
					availableRooms.add(room);
				}
			}
		}
		return availableRooms;
	}

	private boolean isAvailable(Room room, Application application) {
		boolean result = true;
		LocalDateTime datetimeOfArrival = application.getDatetimeOfArrival();
		LocalDateTime datetimeOfLeaving = application.getDatetimeOfLeaving();
		
		List<Application> allApplications = applicationService.getAllApplications();
		for (Application originApplication : allApplications) {
			if ((originApplication.getStatus().equals(ApplicationStatus.PAID)
					|| originApplication.getStatus().equals(ApplicationStatus.BOOKED))
					&& originApplication.getRoomId() == room.getId()
					&& (isBetween(datetimeOfArrival, originApplication.getDatetimeOfArrival(),
							originApplication.getDatetimeOfLeaving())
							|| isBetween(datetimeOfLeaving, originApplication.getDatetimeOfArrival(),
									originApplication.getDatetimeOfLeaving()))) {
				result = false;
				break;
			}
		}
		return result;
	}

	private boolean isBetween(LocalDateTime origin, LocalDateTime start, LocalDateTime end) {
		return origin.isAfter(start) && origin.isBefore(end);
	}
}
