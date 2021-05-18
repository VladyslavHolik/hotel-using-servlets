package holik.hotel.servlet.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Command interface for command pattern.
 */
public interface Command {
	String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
}
