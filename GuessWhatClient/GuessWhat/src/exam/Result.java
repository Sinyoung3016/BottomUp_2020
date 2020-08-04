package exam;

//문제 별 결과
public class Result {
	private String workbook;
	private String problemType; //from problem
	private String P_num;
	private String num;
	private String name;
	private String result;
	
	
	public Result(String workbook, String problemType, String P_num, String num, String name, String result) {
		this.workbook = workbook;//for check
		
		this.problemType = problemType;
		this.num = num;
		this.name = name;
		this.result = result;
	}
	
	public Result(String num, String problemType, String P_num, String name, String result) {
		//for table
		this.num = num;
		this.problemType = problemType;
		this.P_num = P_num;
		this.name = name;
		this.result = result;
		//for table
	}

	public String P_num() { return this.P_num; }
	public String problemType() { return this.problemType; }
	public String num() { return this.num; }
	public String name() { return this.name; }
	public String result() { return this.result; }
	
}
