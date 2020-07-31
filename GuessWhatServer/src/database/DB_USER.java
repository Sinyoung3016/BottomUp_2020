package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import user.Professor;

public class DB_USER extends DBManager {

	public synchronized static Professor getUser(String givenID) {

		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		String[] userInfo = new String [5];

		try {
			conn = getConn();
			state = conn.createStatement();

			String sql;
			sql = "SELECT * FROM Professor WHERE Id = '" + givenID + "'";

			rs = state.executeQuery(sql);
			if (rs.wasNull())
				return null;

			if (rs.next()) {
				userInfo[0] = rs.getString("Id");
				userInfo[1] = rs.getString("Password");
				userInfo[2] = rs.getString("Email");
				userInfo[3] = rs.getString("IsConnected");
				userInfo[4] = rs.getString("PNum");
			}

			Professor returnUser = new Professor(userInfo);
			return returnUser;

		} catch (Exception e) {
			return null;

		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException sqle1) {
				sqle1.printStackTrace();
			}
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public synchronized static boolean userLogIn(String givenID) {

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConn();
			
			String sql;
			sql = "UPDATE Professor SET IsConnected = ? WHERE Id= ?";
			
			try {
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, "true");
				pstmt.setString(2, givenID);
				
				pstmt.executeUpdate();
				pstmt.close();
				conn.close();
				return true;
			}catch(SQLException e) {
				System.out.println("Error : " + e.getMessage() + "FROM userLogin");
				return false;
			}
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

	public synchronized static boolean userLogOut(String givenID) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {

			conn = getConn();

			String sql;
			sql = "UPDATE Professor SET IsConnected ='false' WHERE Id='" + givenID + "'";
			pstmt = conn.prepareStatement(sql);

			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
			return true;
		} catch (Exception e) {
			System.out.println("Error : " + e.getMessage() + "FROM userLogOut");
			return false;
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

	public synchronized static boolean insertUser(String ID, String PassWord, String Email, String is_connect) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {

			conn = getConn();

			String sql;
			sql = "INSERT INTO Professor (Id, Password, Email, IsConnected) VALUES (?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, ID);
			pstmt.setString(2, PassWord);
			pstmt.setString(3, Email);
			pstmt.setString(4, is_connect);

			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
			return true;
		} catch(SQLException e) {
			System.out.println("Error : " + e.getMessage() + "FROM insertUser");
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

}
