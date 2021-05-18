package holik.hotel.servlet.command;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import holik.hotel.servlet.command.constant.Pages;
import holik.hotel.servlet.model.Room;
import holik.hotel.servlet.service.RoomService;
import holik.hotel.servlet.service.impl.DefaultRoomService;

/**
 * Command that forward user to room page.
 */
public class RoomCommand implements Command {
	private static final Logger LOG = Logger.getLogger(RoomCommand.class);
	private final RoomService roomService;

	public RoomCommand() {
		roomService = new DefaultRoomService();
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		int roomId = Integer.parseInt(request.getParameter("id"));

		String errorMessage = null;
		String forward = Pages.PAGE__ERROR_PAGE;

		Optional<Room> optionalRoom = roomService.getRoomById(roomId);

		if (optionalRoom.isEmpty()) {
			errorMessage = "Room with this id doesn't exist";
			request.setAttribute("errorMessage", errorMessage);
			return forward;
		}
		
		LOG.debug("Setting attribute room");
		request.setAttribute("room", optionalRoom.get());
		return Pages.PAGE__ROOM;
	}

	@Override
	public String toString() {
		return "RoomCommand";
	}
}
