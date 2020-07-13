package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import User.Professor;

public class DB_USER extends DBManager {

	public synchronized static Professor getUser(String ID) {

		Connection conn = null;
		Statement state = null;
		ResultSet rs = null;
		String[] userInfo = new String[4];

		try {
			conn = getConn();
			state = conn.createStatement();

			String sql;
			sql = "SELECT * FROM user WHERE id='" + ID + "'";

			rs = state.executeQuery(sql);
			if (rs == null)
				return null;

			if (rs.next()) {
				userInfo[0] = rs.getString("id");
				userInfo[1] = rs.getString("password");
				userInfo[2] = rs.getString("Email");
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

	public synchronized static void userLogIn(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConn();

			String sql;
			sql = "UPDATE user SET is_connected = '1' WHERE id= '" + id + "'";
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

	public synchronized static void userLogOut(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {

			conn = getConn();

			String sql;
			sql = "UPDATE user SET is_connected='0' WHERE id='" + id + "'";
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

	public synchronized static void allUserLogOut() {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {

			conn = getConn();

			String sql;
			sql = "UPDATE user SET is_connected='0'";
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

	public synchronized static void insertUser(String ID, String PassWord, String Email) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {

			conn = getConn();

			String sql;
			sql = "INSERT INTO user (ID, PassWord, Email, is_connected)VALUES (?,?,?,?)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, ID);
			pstmt.setString(2, PassWord);
			pstmt.setString(3, Email);

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

	public synchronized static void deleateUser(String ID) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {

			conn = getConn();

			String sql;
			sql = "DELETE FROM user WHERE ID = ?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, ID);
			pstmt.execute();

			pstmt.close();
			conn.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
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
