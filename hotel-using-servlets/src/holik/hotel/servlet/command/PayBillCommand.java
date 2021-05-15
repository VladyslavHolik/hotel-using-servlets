package holik.hotel.servlet.command;

import java.io.IOException;
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

public class PayBillCommand implements Command {
	private ApplicationService applicationService;
	private RoomService roomService;
	
	public PayBillCommand() {
		applicationService = new DefaultApplicationService();
		roomService = new DefaultRoomService();
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		HttpSession session = request.getSession();
		int userId = (int) session.getAttribute("userId");
		int applicationId = Integer.parseInt(request.getParameter("id"));

		String errorMessage = null;
		String forward = Path.PAGE__ERROR_PAGE;
		
		Optional<Application> optionalApplication = applicationService.getApplicationById(applicationId);
		if (optionalApplication.isPresent()) {
			Application application = optionalApplication.get();
			if (userId == application.getUserId() && ApplicationStatus.BOOKED.equals(application.getStatus())) {
				application.setStatus(ApplicationStatus.PAID);
				Optional<Room> optionalRoom = roomService.getRoomById(application.getRoomId());
				if (optionalRoom.isPresent()) {
					Room room = optionalRoom.get();
					room.setStatus(RoomStatus.BUSY);
					roomService.updateRoom(room);
					applicationService.updateApplication(application);
				}
			} else {
				errorMessage = "Invalid request";
				request.setAttribute("errorMessage", errorMessage);
				return forward;
			}
		} else {
			errorMessage = "Application doesn't exist";
			request.setAttribute("errorMessage", errorMessage);
			return forward;
		}
		return "redirect:home";
	}

}
