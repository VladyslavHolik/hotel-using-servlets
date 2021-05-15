package holik.hotel.servlet.persistence;

import java.util.List;
import java.util.Optional;

import holik.hotel.servlet.model.Application;

public interface ApplicationRepository {
	boolean saveApplication(Application application);
	Optional<Application> getApplicationById(int id);
	List<Application> getAllApplications();
	boolean updateApplication(Application application);
}
