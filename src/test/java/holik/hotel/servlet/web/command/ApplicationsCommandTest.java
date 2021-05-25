package holik.hotel.servlet.web.command;

import holik.hotel.servlet.repository.model.Application;
import holik.hotel.servlet.repository.model.ApplicationStatus;
import holik.hotel.servlet.service.ApplicationService;
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

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationsCommandTest {
    @Mock
    private ApplicationService applicationService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @InjectMocks
    private ApplicationsCommand applicationsCommand;

    @Test
    public void execute() throws ServletException, IOException {
        List<Application> requestedApplications = new ArrayList<>();
        when(applicationService.getApplicationsByStatus(ApplicationStatus.REQUESTED)).thenReturn(requestedApplications);

        applicationsCommand.execute(request, response);
        verify(applicationService).getApplicationsByStatus(ApplicationStatus.REQUESTED);
        verify(request).setAttribute("applications", requestedApplications);
    }
}