package holik.hotel.servlet.repository;

import java.util.List;
import java.util.Optional;

import holik.hotel.servlet.repository.model.Application;

/**
 * Interface for application repository.
 */
public interface ApplicationRepository {
	boolean saveApplication(Application application);
	Optional<Application> getApplicationById(int id);
	List<Application> getAllApplications();
	boolean updateApplication(Application application);
    List<Application> getAllRequestedApplications();
}
