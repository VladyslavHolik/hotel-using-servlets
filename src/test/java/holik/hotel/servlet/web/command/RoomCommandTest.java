package holik.hotel.servlet.web.command;

import holik.hotel.servlet.repository.model.Room;
import holik.hotel.servlet.service.RoomService;
import holik.hotel.servlet.web.validator.RoomValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RoomCommandTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RoomService roomService;
    @Mock
    private RoomValidator roomValidator;
    @InjectMocks
    private RoomCommand roomCommand;

    @Test
    public void execute() throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn("6");
        Room room = new Room();
        when(roomService.getRoomById(6)).thenReturn(Optional.of(room));

        roomCommand.execute(request, response);
        verify(roomValidator).validateRoom(6);
        verify(request).setAttribute("room", room);
    }
}