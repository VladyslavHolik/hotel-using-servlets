package holik.hotel.servlet.web.command;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import holik.hotel.servlet.web.context.ApplicationContext;
import holik.hotel.servlet.web.validator.UserValidator;
import org.apache.log4j.Logger;

import holik.hotel.servlet.web.command.constant.Pages;
import holik.hotel.servlet.web.convertor.UserConvertor;
import holik.hotel.servlet.web.dto.UserDto;
import holik.hotel.servlet.service.UserService;
import holik.hotel.servlet.service.impl.DefaultUserService;

/**
 * Command that registers user.
 */
public class RegisterCommand implements Command {
    private static final Logger LOG = Logger.getLogger(RegisterCommand.class);
    private final UserService userService;
    private final UserValidator userValidator;

    public RegisterCommand() {
        userService = ApplicationContext.getUserService();
        userValidator = ApplicationContext.getUserValidator();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserDto userDto = new UserDto(firstName, lastName, phone, email, password);
        userValidator.validateUser(userDto);
        userService.createUser(UserConvertor.getUserFromDto(userDto));

        return "redirect:home";
    }

    @Override
    public String toString() {
        return "RegisterCommand";
    }
}
