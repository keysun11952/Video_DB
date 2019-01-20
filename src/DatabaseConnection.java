
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

	private final String SampleURL = "jdbc:sqlserver://${dbServer};databaseName=${dbName};user=${user};password={${pass}}";

	private Connection connection = null;

	private String databaseName;
	private String serverName;

	public DatabaseConnection(String serverName, String databaseName) {
		this.serverName = serverName;
		this.databaseName = databaseName;
	}

	public boolean connect(String user, String pass) {
		String connectionUrl = SampleURL.replace("${dbServer}",serverName)
				.replace("${dbName}",databaseName)
				.replace("${user}",user)
				.replace("{${pass}}",pass);
	     try {
			this.connection = DriverManager.getConnection(connectionUrl);
		} catch (SQLException e) {
			return false;
		} 
		return connection!=null;
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
