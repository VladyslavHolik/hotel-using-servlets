package holik.hotel.servlet.web.command;

import holik.hotel.servlet.repository.model.Application;
import holik.hotel.servlet.repository.model.ApplicationStatus;
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
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PayBillCommandTest {
    @Mock
    private ApplicationService applicationService;
    @Mock
    private ApplicationValidator applicationValidator;
    @Mock
    private HttpSession session;
    @Mock
    private HttpServletRequest request;
    @Mock
    private Application application;
    @Mock
    private HttpServletResponse response;
    @InjectMocks
    private PayBillCommand payBillCommand;

    @Test
    public void execute() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("userId")).thenReturn(4);
        when(request.getParameter("id")).thenReturn("6");
        when(applicationService.getApplicationById(6)).thenReturn(Optional.of(application));

        payBillCommand.execute(request, response);
        verify(applicationValidator).validateForPaying(6, 4);
        verify(application).setStatus(ApplicationStatus.PAID);
        verify(applicationService).updateApplication(application);
    }
}