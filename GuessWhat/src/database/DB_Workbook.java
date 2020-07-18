package database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DB_Workbook extends DBManager{
	public synchronized static void insertWorkbook(String BNum, String BMNum, String PNum, String Name, String Size) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConn();
			
			String sql;
			sql = "INSERT INTO Workbook (WNum, BMNum, PNum, Name, Size) VALUES (?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, BNum);
			pstmt.setString(2, BMNum);
			pstmt.setString(3, PNum);
			pstmt.setString(4, Name);
			pstmt.setString(5, Size);
			
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch(SQLException e) {
			System.out.println("Error : " + e.getMessage() + "from insertWorkbook");
		}
		finally {
			try {
				if (conn != null)
					conn.close();
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				System.out.println("Error : " + e.getMessage() + "from insertWorkbook");
			}
		}
		
	}
}



