package holik.hotel.servlet.service.impl;

import holik.hotel.servlet.repository.ApplicationRepository;
import holik.hotel.servlet.repository.model.Application;
import holik.hotel.servlet.repository.model.ApplicationStatus;
import holik.hotel.servlet.repository.model.Room;
import holik.hotel.servlet.repository.model.RoomClass;
import holik.hotel.servlet.service.ApplicationService;
import holik.hotel.servlet.service.RoomService;
import org.checkerframework.checker.units.qual.A;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class DefaultApplicationServiceTest {
    private ApplicationRepository applicationRepository;
    private RoomService roomService;
    private ApplicationService applicationService;

    @Before
    public void setUp() throws Exception {
        applicationRepository = mock(ApplicationRepository.class);
        roomService = mock(RoomService.class);
        applicationService = new DefaultApplicationService(applicationRepository, roomService);
    }

    @Test
    public void saveApplication() {
        Application application = new Application();
        applicationService.saveApplication(application);
        verify(applicationRepository).saveApplication(application);
    }

    @Test
    public void getApplicationById() {
        applicationService.getApplicationById(3);
        verify(applicationRepository).getApplicationById(3);
    }

    @Test
    public void getAllApplications() {
        applicationService.getAllApplications();
        verify(applicationRepository).getAllApplications();
    }

    @Test
    public void getApplicationsByStatus() {
        applicationService.getApplicationsByStatus(ApplicationStatus.BOOKED);
        verify(applicationRepository).getApplicationsByStatus(ApplicationStatus.BOOKED);
    }

    @Test
    public void updateApplication() {
        Application application = new Application();
        applicationService.updateApplication(application);
        verify(applicationRepository).updateApplication(application);
    }

    @Test
    public void canBeBooked() {
        Application application = new Application();
        when(applicationRepository.canBeBooked(application)).thenReturn(true);

        boolean result = applicationService.canBeBooked(application);
        verify(applicationRepository).canBeBooked(application);
        assertTrue(result);
    }

    @Test
    public void bookRoom() {
        Application application = new Application();
        applicationService.bookRoom(application);

        verify(applicationRepository).updateApplication(application);
        assertEquals(ApplicationStatus.BOOKED, application.getStatus());
        assertTrue(application.getDatetimeOfBooking().isBefore(LocalDateTime.now()));
        assertTrue(application.getDatetimeOfBooking().isAfter(LocalDateTime.now().minusMinutes(1)));
    }

    @Test
    public void getFreeRooms() {
        List<Room> availableRooms = new ArrayList<>();
        Room firstRoom = new Room();
        Room secondRoom = new Room();
        Room thirdRoom = new Room();
        Room fourthRoom = new Room();

        firstRoom.setId(2);
        secondRoom.setId(3);
        thirdRoom.setId(4);
        fourthRoom.setId(5);

        availableRooms.add(firstRoom);
        availableRooms.add(secondRoom);
        availableRooms.add(thirdRoom);
        availableRooms.add(fourthRoom);

        when(roomService.getSpecificRooms(4, 5, 1)).thenReturn(availableRooms);

        List<Application> bookedApplications = new ArrayList<>();
        Application bookedApplication = new Application();
        bookedApplication.setRoomId(2);
        bookedApplication.setDatetimeOfArrival(LocalDateTime.now().minusDays(1));
        bookedApplication.setDatetimeOfLeaving(LocalDateTime.now().plusDays(1));
        bookedApplications.add(bookedApplication);

        List<Application> paidApplications = new ArrayList<>();
        Application paidApplication = new Application();
        paidApplication.setRoomId(3);
        paidApplication.setDatetimeOfArrival(LocalDateTime.now().minusDays(1));
        paidApplication.setDatetimeOfLeaving(LocalDateTime.now().plusDays(1));
        paidApplications.add(paidApplication);

        Application paidApplicationButOnAnotherTime = new Application();
        paidApplicationButOnAnotherTime.setRoomId(3);
        paidApplicationButOnAnotherTime.setDatetimeOfArrival(LocalDateTime.now().plusDays(3));
        paidApplicationButOnAnotherTime.setDatetimeOfLeaving(LocalDateTime.now().plusDays(5));
        paidApplications.add(paidApplicationButOnAnotherTime);

        when(applicationRepository.getApplicationsByStatus(ApplicationStatus.BOOKED)).thenReturn(bookedApplications);
        when(applicationRepository.getApplicationsByStatus(ApplicationStatus.PAID)).thenReturn(paidApplications);

        Application myApplication = new Application();
        myApplication.setRoomClass(RoomClass.Business);
        myApplication.setSpace(5);
        myApplication.setDatetimeOfArrival(LocalDateTime.now());
        myApplication.setDatetimeOfLeaving(LocalDateTime.now().plusHours(7));

        List<Room> freeRooms = applicationService.getFreeRooms(myApplication);

        verify(roomService).getSpecificRooms(4,5,1);
        verify(applicationRepository, times(4)).getApplicationsByStatus(ApplicationStatus.BOOKED);
        verify(applicationRepository, times(4)).getApplicationsByStatus(ApplicationStatus.PAID);

        assertEquals(2, freeRooms.size());
        assertTrue(freeRooms.contains(thirdRoom));
        assertTrue(freeRooms.contains(fourthRoom));
    }

    @Test
    public void getFreeRoomsIfAllUnavailable() {
        List<Room> availableRooms = new ArrayList<>();

        when(roomService.getSpecificRooms(5, 1, 1)).thenReturn(availableRooms);

        Application application = new Application();
        application.setRoomClass(RoomClass.Bedroom);
        application.setSpace(1);

        List<Room> freeRooms = applicationService.getFreeRooms(application);
        assertTrue(freeRooms.isEmpty());
    }


    @Test
    public void getFreeRoomsIfAllFree() {
        List<Room> availableRooms = new ArrayList<>();
        Room firstRoom = new Room();
        Room secondRoom = new Room();
        Room thirdRoom = new Room();
        Room fourthRoom = new Room();

        firstRoom.setId(2);
        secondRoom.setId(3);
        thirdRoom.setId(4);
        fourthRoom.setId(5);

        availableRooms.add(firstRoom);
        availableRooms.add(secondRoom);
        availableRooms.add(thirdRoom);
        availableRooms.add(fourthRoom);

        when(roomService.getSpecificRooms(2, 3, 1)).thenReturn(availableRooms);
        when(applicationRepository.getApplicationsByStatus(ApplicationStatus.BOOKED)).thenReturn(new ArrayList<>());
        when(applicationRepository.getApplicationsByStatus(ApplicationStatus.PAID)).thenReturn(new ArrayList<>());

        Application application = new Application();
        application.setRoomClass(RoomClass.Balcony);
        application.setSpace(3);

        List<Room> freeRooms = applicationService.getFreeRooms(application);

        verify(roomService).getSpecificRooms(2,3,1);
        verify(applicationRepository, times(4)).getApplicationsByStatus(ApplicationStatus.BOOKED);
        verify(applicationRepository, times(4)).getApplicationsByStatus(ApplicationStatus.PAID);

        assertEquals(availableRooms, freeRooms);
    }

    @Test
    public void getBookedApplicationsByUserId() {
        applicationService.getBookedApplicationsByUserId(3);
        verify(applicationRepository).getBookedApplicationsByUserId(3);
    }

    @Test
    public void getReadyToBookApplications() {
        List<Application> approvedApplications = new ArrayList<>();
        Application firstApplication = new Application();
        Application secondApplication = new Application();
        Application thirdApplication = new Application();

        firstApplication.setUserId(3);
        secondApplication.setUserId(3);
        thirdApplication.setUserId(4);

        firstApplication.setId(1);
        secondApplication.setId(2);
        thirdApplication.setId(3);

        approvedApplications.add(firstApplication);
        approvedApplications.add(secondApplication);
        approvedApplications.add(thirdApplication);

        when(applicationRepository.getApplicationsByStatus(ApplicationStatus.APPROVED)).thenReturn(approvedApplications);
        when(applicationRepository.canBeBooked(firstApplication)).thenReturn(true);
        when(applicationRepository.canBeBooked(secondApplication)).thenReturn(false);

        List<Application> readyToBookApplications = applicationService.getReadyToBookApplications(3);

        verify(applicationRepository).canBeBooked(firstApplication);
        verify(applicationRepository).canBeBooked(secondApplication);

        assertEquals(1, readyToBookApplications.size());
        assertTrue(readyToBookApplications.contains(firstApplication));
    }

    @Test
    public void processApplicationWhenDeclined() {
        Application application = new Application();
        when(applicationRepository.getApplicationById(2)).thenReturn(Optional.of(application));

        applicationService.processApplication(2, "decline");

        assertEquals(ApplicationStatus.DECLINED, application.getStatus());
        verify(applicationRepository).updateApplication(application);
    }

    @Test
    public void processApplicationWhenApproved() {
        Application application = new Application();
        when(applicationRepository.getApplicationById(2)).thenReturn(Optional.of(application));

        applicationService.processApplication(2, "4");

        assertEquals(ApplicationStatus.APPROVED, application.getStatus());
        assertEquals(4, application.getRoomId());
        verify(applicationRepository).updateApplication(application);
    }

    @Test(expected = NoSuchElementException.class)
    public void processApplicationWhenNotExist() {
        when(applicationRepository.getApplicationById(3)).thenReturn(Optional.empty());
        applicationService.processApplication(3, "4");
    }
}