package holik.hotel.servlet.command;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import holik.hotel.servlet.model.Application;
import holik.hotel.servlet.model.RoomClass;
import holik.hotel.servlet.path.Path;
import holik.hotel.servlet.service.ApplicationService;
import holik.hotel.servlet.service.impl.DefaultApplicationService;

public class ApplicationCommand implements Command {
	private static final Logger LOG = Logger.getLogger(ApplicationCommand.class);
	private ApplicationService applicationService;
	
	public ApplicationCommand() {
		applicationService = new DefaultApplicationService();
	}
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String spaceString = request.getParameter("space");
		String roomClassIdString = request.getParameter("roomclass");
		String datetimeOfArrival = request.getParameter("arrival");
		String datetimeOfLeaving = request.getParameter("leaving");
		
		String errorMessage = null;		
		String forward = Path.PAGE__ERROR_PAGE;
		
		if (spaceString == null || spaceString.isEmpty()) {
			setInvalidSpaceError(request);
			return forward;
		}
		
		if (roomClassIdString == null || roomClassIdString.isEmpty()) {
			setInvalidRoomClassError(request);
			return forward;
		}
		
		if (datetimeOfArrival == null || datetimeOfArrival.isEmpty()) {
			errorMessage = "Invalid arrival date time";
			request.setAttribute("errorMessage", errorMessage);
			return forward;
		}
		
		if (datetimeOfLeaving == null || datetimeOfLeaving.isEmpty()) {
			errorMessage = "Invalid leaving date time";
			request.setAttribute("errorMessage", errorMessage);
			return forward;
		}
		
		int space = Integer.parseInt(spaceString);
		int roomClassId = Integer.parseInt(roomClassIdString);
		LocalDateTime arrival = LocalDateTime.parse(datetimeOfArrival);
		LocalDateTime leaving = LocalDateTime.parse(datetimeOfLeaving);
		
		if (space > 6 || space < 1) {
			setInvalidSpaceError(request);
			return forward;
		}
		
		if (roomClassId > 11 || roomClassId < 1) {
			setInvalidRoomClassError(request);
			return forward;
		}
		
		Application application = new Application();
		application.setSpace(space);
		
		HttpSession session = request.getSession();
		application.setUserId((int) session.getAttribute("userId")); 
		
		application.setRoomClass(RoomClass.getRoomClassFromId(roomClassId));
		application.setDatetimeOfArrival(arrival);
		application.setDatetimeOfLeaving(leaving);
		
		try {
			applicationService.saveApplication(application);
		} catch (SQLException exception) {
			LOG.error("SQL Exception occurred while saving application " + exception.getLocalizedMessage());
			errorMessage = "Something went wrong";
			request.setAttribute("errorMessage", errorMessage);
			return forward;
		}
		
		return "redirect:home";
	}

	private void setInvalidSpaceError(HttpServletRequest request) {
		String errorMessage = "Invalid space";
		request.setAttribute("errorMessage", errorMessage);
	}
	
	private void setInvalidRoomClassError(HttpServletRequest request) {
		String errorMessage = "Invalid room class";
		request.setAttribute("errorMessage", errorMessage);
	}
}
