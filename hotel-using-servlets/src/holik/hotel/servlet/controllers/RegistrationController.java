package holik.hotel.servlet.controllers;

import java.io.IOException;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import holik.hotel.servlet.dto.UserDto;
import holik.hotel.servlet.hashing.Hasher;
import holik.hotel.servlet.models.Role;
import holik.hotel.servlet.models.User;
import holik.hotel.servlet.services.UserService;
import holik.hotel.servlet.services.impl.UserServiceImpl;

@WebServlet("/signup")
public final class RegistrationController extends HttpServlet {
	private static final long serialVersionUID = 1L;
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
		String password = request.getParameter("password");
		
		UserDto userDto = new UserDto(firstName, lastName, phone, email, password);
		
		userService.createUser(getUserFromDto(userDto));
		response.sendRedirect("signin.jsp");
	}
	
	private User getUserFromDto(UserDto userDto) {
		byte[] salt = Hasher.generateRandomSalt();
		byte[] hash = Hasher.generateHash(salt, userDto.getPassword().trim());
		String saltString = Base64.getEncoder().encodeToString(salt);;
		String hashString = Base64.getEncoder().encodeToString(hash);;
		
		User user = new User();
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setPhone(userDto.getPhone());
		user.setEmail(userDto.getEmail());
		user.setSalt(saltString);
		user.setPasswordHash(hashString);
		user.setRole(Role.USER);
		
		return user;
	}

}
