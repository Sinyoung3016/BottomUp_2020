package Room;

import Exam.WorkBook;

//workbook을 풀이하고 결과를 관리
public class BanManager {
	private int BM_num;
	private enum BM_state {	OPEN, ING, CLOSE };
	private String BM_name;
	private String BM_password;
	private WorkBook workbook;
	private AnswerSet answerSet;

	//Constructor start
	public BanManager() {
		
	}
	//Constructor end
	
	//Getter start
	public int BM_num() { return BM_num; } 
	public String BM_name() { return BM_name; }
	public String BM_password() { return BM_password; }
	public WorkBook workbook() { return workbook; }
	public AnswerSet answerSet() { return answerSet; }
	//Getter end
}
