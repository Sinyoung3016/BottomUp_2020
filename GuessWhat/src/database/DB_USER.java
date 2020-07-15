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
		String[] userInfo = new String [4];

		try {
			conn = getConn();
			state = conn.createStatement();

			String sql;
			sql = "SELECT * FROM user WHERE pro_id = '" + givenID + "'";

			rs = state.executeQuery(sql);
			if (rs == null)
				return null;

			if (rs.next()) {
				userInfo[0] = rs.getString("pro_id");
				userInfo[1] = rs.getString("pro_password");
				userInfo[2] = rs.getString("pro_email");
				userInfo[3] = rs.getString("is_connect");
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

	public synchronized static void userLogIn(String givenID) {

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConn();

			String sql;
			sql = "UPDATE user SET is_connect = '1' WHERE pro_id= '" + givenID + "'";
			pstmt = conn.prepareStatement(sql);

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

	public synchronized static void userLogOut(String givenID) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {

			conn = getConn();

			String sql;
			sql = "UPDATE user SET is_connect ='0' WHERE pro_id='" + givenID + "'";
			pstmt = conn.prepareStatement(sql);

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

	public synchronized static void insertUser(String ID, String PassWord, String Email, String is_connect) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {

			conn = getConn();

			String sql;
			sql = "INSERT INTO user (pro_id, pro_password, pro_email, is_connect ) VALUES (?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, ID);
			pstmt.setString(2, PassWord);
			pstmt.setString(3, Email);
			pstmt.setString(4, is_connect);

			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
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

}
