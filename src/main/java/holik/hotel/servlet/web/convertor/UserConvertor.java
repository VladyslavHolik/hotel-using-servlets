package holik.hotel.servlet.web.convertor;

import holik.hotel.servlet.repository.model.Role;
import holik.hotel.servlet.repository.model.User;
import holik.hotel.servlet.service.EncoderService;
import holik.hotel.servlet.service.impl.DefaultEncoderService;
import holik.hotel.servlet.web.dto.UserDto;

import java.util.Base64;

/**
 * Mapping class for user.
 */
public class UserConvertor {
	public static User getUserFromDto(UserDto userDto) {
		EncoderService encoder = new DefaultEncoderService();
		byte[] salt = encoder.generateRandomSalt();
		byte[] hash = encoder.generateHash(salt, userDto.getPassword().trim());
		String saltString = Base64.getEncoder().encodeToString(salt);
        String hashString = Base64.getEncoder().encodeToString(hash);

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
