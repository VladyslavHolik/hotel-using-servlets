package holik.hotel.servlet.web.validator;

public class LoginValidator {
    public void validateLoginData(String email, String password) {
        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            // email or password cannot be empty
            throw new IllegalArgumentException("Invalid email or password");
        }
    }
}
