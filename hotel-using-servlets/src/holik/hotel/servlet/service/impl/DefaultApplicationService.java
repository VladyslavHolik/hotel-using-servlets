package holik.hotel.servlet.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import holik.hotel.servlet.model.Application;
import holik.hotel.servlet.persistence.ApplicationRepository;
import holik.hotel.servlet.persistence.impl.DefaultApplicationRepository;
import holik.hotel.servlet.service.ApplicationService;

public class DefaultApplicationService implements ApplicationService {
	private ApplicationRepository applicationRepository;
	
	public DefaultApplicationService() {
		applicationRepository = new DefaultApplicationRepository();
	}
	
	@Override
	public boolean saveApplication(Application application) throws SQLException {
		return applicationRepository.saveApplication(application);
	}

	@Override
	public Optional<Application> getApplicationById(int id) throws SQLException {
		return applicationRepository.getApplicationById(id);
	}

	@Override
	public List<Application> getAllApplications() throws SQLException {
		return applicationRepository.getAllApplications();
	}
}
