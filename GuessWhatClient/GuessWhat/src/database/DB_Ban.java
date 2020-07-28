package database;

import java.sql.*;

public class DB_Ban extends DBManager {

	public synchronized static void addBan(String Ban_name, int PNum) throws SQLException {

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {

			conn = getConn();

			String s;
			s = "INSERT INTO BAN (Name, PNum) VALUES (?, ?)";
			pstmt = conn.prepareStatement(s);

			pstmt.setString(1, Ban_name);
			pstmt.setInt(2, PNum);

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
	
	
	public synchronized static void searchBan(int PNum) {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {

			conn = getConn();
			stmt = conn.createStatement();

			String s;
			s = "SELECT * FROM Professor WHERE PNum = '" + PNum + "'";

			rs = stmt.executeQuery(s);
			if (rs.next()) {
				
				s = "SELECT * FROM Ban WHERE P_Num = '" + PNum + "'";
				rs = stmt.executeQuery(s);
				
				while(rs.next())
					// PNum 이 개설한 Ban 의 이름을 얻는다
					System.out.println(rs.getString("Name"));

			}
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (conn != null)
					conn.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public synchronized static void modifyBanName(int PNum, String name, String newName) throws SQLException {

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {

			conn = getConn();

			String s;
			s = "UPDATE Ban SET Name = ? WHERE PNum = ? AND Name = ?";
			pstmt = conn.prepareStatement(s);

			pstmt.setString(1, newName);
			pstmt.setInt(2, PNum);
			pstmt.setString(3, name);

			pstmt.executeUpdate();
			pstmt.close();
			conn.close();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}

	}
	
	public synchronized static void removeBan(int PNum, String name) throws SQLException {

		/* ALTER TABLE BanManager 
		  	ADD CONSTRAINT fk_BNum FOREIGN KEY (BNum) 
		  	REFERENCES Ban (BNum) ON DELETE CASCADE;
		*/
		
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {

			conn = getConn();

			String s;
			s = "DELETE FROM Ban WHERE PNum = ? AND Name = ?";
			pstmt = conn.prepareStatement(s);

			pstmt.setInt(1, PNum);
			pstmt.setString(2, name);

			pstmt.executeUpdate();
			pstmt.close();
			conn.close();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}

	}
}
