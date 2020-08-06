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
	public Problem(String[] problemInfo) {
		this.question = problemInfo[0];
		this.answer = problemInfo[1];
		this.type = ProblemType.toProblemType(problemInfo[2]);
		this.answerContent = problemInfo[3];
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
	
	
	public String toString() {
		StringBuilder sb = new StringBuilder("");
		sb.append("Question: " +this.question +", Answer: " + this.answer + ", Type: " +this.type + ", AnswerContent: " + this.answerContent);
		return new String(sb);
	}
	
	public String tokenString() {
		StringBuilder sb = new StringBuilder("");
		sb.append(this.WNum + "`" + this.question + "`" + this.answer + "`" + this.type.toString()+ "`" + this.answerContent);
		return new String(sb);
	}
}
