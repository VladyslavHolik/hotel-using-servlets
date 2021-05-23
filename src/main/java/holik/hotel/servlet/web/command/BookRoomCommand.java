package holik.hotel.servlet.web.command;

import holik.hotel.servlet.repository.model.Application;
import holik.hotel.servlet.service.ApplicationService;
import holik.hotel.servlet.web.validator.ApplicationValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Command that is responsible for booking room.
 */
public class BookRoomCommand implements Command {
	private final ApplicationService applicationService;
	private final ApplicationValidator applicationValidator;

	public BookRoomCommand(ApplicationService applicationService, ApplicationValidator applicationValidator) {
		this.applicationService = applicationService;
		this.applicationValidator = applicationValidator;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		int applicationId = Integer.parseInt(request.getParameter("id"));

		HttpSession session = request.getSession();
		int userId = (int) session.getAttribute("userId");

		applicationValidator.validateForBooking(applicationId, userId);
		Application application = applicationService.getApplicationById(applicationId).orElseThrow();
		applicationService.bookRoom(application);

		return "redirect:/";
	}
}
