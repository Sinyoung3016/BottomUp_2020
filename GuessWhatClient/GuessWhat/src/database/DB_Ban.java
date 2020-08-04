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
	
	public synchronized static boolean insertBan(String name, String PNum) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {

			conn = getConn();

			String sql;
			sql = "INSERT INTO Ban (Name, PNum) VALUES (?,?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, name);
			pstmt.setInt(2, Integer.parseInt(PNum));
	
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
	
	public synchronized static List<Ban> getAllBan(String PNum) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		List<Ban> banList = new ArrayList<>();
		String name = null;
		
		try {
			conn = getConn();
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT * FROM Ban WHERE PNum = '" + PNum + "'";
			rs = stmt.executeQuery(sql);

			while(rs.next()) {
				name = rs.getString("Name");
				banList.add(new Ban(name));
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
	
/*	public synchronized static ArrayList<String> getAllBanList(String PNum){

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		ArrayList<String> banList = new ArrayList<String>();

		try {

			conn = getConn();
			stmt = conn.createStatement();

			String s;
			s = "SELECT * FROM Ban WHERE PNum = '" + PNum + "'";
			rs = stmt.executeQuery(s);

			while(rs.next()) {
				String myBan = rs.getString("Name");
				banList.add(myBan);
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
		return banList;	
	}
	
	
	public synchronized static int getMyNum(String id) throws SQLException {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		int myNum = -1;

		try {

			conn = getConn();
			stmt = conn.createStatement();

			String s;
			s = "SELECT PNum FROM Professor WHERE Id = '" + id + "'";
			rs = stmt.executeQuery(s);

			while(rs.next()) {
				myNum = rs.getInt("PNum");
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
		return myNum;	
	}

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
	
	public synchronized static int getBNum(String id, String name) throws SQLException {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		int pNum = -1;
		int bNum = -1;

		try {
			
			pNum = getMyNum(id);

			conn = getConn();
			stmt = conn.createStatement();

			String s;
			s = "SELECT BNum FROM Ban WHERE PNum = '" + pNum + "' AND Name = '" + name + "'";
			rs = stmt.executeQuery(s);

			while(rs.next()) {
				bNum = rs.getInt("BNum");
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
		return bNum;	
	}
	
/*	public synchronized static ArrayList<String> getAllBanList(int PNum) throws SQLException {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		ArrayList<String> banList = new ArrayList<String>();

		try {

			conn = getConn();
			stmt = conn.createStatement();

			String s;
			s = "SELECT * FROM Ban WHERE PNum = '" + PNum + "'";
			rs = stmt.executeQuery(s);

			while(rs.next()) {
				String myBan = rs.getString("Name");
				banList.add(myBan);
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
		return banList;	
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
	
*/	
}
