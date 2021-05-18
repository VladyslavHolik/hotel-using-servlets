package holik.hotel.servlet.service.impl;

import java.util.List;
import java.util.Optional;

import holik.hotel.servlet.repository.model.Application;
import holik.hotel.servlet.repository.ApplicationRepository;
import holik.hotel.servlet.repository.impl.DefaultApplicationRepository;
import holik.hotel.servlet.service.ApplicationService;

/**
 * Default realization of application service.
 */
public class DefaultApplicationService implements ApplicationService {
	private final ApplicationRepository applicationRepository;
	
	public DefaultApplicationService() {
		applicationRepository = new DefaultApplicationRepository();
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
}
