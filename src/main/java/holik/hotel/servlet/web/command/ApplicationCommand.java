package holik.hotel.servlet.web.command;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import holik.hotel.servlet.web.validator.ApplicationValidator;
import org.apache.log4j.Logger;

import holik.hotel.servlet.repository.model.Application;
import holik.hotel.servlet.repository.model.ApplicationStatus;
import holik.hotel.servlet.repository.model.RoomClass;
import holik.hotel.servlet.service.ApplicationService;
import holik.hotel.servlet.service.impl.DefaultApplicationService;

/**
 * Command that is responsible for creating application.
 */
public class ApplicationCommand implements Command {
	private static final Logger LOG = Logger.getLogger(ApplicationCommand.class);
	private final ApplicationService applicationService;

	public ApplicationCommand() {
		applicationService = new DefaultApplicationService();
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		int space = getInt(request.getParameter("space"));
		int roomClassId = getInt(request.getParameter("roomClass"));
		LocalDateTime arrival = LocalDateTime.parse(request.getParameter("arrival"));
		LocalDateTime leaving = LocalDateTime.parse(request.getParameter("leaving"));

		Application application = getApplication(request, space, roomClassId, arrival, leaving);
		ApplicationValidator.validate(application);

		LOG.debug("Saving application " + application);
		applicationService.saveApplication(application);

		return "redirect:home";
	}

	private Application getApplication(HttpServletRequest request, int space, int roomClassId, LocalDateTime arrival, LocalDateTime leaving) {
		Application application = new Application();
		HttpSession session = request.getSession();
		application.setUserId((int) session.getAttribute("userId"));
		application.setSpace(space);
		application.setRoomClass(RoomClass.getRoomClassFromId(roomClassId));
		application.setDatetimeOfArrival(arrival);
		application.setDatetimeOfLeaving(leaving);
		application.setStatus(ApplicationStatus.REQUESTED);
		return application;
	}

	private int getInt(String string) {
		return Integer.parseInt(string);
	}
}
