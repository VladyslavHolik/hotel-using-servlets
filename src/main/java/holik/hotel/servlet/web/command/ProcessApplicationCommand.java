package holik.hotel.servlet.web.command;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import holik.hotel.servlet.web.command.constant.Pages;
import holik.hotel.servlet.repository.model.Application;
import holik.hotel.servlet.repository.model.ApplicationStatus;
import holik.hotel.servlet.service.ApplicationService;
import holik.hotel.servlet.service.impl.DefaultApplicationService;

/**
 * Command that processes manager's choice for application.
 */
public class ProcessApplicationCommand implements Command {
	private static final Logger LOG = Logger.getLogger(ProcessApplicationCommand.class);
	private final ApplicationService applicationService;
	
	public ProcessApplicationCommand() {
		applicationService = new DefaultApplicationService();
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		int id = Integer.parseInt(request.getParameter("applicationid"));
		String choice = request.getParameter("choice");

		String errorMessage = null;
		String forward = Pages.PAGE_ERROR_PAGE;

		if (choice == null || choice.isEmpty()) {
			errorMessage = "Invalid choice " + choice;
			LOG.debug(errorMessage);
			request.setAttribute("errorMessage", errorMessage);
			return forward;
		}

		Optional<Application> optionalApplication = applicationService.getApplicationById(id);
		if (optionalApplication.isPresent()) {
			Application application = optionalApplication.get();
			processApplication(choice, application);
		}
		return "redirect:applications";
	}

	private void processApplication(String choice, Application application) {
		if ("decline".equals(choice)) {
			application.setStatus(ApplicationStatus.DECLINED);
		} else {
			int roomId = Integer.parseInt(choice);
			application.setRoomId(roomId);
			application.setStatus(ApplicationStatus.APPROVED);
		}
		applicationService.updateApplication(application);
	}
}
