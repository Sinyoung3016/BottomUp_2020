package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import room.BanManager;

public class DB_BanManager extends DBManager {
	
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
	
	public synchronized static boolean insertBanManager(int PNum, int BNum, String name, String code, String workbook) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConn();
			String sql;
			sql = "INSERT INTO BanManager (PNum, BNum, Name, State, Code, WorkBook) VALUES (?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, PNum);
			pstmt.setInt(2, BNum);
			pstmt.setString(3, name);
			pstmt.setString(4, "OPEN");
			
			pstmt.setString(5, code);
			pstmt.setString(6, "workbookName");

			pstmt.executeUpdate();
			
			updateBan(PNum, BNum);
			
			pstmt.close();
			conn.close();
			return true;
		} catch(SQLException e) {
			System.out.println("Error : " + e.getMessage() + "FROM insertBanManager");
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
	public synchronized static List<BanManager> getAllBanManager(int PNum, int BNum) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		List<BanManager> banManagerList = new ArrayList<>();
		//Name, State, Code, WorkBook
		int BMNum = -1;
		String name = null;
		String state = null;
		String code = null;
		String workbook = null;
		int size = -1;

		try {
			conn = getConn();
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT * FROM BanManager WHERE PNum = '" + PNum + "' AND BNum = '" + BNum + "'";
			rs = stmt.executeQuery(sql);

			while(rs.next()) {
				BMNum = rs.getInt("BMNum");
				name = rs.getString("Name");
				state = rs.getString("State");
				code = rs.getString("Code");
				workbook = rs.getString("WorkBook");
				
				banManagerList.add(new BanManager(PNum, BNum, BMNum, name, state, code, workbook, size));
			}
			return banManagerList;
		}catch(Exception e) {
			System.out.println("Error : " + e.getMessage() + "FROM getAllBanManager");
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
	public synchronized static BanManager getCurrentBanManager(int PNum, int BNum) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		BanManager banManager = null;
		//Name, State, Code, WorkBook
		int BMNum = -1;
		String name = null;
		String state = null;
		String code = null;
		String workbook = null;
		int size = -1;

		try {
			conn = getConn();
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT * FROM BanManager WHERE PNum = '" + PNum + "' AND BNum = '" + BNum + "'";
			rs = stmt.executeQuery(sql);

			while(rs.next()) {
				BMNum = rs.getInt("BMNum");
				name = rs.getString("Name");
				state = rs.getString("State");
				code = rs.getString("Code");
				workbook = rs.getString("WorkBook");
				
				banManager = new BanManager(PNum, BNum, BMNum, name, state, code, workbook, size);
			}
			return banManager;
		}catch(Exception e) {
			System.out.println("Error : " + e.getMessage() + "FROM getAllBanManager");
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
	
	//코드로 BanManager가져오는 함수
	public synchronized static BanManager getBanManagerOfCode(String code) {
		BanManager banManager = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String[] banManagerInfo = new String[6];
		
		try {

			conn = getConn();
			stmt = conn.createStatement();

			String s;
			s = "SELECT * FROM BanManager WHERE Code = '" + code + "'";

			rs = stmt.executeQuery(s);
			
			if(rs.next()) {

				banManagerInfo[0] = rs.getString("BMNum");
				banManagerInfo[1] = rs.getString("Name");
				banManagerInfo[2] = rs.getString("State");
				banManagerInfo[3] = rs.getString("Code");
				banManagerInfo[4] = rs.getString("BNum");
				banManagerInfo[5] = rs.getString("StudentSize");
				banManager = new BanManager(banManagerInfo);
				
			}
			
			return banManager;
			
		} catch (Exception e) {
			System.out.println("Error : "  +e.getMessage()  + "getBanManagerOfCode");
			return null;
			
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
