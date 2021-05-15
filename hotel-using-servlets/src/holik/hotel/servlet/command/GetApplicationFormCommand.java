package holik.hotel.servlet.command;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import holik.hotel.servlet.model.Application;
import holik.hotel.servlet.model.Room;
import holik.hotel.servlet.model.RoomStatus;
import holik.hotel.servlet.model.User;
import holik.hotel.servlet.path.Path;
import holik.hotel.servlet.service.ApplicationService;
import holik.hotel.servlet.service.RoomService;
import holik.hotel.servlet.service.UserService;
import holik.hotel.servlet.service.impl.DefaultApplicationService;
import holik.hotel.servlet.service.impl.DefaultRoomService;
import holik.hotel.servlet.service.impl.DefaultUserService;

public final class GetApplicationFormCommand implements Command {
	private ApplicationService applicationService;
	private UserService userService;
	private RoomService roomService;
	
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
		String forward = Path.PAGE__ERROR_PAGE;
		
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
		List<Room> rooms = roomService.getSpecificRooms(application.getRoomClass().getId(),
				application.getSpace(), RoomStatus.FREE.getId());
		
		User user = userOptional.get();
		request.setAttribute("user", user);
		request.setAttribute("application", application);
		request.setAttribute("rooms", rooms);
		return "WEB-INF/applicationForm.jsp";
	}

}
