
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

	private final String SampleURL = "jdbc:sqlserver://${dbServer};databaseName=${dbName};user=${user};password={${pass}}";

	private Connection connection = null;

	private String databaseName;
	private String serverName;
	private static DatabaseConnection dbc = null;

	private DatabaseConnection() {
		this.serverName = "golem.csse.rose-hulman.edu";
		this.databaseName = "Video_DB";
	}

	public static DatabaseConnection getInstance() {
		if (dbc == null) {
			dbc = new DatabaseConnection();
		}
		return dbc;

	}

	public boolean connect(String user, String pass) {
		String connectionUrl = SampleURL.replace("${dbServer}", serverName).replace("${dbName}", databaseName)
				.replace("${user}", user).replace("{${pass}}", pass);
		try {
			this.connection = DriverManager.getConnection(connectionUrl);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return connection != null;
	}

	public Connection getConnection() {
		return this.connection;
	}

	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
