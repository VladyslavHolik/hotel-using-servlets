package holik.hotel.servlet.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import holik.hotel.servlet.models.User;
import holik.hotel.servlet.services.UserService;
import holik.hotel.servlet.services.impl.UserServiceImpl;

@WebServlet("/signup")
public final class RegistrationController extends HttpServlet {

	private UserService userService;
	
	@Override
	public void init() {
		userService = new UserServiceImpl();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String firstName = request.getParameter("first_name");
		String lastName = request.getParameter("last_name");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		String passwordHash = request.getParameter("password");
		
		User user = new User();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setPhone(phone);
		user.setEmail(email);
		user.setPasswordHash(passwordHash);
		
		userService.createUser(user);
		response.sendRedirect("index.jsp");
	}

}
