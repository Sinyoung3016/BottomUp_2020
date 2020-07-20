package authentication;

import java.sql.SQLException;
import database.DB_USER;
import exception.MyException;
import user.Professor;

public class Authentication {

	public synchronized static boolean SignUp(String ID, String Password, String Email)
			throws MyException, SQLException {
		if (!isThereOverlap(ID)) {
			DB_USER.insertUser(ID, Password, Email, "0");
			return true;
		}
		return false;
	}
	
	public static boolean isThereOverlap(String ID) {
		Professor getUser;
		getUser = DB_USER.getUser(ID);
		if (getUser != null) return true;
		else return false;
	}

	public synchronized static boolean LogIn(String ID, String PW) throws MyException {
		Professor getUser;
		getUser = DB_USER.getUser(ID);

		if (getUser == null) {
			throw new MyException("존재하지 않는 ID 입니다.");
		}
		if (!PW.equals(getUser.getPassword())) {
			throw new MyException("잘못된 비밀번호 입니다.");
		}
		
		return true;
	}

	public static void LogOut(String ID) {
		DB_USER.userLogOut(ID);
	}

}
