package authentication;

import java.sql.SQLException;
import database.DB_USER;
import exception.AuthenticationException;
import exception.MyException;
import User.Professor;

public class LogInContext {

	public synchronized static boolean SignUp(String ID, String Password, String Email)
			throws MyException, SQLException {

		if (AuthenticationException.IDFormCheck(ID) && AuthenticationException.passwordFormCheck(Password)) {
			Professor getUser;
			getUser = DB_USER.getUser(ID);
			if (getUser != null) {
				throw new MyException("중복되는 아이디가 있습니다.");
			} else {
				DB_USER.insertUser(ID, Password, Email);
				return true;
			}
		}
		return false;
	}

	public synchronized static boolean LogIn(String ID, String PW) throws MyException {
		Professor getUser;
		getUser = DB_USER.getUser(ID);

		if (getUser == null) {
			throw new MyException("존재하지 않는 ID 입니다.");
		}
		if (!PW.equals(getUser.pro_password())) {
			throw new MyException("틀린 비밀번호 입니다.");
		}
		
		return true;
	}

	public static void LogOut(String ID) {
		DB_USER.userLogOut(ID);
	}

}
