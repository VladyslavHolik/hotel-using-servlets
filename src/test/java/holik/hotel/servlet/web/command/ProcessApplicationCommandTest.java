package holik.hotel.servlet.web.command;

import holik.hotel.servlet.service.ApplicationService;
import holik.hotel.servlet.web.validator.ApplicationValidator;
import holik.hotel.servlet.web.validator.ChoiceValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProcessApplicationCommandTest {
    @Mock
    private ApplicationService applicationService;
    @Mock
    private ApplicationValidator applicationValidator;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private ChoiceValidator choiceValidator;
    @InjectMocks
    private ProcessApplicationCommand processApplicationCommand;

    @Test
    public void execute() throws ServletException, IOException {
        when(request.getParameter("applicationId")).thenReturn("8");
        when(request.getParameter("choice")).thenReturn("decline");

        processApplicationCommand.execute(request, response);
        verify(choiceValidator).validateChoice("decline");
        verify(applicationValidator).validateApplicationId(8);
        verify(applicationService).processApplication(8, "decline");
    }
}