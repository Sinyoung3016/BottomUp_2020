	package authentication;

import database.DB_USER;
import exception.MyException;
import user.Professor;

public class LogInContext {
	public synchronized static boolean logIn(String ID, String pw) throws MyException{
		Professor professor;
		professor = DB_USER.getUser(ID);
		
		if(professor == null) {
			throw new MyException("ID doesn't exist");
		}
		else {
			if(pw.equals(professor.getPassword())) {
				
				if(!professor.isConnected()) {//Success Login
					return true;
				}
				else {
					throw new MyException("This ID is already logged in.");
				}
			} else {
				throw new MyException ("Invalid password.");
			}
	}
}
	public synchronized static boolean overLap(String ID) throws MyException {
		Professor professor;
		professor = DB_USER.getUser(ID);
		
		if(professor == null) {
			return true;
		}
		else
			throw new MyException("It already exists. Please use another Id.");		
	}
}