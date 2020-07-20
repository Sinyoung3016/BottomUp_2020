package exam;
//Problem의 모음
public class WorkBook {
	private int WNum;
	private String BMNum;
	private String PNum;
	private String name;
	private int size;
	private Problem[] problemSet;
	
	//Constructor start
	public WorkBook() {
		problemSet = new Problem[10];
	}
	//Constructor end
	
	//Getter start
	public int getWBNum() { return WNum; }
	public String getBMnum() { return this.BMNum;}
	public String getPNum() { return this.PNum;}
	public String getName() { return name;	}
	public int getSize() { return size;	}
	public Problem[] problemSet() { return problemSet; }
	//Getter end
	
}
