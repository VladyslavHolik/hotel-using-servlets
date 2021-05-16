package holik.hotel.servlet.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import holik.hotel.servlet.model.Role;
import holik.hotel.servlet.path.Path;
import holik.hotel.servlet.path.PathParser;

/**
 * Filter that manages access for all commands;
 */
public class CommandAccessFilter implements Filter {
	private static final Logger LOG = Logger.getLogger(CommandAccessFilter.class);

	private static List<String> commons = new ArrayList<String>();
	private static List<String> outOfControl = new ArrayList<String>();
	private static Map<Role, List<String>> accessMap = new HashMap<Role, List<String>>();

	@Override
	public void destroy() {
		LOG.info("Filter destroy");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (accessAllowed(request)) {
			chain.doFilter(request, response);
		} else {
			String errorMessasge = "You do not have permission to access the requested resource";

			request.setAttribute("errorMessage", errorMessasge);
			LOG.info("Unauthorized user requested access");

			request.getRequestDispatcher(Path.PAGE__ERROR_PAGE).forward(request, response);
		}
	}

	private boolean accessAllowed(ServletRequest request) {
		HttpServletRequest httpRequest = (HttpServletRequest) request;

		String commandName = null;
		if ("POST".equals(httpRequest.getMethod())) {
			commandName = httpRequest.getParameter("command");
		} else if ("GET".equals(httpRequest.getMethod())) {
			commandName = PathParser.getCommand(httpRequest.getRequestURI());
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
	public void init(FilterConfig config) throws ServletException {
		accessMap.put(Role.MANAGER, asList(config.getInitParameter("manager")));
		accessMap.put(Role.USER, asList(config.getInitParameter("user")));
		LOG.debug("Access map - " + accessMap);

		commons = asList(config.getInitParameter("common"));
		LOG.debug("Commons - " + commons);

		outOfControl = asList(config.getInitParameter("out-of-control"));
		LOG.debug("OutOfControl - " + outOfControl);
	}

	private List<String> asList(String string) {
		List<String> list = new ArrayList<String>();
		StringTokenizer tokenizer = new StringTokenizer(string);
		while (tokenizer.hasMoreTokens())
			list.add(tokenizer.nextToken());
		return list;
	}
}
