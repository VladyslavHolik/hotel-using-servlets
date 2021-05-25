package holik.hotel.servlet.web.command;

import holik.hotel.servlet.repository.model.Room;
import holik.hotel.servlet.service.RoomService;
import holik.hotel.servlet.web.command.constant.Pages;
import holik.hotel.servlet.web.validator.RoomValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

public class GetOrderCommand implements Command {
    private static final long DAYS_PERIOD = 1;
    private static final int TIME_OFFSET = 16;
    private final RoomValidator roomValidator;
    private final RoomService roomService;

    public GetOrderCommand(RoomValidator roomValidator, RoomService roomService) {
        this.roomValidator = roomValidator;
        this.roomService = roomService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        int roomId = Integer.parseInt(request.getParameter("id"));
        roomValidator.validateRoom(roomId);

        Room room = roomService.getRoomById(roomId).orElseThrow();
        request.setAttribute("room", room);

        String minTime = LocalDateTime.now().plusDays(DAYS_PERIOD).toString().substring(0, TIME_OFFSET);
        request.setAttribute("minTime", minTime);
        return Pages.PAGE_ORDER;
    }

    @Override
    public String toString() {
        return "GetOrderCommand";
    }
}
