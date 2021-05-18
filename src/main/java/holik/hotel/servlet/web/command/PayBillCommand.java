package holik.hotel.servlet.web.command;

import java.io.IOException;
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

/**
 * Command that is responsible for paying bill.
 */
public class PayBillCommand implements Command {
	private final ApplicationService applicationService;

	public PayBillCommand() {
		applicationService = ApplicationContext.getApplicationService();
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		HttpSession session = request.getSession();
		int userId = (int) session.getAttribute("userId");
		int applicationId = Integer.parseInt(request.getParameter("id"));

		String errorMessage = null;
		String forward = Pages.PAGE_ERROR_PAGE;

		Optional<Application> optionalApplication = applicationService.getApplicationById(applicationId);
		if (optionalApplication.isPresent()) {
			Application application = optionalApplication.get();
			if (userId == application.getUserId() && ApplicationStatus.BOOKED.equals(application.getStatus())) {
				application.setStatus(ApplicationStatus.PAID);
				applicationService.updateApplication(application);
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
