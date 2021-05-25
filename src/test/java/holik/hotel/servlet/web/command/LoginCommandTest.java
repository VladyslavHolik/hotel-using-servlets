package holik.hotel.servlet.web.command;

import holik.hotel.servlet.repository.model.Role;
import holik.hotel.servlet.repository.model.User;
import holik.hotel.servlet.service.UserService;
import holik.hotel.servlet.web.validator.LoginValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LoginCommandTest {
    @Mock
    private HttpSession session;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private LoginValidator loginValidator;
    @Mock
    private UserService userService;
    @InjectMocks
    private LoginCommand loginCommand;

    @Test
    public void execute() throws ServletException, IOException {
        when(request.getParameter("email")).thenReturn("user@gmail.com");
        when(request.getParameter("password")).thenReturn("userPassword");

        when(userService.areCredentialsTrue("user@gmail.com", "userPassword")).thenReturn(true);

        User user = new User();
        user.setId(4);
        user.setRole(Role.USER);

        when(userService.getUserByEmail("user@gmail.com")).thenReturn(Optional.of(user));
        when(request.getSession()).thenReturn(session);

        loginCommand.execute(request, response);
        verify(loginValidator).validateLoginData("user@gmail.com", "userPassword");
        verify(session).setAttribute("userId", 4);
        verify(session).setAttribute("userRole", Role.USER);
    }

    @Test
    public void executeWhenPasswordIsIncorrect() throws ServletException, IOException {
        when(request.getParameter("email")).thenReturn("user@gmail.com");
        when(request.getParameter("password")).thenReturn("userPassword");

        when(userService.areCredentialsTrue("user@gmail.com", "userPassword")).thenReturn(false);

        User user = new User();
        user.setId(4);
        user.setRole(Role.USER);

        loginCommand.execute(request, response);
        verify(loginValidator).validateLoginData("user@gmail.com", "userPassword");
        verify(session, times(0)).setAttribute("userId", 4);
        verify(session, times(0)).setAttribute("userRole", Role.USER);
    }
}