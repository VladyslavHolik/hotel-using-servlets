package holik.hotel.servlet.job;

import holik.hotel.servlet.repository.model.Application;
import holik.hotel.servlet.repository.model.ApplicationStatus;
import holik.hotel.servlet.repository.model.Room;
import holik.hotel.servlet.repository.model.RoomStatus;
import holik.hotel.servlet.service.ApplicationService;
import holik.hotel.servlet.service.RoomService;
import org.apache.log4j.Logger;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Runnable that is responsible for setting current room statuses.
 */
public class RoomStatusSetter implements Runnable {
    private final Logger LOG = Logger.getLogger(RoomStatusSetter.class);
    private final ApplicationService applicationService;
    private final RoomService roomService;

    public RoomStatusSetter(ApplicationService applicationService, RoomService roomService) {
        this.applicationService = applicationService;
        this.roomService = roomService;
    }

    @Override
    public void run() {
        LOG.debug("Room status setting job starts");

        List<Room> availableRooms = roomService.getAvailableRooms();
        for (Room room : availableRooms) {
            room.setRoomStatus(RoomStatus.FREE);
        }

        LocalDateTime now = LocalDateTime.now();
        List<Application> bookedApplications = applicationService.getApplicationsByStatus(ApplicationStatus.BOOKED);
        for (Application application : bookedApplications) {
            if (isNotFreeNow(application, now)) {
                setRoomStatusById(RoomStatus.BOOKED, application.getRoomId(), availableRooms);
            }
        }

        List<Application> paidApplications = applicationService.getApplicationsByStatus(ApplicationStatus.PAID);
        for (Application application : paidApplications) {
            if (isNotFreeNow(application, now)) {
                setRoomStatusById(RoomStatus.BUSY, application.getRoomId(), availableRooms);
            }
        }

        for (Room room : availableRooms) {
            roomService.updateRoom(room);
        }
    }

    private void setRoomStatusById(RoomStatus roomStatus, int roomId, List<Room> rooms) {
        for (Room room : rooms) {
            if (room.getId() == roomId) {
                room.setRoomStatus(roomStatus);
            }
        }
    }

    private boolean isNotFreeNow(Application application, LocalDateTime point) {
        LocalDateTime arrival = application.getDatetimeOfArrival();
        LocalDateTime leaving = application.getDatetimeOfLeaving();
        return point.isAfter(arrival) && point.isBefore(leaving);
    }
}
