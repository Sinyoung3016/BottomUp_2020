package database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DB_Problem extends DBManager{
	public synchronized static void insertProblem(String WNum, String question, String answer, String type, String answerContents) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try{
			conn = getConn();
			
			String sql;
			sql = "INSERT INTO Problem (WNum, Question, Answer, Type, AnswerContent) VALUES (?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, WNum);
			pstmt.setString(2, question);
			pstmt.setString(3, answer);
			pstmt.setString(4, type);
			pstmt.setString(5, answerContents);
			
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch(SQLException e) {
			System.out.println("Error : " + e.getMessage() + "FROM insertProblem.1");
		}
		finally {
			try{
				if(conn != null) conn.close();
				if(pstmt != null) conn.close();
			} catch(SQLException e) {
				System.out.println("Error : " + e.getMessage() + "FROM insertProblem.2 ");
			}
		}
	}
}
