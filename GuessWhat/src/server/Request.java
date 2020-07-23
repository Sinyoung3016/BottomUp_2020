package server;

public enum Request {
Add_Ban("AddBan"),
ADD_BANMANAGER("AddBanManager"),
ADD_WORKBOOK("AddWorkbook"),
ADD_PROBLEM("AddProblem"),
DELETE_BAN("DeleteBan"),
DELETE_BANMANAGER("DeleteBanManger"),
DELETE_WORKBOOK("DeleteWorkbook"),
DELETE_PROBLEM("DeleteProblem"),
MODIFY_BAN("ModifyBan"),
MODIFY_BANMANAGER("ModifyBanManager"),
MODIFY_WORKBOOK("ModifyWorkbook"),
MODIFY_PROBLEM("ModifyProblem"),
GET_BAN("GetBan"),
GET_BANMANGER("GetBanManager"),
GET_WORKBOOK("GetWorkbook"),
GET_PROBLEM("GetProblem");
	
	private String request;
	
	private Request(String request) {
		this.request = request;
	}
	
	public String getRequest() {
		return this.request;
	}
}
