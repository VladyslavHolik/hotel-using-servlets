package holik.hotel.servlet.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import holik.hotel.servlet.model.Application;
import holik.hotel.servlet.model.ApplicationStatus;
import holik.hotel.servlet.service.ApplicationService;
import holik.hotel.servlet.service.impl.DefaultApplicationService;

public class GetMyApplications implements Command {
	private ApplicationService applicationService;
	
	public GetMyApplications() {
		applicationService = new DefaultApplicationService();
	}
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		List<Application> allAplications = applicationService.getAllApplications();
		List<Application> userApprovedAplications = new ArrayList<>();
		HttpSession session = request.getSession();
		int userId = (int) session.getAttribute("userId");
		for (Application application : allAplications) {
			if (application.getUserId() == userId && 
					ApplicationStatus.APPROVED.equals(application.getStatus())) {
				userApprovedAplications.add(application);
			}
		}
		request.setAttribute("applications", userApprovedAplications);
		return "WEB-INF/myapplications.jsp";
	}

}
