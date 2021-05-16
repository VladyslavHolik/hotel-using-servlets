package holik.hotel.servlet.command;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import holik.hotel.servlet.model.Application;
import holik.hotel.servlet.model.ApplicationStatus;
import holik.hotel.servlet.path.Path;
import holik.hotel.servlet.service.ApplicationService;
import holik.hotel.servlet.service.impl.DefaultApplicationService;

/**
 * Command that is responsible for booking room.
 */
public class BookRoomCommand implements Command {
	private ApplicationService applicationService;

	public BookRoomCommand() {
		applicationService = new DefaultApplicationService();
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
			
			LocalDateTime datetimeOfLeaving = application.getDatetimeOfLeaving();
			LocalDateTime now = LocalDateTime.now();
			
			if (datetimeOfLeaving.isBefore(now)) {
				errorMessage = "That application is invalid due to exprire";
				request.setAttribute("errorMessage", errorMessage);
				return forward;
			}
			
			int originUserId = application.getUserId();
			HttpSession session = request.getSession();
			int currentUserId = (int) session.getAttribute("userId");
			if (currentUserId == originUserId) {
				if (isAvailable(application)) {
					bookRoom(application);
				} else {
					errorMessage = "Sorry, that room is already booked";
					request.setAttribute("errorMessage", errorMessage);
					return forward;
				}
			} else {
				errorMessage = "You are not authorized to do this action";
				request.setAttribute("errorMessage", errorMessage);
				return forward;
			}
		}
		return "redirect:home";
	}

	private void bookRoom(Application application) {
		application.setStatus(ApplicationStatus.BOOKED);
		application.setDatetimeOfBooking(LocalDateTime.now());
		applicationService.updateApplication(application);
	}

	private boolean isAvailable(Application application) {
		boolean result = true;
		LocalDateTime datetimeOfArrival = application.getDatetimeOfArrival();
		LocalDateTime datetimeOfLeaving = application.getDatetimeOfLeaving();
		
		List<Application> allApplications = applicationService.getAllApplications();
		for (Application originApplication : allApplications) {
			if ((originApplication.getStatus().equals(ApplicationStatus.PAID)
					|| originApplication.getStatus().equals(ApplicationStatus.BOOKED))
					&& originApplication.getRoomId() == application.getRoomId()
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
