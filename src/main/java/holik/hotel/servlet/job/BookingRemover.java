package holik.hotel.servlet.job;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.log4j.Logger;

import holik.hotel.servlet.repository.model.Application;
import holik.hotel.servlet.repository.model.ApplicationStatus;
import holik.hotel.servlet.service.ApplicationService;
import holik.hotel.servlet.service.impl.DefaultApplicationService;

/**
 * Runnable class that is responsible for declining unpaid orders.
 * The application will be declined if more than 48 hours have passed since the time of booking.
 */
public class BookingRemover implements Runnable {
	private static final Logger LOG = Logger.getLogger(BookingRemover.class);
	private final ApplicationService applicationService;
	
	public BookingRemover() {
		applicationService = new DefaultApplicationService();
	}
	
	@Override
	public void run() {
		LOG.debug("Job starts");
		
		List<Application> allApplications = applicationService.getAllApplications();
		LocalDateTime now = LocalDateTime.now();
		
		for (Application application : allApplications) {
			if (ApplicationStatus.BOOKED.equals(application.getStatus())) {
				LocalDateTime booked = application.getDatetimeOfBooking();
				Duration duration = Duration.between(booked, now);
				if (duration.toHours() > 48) {
					LOG.debug("Setting application to declined, id " + application.getId());
					application.setStatus(ApplicationStatus.DECLINED);
					applicationService.updateApplication(application);
				}
			}
		}
	}

}
