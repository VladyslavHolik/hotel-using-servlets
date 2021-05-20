package holik.hotel.servlet.service.impl;

import holik.hotel.servlet.repository.RoomRepository;
import holik.hotel.servlet.repository.model.Room;
import holik.hotel.servlet.service.RoomService;
import holik.hotel.servlet.web.command.sort.SortMethod;
import holik.hotel.servlet.web.dto.RoomsContent;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class DefaultRoomServiceTest {
    private RoomRepository roomRepository;
    private RoomService roomService;

    @Before
    public void setUp() {
        roomRepository = mock(RoomRepository.class);
        roomService = new DefaultRoomService(roomRepository);
    }

    @Test
    public void getAllRooms() {
        roomService.getAllRooms();
        verify(roomRepository).getAllRooms();
    }

    @Test
    public void getRoomById() {
        roomService.getRoomById(3);
        verify(roomRepository).getRoomById(3);
    }

    @Test
    public void getSpecificRooms() {
        roomService.getSpecificRooms(1, 1, 1);
        verify(roomRepository).getSpecificRooms(1, 1, 1);
    }

    @Test
    public void updateRoom() {
        Room room = new Room();
        roomService.updateRoom(room);
        verify(roomRepository).updateRoom(room);
    }

    @Test
    public void getAvailableRooms() {
        roomService.getAvailableRooms();
        verify(roomRepository).getAvailableRooms();
    }

    @Test
    public void getRoomsContent() {
        RoomsContent roomsContent = roomService.getRoomsContent(2, 5);
        List<RoomsContent.Page> pages = roomsContent.getPages();

        assertEquals(5, pages.size());
        assertEquals("1", pages.get(0).getName());
        assertEquals("2", pages.get(1).getName());
        assertEquals("3", pages.get(2).getName());
        assertEquals("4", pages.get(3).getName());
        assertEquals("5", pages.get(4).getName());

        assertEquals("page-item", pages.get(0).getPageClass());
        assertEquals("page-item active", pages.get(1).getPageClass());
        assertEquals("page-item", pages.get(2).getPageClass());
        assertEquals("page-item", pages.get(3).getPageClass());
        assertEquals("page-item", pages.get(4).getPageClass());
    }

    @Test
    public void getNumberOfPages() {
        List<Room> rooms = new ArrayList<>();
        rooms.add(new Room());
        rooms.add(new Room());
        rooms.add(new Room());
        rooms.add(new Room());
        rooms.add(new Room());

        when(roomRepository.getAllRooms()).thenReturn(rooms);
        assertEquals(2, roomService.getNumberOfPages());
    }

    @Test
    public void getRoomsOnPageWhenSortingByPrice() {
        roomService.getRoomsOnPage(2, SortMethod.PRICE);
        verify(roomRepository).getRoomsOrderedByPrice(3, 3);
    }

    @Test
    public void getRoomsOnPageWhenSortingBySpace() {
        roomService.getRoomsOnPage(3, SortMethod.SPACE);
        verify(roomRepository).getRoomsOrderedBySpace(3, 6);
    }

    @Test
    public void getRoomsOnPageWhenSortingByClass() {
        roomService.getRoomsOnPage(5, SortMethod.CLASS);
        verify(roomRepository).getRoomsOrderedByClass(3, 12);
    }

    @Test
    public void getRoomsOnPageWhenSortingByStatus() {
        roomService.getRoomsOnPage(1, SortMethod.STATUS);
        verify(roomRepository).getRoomsOrderedByStatus(3, 0);
    }
}