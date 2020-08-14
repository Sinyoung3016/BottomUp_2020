package database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import exam.Workbook;

public class DB_Workbook extends DBManager{
	public synchronized static boolean insertWorkbook(String PNum, String Name, String Size) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConn();
			
			String sql;
			sql = "INSERT INTO Workbook (PNum, Name, Size) VALUES (?,?,?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, PNum);
			pstmt.setString(2, Name);
			pstmt.setString(3, Size);
			
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
			return true;
		} catch(SQLException e) {
			System.out.println("Error : " + e.getMessage() + "from insertWorkbook.1");
			return false;
		}
		finally {
			try {
				if (conn != null)
					conn.close();
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				System.out.println("Error : " + e.getMessage() + "from insertWorkbook.2");
			}
		}
		
	}
	
	public synchronized static boolean deleteWorkbook(String WNum) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConn();
			
			String sql;
			sql = "DELETE FROM Workbook WHERE WNum=?";
			pstmt = conn.prepareStatement(sql);
			
			int wNum = Integer.parseInt(WNum);
			pstmt.setInt(1, wNum);
			pstmt.execute();
			
			pstmt.close();
			conn.close();
			return true;
		} catch(Exception e) {
			System.out.println("Error: " + e.getMessage() + "From deleteWorkbook.1");
			return false;
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				System.out.println("Error: " + e.getMessage() + "From deleteWorkbook.2");
			}		
		}
	}
	
	public synchronized static boolean modifyWorkbookName(String WNum, String newName) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try{
			conn = getConn();
			
			String sql = "UPDATE Workbook SET Name = ? WHERE WNum = ?";
			
			try {
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, newName);
				int wNum = Integer.parseInt(WNum);
				pstmt.setInt(2, wNum);
				pstmt.executeUpdate();
				
				pstmt.close();
				conn.close();
				return true;
			} catch(SQLException e) {
				System.out.println("Error : " + e.getMessage() +"FROM modifyName");
				return false;
			}		
		} finally {
			try {
				if (pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {}
		}
	}
	public synchronized static List<Workbook> getAllWorkbook() {
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		List<Workbook> workbookList = new ArrayList<>();
		String[] workbookInfo = new String[2];
		try {
			System.out.print("Workbook List request: ");
			conn = getConn();
			state = conn.createStatement();
			String sql;
			sql = "SELECT * FROM Workbook";
			rs = state.executeQuery(sql);
			
			while(rs.next()) {
				workbookInfo[0] = rs.getString("Name");
				workbookInfo[1] = rs.getString("Size");
				workbookList.add(new Workbook(workbookInfo));
			}
			
			return workbookList;
		}catch(Exception e) {
			System.out.println("Error : " + e.getMessage() + "FROM getAllWorkbook.1");
			return null;
		} finally {
			try {
				if(state != null) state.close();
				if(conn != null) conn.close();
				if(rs != null) rs.close();
			} catch(SQLException e) {
				System.out.println("Error : " + e.getMessage() + "FROM getWorkbookOf.2");
			}
		}
	}
	public synchronized static List<Workbook> getWorkbookList(int PNum) {
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		
		List<Workbook> workbookList = new ArrayList<>();
		
		String[] workbookInfo = new String[5];
		try {
			conn = getConn();
			state = conn.createStatement();
			String sql;
			sql = "SELECT * FROM Workbook WHERE PNum = '" + PNum + "'";
			rs = state.executeQuery(sql);
			
			while(rs.next()) {
				workbookInfo[0] = Integer.toString(PNum);
				workbookInfo[1] = Integer.toString(rs.getInt("WNum"));
				workbookInfo[2] = rs.getString("Name");
				workbookInfo[3] = Integer.toString(rs.getInt("Size"));
				workbookList.add(new Workbook(workbookInfo));
			}
			
			return workbookList;
		}catch(Exception e) {
			System.out.println("Error : " + e.getMessage() + "FROM getAllWorkbook.1");
			return null;
		} finally {
			try {
				if(state != null) state.close();
				if(conn != null) conn.close();
				if(rs != null) rs.close();
			} catch(SQLException e) {
				System.out.println("Error : " + e.getMessage() + "FROM getWorkbookOf.2");
			}
		}
	}
	public synchronized static Workbook getCurrentWorkbook(int WNum) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		Workbook workbook = null;
		String name = null;
		int size = -1;

		try {
			conn = getConn();
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT * FROM Workbook WHERE WNum = '" + WNum + "'";
			rs = stmt.executeQuery(sql);

			if(rs.next()) {
				name = rs.getString("Name");
				size = rs.getInt("Size");
				
				workbook = new Workbook(WNum, name, size);
			}
			return workbook;
		}catch(Exception e) {
			System.out.println("Error : " + e.getMessage() + "FROM getCurrentWB");
			return null;
		} finally {
			try {
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
				if(rs != null) rs.close();
			} catch(SQLException e) {
				System.out.println("Error : " + e.getMessage() + "FROM getCurrentWB (SQL)");
			}
		}
	}
	public synchronized static Workbook getWorkbookOf(int WNum) {
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		Workbook workbook = null;
		String[] workbookInfo = new String[5];
		try {
			conn = getConn();
			state = conn.createStatement();
			String sql;
			sql = "SELECT * FROM Workbook WHERE WNum = '" + WNum + "'";
			rs = state.executeQuery(sql);
			
			if(rs.next()) {
				workbookInfo[0] = rs.getString("PNum");
				workbookInfo[1] = rs.getString("WNum");
				workbookInfo[2] = rs.getString("Name");
				workbookInfo[3] = rs.getString("Size");
				workbook = new Workbook(workbookInfo);
			}
			
			return workbook;
		}catch(Exception e) {
			System.out.println("Error : " + e.getMessage() + "FROM getWorkbookOf.1");
			e.printStackTrace();
			return null;
		} finally {
			try {
				if(state != null) state.close();
				if(conn != null) conn.close();
				if(rs != null) rs.close();
			} catch(SQLException e) {
				System.out.println("Error : " + e.getMessage() + "FROM getWorkbookOf.2");
			}
		}
	}
	
	public synchronized static int getWNumOf(String PNum, String name) {
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		
		try {
			conn = getConn();
			state = conn.createStatement();
			String sql;
			sql = "SELECT * FROM Workbook WHERE Name = '" + name + "' && PNum = '" + PNum + "'" ;
			rs = state.executeQuery(sql);
			
			if(rs.next()) {
				return rs.getInt("WNum");
			}
			
			return -1;
		}catch(Exception e) {
			System.out.println("Error : " + e.getMessage() + "FROM getWNumOf.1");
			e.printStackTrace();
			return -1;
		} finally {
			try {
				if(state != null) state.close();
				if(conn != null) conn.close();
				if(rs != null) rs.close();
			} catch(SQLException e) {
				System.out.println("Error : " + e.getMessage() + "FROM getWNumOf.2");
			}
		}
	}
}



