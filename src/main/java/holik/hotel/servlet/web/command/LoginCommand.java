package holik.hotel.servlet.web.command;

import holik.hotel.servlet.repository.model.User;
import holik.hotel.servlet.service.UserService;
import holik.hotel.servlet.web.command.constant.Pages;
import holik.hotel.servlet.web.validator.LoginValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Command that authenticates user.
 */
public class LoginCommand implements Command {
    private final UserService userService;
    private final LoginValidator loginValidator;

    public LoginCommand(UserService userService, LoginValidator loginValidator) {
        this.userService = userService;
        this.loginValidator = loginValidator;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        loginValidator.validateLoginData(email, password);

        String errorMessage;
        String forward = Pages.PAGE_ERROR_PAGE;

        if (userService.areCredentialsTrue(email, password)) {
            User user = userService.getUserByEmail(email).orElseThrow();
            authenticateUser(request, user);
        } else {
            errorMessage = "Email or password is incorrect";
            request.setAttribute("errorMessage", errorMessage);
            return forward;
        }

        return "redirect:/";
    }

    private void authenticateUser(HttpServletRequest request, User user) {
        HttpSession session = request.getSession();
        session.setAttribute("userId", user.getId());
        session.setAttribute("userRole", user.getRole());
    }

    @Override
    public String toString() {
        return "LoginCommand";
    }
}
