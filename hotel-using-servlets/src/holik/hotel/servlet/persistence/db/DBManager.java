package holik.hotel.servlet.persistence.db;

import java.sql.Connection;
import java.sql.SQLException;

import org.postgresql.jdbc3.Jdbc3PoolingDataSource;

public class DBManager {
	private static Jdbc3PoolingDataSource source;
	
	static {
		source = new Jdbc3PoolingDataSource();
		source.setDataSourceName("Postgres datasource");
		source.setServerName("localhost");
		source.setPortNumber(5432);
		source.setDatabaseName("hotel_servlets");
		source.setUser("servlet_app");
		source.setPassword("ksdfh3kldsf9");
		source.setMaxConnections(10);
	}
	public static Connection getConnection() {
		Connection connection = null;
		try {
			connection = source.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
}
