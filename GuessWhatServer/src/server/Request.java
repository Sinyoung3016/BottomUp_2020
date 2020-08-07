package server;

public enum Request {
OVERLAP("OverLap"),	
LOGIN("Login"),
LOGOUT("LogOut"),
SIGNUP("SignUp"),
Join("Join"),
ADD_BAN("AddBan"),
ADD_BANMANAGER("AddBanManager"),
ADD_WORKBOOK("AddWorkbook"),
ADD_PROBLEM("AddProblem"),
DELETE_BAN("DeleteBan"),
DELETE_BANMANAGER("DeleteBanManger"),
DELETE_WORKBOOK("DeleteWorkbook"),
DELETE_PROBLEM("DeleteProblem"),
MODIFY_PROFESSOR("ModifyProfessor"),
MODIFY_BAN("ModifyBan"),
MODIFY_BANMANAGER("ModifyBanManager"),
MODIFY_WORKBOOK("ModifyWorkbook"),
MODIFY_PROBLEM("ModifyProblem"),
GET_PROFESSOR("GetProfessor"),
GET_BAN("GetBan"),
GET_ALLBAN("GetAllBan"),
GET_BANMANGER("GetBanManager"),
GET_WORKBOOK("GetWorkbook"),
GET_PROBLEM("GetProblem"),
GET_WORKBOOK_PROBLEM("GetWorkbookProblem"),
GET_ANSWERLIST("GetAnswerList"),
GET_TYPELIST("GetTypeList")
;
	
	private String request;
	
	private Request(String request) {
		this.request = request;
	}
	
	public String getRequest() {
		return this.request;
	}
}
