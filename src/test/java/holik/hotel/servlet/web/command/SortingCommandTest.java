package holik.hotel.servlet.web.command;

import holik.hotel.servlet.web.command.sort.SortMethod;
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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SortingCommandTest {
    @Mock
    private HttpSession session;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @InjectMocks
    private SortingCommand sortingCommand;

    @Test
    public void execute() throws ServletException, IOException {
        when(request.getParameter("sortingMethod")).thenReturn("price");
        when(request.getSession()).thenReturn(session);
        sortingCommand.execute(request, response);
        verify(session).setAttribute("sort", SortMethod.PRICE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void executeWhenInvalidSort() throws ServletException, IOException {
        when(request.getParameter("sortingMethod")).thenReturn("not sorting method");
        sortingCommand.execute(request, response);
    }
}