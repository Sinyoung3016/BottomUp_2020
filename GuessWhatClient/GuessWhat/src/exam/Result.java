package exam;

//문제 별 결과
public class Result {
	private String workbook;
	private String num;
	private String name;
	private String result;
	
	public Result(String workbook, String num, String name, String result) {
		this.workbook = workbook;//for check
		//for table
		this.num = num;
		this.name = name;
		this.result = result;
		//for table
	}
	
	public Result(String num, String name, String result) {
		//for table
		this.num = num;
		this.name = name;
		this.result = result;
		//for table
	}
	
	public String num() { return this.num; }
	public String name() { return this.name; }
	public String result() { return this.result; }
	
}
