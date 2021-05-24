package holik.hotel.servlet.web.filter;

import holik.hotel.servlet.repository.model.Role;
import holik.hotel.servlet.web.command.constant.Pages;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * Filter that manages access for all commands;
 */
public class CommandAccessFilter implements Filter {
	private static final Logger LOG = Logger.getLogger(CommandAccessFilter.class);

	private static List<String> commons = new ArrayList<>();
	private static List<String> outOfControl = new ArrayList<>();
	private static final Map<Role, List<String>> accessMap = new HashMap<>();

	@Override
	public void destroy() {
		LOG.debug("Filter destroy");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (isAccessAllowed(request)) {
			chain.doFilter(request, response);
		} else {
			String errorMessage = "You do not have permission to access the requested resource";

			request.setAttribute("errorMessage", errorMessage);
			LOG.debug("Unauthorized user requested access");

			HttpServletResponse httpResponse = (HttpServletResponse) response;
			// TODO 
			//httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
			request.getRequestDispatcher(Pages.PAGE_ERROR_PAGE).forward(request, response);
		}
	}

	private boolean isAccessAllowed(ServletRequest request) {
		HttpServletRequest httpRequest = (HttpServletRequest) request;

		String commandName = null;
		if ("POST".equals(httpRequest.getMethod())) {
			commandName = httpRequest.getParameter("command");
		} else if ("GET".equals(httpRequest.getMethod())) {
			commandName = httpRequest.getRequestURI();
		}
		
		if (commandName == null || commandName.isEmpty())
			return false;

		if (outOfControl.contains(commandName))
			return true;

		HttpSession session = httpRequest.getSession(false);
		if (session == null)
			return false;

		Role userRole = (Role) session.getAttribute("userRole");
		if (userRole == null)
			return false;

		return accessMap.get(userRole).contains(commandName) || commons.contains(commandName);
	}
	
	@Override
	public void init(FilterConfig config) {
		// TODO
		accessMap.put(Role.MANAGER, asList(config.getInitParameter("manager")));
		accessMap.put(Role.USER, asList(config.getInitParameter("user")));
		LOG.debug("Access map - " + accessMap);

		commons = asList(config.getInitParameter("common"));
		LOG.debug("Commons - " + commons);

		outOfControl = asList(config.getInitParameter("out-of-control"));
		LOG.debug("OutOfControl - " + outOfControl);
	}

	private List<String> asList(String string) {
		List<String> list = new ArrayList<>();
		StringTokenizer tokenizer = new StringTokenizer(string);
		while (tokenizer.hasMoreTokens())
			list.add(tokenizer.nextToken());
		return list;
	}
}
