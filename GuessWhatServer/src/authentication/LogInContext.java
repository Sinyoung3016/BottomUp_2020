package authentication;

import database.DB_USER;
import user.Professor;

public class LogInContext {
	//����ó�� �ȵǾ�����
	public synchronized static boolean logIn(String ID, String pw)  {
		Professor professor;
		professor = DB_USER.getUser(ID);
		
		if(professor == null) {
			return false;
		}
		else {
			if(professor.getPassword().equals(pw)) {
				if(!professor.isConnected()) {
					return true;
				}
			}
		return false;
	}
}
}