package holik.hotel.servlet.web.command;

import holik.hotel.servlet.repository.model.Bill;
import holik.hotel.servlet.service.BillService;
import holik.hotel.servlet.web.command.constant.Pages;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Command that forwards user to page that contains his bills.
 */
public class GetBillsCommand implements Command {
	private final BillService billService;
	
	public GetBillsCommand(BillService billService) {
		this.billService = billService;
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

	@Override
	public String toString() {
		return "GetBillsCommand";
	}
}
