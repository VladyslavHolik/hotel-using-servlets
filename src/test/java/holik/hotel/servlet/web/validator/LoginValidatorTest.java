package holik.hotel.servlet.web.validator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LoginValidatorTest {
    @InjectMocks
    private LoginValidator loginValidator;

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