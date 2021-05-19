package holik.hotel.servlet.repository;

import java.util.List;
import java.util.Optional;

import holik.hotel.servlet.repository.model.Application;
import holik.hotel.servlet.repository.model.ApplicationStatus;

/**
 * Interface for application repository.
 */
public interface ApplicationRepository {
	void saveApplication(Application application);
	Optional<Application> getApplicationById(int id);
	List<Application> getAllApplications();
	void updateApplication(Application application);
    List<Application> getApplicationsByStatus(ApplicationStatus status);
    boolean canBeBooked(Application application);
	List<Application> getBookedApplicationsByUserId(int userId);
}
