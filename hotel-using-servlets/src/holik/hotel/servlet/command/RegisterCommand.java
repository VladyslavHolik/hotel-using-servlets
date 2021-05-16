package holik.hotel.servlet.command;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import holik.hotel.servlet.dto.UserDto;
import holik.hotel.servlet.mapper.UserMapper;
import holik.hotel.servlet.path.Path;
import holik.hotel.servlet.service.UserService;
import holik.hotel.servlet.service.impl.DefaultUserService;

/**
 * Command that registers user.
 */
public class RegisterCommand implements Command {
	private static final Logger LOG = Logger.getLogger(RegisterCommand.class);
	private UserService userService;

	public RegisterCommand() {
		userService = new DefaultUserService();
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String firstName = request.getParameter("first_name");
		String lastName = request.getParameter("last_name");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		String errorMessage = null;
		String forward = Path.PAGE__ERROR_PAGE;

		if (firstName == null || lastName == null || phone == null || email == null || password == null
				|| firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty() || email.isEmpty()
				|| password.isEmpty()) {
			errorMessage = "All fields should be filled";
			request.setAttribute("errorMessage", errorMessage);
			return forward;
		}

		if (userService.getUserByEmail(email).isPresent()) {
			errorMessage = "User with this email already exists";
			request.setAttribute("errorMessage", errorMessage);
			return forward;
		}

		Pattern pattern = Pattern.compile("[0-9]{1,3} [0-9]{2} [0-9]{3} [0-9]{4}");
		Matcher matcher = pattern.matcher(phone);
		if (!matcher.matches()) {
			errorMessage = "Phone is invalid";
			request.setAttribute("errorMessage", errorMessage);
			return forward;
		}

		UserDto userDto = new UserDto(firstName, lastName, phone, email, password);

		try {
			userService.createUser(UserMapper.getUserFromDto(userDto));
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			LOG.error("Exception with hashing algorithm occurred: " + e.getLocalizedMessage());
			errorMessage = "Something went wrong";
			request.setAttribute("errorMessage", errorMessage);
			return forward;
		}
		return "redirect:home";
	}

	@Override
	public String toString() {
		return "RegisterCommand";
	}
}
