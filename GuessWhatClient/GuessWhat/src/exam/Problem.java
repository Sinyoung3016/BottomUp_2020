package exam;

//각각의 문제
public class Problem {
	
	private int P_Num;
	private int W_Num;
	
	private int PB_Num;
	private ProblemType type;
	private String question;
	private String answer;
	private String answerContent; //"N1`N2`N3`N4`N5"
	private String[] answerContentList; //[N1][N2][N3][N4][N5] 
	
	//Constructor start
	public Problem(int PB_Num) {
		this.PB_Num = PB_Num;
	}
	
	public Problem(String[] problemInfo) {
		this.P_Num = Integer.parseInt(problemInfo[0]);
		this.W_Num = Integer.parseInt(problemInfo[1]);
		this.PB_Num = Integer.parseInt(problemInfo[2]);
		this.type = ProblemType.toProblemType(problemInfo[3]);
		this.question = problemInfo[4];
		this.answer = problemInfo[5];
		this.answerContent = problemInfo[6];
	}
	public Problem(int p, int w, int pb, String pbtype, String q, String a, String ac ) {
		this.P_Num = p;
		this.W_Num = w;
		this.PB_Num = pb;
		this.type = this.stringToType(pbtype);
		this.question = q;
		this.answer = a;
		this.answerContent = ac;
		this.answerContentList = this.answerContent.split("~");
	}
	public Problem(int P_num, int W_num, int PB_num, ProblemType type, String question, String answer, String answerContent ) {
		this.P_Num = P_num;
		this.W_Num = W_num;
		this.PB_Num = PB_num;
		this.type = type;
		this.question = question;
		this.answer = answer;
		this.answerContent = answerContent;
		this.answerContentList = this.answerContent.split("~");
	}	
	public Problem(int P_num, int W_num, int PB_num, ProblemType type, String question, String answer ) {
		this.P_Num = P_num;
		this.W_Num = W_num;
		this.PB_Num = PB_num;
		this.type = type;
		this.question = question;
		this.answer = answer;
		this.answerContent = null;
		this.answerContentList = null;
	}	
	
	public Problem(String responseTokens) {
		String[] tokens = responseTokens.split("`");
		this.PB_Num = Integer.parseInt(tokens[0]);
		this.W_Num = Integer.parseInt(tokens[1]);
		this.question = tokens[2];
		this.answer = tokens[3];
		this.type = ProblemType.toProblemType(tokens[4]);
		this.answerContent = tokens[5];
		this.answerContentList = this.answerContent.split("~");
	}
	//Constructor end

	public void setPB_num(int n) {
		this.PB_Num = n;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public void setAnswer(String answer) {
		this.answer= answer;
	}
	public void setType(ProblemType type) {
		this.type = type;
	}
	public void setAnswerContent(String answerContent) {
		this.answerContent = answerContent;
		this.answerContentList = answerContent.split("`");
	}
	
	//Getter start
	public int P_Num() { return this.P_Num; }
	public int W_Num() { return this.W_Num; } 
	public int PB_Num() { return this.PB_Num; }
	public String question() { return this.question; }
	public String answer() { return this.answer; }
	public ProblemType getType() { return this.type;}
	public String getAnswerContent() {return this.answerContent;}
	public String [] getAnswerContentList() {return this.answerContentList;}
	//Getter end
	
	private ProblemType stringToType(String type) {
		switch(type){
			case  "MultipleChoice":
				return ProblemType.MultipleChoice;
			case "ShortAnswer":
				return ProblemType.ShortAnswer;
			default:
				return ProblemType.Subjective;
		}
	}
	public String toString() {
		StringBuilder sb = new StringBuilder("");
		sb.append("PBNum : " + this.PB_Num + " WNum : " + this.W_Num + " Question : " + this.question +", Answer: " + this.answer + ", Type: " +this.type + ", AnswerContent: " + this.answerContent);
		return new String(sb);
	}
	
	public String tokenString(int wnum) {
		return wnum + " ` " + this.question + "`" + this.answer + "`" + this.type + "`" + this.answerContent;
	}
}
