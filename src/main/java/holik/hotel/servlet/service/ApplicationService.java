package holik.hotel.servlet.service;

import java.util.List;
import java.util.Optional;

import holik.hotel.servlet.model.Application;

/**
 * Interface for application service.
 */
public interface ApplicationService {
	boolean saveApplication(Application application);
	Optional<Application> getApplicationById(int id);
	List<Application> getAllApplications();
	boolean updateApplication(Application application);
}
