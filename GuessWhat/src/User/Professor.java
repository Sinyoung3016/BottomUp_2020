package user;

import exam.WorkBook;
import room.Ban;

//문제 출제자
public class Professor {

	private final int DEFAULT_BAN_SIZE = 10;
	private final int DEFAULT_WORKBOOK_SIZE = 10;

	private String pro_num;
	private String pro_id;
	private String pro_password;
	private String pro_email;
	private int is_connect;
	private Ban[] banSet;
	private WorkBook[] workbookSet;

	// Constructor start
	public Professor(String[] info) {
		this.setPro_id(info[0]);
		this.setPro_password(info[1]);
		this.setPro_email(info[2]);
		this.setIs_connect(Integer.parseInt(info[3]));
		this.setBanSet(DEFAULT_BAN_SIZE);
		this.setWorkbookSet(DEFAULT_WORKBOOK_SIZE);
	}
	// Constructor end

	// Setter start
	public void setPro_num(String newPro_num) {
		this.pro_num = newPro_num;
	}

	public void setPro_id(String newPro_id) {
		this.pro_id = newPro_id;
	}

	public void setPro_password(String newPro_password) {
		this.pro_password = newPro_password;
	}

	public void setPro_email(String newPro_email) {
		this.pro_email = newPro_email;
	}
	
	public void setIs_connect(int is_connect) {
		this.is_connect = is_connect;
	}

	public void setBanSet(int newBanSize) {
		banSet = new Ban[newBanSize];
	}

	public void setWorkbookSet(int newWorkbookSize) {
		workbookSet = new WorkBook[newWorkbookSize];
	}
	// Setter end

	// Getter start
	public String pro_num() {
		return this.pro_num;
	}

	public String pro_id() {
		return this.pro_id;
	}

	public String pro_password() {
		return this.pro_password;
	}

	public String pro_email() {
		return this.pro_email;
	}

	public int is_connect() {
		return this.is_connect;
	}

	public Ban[] banSet() {
		return this.banSet;
	}

	public WorkBook[] workbookSet() {
		return this.workbookSet;
	}

// Getter end
}
