package holik.hotel.servlet.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import holik.hotel.servlet.models.Room;
import holik.hotel.servlet.services.RoomService;
import holik.hotel.servlet.services.impl.DefaultRoomService;

public class RoomsCommand implements Command {
	private RoomService roomService;
	
	public RoomsCommand() {
		roomService = new DefaultRoomService();
	}
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		List<Room> rooms = roomService.getAllRooms();
		request.setAttribute("rooms", rooms);
		return "rooms.jsp";
	}

}
