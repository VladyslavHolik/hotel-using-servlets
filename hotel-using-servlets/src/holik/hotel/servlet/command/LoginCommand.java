package holik.hotel.servlet.command;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import holik.hotel.servlet.model.User;
import holik.hotel.servlet.path.Path;
import holik.hotel.servlet.service.UserService;
import holik.hotel.servlet.service.impl.DefaultUserService;
import holik.hotel.servlet.util.DefaultEncoder;
import holik.hotel.servlet.util.Encoder;

public class LoginCommand implements Command {
	private static final Logger LOG = Logger.getLogger(LoginCommand.class);
	private UserService userService;
	
	public LoginCommand() {
		userService = new DefaultUserService();
	}
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		String errorMessage = null;		
		String forward = Path.PAGE__ERROR_PAGE;
		
		if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
			errorMessage = "Email or password cannot be empty";
			LOG.info("Invalid credentials: " + errorMessage);
			request.setAttribute("errorMessage", errorMessage);
			return forward;
		}
		Optional<User> userOptional = null;
		try {
			userOptional = userService.getUserByEmail(email);
		} catch (SQLException exception) {
			LOG.error("SQL exception:" + exception.getLocalizedMessage());
		}
		if (userOptional != null && userOptional.isPresent()) {
			User user = userOptional.get();
			String salt = user.getSalt();
			String hash = user.getPasswordHash();
			try {
				Encoder encoder = new DefaultEncoder();
				if (encoder.areHashesEqual(salt, hash, password)) {
					HttpSession session = request.getSession();
					session.setAttribute("userId", user.getId());
					session.setAttribute("userRole", user.getRole());
				} else {
					errorMessage = "Email or password is incorrect";
					request.setAttribute("errorMessage", errorMessage);
					return forward;
				}
			} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
				errorMessage = "Something went wrong";
				LOG.error("Exception with hashing algorithm occurred: " + e.getLocalizedMessage());
				request.setAttribute("errorMessage", errorMessage);
				return forward;
			}
		}
		return "redirect:home";
	}

	@Override
	public String toString() {
		return "LoginCommand";
	}
}
