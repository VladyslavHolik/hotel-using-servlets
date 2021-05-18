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
import holik.hotel.servlet.web.context.ApplicationContext;
import holik.hotel.servlet.web.validator.ApplicationValidator;

/**
 * Command that forward manager to page with application,
 * so that manager could decline or set room for it.
 */
public final class GetApplicationFormCommand implements Command {
	private final ApplicationService applicationService;
	private final UserService userService;
	private final ApplicationValidator applicationValidator;

	public GetApplicationFormCommand() {
		applicationService = ApplicationContext.getApplicationService();
		userService = ApplicationContext.getUserService();
		applicationValidator = ApplicationContext.getApplicationValidator();
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		int id = Integer.parseInt(request.getParameter("id"));
		applicationValidator.validateApplicationId(id);

		Application application = applicationService.getApplicationById(id).orElseThrow();
		List<Room> freeRooms = applicationService.getFreeRooms(application);
		User user = userService.getUserById(application.getUserId()).orElseThrow();

		request.setAttribute("user", user);
		request.setAttribute("application", application);
		request.setAttribute("rooms", freeRooms);
		return Pages.PAGE_APPLICATION_FORM;
	}
}
