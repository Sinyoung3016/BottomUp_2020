package user;

import model.StudentDataModel;

//문제 풀이자
public class Student {

	private int P_num;
	private int ban_num;
	private int BM_num;

	private int S_num;
	private String name;
	public String[] answer;
	private boolean[] isEmpty;
	private String[] result; // O X N

	public Student(int P_num, int ban_num, int BM_num, int S_num, String name, boolean isEmpty, String answer,
			String result) {
		this.P_num = P_num;
		this.ban_num = ban_num;
		this.BM_num = BM_num;
		this.S_num = S_num;
		this.name = name;
		this.answer = answer.split(":");
		this.result = result.split(":");
	}

	public Student(int P_num, int ban_num, int BM_num, int S_num, String name, String answer) {
		this.P_num = P_num;
		this.ban_num = ban_num;
		this.BM_num = BM_num;
		this.S_num = S_num;
		this.name = name;
		this.answer = answer.split(":");
	}

	public Student() {
		this.ban_num = StudentDataModel.banManager.ban_num();
		this.BM_num = StudentDataModel.banManager.BM_num();
		this.name = StudentDataModel.studentName;

	}

	public Student(int size, String responseTokens) {
		// SNum~BNum~BMNum~Name~N1~N2~...~N15~Result
		String[] studentInfo = responseTokens.split("~");
		this.S_num = Integer.parseInt(studentInfo[0]);
		this.ban_num = Integer.parseInt(studentInfo[1]);
		this.BM_num = Integer.parseInt(studentInfo[2]);
		this.name = studentInfo[3];
		this.answer = new String[size];

		int index = 3;
		for (int i = 0; i < size; i++) {
			answer[i] = studentInfo[index];
			index++;
		}

		this.result = studentInfo[19].split("`");
	}

	// Getter start
	public int P_num() {
		return this.P_num;
	}

	public int ban_num() {
		return this.ban_num;
	}

	public int BM_num() {
		return this.BM_num;
	}

	public int S_num() {
		return this.S_num;
	}

	public String S_num_toString() {
		return Integer.toString(this.S_num);
	}

	public String name() {
		return this.name;
	}

	public String[] answer() {
		return this.answer;
	}

	public String[] result() {
		return this.result;
	}
	// Getter end

	// Setter start
	public void setAnswer(String[] answer) {
		this.answer = answer;
	}

	public void setAnswerNum(String answer, int n) {
		this.answer[n] = answer;
	}

	public void setResult(String result) {
		for (int i = 0; i < result.length(); i++) {
			this.result[i] = result.charAt(i) + "";
		}
	}

	public void setResultWithList(String[] result) {
		this.result = result;
	}

	// Setter End
	public String tokenAnswer() {
		StringBuilder sb = new StringBuilder("");
		for (int i = 0; i < this.answer.length - 1; i++) {
			sb.append(this.answer()[i]);
			sb.append("`");
		}
		sb.append(this.answer()[this.answer().length - 1]);
		return new String(sb);
	}

	public String tokenResult() {
		StringBuilder sb = new StringBuilder("");
		for (int i = 0; i < this.result.length - 1; i++) {
			sb.append(this.result()[i]);
			sb.append("`");
		}
		sb.append(this.result()[this.result().length - 1]);
		return new String(sb);
	}
}
