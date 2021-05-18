package holik.hotel.servlet.service.impl;

import holik.hotel.servlet.repository.model.Application;
import holik.hotel.servlet.repository.model.Bill;
import holik.hotel.servlet.repository.model.Room;
import holik.hotel.servlet.service.ApplicationService;
import holik.hotel.servlet.service.BillService;
import holik.hotel.servlet.service.RoomService;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DefaultBillService implements BillService {
    private final ApplicationService applicationService;
    private final RoomService roomService;

    public DefaultBillService(ApplicationService applicationService, RoomService roomService) {
        this.applicationService = applicationService;
        this.roomService = roomService;
    }

    @Override
    public List<Bill> getUserBills(int userId) {
        List<Bill> bills = new ArrayList<>();

        List<Application> bookedApplications = applicationService.getBookedApplicationsByUserId(userId);
        for (Application application : bookedApplications) {
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
        return bills;
    }
}
