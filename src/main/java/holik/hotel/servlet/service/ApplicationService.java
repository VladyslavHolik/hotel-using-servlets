package holik.hotel.servlet.service;

import holik.hotel.servlet.repository.model.Application;
import holik.hotel.servlet.repository.model.ApplicationStatus;
import holik.hotel.servlet.repository.model.Room;

import java.util.List;
import java.util.Optional;

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
	void processApplication(int applicationId, String choice);
}
