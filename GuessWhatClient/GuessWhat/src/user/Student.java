package user;

//문제 풀이자
public class Student {
	
	private int P_num;
	private int ban_num;
	private int BM_num;
	
	private int S_num;
	private String name;
	private String [] answer;
	private boolean [] isEmpty;
	private String [] result; //O X N
	
	public Student(int P_num, int ban_num, int BM_num, int S_num, String name, boolean isEmpty, String answer, String result) {
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
	
	public void setResult(String result) { this.result = result.split(":"); }
	
	//Getter start
	public int P_num() { return this.P_num; }
	public int ban_num() { return this.ban_num; }
	public int BM_num() { return this.BM_num; }
	public int S_num() { return this.S_num; }
	public String S_num_toString() { return Integer.toString(this.S_num); }
	public String name() { return this.name; }
	public String[] answer() { return this.answer; }
	public String[] result() { return this.result; }
	//Getter end
}
