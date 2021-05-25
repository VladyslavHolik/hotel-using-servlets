package holik.hotel.servlet.web.validator;

import holik.hotel.servlet.repository.model.Room;
import holik.hotel.servlet.service.RoomService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PageValidatorTest {
    @Mock
    private RoomService roomService;
    @InjectMocks
    private PageValidator pageValidator;

    @Test
    public void validatePage() {
        List<Room> rooms = new ArrayList<>();
        rooms.add(new Room());
        rooms.add(new Room());
        rooms.add(new Room());
        rooms.add(new Room());
        rooms.add(new Room());
        rooms.add(new Room());

        when(roomService.getAvailableRooms()).thenReturn(rooms);

        pageValidator.validatePage(2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validatePageIfInvalid() {
        List<Room> rooms = new ArrayList<>();
        rooms.add(new Room());
        rooms.add(new Room());
        rooms.add(new Room());
        rooms.add(new Room());
        rooms.add(new Room());
        rooms.add(new Room());

        when(roomService.getAvailableRooms()).thenReturn(rooms);

        pageValidator.validatePage(3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validatePageIfNumberIsInvalid() {
        pageValidator.validatePage(-1);
    }
}