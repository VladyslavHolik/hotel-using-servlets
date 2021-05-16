package holik.hotel.servlet.listener;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import holik.hotel.servlet.booking.BookingRemover;

/**
 * Listener that sets log4j and executor.
 */
public class ContextListener implements ServletContextListener {
	private static final Logger LOG = Logger.getLogger(ContextListener.class);
	private static final int RATE = 5;
	private static final TimeUnit TIME_UNIT = TimeUnit.MINUTES;
	private ScheduledExecutorService executor;
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		// setting log4j
        ServletContext context = event.getServletContext();
        String log4jConfigFile = context.getInitParameter("log4j-config-location");
        String fullPath = context.getRealPath("") + File.separator + log4jConfigFile;
         
        PropertyConfigurator.configure(fullPath);
        
        // setting executor
        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(new BookingRemover(), 0, RATE, TIME_UNIT);
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		// shutdown executor
		executor.shutdown();
		try {
			if (!executor.awaitTermination(3, TimeUnit.SECONDS)) {
				executor.shutdownNow();
			}
		} catch (InterruptedException exception) {
			LOG.error("Interrupted exception " + exception.getLocalizedMessage());
		}
	}
}
