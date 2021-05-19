package holik.hotel.servlet.web.validator;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LoginValidatorTest {
    private LoginValidator loginValidator;

    @Before
    public void initialize() {
        loginValidator = new LoginValidator();
    }

    @Test
    public void validateLoginData() {
        loginValidator.validateLoginData("some@gmail.com", "password");
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateLoginDataIfEmailIsNull() {
        loginValidator.validateLoginData(null, "password");
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateLoginDataIfPasswordIsNull() {
        loginValidator.validateLoginData("some@gmail.com", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateLoginDataIfEmailIsEmpty() {
        loginValidator.validateLoginData("", "password");
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateLoginDataIfPasswordIsEmpty() {
        loginValidator.validateLoginData("some@gmail.com", "");
    }
}