package holik.hotel.servlet.web.command;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import holik.hotel.servlet.service.EncoderService;
import org.apache.log4j.Logger;

import holik.hotel.servlet.web.command.constant.Pages;
import holik.hotel.servlet.repository.model.User;
import holik.hotel.servlet.service.UserService;
import holik.hotel.servlet.service.impl.DefaultUserService;
import holik.hotel.servlet.service.impl.DefaultEncoderService;

/**
 * Command that authenticates user.
 */
public class LoginCommand implements Command {
	private static final Logger LOG = Logger.getLogger(LoginCommand.class);
	private final UserService userService;

	public LoginCommand() {
		userService = new DefaultUserService();
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		String errorMessage = null;
		String forward = Pages.PAGE_ERROR_PAGE;

		if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
			errorMessage = "Email or password cannot be empty";
			LOG.info("Invalid credentials: " + errorMessage);
			request.setAttribute("errorMessage", errorMessage);
			return forward;
		}
		Optional<User> userOptional = null;
		userOptional = userService.getUserByEmail(email);

		if (userOptional != null && userOptional.isPresent()) {
			User user = userOptional.get();
			String salt = user.getSalt();
			String hash = user.getPasswordHash();
			try {
				EncoderService encoder = new DefaultEncoderService();
				if (encoder.areHashesEqual(salt, hash, password)) {
					authenticateUser(request, user);
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

	private void authenticateUser(HttpServletRequest request, User user) {
		HttpSession session = request.getSession();
		session.setAttribute("userId", user.getId());
		session.setAttribute("userRole", user.getRole());
	}

	@Override
	public String toString() {
		return "LoginCommand";
	}
}
