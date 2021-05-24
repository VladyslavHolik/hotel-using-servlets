package holik.hotel.servlet.web.validator;

import holik.hotel.servlet.repository.model.Application;
import holik.hotel.servlet.repository.model.ApplicationStatus;
import holik.hotel.servlet.repository.model.RoomClass;
import holik.hotel.servlet.repository.model.User;
import holik.hotel.servlet.service.ApplicationService;
import holik.hotel.servlet.service.UserService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

public class ApplicationValidator {
    private final ApplicationService applicationService;
    private final UserService userService;

    public ApplicationValidator(ApplicationService applicationService, UserService userService) {
        this.applicationService = applicationService;
        this.userService = userService;
    }

    // TODO collect errors
    public void validate(Application application) {
        if (application.getUserId() < 1) {
            // user cannot have id less than 1
            throw new IllegalArgumentException("User does not exist");
        }

        Optional<User> optionalUser = userService.getUserById(application.getUserId());
        if (optionalUser.isEmpty()) {
            // user must exist
            throw new IllegalArgumentException("User does not exist");
        }

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

        LocalDateTime now = LocalDateTime.now();
        if (arrival.isBefore(now.plusHours(23))) {
            // arrival should be more than one day after creating application
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

    public void validateApplicationId(int applicationId) {
        Optional<Application> applicationOptional = applicationService.getApplicationById(applicationId);
        if (applicationOptional.isEmpty()) {
            // non existing application
            throw new IllegalArgumentException("Invalid application id" + applicationId);
        }

        Application application = applicationOptional.get();
        Optional<User> userOptional = userService.getUserById(application.getUserId());

        if (userOptional.isEmpty()) {
            // application cannot have non existing user
            throw new IllegalArgumentException("Invalid user id in application " + applicationId);
        }
    }

    public void validateForBooking(int applicationId, int userId) {
        Optional<Application> optionalApplication = applicationService.getApplicationById(applicationId);
        Application application = optionalApplication.orElseThrow(() -> new IllegalArgumentException("Invalid application id"));

        LocalDateTime datetimeOfLeaving = application.getDatetimeOfLeaving();
        LocalDateTime now = LocalDateTime.now();

        if (datetimeOfLeaving.isBefore(now)) {
            // Application should not be expired
            throw new IllegalArgumentException("Application is expired");
        }

        int originUserId = application.getUserId();

        if (userId != originUserId) {
            // Only authorized user can book room
            throw new IllegalArgumentException("User is not authorized");
        }
        if (!applicationService.canBeBooked(application)) {
            // Can't book room when it is already booked or paid
            throw new IllegalArgumentException("Room is already booked");
        }
    }

    public void validateForPaying(int applicationId, int userId) {
        Optional<Application> optionalApplication = applicationService.getApplicationById(applicationId);
        Application application = optionalApplication.orElseThrow(() -> new IllegalArgumentException("Invalid application id"));
        if (!(userId == application.getUserId() && ApplicationStatus.BOOKED.equals(application.getStatus()))) {
            throw new IllegalStateException("Application can't be paid");
        }
    }
}
