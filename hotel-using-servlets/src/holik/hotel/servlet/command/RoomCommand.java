package holik.hotel.servlet.command;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import holik.hotel.servlet.model.Room;
import holik.hotel.servlet.path.Path;
import holik.hotel.servlet.service.RoomService;
import holik.hotel.servlet.service.impl.DefaultRoomService;

public class RoomCommand implements Command {
	private static final Logger LOG = Logger.getLogger(RoomCommand.class);
	private RoomService roomService;
	
	public RoomCommand() {
		roomService = new DefaultRoomService();
	}
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		int roomId = Integer.parseInt(request.getParameter("id"));
		
		String errorMessage = null;		
		String forward = Path.PAGE__ERROR_PAGE;
		
		Optional<Room> optionalRoom = null;
		try {
			optionalRoom = roomService.getRoomById(roomId);
		} catch (SQLException e) {
			errorMessage = "Something went wrong";
			request.setAttribute("errorMessage", errorMessage);
			LOG.error("SQL Exception occurred while getting room by id" + e.getLocalizedMessage());
			return forward;	
		}
		
		if (optionalRoom.isEmpty()) {
			errorMessage = "Room with this id doesn't exist";
			request.setAttribute("errorMessage", errorMessage);
			return forward;	
		}
		
		request.setAttribute("room", optionalRoom.get());
		return "room.jsp";
	}
	
	@Override
	public String toString() {
		return "RoomCommand []";
	}
}
