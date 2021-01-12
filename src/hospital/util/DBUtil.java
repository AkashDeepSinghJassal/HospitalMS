package hospital.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtil {

	static boolean offline = false;
	static Connection conn = null;

	static {
		try {
			if (offline) {
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/emp", "root", null);
				// Class.forName("oracle.jdbc.driver.OracleDriver");
				// conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe",
				// "system", "oracle");
			} else {
				DriverManager.setLoginTimeout(10);
				conn = DriverManager.getConnection(
						"jdbc:mysql://hospital-db.cowul8deiwyt.ap-south-1.rds.amazonaws.com/hospital", "admin",
						"hospital123");

			}

			if (!conn.isClosed()) {
				System.out.println("Connection established");
			} else {
				System.out.println("Connection is not established");
			}

		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Exception in DBUtilFile");
			e.printStackTrace();
		}
	}

	public static Connection getDBConnection() {
		return conn;
	}

	public static void closeQuietly() {
		try {
			conn.close();
		} catch (SQLException e) {
			/* ignore */;
		}
	}

	public static void closeQuietly(Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {
			/* ignore */;
		}
	}

	public static void closeQuietly(PreparedStatement statement) {
		try {
			statement.close();
		} catch (SQLException e) {
			/* ignore */;
		}
	}

	public static void closeQuietly(ResultSet resultSet) {
		try {
			resultSet.close();
		} catch (SQLException e) {
			/* ignore */;
		}
	}
}
// url - hospital-db.cowul8deiwyt.ap-south-1.rds.amazonaws.com