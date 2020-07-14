package exam;
//Problem의 모음
public class WorkBook {
	private int WB_num;
	private String WB_name;
	private int WB_size;
	private Problem[] problemSet;
	
	//Constructor start
	public WorkBook() {
		problemSet = new Problem[10];
	}
	//Constructor end
	
	//Getter start
	public int WB_num() { return WB_num; }
	public String WB_name() { return WB_name;	}
	public int WB_size() { return WB_size;	}
	public Problem[] problemSet() { return problemSet; }
	//Getter end
	
	

}
