package holik.hotel.servlet.web.command;

import holik.hotel.servlet.repository.exception.EntityExistsException;
import holik.hotel.servlet.service.UserService;
import holik.hotel.servlet.web.command.constant.Pages;
import holik.hotel.servlet.web.converter.UserConverter;
import holik.hotel.servlet.web.dto.UserDto;
import holik.hotel.servlet.web.validator.UserValidator;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Command that registers user.
 */
public class RegisterCommand implements Command {
    private static final Logger LOG = Logger.getLogger(RegisterCommand.class);
    private final UserService userService;
    private final UserValidator userValidator;
    private final UserConverter userConverter;

    public RegisterCommand(UserService userService, UserValidator userValidator, UserConverter userConverter) {
        this.userService = userService;
        this.userValidator = userValidator;
        this.userConverter = userConverter;
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

        LOG.debug("Registering user");
        String forward = Pages.PAGE_ERROR_PAGE;

        try {
            userService.save(userConverter.convertToEntity(userDto));
        } catch (EntityExistsException exception) {
            String errorMessage = "User with this email already exists";
            request.setAttribute("errorMessage", errorMessage);
            return forward;
        }
        return "redirect:/";
    }

    @Override
    public String toString() {
        return "RegisterCommand";
    }
}
