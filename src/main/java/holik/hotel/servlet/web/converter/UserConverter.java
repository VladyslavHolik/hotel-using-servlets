package holik.hotel.servlet.web.converter;

import holik.hotel.servlet.repository.model.Role;
import holik.hotel.servlet.repository.model.User;
import holik.hotel.servlet.service.EncoderService;
import holik.hotel.servlet.web.dto.UserDto;

import java.util.Base64;

/**
 * Mapping class for user.
 */
public class UserConverter implements Converter<UserDto, User> {
	private final EncoderService encoderService;

	public UserConverter(EncoderService encoderService) {
		this.encoderService = encoderService;
	}

	@Override
	public User covertToEntity(UserDto userDto) {
		byte[] salt = encoderService.generateRandomSalt();
		byte[] hash = encoderService.generateHash(salt, userDto.getPassword().trim());
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
