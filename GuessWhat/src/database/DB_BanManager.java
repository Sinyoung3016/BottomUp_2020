package database;

import java.sql.*;

public class DB_BanManager extends DBManager {

	public synchronized static void addBanManager(String name, String state, String code) throws SQLException {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = getConn();

			String s;
			s = "INSERT INTO BanManager (Name, State, Code) VALUES (?, ?, ?)";
			pstmt = con.prepareStatement(s);

			pstmt.setString(1, name);
			pstmt.setString(2, state);
			pstmt.setString(3, code);

			pstmt.executeUpdate();
			pstmt.close();
			con.close();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}	

	}

	public synchronized static String SearchBanManager(String BNum) {

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {

			con = getConn();
			stmt = con.createStatement();

			String s;
			s = "SELECT * FROM Ban WHERE B_Num = '" + BNum + "'";

			rs = stmt.executeQuery(s);

			if (rs.next())
				return rs.getString("Name");
			else
				return null;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.exit(0);
			return null;
		}	
	}
}
