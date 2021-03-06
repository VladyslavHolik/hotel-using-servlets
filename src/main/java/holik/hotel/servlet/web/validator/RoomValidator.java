package holik.hotel.servlet.web.validator;

import holik.hotel.servlet.repository.model.Room;
import holik.hotel.servlet.service.RoomService;

import java.util.Optional;

public class RoomValidator {
    private final RoomService roomService;

    public RoomValidator(RoomService roomService) {
        this.roomService = roomService;
    }

    public void validateRoom(int roomId) {
        if (roomId < 1) {
            // room cannot have id less than 1
            throw new IllegalArgumentException("Room doesn't exit");
        }

        Optional<Room> optionalRoom = roomService.getRoomById(roomId);
        if (optionalRoom.isEmpty()) {
            // room must exist
            throw new IllegalArgumentException("Room doesn't exit");
        }
    }
}
