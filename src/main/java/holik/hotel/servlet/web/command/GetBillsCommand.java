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

import holik.hotel.servlet.service.BillService;
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
	private final BillService billService;
	
	public GetBillsCommand() {
		billService = ApplicationContext.getBillService();
	}
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		HttpSession session = request.getSession();
		int userId = (int) session.getAttribute("userId");
		List<Bill> bills = billService.getUserBills(userId);
		
		request.setAttribute("bills", bills);
		return Pages.PAGE_BILLS;
	}
}
