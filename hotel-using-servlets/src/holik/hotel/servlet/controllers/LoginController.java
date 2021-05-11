package holik.hotel.servlet.controllers;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import holik.hotel.servlet.hashing.Hasher;
import holik.hotel.servlet.models.User;
import holik.hotel.servlet.services.UserService;
import holik.hotel.servlet.services.impl.UserServiceImpl;

@WebServlet("/signin")
public class LoginController extends HttpServlet {

	private UserService userService;
	
	@Override
	public void init() {
		userService = new UserServiceImpl();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		Optional<User> userOptional = userService.getUserByEmail(email);
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			String salt = user.getSalt();
			String hash = user.getPasswordHash();
			if (Hasher.areHashesEqual(salt, hash, password)) {
				HttpSession session = request.getSession();
				session.setAttribute("user_id", user.getId());
				session.setAttribute("user_role_id", user.getRole().getId());
			}
		}
		response.sendRedirect("index.jsp");
	}

}
