package holik.hotel.servlet.command;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
 * Command that forwards user to his applications.
 */
public class GetMyApplications implements Command {
	private ApplicationService applicationService;

	public GetMyApplications() {
		applicationService = new DefaultApplicationService();
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		List<Application> userApprovedApplications = getApprovedApplications(request);
		request.setAttribute("applications", userApprovedApplications);
		return Path.PAGE__MY_APPLICATIONS;
	}

	private List<Application> getApprovedApplications(HttpServletRequest request) {
		List<Application> allAplications = applicationService.getAllApplications();
		List<Application> approvedApplications = new ArrayList<>();
		HttpSession session = request.getSession();
		int userId = (int) session.getAttribute("userId");
		for (Application application : allAplications) {
			if (application.getUserId() == userId && ApplicationStatus.APPROVED.equals(application.getStatus())
					&& isAvailable(application)) {
				approvedApplications.add(application);
			}
		}
		return approvedApplications;
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
