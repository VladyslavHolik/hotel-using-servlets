package holik.hotel.servlet.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Command that invalidates user session.
 */
public class LogoutCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		
		return "redirect:home";
	}

	@Override
	public String toString() {
		return "LogoutCommand";
	}
}
