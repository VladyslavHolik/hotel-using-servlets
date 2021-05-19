package holik.hotel.servlet.web.validator;

import holik.hotel.servlet.repository.model.Room;
import holik.hotel.servlet.service.RoomService;

import java.util.Optional;

public class ChoiceValidator {
    private final RoomService roomService;

    public ChoiceValidator(RoomService roomService) {
        this.roomService = roomService;
    }

    public void validateChoice(String choice) {
        if (choice == null || choice.isEmpty()) {
            throw new IllegalArgumentException("Choice cannot be empty");
        }

        if (!"decline".equals(choice)) {
            int roomId = Integer.parseInt(choice);

            if (roomId < 1) {
                // non existing room
                throw new IllegalArgumentException("Invalid room id");
            }

            Optional<Room> optionalRoom = roomService.getRoomById(roomId);
            if (optionalRoom.isEmpty()) {
                // non existing room
                throw new IllegalArgumentException("Invalid room id");
            }
        }
    }
}
