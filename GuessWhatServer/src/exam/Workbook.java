package exam;
//Problem의 모음
public class Workbook {
	private int WNum;
	private String BMNum;
	private String PNum;
	private String name;
	private int size;
	private Problem[] problemSet;
	
	//Constructor start
	public Workbook() {
		problemSet = new Problem[10];
	}
	
	public Workbook(String[] workbookInfo) {
		this.name = workbookInfo[0];
		this.size = Integer.parseInt(workbookInfo[1]);
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
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("");
		sb.append("Name:"+ this.name + ", Size:" + this.size);
		return new String(sb);
	}
}
