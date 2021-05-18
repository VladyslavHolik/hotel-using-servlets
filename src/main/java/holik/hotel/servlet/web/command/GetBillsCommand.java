package holik.hotel.servlet.web.command;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import holik.hotel.servlet.web.command.constant.Pages;
import holik.hotel.servlet.repository.model.Application;
import holik.hotel.servlet.repository.model.ApplicationStatus;
import holik.hotel.servlet.repository.model.Bill;
import holik.hotel.servlet.repository.model.Room;
import holik.hotel.servlet.service.ApplicationService;
import holik.hotel.servlet.service.RoomService;
import holik.hotel.servlet.service.impl.DefaultApplicationService;
import holik.hotel.servlet.service.impl.DefaultRoomService;
import holik.hotel.servlet.web.context.ApplicationContext;

/**
 * Command that forwards user to page that contains his bills.
 */
public class GetBillsCommand implements Command {
	private final ApplicationService applicationService;
	private final RoomService roomService;
	
	public GetBillsCommand() {
		applicationService = ApplicationContext.getApplicationService();
		roomService = ApplicationContext.getRoomService();
	}
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		HttpSession session = request.getSession();
		int userId = (int) session.getAttribute("userId");
		List<Application> allApplications = applicationService.getAllApplications();
		List<Bill> bills = getUserBills(userId, allApplications);
		
		request.setAttribute("bills", bills);
		return Pages.PAGE_BILLS;
	}

	private List<Bill> getUserBills(int userId, List<Application> allApplications) {
		List<Bill> bills = new ArrayList<>();
		
		for (Application application : allApplications) {
			if (application.getUserId() == userId && 
					ApplicationStatus.BOOKED.equals(application.getStatus())) {
				Duration duration = Duration.between(application.getDatetimeOfArrival(),
						application.getDatetimeOfLeaving());
				long hours = duration.toHours();
				Optional<Room> optionalRoom = roomService.getRoomById(application.getRoomId());
				if (optionalRoom.isPresent()) {
					Bill bill = new Bill();
					
					Room room = optionalRoom.get();
					long price = hours * room.getPrice();
					
					bill.setApplication(application);
					bill.setRoom(room);
					bill.setPrice(price);
					bills.add(bill);
				}
			}
		}
		return bills;
	}

}
