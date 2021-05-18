package holik.hotel.servlet.web.command;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import holik.hotel.servlet.web.command.constant.Pages;
import holik.hotel.servlet.repository.model.Application;
import holik.hotel.servlet.repository.model.ApplicationStatus;
import holik.hotel.servlet.service.ApplicationService;
import holik.hotel.servlet.service.impl.DefaultApplicationService;
import holik.hotel.servlet.web.context.ApplicationContext;
import holik.hotel.servlet.web.validator.ApplicationValidator;

/**
 * Command that is responsible for booking room.
 */
public class BookRoomCommand implements Command {
	private final ApplicationService applicationService;
	private final ApplicationValidator applicationValidator;

	public BookRoomCommand() {
		applicationService = ApplicationContext.getApplicationService();
		applicationValidator = ApplicationContext.getApplicationValidator();
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

		return "redirect:home";
	}
}
