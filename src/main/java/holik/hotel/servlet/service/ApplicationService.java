package holik.hotel.servlet.service;

import java.util.List;
import java.util.Optional;

import holik.hotel.servlet.repository.model.Application;

/**
 * Interface for application service.
 */
public interface ApplicationService {
	boolean saveApplication(Application application);
	Optional<Application> getApplicationById(int id);
	List<Application> getAllApplications();
	List<Application> getAllRequestedApplications();
	boolean updateApplication(Application application);
}
