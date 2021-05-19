package holik.hotel.servlet.web.validator;

import holik.hotel.servlet.repository.model.Room;
import holik.hotel.servlet.service.RoomService;
import holik.hotel.servlet.web.dto.RoomsContent;

import java.util.List;

public class PageValidator {
    private final RoomService roomService;

    public PageValidator(RoomService roomService) {
        this.roomService = roomService;
    }

    public void validatePage(int numberOfPage) {
        List<Room> availableRooms = roomService.getAvailableRooms();
        int numberOfPages = (int) Math.ceil((double) availableRooms.size() / RoomsContent.NUMBER_OF_ROOMS_ON_PAGE);

        if (numberOfPage > numberOfPages || numberOfPage < 1) {
            // Page must exist
            throw new IllegalArgumentException("Invalid page number");
        }
    }
}
