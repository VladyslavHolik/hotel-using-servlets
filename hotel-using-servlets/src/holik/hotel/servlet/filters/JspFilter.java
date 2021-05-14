package holik.hotel.servlet.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class JspFilter implements Filter {
	private static final Logger LOG = Logger.getLogger(JspFilter.class);
	
	public void destroy() {}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		LOG.info("JspFilter request");
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.sendRedirect("controller?command=home");
	}

	public void init(FilterConfig fConfig) throws ServletException {}
}
