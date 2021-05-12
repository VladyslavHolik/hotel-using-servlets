package holik.hotel.servlet.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import holik.hotel.servlet.models.Room;
import holik.hotel.servlet.services.RoomService;
import holik.hotel.servlet.services.impl.RoomServiceImpl;


@WebServlet("/rooms")
public class RoomsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RoomService roomService;
	
	@Override
	public void init() {
		roomService = new RoomServiceImpl();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Room> list = roomService.getAllRooms();
		request.setAttribute("list", list);
		RequestDispatcher dispatcher = request.getRequestDispatcher("rooms.jsp");
		dispatcher.forward(request, response);
	}

}
