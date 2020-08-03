package database;

import java.sql.*;

import room.BanManager;

public class DB_BanManager extends DBManager {
	
	public synchronized static void addBanManager(int bNum, String name, String code) throws SQLException {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = getConn();

			String s;
			s = "INSERT INTO BanManager (Name, Code, BNum) VALUES (?, ?, ?)";
			pstmt = con.prepareStatement(s);

			pstmt.setString(1, name);
			pstmt.setString(2, code);
			pstmt.setInt(3, bNum);
			

			pstmt.executeUpdate();
			pstmt.close();
			con.close();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}	

	}
	
	public synchronized static void modifyName(int BNum, String name, String newName) throws SQLException {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = getConn();

			String s;
			s = "UPDATE BanManager SET Name = ? WHERE BNum = ? AND Name = ?";
			pstmt = con.prepareStatement(s);

			pstmt.setString(1, newName);
			pstmt.setInt(2, BNum);
			pstmt.setString(3, name);

			pstmt.executeUpdate();
			pstmt.close();
			con.close();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}	
	}
	
	public synchronized static void modifyState(int BNum, String state, String name) throws SQLException {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = getConn();

			String s;
			s = "UPDATE BanManager SET State = ? WHERE BNum = ? AND Name = ?";
			pstmt = con.prepareStatement(s);

			pstmt.setString(1, state);
			pstmt.setInt(2, BNum);
			pstmt.setString(3, name);

			pstmt.executeUpdate();
			pstmt.close();
			con.close();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}	
	}

	public synchronized static void searchAllBanManager(int bNum) {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {

			conn = getConn();
			stmt = conn.createStatement();

			String s;
			s = "SELECT * FROM Ban WHERE BNum = '" + bNum + "'";

			rs = stmt.executeQuery(s);
			if (rs.next()) {
				
			//	System.out.println(rs.getNString("Name") + "의 BanManager 목록: ");
				
				s = "SELECT * FROM BanManager WHERE BNum = '" + bNum + "'";
				rs = stmt.executeQuery(s);
				
				while(rs.next())
					// BNum 이 소유한 BanManager 의 이름을 얻는다
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
	
	public synchronized static BanManager getBanManagerOfCode(String code) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {

			conn = getConn();
			stmt = conn.createStatement();

			String s;
			s = "SELECT * FROM BanManager WHERE Code = '" + code + "'";

			rs = stmt.executeQuery(s);
			if (rs.next()) {
				
			//	System.out.println(rs.getNString("Name") + "의 BanManager 목록: ");
				
				s = "SELECT * FROM BanManager WHERE BNum = '" + bNum + "'";
				rs = stmt.executeQuery(s);
				
				while(rs.next())
					// BNum 이 소유한 BanManager 의 이름을 얻는다
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
	
	public synchronized static void removeBanManager(int bNum, String name) throws SQLException {

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {

			conn = getConn();

			String s;
			s = "DELETE FROM BanManager WHERE BNum = ? AND Name = ?";
			pstmt = conn.prepareStatement(s);

			pstmt.setInt(1, bNum);
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
