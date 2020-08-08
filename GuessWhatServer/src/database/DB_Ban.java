package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import room.Ban;

public class DB_Ban extends DBManager {
	
	public synchronized static Boolean updateBan(int PNum, int BNum) {
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		int bmSize = 0;
		
		try {
			conn = getConn();
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT * FROM BanManager WHERE PNum = '" + PNum + "' AND BNum = '" + BNum + "'";
			rs = stmt.executeQuery(sql);

			while(rs.next()) {
				bmSize++;
			}
			
			String sql2;
			sql2 = "Update Ban SET Size = ? WHERE PNum = ? AND BNum = ?";
			pstmt = conn.prepareStatement(sql2);
			
			pstmt.setInt(1, bmSize);
			pstmt.setInt(2, PNum);
			pstmt.setInt(3, BNum);

			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
			
			return true;
		}catch(Exception e) {
			System.out.println("Error : " + e.getMessage() + "FROM update Ban");
			return null;
		} finally {
			try {
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
				if(rs != null) rs.close();
			} catch(SQLException e) {
				System.out.println("Error : " + e.getMessage() + "FROM update Ban (SQL)");
			}
		}
	}
	
	public synchronized static boolean insertBan(int PNum, String Name) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConn();
			String sql;
			sql = "INSERT INTO Ban (PNum, Name, Size) VALUES (?,?, ?)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, PNum);
			pstmt.setString(2, Name);
			pstmt.setInt(3, 0);

			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
			return true;
		} catch(SQLException e) {
			System.out.println("Error : " + e.getMessage() + "FROM insertBan");
			return false;
		}
		finally {
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

	public synchronized static Ban getBan(int PNum, int BNum) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		Ban ban = null;
		String name = null;
		int size = -1;

		try {
			conn = getConn();
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT * FROM Ban WHERE PNum = '" + PNum + "' AND BNum = '" + BNum + "'";
			rs = stmt.executeQuery(sql);

			if(rs.next()) {
				name = rs.getString("Name");
				size = rs.getInt("Size");
				
				ban = new Ban(PNum, BNum, name, size);
			}
			return ban;
		}catch(Exception e) {
			System.out.println("Error : " + e.getMessage() + "FROM getBan");
			return null;
		} finally {
			try {
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
				if(rs != null) rs.close();
			} catch(SQLException e) {
				System.out.println("Error : " + e.getMessage() + "FROM getBan (SQL)");
			}
		}
	}
	
	public synchronized static List<Ban> getAllBan(int PNum) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		List<Ban> banList = new ArrayList<>();
		int BNum = -1;
		String name = null;
		int size = -1;

		try {
			conn = getConn();
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT * FROM Ban WHERE PNum = '" + PNum + "'";
			rs = stmt.executeQuery(sql);

			while(rs.next()) {
				BNum = rs.getInt("BNum");
				name = rs.getString("Name");
				size = rs.getInt("Size");
				
				updateBan(PNum, BNum);
				banList.add(new Ban(PNum, BNum, name, size));
			}
			return banList;
		}catch(Exception e) {
			System.out.println("Error : " + e.getMessage() + "FROM getAllBan");
			return null;
		} finally {
			try {
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
				if(rs != null) rs.close();
			} catch(SQLException e) {
				System.out.println("Error : " + e.getMessage() + "FROM getAllBan (SQL)");
			}
		}
	}
	public synchronized static boolean deleteBan(int PNum, int BNum) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConn();

			String s;
			s = "DELETE FROM Ban WHERE PNum = ? AND BNum = ?";
			pstmt = conn.prepareStatement(s);

			pstmt.setInt(1, PNum);
			pstmt.setInt(2, BNum);

			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
			return true;
			
		}catch(Exception e) {
			System.out.println("Error : " + e.getMessage() + "FROM deleteBan");
			return false;
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				System.out.println("Error : " + e.getMessage() + "FROM deleteBan (SQL)");
			}
		}
	}
	public synchronized static boolean modifyBanName(int PNum, int BNum, String newName) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConn();

			String s;
			s = "UPDATE Ban SET Name = ? WHERE PNum = ? AND BNum = ?";
			pstmt = conn.prepareStatement(s);

			pstmt.setString(1, newName);
			pstmt.setInt(2, PNum);
			pstmt.setInt(3, BNum);

			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
			return true;
			
		}catch(Exception e) {
			System.out.println("Error : " + e.getMessage() + "FROM modifyBan");
			return false;
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				System.out.println("Error : " + e.getMessage() + "FROM modifyBan (SQL)");
			}
		}
	}
}
