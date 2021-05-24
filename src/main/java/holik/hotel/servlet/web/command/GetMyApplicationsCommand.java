package holik.hotel.servlet.web.command;

import holik.hotel.servlet.repository.model.Application;
import holik.hotel.servlet.service.ApplicationService;
import holik.hotel.servlet.web.command.constant.Pages;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Command that forwards user to his applications.
 */
public class GetMyApplicationsCommand implements Command {
	private final ApplicationService applicationService;

	public GetMyApplicationsCommand(ApplicationService applicationService) {
		this.applicationService = applicationService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		HttpSession session = request.getSession();
		int userId = (int) session.getAttribute("userId");

		List<Application> readyToBookApplications = applicationService.getReadyToBookApplications(userId);
		request.setAttribute("applications", readyToBookApplications);
		return Pages.PAGE_MY_APPLICATIONS;
	}

	@Override
	public String toString() {
		return "GetMyApplicationsCommand";
	}
}
