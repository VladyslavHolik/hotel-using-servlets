package holik.hotel.servlet.web.validator;

import holik.hotel.servlet.repository.model.Application;
import holik.hotel.servlet.repository.model.ApplicationStatus;
import holik.hotel.servlet.repository.model.RoomClass;
import holik.hotel.servlet.repository.model.User;
import holik.hotel.servlet.service.ApplicationService;
import holik.hotel.servlet.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationValidatorTest {
    @Mock
    private UserService userService;
    @Mock
    private ApplicationService applicationService;
    @InjectMocks
    private ApplicationValidator applicationValidator;

    @Test
    public void validateCorrectApplication() {
        Application application = new Application();
        application.setUserId(1);
        application.setSpace(1);
        application.setRoomId(1);
        application.setRoomClass(RoomClass.FamilyRoom);
        application.setDatetimeOfArrival(LocalDateTime.now().plusDays(1));
        application.setDatetimeOfLeaving(LocalDateTime.now().plusDays(3));

        when(userService.getUserById(1)).thenReturn(Optional.of(new User()));
        applicationValidator.validate(application);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateApplicationWithInvalidSpaceCase1() {
        Application application = new Application();
        application.setUserId(1);
        application.setSpace(-1);
        application.setRoomId(2);
        application.setRoomClass(RoomClass.Apartment);
        application.setDatetimeOfArrival(LocalDateTime.now());
        application.setDatetimeOfLeaving(LocalDateTime.now().plusDays(3));

        applicationValidator.validate(application);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateApplicationWithInvalidSpaceCase2() {
        Application application = new Application();
        application.setUserId(1);
        application.setSpace(7);
        application.setRoomId(2);
        application.setRoomClass(RoomClass.Apartment);
        application.setDatetimeOfArrival(LocalDateTime.now());
        application.setDatetimeOfLeaving(LocalDateTime.now().plusDays(3));

        applicationValidator.validate(application);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateApplicationWithInvalidRoomId() {
        Application application = new Application();
        application.setUserId(1);
        application.setSpace(4);
        application.setRoomId(-2);
        application.setRoomClass(RoomClass.Apartment);
        application.setDatetimeOfArrival(LocalDateTime.now());
        application.setDatetimeOfLeaving(LocalDateTime.now().plusDays(3));

        applicationValidator.validate(application);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateApplicationWithInvalidRoomClass() {
        Application application = new Application();
        application.setUserId(1);
        application.setSpace(-1);
        application.setRoomId(2);
        application.setRoomClass(null);
        application.setDatetimeOfArrival(LocalDateTime.now());
        application.setDatetimeOfLeaving(LocalDateTime.now().plusDays(3));

        applicationValidator.validate(application);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateApplicationWithInvalidArrivalDate() {
        Application application = new Application();
        application.setUserId(1);
        application.setSpace(-1);
        application.setRoomId(2);
        application.setRoomClass(RoomClass.HoneymoonRoom);
        application.setDatetimeOfArrival(null);
        application.setDatetimeOfLeaving(LocalDateTime.now().plusDays(3));

        applicationValidator.validate(application);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateApplicationWithInvalidLeavingDate() {
        Application application = new Application();
        application.setUserId(1);
        application.setSpace(-1);
        application.setRoomId(2);
        application.setRoomClass(RoomClass.HoneymoonRoom);
        application.setDatetimeOfArrival(LocalDateTime.now());
        application.setDatetimeOfLeaving(null);

        applicationValidator.validate(application);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateApplicationWithInvalidArrivalAndLeaving() {
        Application application = new Application();
        application.setUserId(1);
        application.setSpace(-1);
        application.setRoomId(2);
        application.setRoomClass(RoomClass.Apartment);
        application.setDatetimeOfArrival(LocalDateTime.now());
        application.setDatetimeOfLeaving(LocalDateTime.now().plusHours(5));

        applicationValidator.validate(application);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateApplicationWithInvalidUserIdCase1() {
        Application application = new Application();
        application.setUserId(-1);
        application.setSpace(4);
        application.setRoomId(2);
        application.setRoomClass(RoomClass.Apartment);
        application.setDatetimeOfArrival(LocalDateTime.now());
        application.setDatetimeOfLeaving(LocalDateTime.now().plusHours(5));

        applicationValidator.validate(application);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateApplicationWithInvalidUserIdCase2() {
        when(userService.getUserById(23)).thenReturn(Optional.empty());
        Application application = new Application();
        application.setUserId(23);
        application.setSpace(4);
        application.setRoomId(2);
        application.setRoomClass(RoomClass.Apartment);
        application.setDatetimeOfArrival(LocalDateTime.now());
        application.setDatetimeOfLeaving(LocalDateTime.now().plusHours(5));

        applicationValidator.validate(application);
    }

    @Test
    public void validateApplicationId() {
        Optional<User> optionalUser = Optional.of(new User());
        Application application = new Application();
        application.setUserId(1);
        Optional<Application> optionalApplication = Optional.of(application);
        when(applicationService.getApplicationById(3)).thenReturn(optionalApplication);
        when(userService.getUserById(1)).thenReturn(optionalUser);

        applicationValidator.validateApplicationId(3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateApplicationIdWithInvalidApplication() {
        Optional<Application> optionalApplication = Optional.empty();
        when(applicationService.getApplicationById(3)).thenReturn(optionalApplication);

        applicationValidator.validateApplicationId(3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateApplicationIdWithInvalidUser() {
        Optional<User> optionalUser = Optional.empty();
        Application application = new Application();
        application.setUserId(1);
        Optional<Application> optionalApplication = Optional.of(application);
        when(applicationService.getApplicationById(3)).thenReturn(optionalApplication);
        when(userService.getUserById(1)).thenReturn(optionalUser);

        applicationValidator.validateApplicationId(3);
    }

    @Test
    public void validateForBooking() {
        Application application = new Application();
        application.setUserId(1);
        application.setDatetimeOfLeaving(LocalDateTime.now().plusDays(4));

        when(applicationService.getApplicationById(2)).thenReturn(Optional.of(application));
        when(applicationService.canBeBooked(application)).thenReturn(true);

        applicationValidator.validateForBooking(2, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateForBookingWithInvalidApplicationId() {
        applicationValidator.validateForBooking(2, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateForBookingWithInvalidUserId() {
        Application application = new Application();
        application.setUserId(5);
        application.setDatetimeOfLeaving(LocalDateTime.now().plusDays(4));

        when(applicationService.getApplicationById(2)).thenReturn(Optional.of(application));

        applicationValidator.validateForBooking(2, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateForBookingWithInvalidApplication() {
        Application application = new Application();
        application.setUserId(5);
        application.setDatetimeOfLeaving(LocalDateTime.now().plusDays(4));

        when(applicationService.getApplicationById(2)).thenReturn(Optional.of(application));
        when(applicationService.canBeBooked(application)).thenReturn(false);

        applicationValidator.validateForBooking(2, 5);
    }

    @Test
    public void validateForPaying() {
        Application application = new Application();
        application.setUserId(5);
        application.setStatus(ApplicationStatus.BOOKED);

        when(applicationService.getApplicationById(2)).thenReturn(Optional.of(application));

        applicationValidator.validateForPaying(2, 5);
    }

    @Test(expected = IllegalStateException.class)
    public void validateForPayingWithInvalidUser() {
        Application application = new Application();
        application.setUserId(4);
        application.setStatus(ApplicationStatus.BOOKED);

        when(applicationService.getApplicationById(2)).thenReturn(Optional.of(application));

        applicationValidator.validateForPaying(2, 5);
    }

    @Test(expected = IllegalStateException.class)
    public void validateForPayingWithInvalidApplicationStatus() {
        Application application = new Application();
        application.setUserId(5);
        application.setStatus(ApplicationStatus.PAID);

        when(applicationService.getApplicationById(2)).thenReturn(Optional.of(application));

        applicationValidator.validateForPaying(2, 5);
    }
}