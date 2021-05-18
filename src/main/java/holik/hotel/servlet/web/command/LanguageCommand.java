package holik.hotel.servlet.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;

import org.apache.log4j.Logger;

import holik.hotel.servlet.web.command.constant.Pages;

/**
 * Command that sets preferred language for interface.
 */
public class LanguageCommand implements Command {
	private static final Logger LOG = Logger.getLogger(LanguageCommand.class);
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String languageToSet = request.getParameter("lang");
		
		String errorMessage = null;		
		String forward = Pages.PAGE_ERROR_PAGE;
		
		if (languageToSet == null || !(languageToSet.equals("ru") || languageToSet.equals("en"))) {
			errorMessage = "Invalid locale";
			request.setAttribute("errorMessage", errorMessage);
			LOG.error(errorMessage);
			return forward;
		}
		
		HttpSession session = request.getSession();
		Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", languageToSet);		
		LOG.info("User changes locale to " + languageToSet);
		return "redirect:home";
	}

	@Override
	public String toString() {
		return "LanguageCommand";
	}
}