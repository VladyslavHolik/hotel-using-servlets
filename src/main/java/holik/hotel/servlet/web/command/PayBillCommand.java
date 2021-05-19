package holik.hotel.servlet.web.command;

import holik.hotel.servlet.repository.model.Application;
import holik.hotel.servlet.repository.model.ApplicationStatus;
import holik.hotel.servlet.service.ApplicationService;
import holik.hotel.servlet.web.context.ApplicationContext;
import holik.hotel.servlet.web.validator.ApplicationValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Command that is responsible for paying bill.
 */
public class PayBillCommand implements Command {
    private final ApplicationService applicationService;
    private final ApplicationValidator applicationValidator;

    public PayBillCommand() {
        applicationService = ApplicationContext.getApplicationService();
        applicationValidator = ApplicationContext.getApplicationValidator();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("userId");
        int applicationId = Integer.parseInt(request.getParameter("id"));

        applicationValidator.validateForPaying(applicationId, userId);

        Application application = applicationService.getApplicationById(applicationId).orElseThrow();

        application.setStatus(ApplicationStatus.PAID);
        applicationService.updateApplication(application);

        return "redirect:/home";
    }

}
