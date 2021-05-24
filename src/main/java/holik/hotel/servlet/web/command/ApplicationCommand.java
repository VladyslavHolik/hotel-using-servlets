package holik.hotel.servlet.web.command;

import holik.hotel.servlet.repository.model.Application;
import holik.hotel.servlet.repository.model.ApplicationStatus;
import holik.hotel.servlet.repository.model.RoomClass;
import holik.hotel.servlet.service.ApplicationService;
import holik.hotel.servlet.web.validator.ApplicationValidator;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Command that is responsible for creating application.
 */
public class ApplicationCommand implements Command {
	private static final Logger LOG = Logger.getLogger(ApplicationCommand.class);
	private final ApplicationService applicationService;
	private final ApplicationValidator applicationValidator;

	public ApplicationCommand(ApplicationService applicationService, ApplicationValidator applicationValidator) {
		this.applicationService = applicationService;
		this.applicationValidator = applicationValidator;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		int space = Integer.parseInt(request.getParameter("space"));
		int roomClassId = Integer.parseInt(request.getParameter("roomClass"));
		LocalDateTime arrival = LocalDateTime.parse(request.getParameter("arrival"));
		LocalDateTime leaving = LocalDateTime.parse(request.getParameter("leaving"));

		Application application = getApplication(request, space, roomClassId, arrival, leaving);
		applicationValidator.validate(application);

		LOG.debug("Saving application " + application);
		applicationService.saveApplication(application);

		return "redirect:/";
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

	@Override
	public String toString() {
		return "ApplicationCommand";
	}
}
