package holik.hotel.servlet.command;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import holik.hotel.servlet.models.User;
import holik.hotel.servlet.path.Path;
import holik.hotel.servlet.services.UserService;
import holik.hotel.servlet.services.impl.DefaultUserService;
import holik.hotel.servlet.util.DefaultEncoder;
import holik.hotel.servlet.util.Encoder;

public class LoginCommand implements Command {
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
			request.setAttribute("errorMessage", errorMessage);
			return forward;
		}
		Optional<User> userOptional = userService.getUserByEmail(email);
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			String salt = user.getSalt();
			String hash = user.getPasswordHash();
			try {
				Encoder encoder = new DefaultEncoder();
				if (encoder.areHashesEqual(salt, hash, password)) {
					HttpSession session = request.getSession();
					session.setAttribute("user_id", user.getId());
					session.setAttribute("user_role_id", user.getRole().getId());
				} else {
					errorMessage = "Email or password is incorrect";
					request.setAttribute("errorMessage", errorMessage);
					return forward;
				}
			} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
				errorMessage = "Something went wrong";
				request.setAttribute("errorMessage", errorMessage);
				return forward;
			}
		}
		return "redirect:controller?command=home";
	}

}
