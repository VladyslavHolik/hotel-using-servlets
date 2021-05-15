package holik.hotel.servlet.command;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetApplicationCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		request.setAttribute("minTime", LocalDateTime.now().plusDays(1).toString().substring(0, 16));
		return "WEB-INF/application.jsp";
	}

}
