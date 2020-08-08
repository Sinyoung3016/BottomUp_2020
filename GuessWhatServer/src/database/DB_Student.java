package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DB_Student extends DBManager{
	public synchronized static boolean insertStudent(String BNum, String BMNum, String WNum, String Name,String Answer,String Result) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try{
			conn = getConn();
			
			String sql;
			String[] answerList = Answer.split("`");
			int length = answerList.length;
			
			StringBuilder sb = new StringBuilder("INSERT INTO Student (BNum, BMNum, WNum, Name,");
			for(int i = 1; i <= length; i++) {
				sb.append("N" + i + ",");
			}
			sb.append("Result) VALUES (?");
			for(int i = 0; i < (length + 4); i++) {
				sb.append(",?");
			}
			sb.append(")");
			sql = new String(sb);
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, Integer.parseInt(BNum));
			pstmt.setInt(2, Integer.parseInt(BMNum));
			pstmt.setInt(3, Integer.parseInt(WNum));
			pstmt.setString(4, Name);
			int index = 5;
			int n = 0;
			while(n < length) {
				pstmt.setString(index, answerList[n]);
				index++;
				n++;
			}
			pstmt.setString(index, Result);
			
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
			return true;
		} catch(SQLException e) {
			System.out.println("Error : " + e.getMessage() + "FROM insertStudent.1");
			return false;
		}
		finally {
			try{
				if(conn != null) conn.close();
				if(pstmt != null) conn.close();
			} catch(SQLException e) {
				System.out.println("Error : " + e.getMessage() + "FROM insertStudent.2 ");
			}
		}
	}
}
