package holik.hotel.servlet.web.command;

import holik.hotel.servlet.service.ApplicationService;
import holik.hotel.servlet.web.validator.ApplicationValidator;
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
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationCommandTest {
    @Mock
    private ApplicationService applicationService;
    @Mock
    private ApplicationValidator applicationValidator;
    @Mock
    private HttpSession session;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @InjectMocks
    private ApplicationCommand applicationCommand;

    @Test
    public void executeWhenApplication() throws ServletException, IOException {
        when(request.getParameter("space")).thenReturn("3");
        when(request.getParameter("roomClass")).thenReturn("1");
        when(request.getParameter("arrival")).thenReturn(LocalDateTime.now().plusDays(3).toString());
        when(request.getParameter("leaving")).thenReturn(LocalDateTime.now().plusDays(5).toString());
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("userId")).thenReturn(2);

        applicationCommand.execute(request, response);
        verify(applicationValidator).validate(any());
        verify(applicationService).saveApplication(any());
    }
}