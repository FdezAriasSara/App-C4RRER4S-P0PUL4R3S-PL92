package alb.util.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Jdbc {

/*  	private static String DRIVER = "org.hsqldb.jdbcDriver";
	private static String URL = "jdbc:hsqldb:hsql://localhost";
	private static String USER = "sa";
	private static String PASS = "";
*/

/*  Configuration for Oracle 
	private static String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static String URL = "jdbc:oracle:thin:@156.35.94.99:1521:DESA";
	private static String USER = "";
	private static String PASS = "";
*/
	
/* Configuration for MySQL
*/	
	private static String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static String URL = "jdbc:mysql://130.61.249.212:8100/ips";
	private static String USER = "user";
	private static String PASS = "ips2021";
	private static Connection con;
	
	static {
		try {
			Class.forName( DRIVER );
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Driver not found in classpath", e);
		}
	}
	
	public static Connection getConnection() throws SQLException {
		if(con == null || con.isClosed()) {
			con = DriverManager.getConnection(URL, USER, PASS);
		}
		
		return con;
	}

	public static void close(ResultSet rs, Statement st, Connection c) {
		close(rs);
		close(st);
		close(c);
	}

	public static void close(ResultSet rs, Statement st) {
		close(rs);
		close(st);
	}

	protected static void close(ResultSet rs) {
		if (rs != null) try { rs.close(); } catch(SQLException e) {/* ignore */}
	}

	public static void close(Statement st) {
		if (st != null ) try { st.close(); } catch(SQLException e) {/* ignore */}
	}

	public static void close(Connection c) {
		return;
		//if (c != null) try { c.close(); } catch(SQLException e) {/* ignore */}
	}
	public static void closeConnection()  {
		if(con!=null) {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
	}

	public static Connection createThreadConnection() throws SQLException {
		Connection con = getConnection();
		// con.setAutoCommit( false );
		threadConnection.set(con);
		return con;
	}

	private static ThreadLocal<Connection> threadConnection = new ThreadLocal<Connection>();

	public static Connection getCurrentConnection() {
		return threadConnection.get();
	}

}
