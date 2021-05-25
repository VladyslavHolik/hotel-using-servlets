package holik.hotel.servlet.web.command;

import holik.hotel.servlet.repository.model.Application;
import holik.hotel.servlet.service.ApplicationService;
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
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookRoomCommandTest {
    @Mock
    private ApplicationService applicationService;
    @Mock
    private ApplicationValidator applicationValidator;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @InjectMocks
    private BookRoomCommand bookRoomCommand;

    @Test
    public void execute() throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn("3");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("userId")).thenReturn(4);

        Application application = new Application();
        when(applicationService.getApplicationById(3)).thenReturn(Optional.of(application));

        bookRoomCommand.execute(request, response);
        verify(applicationValidator).validateForBooking(3, 4);
        verify(applicationService).bookRoom(application);
    }
}