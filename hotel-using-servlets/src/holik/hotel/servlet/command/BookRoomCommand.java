package holik.hotel.servlet.command;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import holik.hotel.servlet.model.Application;
import holik.hotel.servlet.model.ApplicationStatus;
import holik.hotel.servlet.model.Room;
import holik.hotel.servlet.model.RoomStatus;
import holik.hotel.servlet.path.Path;
import holik.hotel.servlet.service.ApplicationService;
import holik.hotel.servlet.service.RoomService;
import holik.hotel.servlet.service.impl.DefaultApplicationService;
import holik.hotel.servlet.service.impl.DefaultRoomService;

public class BookRoomCommand implements Command {
	private ApplicationService applicationService;
	private RoomService roomService;
	
	public BookRoomCommand() {
		applicationService = new DefaultApplicationService();
		roomService = new DefaultRoomService();
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		int applicationId = Integer.parseInt(request.getParameter("id"));
		
		String errorMessage = null;
		String forward = Path.PAGE__ERROR_PAGE;
		
		Optional<Application> optionalApplication = applicationService.getApplicationById(applicationId);
		if (optionalApplication.isPresent()) {
			Application application = optionalApplication.get();
			int originUserId = application.getUserId();
			HttpSession session = request.getSession();
			int currentUserId = (int) session.getAttribute("userId");
			if (currentUserId == originUserId) {
				application.setStatus(ApplicationStatus.BOOKED);
				Optional<Room> optionalRoom = roomService.getRoomById(application.getRoomId());
				if (optionalRoom.isPresent()) {
					Room room = optionalRoom.get();
					room.setStatus(RoomStatus.BOOKED);
					roomService.updateRoom(room);
					application.setDatetimeOfBooking(LocalDateTime.now());
					applicationService.updateApplication(application);
				}
			} else {
				errorMessage = "You are not authorized to do this action";
				request.setAttribute("errorMessage", errorMessage);
				return forward;
			}
		}
		return "redirect:home";
	}

}
