package holik.hotel.servlet.web.command;

import holik.hotel.servlet.repository.model.Application;
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
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GetMyApplicationsCommandTest {
    @Mock
    private ApplicationService applicationService;
    @Mock
    private HttpSession session;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @InjectMocks
    private GetMyApplicationsCommand getMyApplicationsCommand;

    @Test
    public void execute() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("userId")).thenReturn(8);

        List<Application> applications = new ArrayList<>();
        when(applicationService.getReadyToBookApplications(8)).thenReturn(applications);

        getMyApplicationsCommand.execute(request, response);
        verify(request).setAttribute("applications", applications);
    }
}