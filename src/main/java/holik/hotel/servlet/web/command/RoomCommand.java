package holik.hotel.servlet.web.command;

import holik.hotel.servlet.repository.model.Room;
import holik.hotel.servlet.service.RoomService;
import holik.hotel.servlet.web.command.constant.Pages;
import holik.hotel.servlet.web.validator.RoomValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Command that forward user to room page.
 */
public class RoomCommand implements Command {
	private final RoomService roomService;
	private final RoomValidator roomValidator;

	public RoomCommand(RoomService roomService, RoomValidator roomValidator) {
		this.roomService = roomService;
		this.roomValidator = roomValidator;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		int roomId = Integer.parseInt(request.getParameter("id"));
		roomValidator.validateRoom(roomId);

		Room room = roomService.getRoomById(roomId).orElseThrow();
		request.setAttribute("room", room);
		return Pages.PAGE_ROOM;
	}

	@Override
	public String toString() {
		return "RoomCommand";
	}
}
