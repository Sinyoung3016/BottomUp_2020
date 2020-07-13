package database;

import java.sql.Connection;
import java.sql.DriverManager;

public abstract class DBManager {
	public final static String JDBC_DRIVER="com.mysql.cj.jdbc.Driver";
	public final static String DB_URL ="jdbc:mysql://localhost:3306/test";
	public final static String USER_NAME="root";
	public final static String PASSWORD="201902699";
	
	public static Connection getConn(){
        Connection conn = null;
       
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD); //2. ����̹� ����
           
        } catch (Exception e) {
            e.printStackTrace();
        }
        
       
        return conn;
    }
	
}