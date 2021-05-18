package holik.hotel.servlet.web.validator;

import holik.hotel.servlet.repository.model.Application;
import holik.hotel.servlet.repository.model.RoomClass;

import java.time.Duration;
import java.time.LocalDateTime;

public class ApplicationValidator {
    public static void validate(Application application) {
        RoomClass roomClass = application.getRoomClass();
        if (roomClass == null) {
            // room should have class
            throw new IllegalArgumentException("Invalid room class");
        }

        LocalDateTime arrival = application.getDatetimeOfArrival();
        if (arrival == null) {
            // application should have arrival date
            throw new IllegalArgumentException("Invalid arrival date");
        }

        LocalDateTime leaving = application.getDatetimeOfLeaving();
        if (leaving == null) {
            // application should have arrival date
            throw new IllegalArgumentException("Invalid leaving date");
        }

        if (arrival.isAfter(leaving)) {
            // arrival date should be before leaving date
            throw new IllegalArgumentException("Invalid arrival date");
        }

        Duration duration = Duration.between(arrival, leaving);
        if (duration.toHours() < 6) {
            // duration between arrival and leaving should be more than 6 hours
            throw new IllegalArgumentException("Invalid duration between arrival and leaving");
        }

        int space = application.getSpace();
        if (space > 6 || space < 1) {
            // room should have valid space
            throw new IllegalArgumentException("Invalid space");
        }
    }
}
