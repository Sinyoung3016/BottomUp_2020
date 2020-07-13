package exception;

public class AuthenticationException {
	
	public static boolean IDFormCheck(String id) throws MyException {
		if(id.length()!=0) return true;
		throw new MyException("[경고] ID를 입력해주세요.");
	}
	
	public static boolean passwordFormCheck(String pw) throws MyException{
		if(pw.length()!=0) return true;
		throw new MyException("[경고] 비밀번호를 입력해주세요.");
	}
	
	public static boolean emailFormCheck(String email) throws MyException {
		if(email.length()!=0) return true;
		throw new MyException("[경고] Email을 입력해주세요.");
	}
}
