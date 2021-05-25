package holik.hotel.servlet.web.command;

import holik.hotel.servlet.repository.model.Application;
import holik.hotel.servlet.repository.model.Room;
import holik.hotel.servlet.repository.model.User;
import holik.hotel.servlet.service.ApplicationService;
import holik.hotel.servlet.service.UserService;
import holik.hotel.servlet.web.validator.ApplicationValidator;
import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetApplicationFormCommandTest {
    @Mock
    private ApplicationService applicationService;
    @Mock
    private ApplicationValidator applicationValidator;
    @Mock
    private UserService userService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @InjectMocks
    private GetApplicationFormCommand getApplicationFormCommand;

    @Test
    public void execute() throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn("4");

        Application application = new Application();
        application.setUserId(3);

        List<Room> freeRooms = new ArrayList<>();
        User user = new User();
        when(applicationService.getApplicationById(4)).thenReturn(Optional.of(application));
        when(applicationService.getFreeRooms(application)).thenReturn(freeRooms);
        when(userService.getUserById(3)).thenReturn(Optional.of(user));

        getApplicationFormCommand.execute(request, response);
        verify(applicationValidator).validateApplicationId(4);
        verify(request).setAttribute("user", user);
        verify(request).setAttribute("application", application);
        verify(request).setAttribute("rooms", freeRooms);
    }
}