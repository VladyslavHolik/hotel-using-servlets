package holik.hotel.servlet.web.validator;

import holik.hotel.servlet.service.UserService;
import holik.hotel.servlet.web.dto.UserDto;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {
    private final UserService userService;

    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    public void validateUser(UserDto userDto) {
        String firstName = userDto.getFirstName();
        String lastName = userDto.getLastName();
        String phone = userDto.getPhone();
        String email = userDto.getEmail();
        String password = userDto.getPassword();

        if (firstName == null || lastName == null || phone == null || email == null || password == null
                || firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty() || email.isEmpty()
                || password.isEmpty()) {
            // Fields must not be empty
            throw new IllegalArgumentException("Fields should be filled");
        }

        if (userService.getUserByEmail(email).isPresent()) {
            // Email must be unique
            throw new IllegalArgumentException("Email is not unique");
        }

        Pattern pattern = Pattern.compile("[0-9]{1,3} [0-9]{2} [0-9]{3} [0-9]{4}");
        Matcher matcher = pattern.matcher(phone);
        if (!matcher.matches()) {
            // phone must be valid against pattern
            throw new IllegalArgumentException("Invalid phone number");
        }
    }
}
