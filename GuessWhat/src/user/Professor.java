package user;

import exam.Workbook;
import room.Ban;

//문제 출제자
public class Professor {

	private final int DEFAULT_BAN_SIZE = 10;
	private final int DEFAULT_WORKBOOK_SIZE = 10;

	private String Pnum;
	private String Id;
	private String name;
	private String email;
	private String password;
	private int isConnected;
	private Ban[] banSet;
	private Workbook[] workbookSet;

	// Constructor start
	public Professor(String[] info) {
		this.setId(info[0]);
		this.setPassword(info[1]);
		this.setEmail(info[2]);
		this.setConnected(Integer.parseInt(info[3]));
		this.setBanSet(DEFAULT_BAN_SIZE);
		this.setWorkbookSet(DEFAULT_WORKBOOK_SIZE);
	}
	// Constructor end

	// Setter start
	public void setPNum(String newPro_num) {
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
	
	public void setConnected(int is_connect) {
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
	public String getPNum() {
		return this.Pnum;
	}

	public String getId() {
		return this.Id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getPassword() {
		return this.password;
	}

	public String getEmail() {
		return this.email;
	}

	public int getIsConnected() {
		return this.isConnected;
	}

	public Ban[] banSet() {
		return this.banSet;
	}

	public Workbook[] workbookSet() {
		return this.workbookSet;
	}

// Getter end
}
