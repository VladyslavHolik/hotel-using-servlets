package holik.hotel.servlet.service;

import java.util.List;
import java.util.Optional;

import holik.hotel.servlet.repository.model.Application;
import holik.hotel.servlet.repository.model.ApplicationStatus;
import holik.hotel.servlet.repository.model.Room;

/**
 * Interface for application service.
 */
public interface ApplicationService {
	void saveApplication(Application application);
	Optional<Application> getApplicationById(int id);
	List<Application> getAllApplications();
	List<Application> getApplicationsByStatus(ApplicationStatus status);
	void updateApplication(Application application);
	boolean canBeBooked(Application application);
	void bookRoom(Application application);
	List<Room> getFreeRooms(Application application);
	List<Application> getBookedApplicationsByUserId(int userId);
	List<Application> getReadyToBookApplications(int userId);
}
