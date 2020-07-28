package exam;
//문제형식

public enum ProblemType {
	MultipleChoice, //객관식
	ShortAnswer, //단답식
	Subjective; //주관식
	
	public static final String[] PROBLEMTYPE_NAME = {"MulitpleChoice", "ShortAnswer", "Subjective"};
	
	public String typeName() {
		return ProblemType.PROBLEMTYPE_NAME[this.ordinal()];
	}
	
	public static ProblemType toProblemType(String type) {
		switch(type) {
		case "MultipleChoice" :
			return ProblemType.MultipleChoice;
		case "ShortAnswer" :
			return ProblemType.ShortAnswer;
		default:
			return ProblemType.Subjective;
		}
	}
}
