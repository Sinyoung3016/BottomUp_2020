package exam;
//각각의 문제
public class Problem {
	private int PBNum;
	private String WNum;
	private String question;
	private String answer;
	private ProblemType type;
	private String answerContent;
	
	//Constructor start
	public Problem() {
		
	}
	//Constructor end
	
	//Getter start
	public int getPBNum() { return this.PBNum; }
	public String getWNum() { return this.WNum; } 
	public String getQuestion() { return this.question; }
	public String getAnswer() { return this.answer; }
	public ProblemType getType() { return this.type;}
	public String getAnswerContent() {return this.answerContent;}
	//Getter end
	
}
