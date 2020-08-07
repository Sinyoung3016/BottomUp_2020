package database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import exam.Problem;

public class DB_Problem extends DBManager{
	public synchronized static boolean insertProblem(String WNum, String question, String answer, String type, String answerContents) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try{
			conn = getConn();
			
			String sql;
			sql = "INSERT INTO Problem (WNum, Question, Answer, Type, AnswerContents) VALUES (?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, WNum);
			pstmt.setString(2, question);
			pstmt.setString(3, answer);
			pstmt.setString(4, type);
			pstmt.setString(5, answerContents);
			
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
			return true;
		} catch(SQLException e) {
			System.out.println("Error : " + e.getMessage() + "FROM insertProblem.1");
			return false;
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
	
	public synchronized static boolean deleteProblem(String PNum) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConn();
			
			String sql;
			sql = "DELETE FROM Problem WHERE PNum = ?";
			pstmt = conn.prepareStatement(sql);
			
			int pNum = Integer.parseInt(PNum);
			pstmt.setInt(1, pNum);
			pstmt.execute();
			
			pstmt.close();
			conn.close();
			return true;
		} catch(SQLException e) {
			System.out.println("Error : " + e.getMessage() +"FROM deleteProblem.1");
			return false;
		}
		finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				System.out.println("Error :" +e.getMessage() + "FROM deleteProblem.2");
			}
		}
	}
	
	public synchronized static boolean modifyProblemName(String PNum, String newQuestion) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConn();
			
			String sql = "UPDATE Problem SET Question = ? WHERE PNUM = ?";
			
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, newQuestion);
				int pNum = Integer.parseInt(PNum);
				pstmt.setInt(2, pNum);
				pstmt.executeUpdate();
				
				pstmt.close();
				conn.close();
				return true;
			} catch(SQLException e) {
				System.out.println("Error : " +e.getMessage() + "FROM modifyPrblemName.1");
				return false;
			}
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				System.out.println("Error : " + e.getMessage() + "FROM modifyPrblemName.2");
			}
		}
	
	}
	
	public synchronized static List<Problem> getProblemListOf(int WNum){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Problem> problemList = new ArrayList<>();
		String[] problemInfo = new String[6];
		
		try {
			conn = getConn();
			
			
			String sql;
			sql = "SELECT * FROM Problem Where WNum = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, WNum);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				problemInfo[0] = rs.getString("PBNum");
				problemInfo[1] = rs.getString("WNum");
				problemInfo[2] = rs.getString("Question");
				problemInfo[3] = rs.getString("Answer");
				problemInfo[4] = rs.getString("Type");
				problemInfo[5] = rs.getString("AnswerContents");
				problemList.add(new Problem(problemInfo));
			}
			
			return problemList;
		} catch(Exception e) {
			System.out.println("Error : " + e.getMessage() + "FROM getProblemOf.1");
			return null;
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
				if(rs != null)  rs.close();
			} catch(SQLException e) {
				System.out.println("Error : " + e.getMessage() + "FROM getProblemOf.2");
			}
		}
	}
	
	public synchronized static Problem getProblemOf(int WNum, int index){
		List<Problem> problemList = getProblemListOf(WNum);
		Problem problem = problemList.get(index);
		return problem;
		}
	
	public synchronized static List<String> getAnswerList(int WNum){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> answerList = new ArrayList<>();
		
		try {
			conn = getConn();
			
			String sql;
			sql = "SELECT * FROM Problem Where WNum = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, WNum);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				answerList.add(rs.getString("Answer"));
			}
			
			return answerList;
		} catch(Exception e) {
			System.out.println("Error : " + e.getMessage() + "FROM getAnswerList.1");
			return null;
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
				if(rs != null)  rs.close();
			} catch(SQLException e) {
				System.out.println("Error : " + e.getMessage() + "FROM getAnswerList.2");
			}
		}
	}
	
	public synchronized static List<String> getTypeList(int WNum){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> answerList = new ArrayList<>();
		
		try {
			conn = getConn();
			
			String sql;
			sql = "SELECT * FROM Problem Where WNum = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, WNum);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				answerList.add(rs.getString("Type"));
			}
			
			return answerList;
		} catch(Exception e) {
			System.out.println("Error : " + e.getMessage() + "FROM getTypeList.1");
			return null;
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
				if(rs != null)  rs.close();
			} catch(SQLException e) {
				System.out.println("Error : " + e.getMessage() + "FROM getTypeList.2");
			}
		}
	}
}
