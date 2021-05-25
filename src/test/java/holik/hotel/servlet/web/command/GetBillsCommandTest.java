package holik.hotel.servlet.web.command;

import holik.hotel.servlet.repository.model.Bill;
import holik.hotel.servlet.service.BillService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetBillsCommandTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private BillService billService;
    @InjectMocks
    private GetBillsCommand getBillsCommand;

    @Test
    public void execute() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("userId")).thenReturn(5);

        List<Bill> bills = new ArrayList<>();
        when(billService.getUserBills(5)).thenReturn(bills);

        getBillsCommand.execute(request, response);
        verify(request).setAttribute("bills", bills);
    }
}