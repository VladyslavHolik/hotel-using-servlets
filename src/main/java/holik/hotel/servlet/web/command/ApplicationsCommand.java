package holik.hotel.servlet.web.command;

import holik.hotel.servlet.repository.model.Application;
import holik.hotel.servlet.repository.model.ApplicationStatus;
import holik.hotel.servlet.service.ApplicationService;
import holik.hotel.servlet.web.command.constant.Pages;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Command that is responsible for forwarding user to applications page.
 */
public class ApplicationsCommand implements Command {
	private final ApplicationService applicationService;
	
	public ApplicationsCommand(ApplicationService applicationService) {
		this.applicationService = applicationService;
	}
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		List<Application> requestedApplications = applicationService.getApplicationsByStatus(ApplicationStatus.REQUESTED);
		request.setAttribute("applications", requestedApplications);
		return Pages.PAGE_APPLICATIONS;
	}

	@Override
	public String toString() {
		return "Applications Command";
	}
}
