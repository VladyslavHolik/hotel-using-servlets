package holik.hotel.servlet.web.filter;

import holik.hotel.servlet.repository.model.Role;
import holik.hotel.servlet.web.command.access.AccessManager;
import holik.hotel.servlet.web.context.ApplicationContext;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AccessFilter implements Filter {
	private static final Logger LOG = Logger.getLogger(AccessFilter.class);
	private AccessManager accessManager;

	@Override
	public void destroy() {
		LOG.debug("Filter destroy");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String uri = httpRequest.getRequestURI();

		HttpSession session = httpRequest.getSession(false);
		Role role = null;
		if (session != null) {
			role = (Role) session.getAttribute("userRole");
		}

		if (accessManager.isAccessAllowed(uri, role)) {
			chain.doFilter(request, response);
		} else {
			LOG.debug("Unauthorized user requested access");
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
		}
	}

	@Override
	public void init(FilterConfig config) {
		accessManager = ApplicationContext.getAccessManager();
	}
}
