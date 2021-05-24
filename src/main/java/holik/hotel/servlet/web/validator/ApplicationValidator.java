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

    public void validate(Application application) {
        StringBuilder errors = new StringBuilder();

        if (application.getUserId() < 1) {
            // user cannot have id less than 1
            errors.append("Invalid user id;");
        }

        Optional<User> optionalUser = userService.getUserById(application.getUserId());
        if (optionalUser.isEmpty()) {
            // user must exist
            errors.append("User does not exist;");
        }

        RoomClass roomClass = application.getRoomClass();
        if (roomClass == null) {
            // room should have class
            errors.append("Invalid room class;");
        }

        int space = application.getSpace();
        if (space > 6 || space < 1) {
            // room should have valid space
            errors.append("Invalid space;");
        }

        LocalDateTime arrival = application.getDatetimeOfArrival();
        if (arrival == null) {
            // application should have arrival date
            errors.append("Invalid arrival date;");
        }

        LocalDateTime leaving = application.getDatetimeOfLeaving();
        if (leaving == null) {
            // application should have arrival date
            errors.append("Invalid leaving date;");
        }

        if (arrival != null && leaving != null) {
            LocalDateTime now = LocalDateTime.now();
            if (arrival.isBefore(now.plusHours(23))) {
                // arrival should be more than one day after creating application
                errors.append("Arrival should be more than one day after creating application;");
            }

            if (arrival.isAfter(leaving)) {
                // arrival date should be before leaving date
                errors.append("Arrival date should be before leaving date;");
            }

            Duration duration = Duration.between(arrival, leaving);
            if (duration.toHours() < 6) {
                // duration between arrival and leaving should be more than 6 hours
                errors.append("Invalid duration between arrival and leaving;");
            }
        }

        if (!errors.toString().isEmpty()) {
            throw new IllegalArgumentException(errors.toString());
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

        StringBuilder errors = new StringBuilder();

        if (datetimeOfLeaving.isBefore(now)) {
            // Application should not be expired
            errors.append("Application is expired;");
        }

        int originUserId = application.getUserId();

        if (userId != originUserId) {
            // Only authorized user can book room
            errors.append("User is not authorized;");
        }

        if (!errors.toString().isEmpty()) {
            throw new IllegalArgumentException(errors.toString());
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
