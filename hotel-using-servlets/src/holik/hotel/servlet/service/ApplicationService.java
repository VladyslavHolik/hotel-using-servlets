package holik.hotel.servlet.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import holik.hotel.servlet.model.Application;

public interface ApplicationService {
	boolean saveApplication(Application application) throws SQLException;
	Optional<Application> getApplicationById(int id) throws SQLException;
	List<Application> getAllApplications() throws SQLException;
}
