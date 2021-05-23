package holik.hotel.servlet.web.command;

import holik.hotel.servlet.web.validator.LanguageValidator;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;

/**
 * Command that sets preferred language for interface.
 */
public class LanguageCommand implements Command {
	private static final Logger LOG = Logger.getLogger(LanguageCommand.class);
	private final LanguageValidator languageValidator;

	public LanguageCommand(LanguageValidator languageValidator) {
		this.languageValidator = languageValidator;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String languageToSet = request.getParameter("lang");
		languageValidator.validateLanguage(languageToSet);

		HttpSession session = request.getSession();
		Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", languageToSet);		
		LOG.debug("User changes locale to " + languageToSet);
		return "redirect:/";
	}

	@Override
	public String toString() {
		return "LanguageCommand";
	}
}
