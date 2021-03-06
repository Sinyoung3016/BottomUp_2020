package exam;

//각각의 문제
public class Problem {
	private int PBNum;
	private int WNum;
	private String question;
	private String answer;
	private ProblemType type;
	private String answerContent;
	
	//Constructor start
	public Problem(String[] problemInfo) {
		this.PBNum = Integer.parseInt(problemInfo[0]);
		this.WNum = Integer.parseInt(problemInfo[1]);
		this.question = problemInfo[2];
		this.answer = problemInfo[3];
		this.type = ProblemType.toProblemType(problemInfo[4]);
		this.answerContent = problemInfo[5];
	}
	
	//Constructor end
	
	//Getter start
	public int getPBNum() { return this.PBNum; }
	public int getWNum() { return this.WNum; } 
	public String getQuestion() { return this.question; }
	public String getAnswer() { return this.answer; }
	public ProblemType getType() { return this.type;}
	public String getAnswerContent() {return this.answerContent;}
	//Getter end
	
	
	public String toString() {
		StringBuilder sb = new StringBuilder("");
		sb.append("PBNum : " + this.PBNum + " WNum : " + this.WNum + " Question : " + this.question +", Answer: " + this.answer + ", Type: " +this.type + ", AnswerContent: " + this.answerContent);
		return new String(sb);
	}
	
	public String tokenString() {
		StringBuilder sb = new StringBuilder("");
		sb.append(this.PBNum + "`" + this.WNum + "`" + this.question + "`" + this.answer + "`" + this.type.toString()+ "`" + this.answerContent);
		return new String(sb);
	}
}
