package holik.hotel.servlet.persistence.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.postgresql.jdbc3.Jdbc3PoolingDataSource;

public final class DBManager {
	
	public static Connection getConnection() {
		Connection connection = null;
		try {
			Context initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			
			DataSource ds = (DataSource)envContext.lookup("jdbc/ST4DB");
			connection = ds.getConnection();
		} catch (NamingException | SQLException ex) {
			ex.printStackTrace();
		}
		return connection;
	}
	
	public static void commitAndClose(Connection connection) {
		try {
			connection.commit();
			connection.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static void rollbackAndClose(Connection connection) {
		try {
			connection.rollback();
			connection.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
}
