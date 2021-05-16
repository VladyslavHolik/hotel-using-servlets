package holik.hotel.servlet.persistence.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

/**
 * Class that realizes actions with database.
 */
public final class DBManager {
	private static final Logger LOG = Logger.getLogger(DBManager.class);
	
	public static Connection getConnection() {
		Connection connection = null;
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");

			DataSource ds = (DataSource) envContext.lookup("jdbc/ST4DB");
			connection = ds.getConnection();
		} catch (NamingException | SQLException exception) {
			LOG.error("Exception occurred " + exception.getLocalizedMessage());
		}
		return connection;
	}

	public static void commitAndClose(Connection connection) {
		try {
			connection.commit();
			connection.close();
		} catch (SQLException exception) {
			LOG.error("Exception occurred while commiting connection" + exception.getLocalizedMessage());
		}
	}

	public static void rollbackAndClose(Connection connection){
		try {
			connection.rollback();
			connection.close();
		} catch (SQLException exception) {
			LOG.error("Exception occurred while rollbacking connection" + exception.getLocalizedMessage());
		}
	}
}
