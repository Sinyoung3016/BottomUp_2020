package exam;
//Problem의 모음
public class Workbook {
	private int WNum;
	private int BMNum;
	private int PNum;
	private String name;
	private int size;
	private Problem[] problemSet;
	
	//Constructor start
	public Workbook() {
		problemSet = new Problem[10];
	}
	
	public Workbook(String[] workbookInfo) {
		this.PNum = Integer.parseInt(workbookInfo[0]);
		this.BMNum = Integer.parseInt(workbookInfo[1]);
		this.WNum = Integer.parseInt(workbookInfo[2]);
		this.name = workbookInfo[3];
		this.size = Integer.parseInt(workbookInfo[4]);
	}
	//Constructor end
	
	//Getter start
	public int getWBNum() { return WNum; }
	public int getBMnum() { return this.BMNum;}
	public int getPNum() { return this.PNum;}
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
	
	public String tokenString() {
		StringBuilder sb = new StringBuilder("");
		sb.append(this.PNum + "-" + this.BMNum + "-" + this.WNum + "-" + this.name + "-" + this.size );
		return new String(sb);
	}
}
