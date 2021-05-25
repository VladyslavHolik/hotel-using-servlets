package holik.hotel.servlet.web.validator;

import holik.hotel.servlet.repository.model.Room;
import holik.hotel.servlet.service.RoomService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RoomValidatorTest {
    @Mock
    private RoomService roomService;
    @InjectMocks
    private RoomValidator roomValidator;

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