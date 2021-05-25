package holik.hotel.servlet.web.command;

import holik.hotel.servlet.repository.exception.EntityExistsException;
import holik.hotel.servlet.repository.model.User;
import holik.hotel.servlet.service.UserService;
import holik.hotel.servlet.web.converter.UserConverter;
import holik.hotel.servlet.web.dto.UserDto;
import holik.hotel.servlet.web.validator.UserValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegisterCommandTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private UserService userService;
    @Mock
    private UserValidator userValidator;
    @Mock
    private UserConverter userConverter;
    @InjectMocks
    private RegisterCommand registerCommand;

    @Test
    public void execute() throws EntityExistsException {
        when(request.getParameter("first_name")).thenReturn("Fred");
        when(request.getParameter("last_name")).thenReturn("White");
        when(request.getParameter("phone")).thenReturn("380 99 186 7781");
        when(request.getParameter("email")).thenReturn("fred@gmail.com");
        when(request.getParameter("password")).thenReturn("fredPassword");

        User user = new User();
        when(userConverter.convertToEntity(Mockito.any())).thenReturn(user);

        registerCommand.execute(request, response);

        verify(userValidator).validateUser(Mockito.any());
        verify(userService).save(user);
    }
}