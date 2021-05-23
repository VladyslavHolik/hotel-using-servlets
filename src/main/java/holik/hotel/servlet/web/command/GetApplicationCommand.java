package holik.hotel.servlet.web.command;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import holik.hotel.servlet.web.command.constant.Pages;

/**
 * Command that forward user to form for creating application.
 */
public class GetApplicationCommand implements Command {
	private static final long DAYS_PERIOD = 1;
	private static final int TIME_OFFSET = 16;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String minTime = LocalDateTime.now().plusDays(DAYS_PERIOD).toString().substring(0, TIME_OFFSET);
		request.setAttribute("minTime", minTime);
		return Pages.PAGE_APPLICATION;
	}
}
