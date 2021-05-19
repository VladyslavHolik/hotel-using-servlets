package holik.hotel.servlet.web.validator;

import holik.hotel.servlet.repository.model.Room;
import holik.hotel.servlet.service.RoomService;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ChoiceValidatorTest {
    private ChoiceValidator choiceValidator;
    private RoomService roomService;

    @Before
    public void initialize() {
        roomService = mock(RoomService.class);
        choiceValidator = new ChoiceValidator(roomService);
    }

    @Test
    public void validateChoiceIfValidDeclined() {
        String choice = "decline";
        choiceValidator.validateChoice(choice);
    }

    @Test
    public void validateChoiceIfValidRoom() {
        when(roomService.getRoomById(2)).thenReturn(Optional.of(new Room()));
        String choice = "2";
        choiceValidator.validateChoice(choice);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateChoiceIfNull() {
        choiceValidator.validateChoice(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateChoiceIfEmpty() {
        choiceValidator.validateChoice("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateChoiceIfInvalid() {
        choiceValidator.validateChoice("This is not a choice");
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateChoiceIfRoomIsInvalid() {
        when(roomService.getRoomById(3)).thenReturn(Optional.empty());
        String choice = "3";
        choiceValidator.validateChoice(choice);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateChoiceIfRoomIdInvalid() {
        String choice = "-5";
        choiceValidator.validateChoice(choice);
    }
}