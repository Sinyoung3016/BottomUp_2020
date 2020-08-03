package exam;

//문제 풀이자
public class Student {
	private String workbook;
	private String num;
	private String name;
	private String [] answer;
	
	public Student(String workbook, String num, String name, String answer) {
		this.workbook = workbook;//for check
		//for table
		this.num = num;
		this.name = name;
		this.answer = answer.split(":");
		//for table
	}
	
	public Student(String num, String name, String answer) {
		//for table
		this.num = num;
		this.name = name;
		this.answer = answer.split(":");
		//for table
	}
	
	public String num() { return this.num; }
	public String name() { return this.name; }
	public String[] answer() { return this.answer; }
	
	
}
