package holik.hotel.servlet.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import holik.hotel.servlet.models.Room;
import holik.hotel.servlet.services.RoomService;
import holik.hotel.servlet.services.impl.DefaultRoomService;


@WebServlet("/rooms")
public class RoomsController extends HttpServlet {
	private RoomService roomService;
	
	@Override
	public void init() {
		roomService = new DefaultRoomService();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Room> rooms = roomService.getAllRooms();
		request.setAttribute("rooms", rooms);
		RequestDispatcher dispatcher = request.getRequestDispatcher("rooms.jsp");
		dispatcher.forward(request, response);
	}
}
