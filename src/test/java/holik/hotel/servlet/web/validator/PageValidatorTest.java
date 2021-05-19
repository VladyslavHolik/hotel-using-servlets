package holik.hotel.servlet.web.validator;

import holik.hotel.servlet.repository.model.Room;
import holik.hotel.servlet.service.RoomService;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PageValidatorTest {
    private RoomService roomService;
    private PageValidator pageValidator;

    @Before
    public void initialize() {
        roomService = mock(RoomService.class);
        pageValidator = new PageValidator(roomService);
    }

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