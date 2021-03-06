package user;

import exam.Workbook;
import room.Ban;

//문제 출제자
public class Professor {

	private final int DEFAULT_BAN_SIZE = 10;
	private final int DEFAULT_WORKBOOK_SIZE = 10;
	
	//Test
	private int Pnum;
	private String Id;
	private String name;
	private String email;
	private String password;
	private String isConnected;
	private Ban[] banSet;
	private Workbook[] workbookSet;

	// Constructor start
	public Professor(String[] info) {
		this.setId(info[0]);
		this.setPassword(info[1]);
		this.setEmail(info[2]);
		this.setConnected(info[3]);
		this.setBanSet(DEFAULT_BAN_SIZE);
		this.setWorkbookSet(DEFAULT_WORKBOOK_SIZE);
	}
	
	public Professor(String tokens) {
		String token[] = tokens.split("-");
		this.Pnum = Integer.parseInt(token[0]);
		this.Id = token[1];
		this.name = token[2];
		this.email = token[3];
		this.password = token[4];
	}
	// Constructor end

	// Setter start
	public void setPNum(int newPro_num) {
		this.Pnum = newPro_num;
	}

	public void setId(String newPro_id) {
		this.Id = newPro_id;
	}
	
	public void setName(String newName) {
		this.name = newName;
	}
	
	public void setPassword(String newPro_password) {
		this.password = newPro_password;
	}

	public void setEmail(String newPro_email) {
		this.email = newPro_email;
	}
	
	public void setConnected(String is_connect) {
		this.isConnected = is_connect;
	}

	public void setBanSet(int newBanSize) {
		banSet = new Ban[newBanSize];
	}

	public void setWorkbookSet(int newWorkbookSize) {
		workbookSet = new Workbook[newWorkbookSize];
	}
	// Setter end

	// Getter start
	public int P_Num() {
		return this.Pnum;
	}

	public String Id() {
		return this.Id;
	}
	
	public String name() {
		return this.name;
	}
	
	public String password() {
		return this.password;
	}

	public String email() {
		return this.email;
	}

	public String isConnected() {
		return this.isConnected;
	}

	public Ban[] banSet() {
		return this.banSet;
	}

	public Workbook[] workbookSet() {
		return this.workbookSet;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder("");
		sb.append("ID: " + this.Id + ", Name: " + this.name + ", Password: " +this.password +", Email" + this.email + ", IsConnected"  +this.isConnected);
		return new String(sb);
	}

// Getter end
}
