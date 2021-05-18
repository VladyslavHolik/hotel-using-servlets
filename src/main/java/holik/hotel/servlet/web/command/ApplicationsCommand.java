package holik.hotel.servlet.web.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import holik.hotel.servlet.web.command.constant.Pages;
import holik.hotel.servlet.repository.model.Application;
import holik.hotel.servlet.repository.model.ApplicationStatus;
import holik.hotel.servlet.service.ApplicationService;
import holik.hotel.servlet.service.impl.DefaultApplicationService;

/**
 * Command that is responsible for forwarding user to applications page.
 */
public class ApplicationsCommand implements Command {
	private final ApplicationService applicationService;
	
	public ApplicationsCommand() {
		applicationService = new DefaultApplicationService();
	}
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		List<Application> allApplications = applicationService.getAllApplications();
		List<Application> requestedApplications = new ArrayList<>();
		for (Application application : allApplications) {
			if (application.getStatus().equals(ApplicationStatus.REQUESTED)) {
				requestedApplications.add(application);
			}
		}
		request.setAttribute("applications", requestedApplications);
		return Pages.PAGE_APPLICATIONS;
	}

	@Override
	public String toString() {
		return "Applications Command";
	}
}