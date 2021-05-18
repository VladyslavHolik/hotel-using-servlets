package holik.hotel.servlet.web.command;

import holik.hotel.servlet.service.ApplicationService;
import holik.hotel.servlet.web.context.ApplicationContext;
import holik.hotel.servlet.web.validator.ApplicationValidator;
import holik.hotel.servlet.web.validator.ChoiceValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Command that processes manager's choice for application.
 */
public class ProcessApplicationCommand implements Command {
	private final ApplicationService applicationService;
	private final ChoiceValidator choiceValidator;
	private final ApplicationValidator applicationValidator;

	public ProcessApplicationCommand() {
		applicationService = ApplicationContext.getApplicationService();
		choiceValidator = ApplicationContext.getChoiceValidator();
		applicationValidator = ApplicationContext.getApplicationValidator();
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		int id = Integer.parseInt(request.getParameter("applicationId"));
		String choice = request.getParameter("choice");
		choiceValidator.validateChoice(choice);
		applicationValidator.validateApplicationId(id);

		applicationService.processApplication(id, choice);
		return "redirect:applications";
	}
}
