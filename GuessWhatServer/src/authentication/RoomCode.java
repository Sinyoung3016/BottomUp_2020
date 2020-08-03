package authentication;

import database.DB_BanManager;
import exception.MyException;
import room.Ban;
import room.BanManager;

public class RoomCode {
	public synchronized static boolean join(String code) throws MyException{
		BanManager banManager;
		banManager = DB_BanManager.getBanManagerOfCode(code);
		
		if(banManager == null) {
			throw new MyException("The class doesn't exist");
		}
		else {
			return true;
		}
		
	}
}
