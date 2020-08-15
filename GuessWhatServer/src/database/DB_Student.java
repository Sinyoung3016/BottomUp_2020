package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import user.Student;
import exam.Workbook;

public class DB_Student extends DBManager{
	public synchronized static boolean insertStudent(String BNum, String BMNum, String Name,String Answer,String Result) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try{
			conn = getConn();
			
			String sql;
			String[] answerList = Answer.split("`");
			int length = answerList.length;
			
			StringBuilder sb = new StringBuilder("INSERT INTO Student (BNum, BMNum, Name,");
			for(int i = 1; i <= length; i++) {
				sb.append("N" + i + ",");
			}
			sb.append("Result) VALUES (?");
			for(int i = 0; i < (length + 3); i++) {
				sb.append(",?");
			}
			sb.append(")");
			sql = new String(sb);
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, Integer.parseInt(BNum));
			pstmt.setInt(2, Integer.parseInt(BMNum));
			pstmt.setString(3, Name);
			int index = 4;
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
	public synchronized static String getStudent(int BMNum) {
		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		
		try {
			conn = getConn();
			state = conn.createStatement();
			String sql;
			sql = "SELECT * FROM Student WHERE BMNum = '" + BMNum + "'";
			rs = state.executeQuery(sql);
			
			StringBuilder sb = new StringBuilder("");
			while(rs.next()) {
				sb.append(rs.getString("SNum") + "~");
				sb.append(rs.getString("BMNum") + "~");
				sb.append(rs.getString("BNum") + "~");
				sb.append(rs.getString("Name") + "~");

				for(int i = 1; i <= 15; i++) {
					String answer = rs.getString("N" + i);
					if(answer == null) {
						answer = " ";
					}
					sb.append( answer + "~"); 
				}
				sb.append(rs.getString("Result"));
				sb.append("_");
			}
			sb.deleteCharAt(sb.length()-1);
			
			return new String(sb);
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
}
