package exam;

//각각의 문제
public class Problem {
	
	private int P_Num;
	private int W_Num;
	
	private int PB_Num;
	private ProblemType type;
	private String question;
	private String answer;
	private String answerContent;
	
	//Constructor start
	public Problem(String[] problemInfo) {
		this.P_Num = Integer.parseInt(problemInfo[0]);
		this.W_Num = Integer.parseInt(problemInfo[1]);
		this.PB_Num = Integer.parseInt(problemInfo[2]);
		this.type = ProblemType.toProblemType(problemInfo[3]);
		this.question = problemInfo[4];
		this.answer = problemInfo[5];
		this.answerContent = problemInfo[6];
	}
	
	public Problem(String responseTokens) {
		String[] tokens = responseTokens.split("`ㅜ");
		this.P_Num = Integer.parseInt(tokens[0]);
		this.W_Num = Integer.parseInt(tokens[1]);
		this.PB_Num = Integer.parseInt(tokens[2]);
		this.type = ProblemType.toProblemType(tokens[3]);
		this.question = tokens[4];
		this.answer = tokens[5];
		this.answerContent = tokens[6];		
	}
	
	//Constructor end
	
	//Getter start
	public int P_Num() { return this.P_Num; }
	public int W_Num() { return this.W_Num; } 
	public int PB_Num() { return this.PB_Num; }
	public String question() { return this.question; }
	public String answer() { return this.answer; }
	public ProblemType getType() { return this.type;}
	public String getAnswerContent() {return this.answerContent;}
	//Getter end
	
	
	public String toString() {
		StringBuilder sb = new StringBuilder("");
		sb.append("Question: " +this.question +", Answer: " + this.answer + ", Type: " +this.type + ", AnswerContent: " + this.answerContent);
		return new String(sb);
	}
}
