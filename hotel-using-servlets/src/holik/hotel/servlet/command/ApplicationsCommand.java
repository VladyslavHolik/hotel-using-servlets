package holik.hotel.servlet.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import holik.hotel.servlet.model.Application;
import holik.hotel.servlet.service.ApplicationService;
import holik.hotel.servlet.service.impl.DefaultApplicationService;

public class ApplicationsCommand implements Command {
	private ApplicationService applicationService;
	
	public ApplicationsCommand() {
		applicationService = new DefaultApplicationService();
	}
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		List<Application> applications = applicationService.getAllApplications();
		request.setAttribute("applications", applications);
		return "WEB-INF/applications.jsp";
	}

	@Override
	public String toString() {
		return "Applications Command";
	}
}
