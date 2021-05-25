package holik.hotel.servlet.web.command;

import holik.hotel.servlet.repository.model.Room;
import holik.hotel.servlet.service.RoomService;
import holik.hotel.servlet.web.command.sort.SortMethod;
import holik.hotel.servlet.web.dto.RoomsContent;
import holik.hotel.servlet.web.validator.PageValidator;
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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RoomsCommandTest {
    @Mock
    private RoomService roomService;
    @Mock
    private PageValidator pageValidator;
    @Mock
    private HttpSession session;
    @Mock
    private HttpServletRequest request;
    @Mock
    private RoomsContent roomsContent;
    @Mock
    private HttpServletResponse response;
    @InjectMocks
    private RoomsCommand roomsCommand;

    @Test
    public void execute() throws ServletException, IOException {
        when(request.getParameter("page")).thenReturn("2");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("sort")).thenReturn(SortMethod.PRICE);

        List<Room> rooms = new ArrayList<>();

        when(roomService.getNumberOfPages()).thenReturn(10);
        when(roomService.getRoomsContent(2, 10)).thenReturn(roomsContent);
        when(roomService.getRoomsOnPage(2, SortMethod.PRICE)).thenReturn(rooms);

        roomsCommand.execute(request, response);
        verify(pageValidator).validatePage(2);
        verify(roomsContent).setRooms(rooms);
        verify(request).setAttribute("roomsContent", roomsContent);
    }
}