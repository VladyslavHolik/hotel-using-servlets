package holik.hotel.servlet.web.validator;

import holik.hotel.servlet.repository.model.Room;
import holik.hotel.servlet.service.RoomService;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RoomValidatorTest {
    private RoomService roomService;
    private RoomValidator roomValidator;

    @Before
    public void initialize() {
        roomService = mock(RoomService.class);
        roomValidator = new RoomValidator(roomService);
    }

    @Test
    public void validateRoom() {
        when(roomService.getRoomById(3)).thenReturn(Optional.of(new Room()));
        roomValidator.validateRoom(3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateRoomIfIdIsInvalid() {
        roomValidator.validateRoom(-3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateRoomIfDoesNotExist() {
        when(roomService.getRoomById(4)).thenReturn(Optional.empty());
        roomValidator.validateRoom(4);
    }

}