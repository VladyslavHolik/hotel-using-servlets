package holik.hotel.servlet.web.command;

import holik.hotel.servlet.repository.model.Room;
import holik.hotel.servlet.service.RoomService;
import holik.hotel.servlet.web.command.constant.Pages;
import holik.hotel.servlet.web.command.sort.SortMethod;
import holik.hotel.servlet.web.dto.RoomsContent;
import holik.hotel.servlet.web.validator.PageValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Command that forwards user to rooms page.
 */
public class RoomsCommand implements Command {
	private final RoomService roomService;
	private final PageValidator pageValidator;

	public RoomsCommand(RoomService roomService, PageValidator pageValidator) {
		this.roomService = roomService;
		this.pageValidator = pageValidator;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String pageNumberString = request.getParameter("page");
		if (pageNumberString == null) {
			pageNumberString = "1";
		}
		int pageNumber = Integer.parseInt(pageNumberString);
		pageValidator.validatePage(pageNumber);
		
		SortMethod method = getSortMethod(request);
		int numberOfPages = roomService.getNumberOfPages();

		RoomsContent roomsContent = roomService.getRoomsContent(pageNumber, numberOfPages);
		List<Room> roomsOnPage = roomService.getRoomsOnPage(pageNumber, method);

		roomsContent.setRooms(roomsOnPage);
		request.setAttribute("roomsContent", roomsContent);
		return Pages.PAGE_ROOMS;
	}

	private SortMethod getSortMethod(HttpServletRequest request) {
		SortMethod method;
		HttpSession session = request.getSession();
		Object methodObject = session.getAttribute("sort");
		if (methodObject == null) {
			method = SortMethod.CLASS;
		} else {
			method = (SortMethod) methodObject;
		}
		return method;
	}

	@Override
	public String toString() {
		return "RoomsCommand";
	}
}
