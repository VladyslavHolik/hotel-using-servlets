package holik.hotel.servlet.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import holik.hotel.servlet.command.Command;
import holik.hotel.servlet.command.CommandManager;

public class MainController extends HttpServlet {
       
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		manage(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		manage(request, response);
	}
	
	private void manage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String commandName = request.getParameter("command");
		Command command = CommandManager.get(commandName);
		String forward = command.execute(request, response);
		if (forward != null && !forward.startsWith("redirect:")) {
			RequestDispatcher dispatcher = request.getRequestDispatcher(forward);
			dispatcher.forward(request, response);
		} else {
			response.sendRedirect(forward.substring(9));
		}
	}
}
