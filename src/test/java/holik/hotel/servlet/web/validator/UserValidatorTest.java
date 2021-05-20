package holik.hotel.servlet.web.validator;

import holik.hotel.servlet.repository.model.User;
import holik.hotel.servlet.service.UserService;
import holik.hotel.servlet.web.dto.UserDto;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserValidatorTest {
    private UserService userService;
    private UserValidator userValidator;

    @Before
    public void setUp() {
        userService = mock(UserService.class);
        userValidator = new UserValidator(userService);
    }

    @Test
    public void validateUser() {
        String firstName = "Fred";
        String lastName = "White";
        String phone = "380 99 182 1111";
        String email = "fred@gmail.com";
        String password = "fredPassword";

        UserDto user = new UserDto(firstName, lastName, phone, email, password);
        when(userService.getUserByEmail("fred@gmail.com")).thenReturn(Optional.empty());

        userValidator.validateUser(user);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateUserIfFirstNameIsNull() {
        String lastName = "White";
        String phone = "380 99 182 1111";
        String email = "fred@gmail.com";
        String password = "fredPassword";
        UserDto user = new UserDto(null, lastName, phone, email, password);

        userValidator.validateUser(user);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateUserIfLastNameIsNull() {
        String firstName = "Fred";
        String phone = "380 99 182 1111";
        String email = "fred@gmail.com";
        String password = "fredPassword";
        UserDto user = new UserDto(firstName, null, phone, email, password);

        userValidator.validateUser(user);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateUserIfPhoneIsNull() {
        String firstName = "Fred";
        String lastName = "White";
        String email = "fred@gmail.com";
        String password = "fredPassword";
        UserDto user = new UserDto(firstName, lastName, null, email, password);

        userValidator.validateUser(user);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateUserIfEmailIsNull() {
        String firstName = "Fred";
        String lastName = "White";
        String phone = "380 99 182 1111";
        String password = "fredPassword";
        UserDto user = new UserDto(firstName, lastName, phone, null, password);

        userValidator.validateUser(user);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateUserIfPasswordIsNull() {
        String firstName = "Fred";
        String lastName = "White";
        String phone = "380 99 182 1111";
        String email = "fred@gmail.com";
        UserDto user = new UserDto(firstName, lastName, phone, email, null);

        userValidator.validateUser(user);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateUserIfPhoneIsInvalid() {
        String firstName = "Fred";
        String lastName = "White";
        String phone = "not a phone number";
        String email = "fred@gmail.com";
        String password = "fredPassword";

        UserDto user = new UserDto(firstName, lastName, phone, email, password);
        when(userService.getUserByEmail("fred@gmail.com")).thenReturn(Optional.empty());

        userValidator.validateUser(user);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateUserIfUserExists() {
        String firstName = "Fred";
        String lastName = "White";
        String phone = "not a phone number";
        String email = "fred@gmail.com";
        String password = "fredPassword";

        UserDto user = new UserDto(firstName, lastName, phone, email, password);
        when(userService.getUserByEmail("fred@gmail.com")).thenReturn(Optional.of(new User()));

        userValidator.validateUser(user);
    }
}