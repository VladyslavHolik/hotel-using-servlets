package holik.hotel.servlet.service.impl;

import holik.hotel.servlet.repository.model.Application;
import holik.hotel.servlet.repository.model.Bill;
import holik.hotel.servlet.repository.model.Room;
import holik.hotel.servlet.service.ApplicationService;
import holik.hotel.servlet.service.RoomService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultBillServiceTest {
    @Mock
    private ApplicationService applicationService;
    @Mock
    private RoomService roomService;
    @InjectMocks
    private DefaultBillService billService;

    @Test
    public void getUserBills() {
        List<Application> bookedApplications = new ArrayList<>();
        Application firstApplication = new Application();
        Application secondApplication = new Application();
        Application thirdApplication = new Application();

        LocalDateTime now = LocalDateTime.now();
        firstApplication.setDatetimeOfArrival(now);
        secondApplication.setDatetimeOfArrival(now);
        thirdApplication.setDatetimeOfArrival(now);

        firstApplication.setDatetimeOfLeaving(now.plusDays(1));
        secondApplication.setDatetimeOfLeaving(now.plusDays(2));
        thirdApplication.setDatetimeOfLeaving(now.plusDays(3));

        firstApplication.setRoomId(1);
        secondApplication.setRoomId(2);
        thirdApplication.setRoomId(3);

        bookedApplications.add(firstApplication);
        bookedApplications.add(secondApplication);
        bookedApplications.add(thirdApplication);

        Room firstRoom = new Room();
        Room secondRoom = new Room();
        Room thirdRoom = new Room();

        firstRoom.setId(1);
        secondRoom.setId(2);
        thirdRoom.setId(3);

        firstRoom.setPrice(2);
        secondRoom.setPrice(3);
        thirdRoom.setPrice(4);

        when(roomService.getRoomById(1)).thenReturn(Optional.of(firstRoom));
        when(roomService.getRoomById(2)).thenReturn(Optional.of(secondRoom));
        when(roomService.getRoomById(3)).thenReturn(Optional.of(thirdRoom));
        when(applicationService.getBookedApplicationsByUserId(5)).thenReturn(bookedApplications);

        List<Bill> bills = billService.getUserBills(5);

        verify(roomService).getRoomById(1);
        verify(roomService).getRoomById(2);
        verify(roomService).getRoomById(3);
        verify(applicationService).getBookedApplicationsByUserId(5);

        Bill firstBill = bills.get(0);
        Bill secondBill = bills.get(1);
        Bill thirdBill = bills.get(2);

        assertEquals(3, bills.size());
        assertEquals(firstApplication, firstBill.getApplication());
        assertEquals(secondApplication, secondBill.getApplication());
        assertEquals(thirdApplication, thirdBill.getApplication());

        assertEquals(1, firstBill.getRoom().getId());
        assertEquals(2, secondBill.getRoom().getId());
        assertEquals(3, thirdBill.getRoom().getId());

        assertEquals(2 * 24, firstBill.getPrice(), 0.0);
        assertEquals(3 * 24 * 2, secondBill.getPrice(), 0.0);
        assertEquals(4 * 24 * 3, thirdBill.getPrice(), 0.0);
    }

    @Test
    public void getUserBillsWhenNoBills() {
        when(applicationService.getBookedApplicationsByUserId(2)).thenReturn(new ArrayList<>());
        List<Bill> bills = billService.getUserBills(2);
        assertEquals(0, bills.size());
    }
}