package Model;
//문제 출제자
public class Professor { 

	private int pro_num;
	private String pro_id;
	private String pro_password;
	private String pro_email;
	private Ban[] banSet;
	private WorkBook[] workbookSet;
	
	//Constructor start
	public Professor() {
		
		banSet = new Ban[10];
		workbookSet = new WorkBook[10];
	}	
	//Constructor end
	
	//Getter start
	public int pro_num() { return this.pro_num; }
	public String pro_id() { return this.pro_id;}
	public String pro_password() { return this.pro_password;}
	public String pro_email() { return this.pro_email; }
	public Ban[] banSet() { return this.banSet;}
	public WorkBook[] workbookSet() { return this.workbookSet;	}	
	//Getter end
	
	
}
