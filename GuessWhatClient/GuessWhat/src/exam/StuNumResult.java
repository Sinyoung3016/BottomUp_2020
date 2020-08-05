package exam;

//PB_num 문제 별 결과
public class StuNumResult {
	
	private int P_num;
	private int ban_num;
	private int BM_num;
	
	private String workbook;
	private int PB_num;
	private String problemType; //from problem
	
	private String S_name;
	private String S_result;
	
	
	public StuNumResult(int P_num, int ban_num, int BM_num, String workbook, int PB_num, String problemType, String S_name, String S_result) {
		this.P_num = P_num;
		this.ban_num = ban_num;
		this.BM_num = BM_num;
		this.workbook = workbook;
		this.PB_num = PB_num;
		this.problemType = problemType;
		this.S_name = S_name;
		this.S_result = S_result;
	}
	

	public int P_num() { return this.P_num; }
	public int ban_num() { return this.ban_num; }
	public int BM_num() { return this.BM_num; }
	public String workbook() { return this.workbook; }
	public int PB_num() { return this.PB_num; }
	public String problemType() { return this.problemType; }
	public String S_name() { return this.S_name; }
	public String S_result() { return this.S_result; }
	
}
