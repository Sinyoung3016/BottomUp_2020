package database;

import java.sql.*;

public class DB_Ban extends DBManager {

	public synchronized static void addBan(String Ban_name) throws SQLException {

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {

			conn = getConn();

			String s;
			s = "INSERT INTO BAN (NAME) VALUES (?)";
			pstmt = conn.prepareStatement(s);

			pstmt.setString(1, Ban_name);

			pstmt.executeUpdate();
			pstmt.close();
			conn.close();

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (conn != null)
					conn.close();
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public synchronized static String SearchBan(String PNum) {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {

			conn = getConn();
			stmt = conn.createStatement();

			String s;
			s = "SELECT * FROM Ban WHERE P_Num = '" + PNum + "'";

			rs = stmt.executeQuery(s);

			if (rs.next())
				return rs.getString("Name");
			else
				return null;

		} catch (Exception e) {
			return null;

		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException sqle1) {
				sqle1.printStackTrace();
			}
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public synchronized static void modifyBanName(String name) throws SQLException {

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {

			conn = getConn();

			String s;
			s = "UPDATE Ban SET Name = '" + name + "'";
			pstmt = conn.prepareStatement(s);

			pstmt.setString(1, name);

			pstmt.executeUpdate();
			pstmt.close();
			conn.close();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}

	}
	
	public synchronized static void removeBan(String name) throws SQLException {

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {

			conn = getConn();

			String s;
			s = "UPDATE Ban SET Name = '" + name + "'";
			pstmt = conn.prepareStatement(s);

			pstmt.setString(1, name);

			pstmt.executeUpdate();
			pstmt.close();
			conn.close();

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (conn != null)
					conn.close();
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	} 
}
