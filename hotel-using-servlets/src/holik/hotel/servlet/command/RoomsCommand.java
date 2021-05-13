package holik.hotel.servlet.command;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import holik.hotel.servlet.dto.RoomsContent;
import holik.hotel.servlet.models.Room;
import holik.hotel.servlet.path.Path;
import holik.hotel.servlet.services.RoomService;
import holik.hotel.servlet.services.impl.DefaultRoomService;

public class RoomsCommand implements Command {
	private static final Logger LOG = Logger.getLogger(RoomsCommand.class);
	private RoomService roomService;
	
	public RoomsCommand() {
		roomService = new DefaultRoomService();
	}
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String pageNumberString = request.getParameter("page");
		int pageNumber = Integer.parseInt(pageNumberString);
		
		String errorMessage = null;		
		String forward = Path.PAGE__ERROR_PAGE;
		
		List<Room> rooms = null;
		try {
			rooms = roomService.getAllRooms();
		} catch (SQLException e) {
			errorMessage = "Something went wrong";
			request.setAttribute("errorMessage", errorMessage);
			LOG.error("SQL Exception occurred while getting all rooms " + e.getLocalizedMessage());
			return forward;	
		}
		
		int numberOfPages = (int) Math.ceil(rooms.size() / 4.0);
		
		if (pageNumber > numberOfPages || pageNumber < 1) {
			errorMessage = "Incorrect page number";
			request.setAttribute("errorMessage", errorMessage);
			return forward;
		}
		
		RoomsContent roomsContent = new RoomsContent();
		List<RoomsContent.Page> pages = new ArrayList<>();
		for (int currentPage = 1; currentPage <= numberOfPages; currentPage++) {
			RoomsContent.Page page = new RoomsContent.Page();
			page.setName("" + currentPage);
			String pageClass = "page-item";
			if (currentPage == pageNumber) {
				pageClass += " active";
			}
			page.setPageClass(pageClass);
			pages.add(page);
		}
		roomsContent.setPages(pages);
		
		int startRoomIndex = (pageNumber - 1) * 4;
		int endRoomIndex = startRoomIndex + 3 > rooms.size() - 1 ? rooms.size() - 1 : startRoomIndex + 3;
		List<Room> roomsOnPage = new ArrayList<>();
		for (int i = startRoomIndex; i <= endRoomIndex; i++) {
			roomsOnPage.add(rooms.get(i));
		}
		
		roomsContent.setRooms(roomsOnPage);
		request.setAttribute("roomsContent", roomsContent);
		return "rooms.jsp";
	}

	@Override
	public String toString() {
		return "RoomsCommand";
	}
}
