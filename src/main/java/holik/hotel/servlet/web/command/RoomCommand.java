package holik.hotel.servlet.web.command;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import holik.hotel.servlet.web.context.ApplicationContext;
import holik.hotel.servlet.web.validator.RoomValidator;
import org.apache.log4j.Logger;

import holik.hotel.servlet.web.command.constant.Pages;
import holik.hotel.servlet.repository.model.Room;
import holik.hotel.servlet.service.RoomService;
import holik.hotel.servlet.service.impl.DefaultRoomService;

/**
 * Command that forward user to room page.
 */
public class RoomCommand implements Command {
	private static final Logger LOG = Logger.getLogger(RoomCommand.class);
	private final RoomService roomService;
	private final RoomValidator roomValidator;

	public RoomCommand() {
		roomService = ApplicationContext.getRoomService();
		roomValidator = ApplicationContext.getRoomValidator();
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
