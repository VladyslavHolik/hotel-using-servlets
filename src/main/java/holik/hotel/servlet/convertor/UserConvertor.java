package holik.hotel.servlet.convertor;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import holik.hotel.servlet.dto.UserDto;
import holik.hotel.servlet.model.Role;
import holik.hotel.servlet.model.User;
import holik.hotel.servlet.util.DefaultEncoder;
import holik.hotel.servlet.util.Encoder;

/**
 * Mapping class for user.
 */
public class UserConvertor {
	public static User getUserFromDto(UserDto userDto) throws NoSuchAlgorithmException, InvalidKeySpecException {
		Encoder encoder = new DefaultEncoder();
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
