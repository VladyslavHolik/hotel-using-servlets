package holik.hotel.servlet.service.impl;

import holik.hotel.servlet.repository.ApplicationRepository;
import holik.hotel.servlet.repository.model.Application;
import holik.hotel.servlet.repository.model.ApplicationStatus;
import holik.hotel.servlet.repository.model.Room;
import holik.hotel.servlet.repository.model.RoomAvailability;
import holik.hotel.servlet.service.ApplicationService;
import holik.hotel.servlet.service.RoomService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Default realization of application service.
 */
public class DefaultApplicationService implements ApplicationService {
	private final ApplicationRepository applicationRepository;
	private final RoomService roomRepository;
	
	public DefaultApplicationService(ApplicationRepository applicationRepository, RoomService roomRepository) {
		this.applicationRepository = applicationRepository;
		this.roomRepository = roomRepository;
	}
	
	@Override
	public boolean saveApplication(Application application) {
		return applicationRepository.saveApplication(application);
	}

	@Override
	public Optional<Application> getApplicationById(int id) {
		return applicationRepository.getApplicationById(id);
	}

	@Override
	public List<Application> getAllApplications() {
		return applicationRepository.getAllApplications();
	}

	@Override
	public List<Application> getAllRequestedApplications() {
		return applicationRepository.getAllRequestedApplications();
	}

	@Override
	public boolean updateApplication(Application application) {
		return applicationRepository.updateApplication(application);
	}

	@Override
	public boolean canBeBooked(Application application) {
		return applicationRepository.canBeBooked(application);
	}

	@Override
	public void bookRoom(Application application) {
		application.setStatus(ApplicationStatus.BOOKED);
		application.setDatetimeOfBooking(LocalDateTime.now());
		updateApplication(application);
	}

	@Override
	public List<Room> getFreeRooms(Application application) {
		int roomClassId = application.getRoomClass().getId();
		int space = application.getSpace();
		int status = RoomAvailability.AVAILABLE.getId();
		List<Room> availableRooms = roomRepository.getSpecificRooms(roomClassId, space, status);
		List<Room> freeRooms = new ArrayList<>();

		for (Room room : availableRooms) {
			if (isAvailable(room, application)) {
				freeRooms.add(room);
			}
		}
		return freeRooms;
	}

	@Override
	public List<Application> getBookedApplicationsByUserId(int userId) {
		return applicationRepository.getBookedApplicationsByUserId(userId);
	}

	private boolean isAvailable(Room room, Application application) {
		boolean result = true;
		LocalDateTime datetimeOfArrival = application.getDatetimeOfArrival();
		LocalDateTime datetimeOfLeaving = application.getDatetimeOfLeaving();

		List<Application> allApplications = getAllApplications();
		for (Application originApplication : allApplications) {
			if ((originApplication.getStatus().equals(ApplicationStatus.PAID)
					|| originApplication.getStatus().equals(ApplicationStatus.BOOKED))
					&& originApplication.getRoomId() == room.getId()
					&& (isBetween(datetimeOfArrival, originApplication.getDatetimeOfArrival(),
					originApplication.getDatetimeOfLeaving())
					|| isBetween(datetimeOfLeaving, originApplication.getDatetimeOfArrival(),
					originApplication.getDatetimeOfLeaving()))) {
				result = false;
				break;
			}
		}
		return result;
	}

	private boolean isBetween(LocalDateTime origin, LocalDateTime start, LocalDateTime end) {
		return origin.isAfter(start) && origin.isBefore(end);
	}
}
