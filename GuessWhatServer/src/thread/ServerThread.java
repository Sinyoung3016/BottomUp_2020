package thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;

import authentication.LogInContext;
import authentication.RoomCode;
import database.DB_Ban;
import database.DB_BanManager;
import database.DB_Problem;
import database.DB_Student;
import database.DB_USER;
import database.DB_Workbook;
import exam.Problem;
import exam.Workbook;
import exception.MyException;
import model.ServerDataModel;
import room.Ban;
import room.BanManager;
import server.Request;
import user.Professor;

public class ServerThread extends Thread{

	private Socket socket;
	private BufferedReader br = null;
	private PrintWriter pw = null;
	private String id = "";
	private String clientRequest = null;
	private ServerDataModel dataModel;

	public ServerThread(Socket socket, ServerDataModel dataModel) {
		this.socket = socket;
		this.dataModel = dataModel;

	}

	@Override
	public void run() {
		try {

			InputStreamReader isr = new InputStreamReader(this.socket.getInputStream(), StandardCharsets.UTF_8);
			this.br = new BufferedReader(isr);

			OutputStreamWriter osw = new OutputStreamWriter(this.socket.getOutputStream(),StandardCharsets.UTF_8);
			this.pw = new PrintWriter(osw);

			while(true) {
				try {
					String message = br.readLine();
					if(message == "quit") {
						socket.close();
						break;
					}
					if(message != null) {
						String[] requestTokens = message.split(":");
						if(requestTokens[0].equals(Request.LOGIN.getRequest())) {//Login:Id
							clientRequest = "LogIn";
							this.Login(requestTokens[1],requestTokens[2]);
						}
						else if(requestTokens[0].equals(Request.LOGOUT.getRequest())) {//Logout:Id
							clientRequest = "LogOut";
							this.Logout(requestTokens[1]);
						}
						else if(requestTokens[0].equals(Request.OVERLAP.getRequest())) { //OverLap:Id
							clientRequest = "OverLap";
							this.overLap(requestTokens[1]);
						}
						else if(requestTokens[0].equals(Request.SIGNUP.getRequest())) { //SignUp:Id:Password:Email
							clientRequest = "SignUp";
							this.signUp(requestTokens[1],requestTokens[2],requestTokens[3]);
						}
						else if(requestTokens[0].equals(Request.Join.getRequest())) { //Join:Code
							clientRequest = "Join";
							this.join(requestTokens[1]);
						}
						else if(requestTokens[0].equals(Request.ADD_BAN.getRequest())) { //AddBan:(BNum):Name:PNum
							clientRequest = "AddBan";
							this.addBan(requestTokens[1]);
						}else if(requestTokens[0].equals(Request.ADD_BANMANAGER.getRequest())) { //AddBanManager:PNum:BNum:Name:Code:Workbook
							clientRequest = "AddBanManager";
							this.addBanManager(requestTokens[1], requestTokens[2], requestTokens[3], requestTokens[4], requestTokens[5]);
						}
						else if(requestTokens[0].equals(Request.ADD_WORKBOOK.getRequest())) { //AddWorkbook:PNum:Name:Size
							clientRequest = "AddWorkbook";
							this.addWorkbook(requestTokens[1], requestTokens[2], requestTokens[3]);
						}
						else if(requestTokens[0].equals(Request.ADD_PROBLEM.getRequest())) { //AddProblem:problem1_problem2(WNum`question`answer`type`answerContents)...
							clientRequest = "AddProblem";
							this.addProblem(requestTokens[1]);
						}else if(requestTokens[0].equals(Request.ADD_STUDENT.getRequest())) { //AddStudent:BNum:BMNum:WNum:Name:Answer:Result
							clientRequest = "AddStudent";
							this.addStudent(requestTokens[1], requestTokens[2], requestTokens[3], requestTokens[4], requestTokens[5],requestTokens[6]);
						}
						else if(requestTokens[0].equals(Request.DELETE_BAN.getRequest())) { //DeleteBan:PNum:BNum
							clientRequest = "DeleteBan";
							this.deleteBan(requestTokens[1], requestTokens[2]);
						}else if(requestTokens[0].equals(Request.DELETE_BANMANAGER.getRequest())) { //DeleteBanManager:PNum:BNum:BMNum
							clientRequest = "DeleteBanManager";
							this.deleteBanManager(requestTokens[1], requestTokens[2], requestTokens[3]);
						}
						else if(requestTokens[0].equals(Request.DELETE_WORKBOOK.getRequest())) { //DeleteWorkbook:WNum
							clientRequest = "DeleteWorkbook";
							this.deleteWorkbook(requestTokens[1]);
						}
						else if(requestTokens[0].equals(Request.DELETE_PROBLEM.getRequest())) { //DeleteProblem:PNum
							clientRequest = "DeleteProblem";
							this.deleteProblem(requestTokens[1]);
						}else if(requestTokens[0].equals(Request.MODIFY_PROFESSOR.getRequest())) { //ModifyProfessor:ID:newEmail:newPassWord
							clientRequest = "ModifyProfessor";
							this.modifyProfessor(requestTokens[1], requestTokens[2], requestTokens[3]);
						}else if(requestTokens[0].equals(Request.MODIFY_BAN.getRequest())) { //ModifyBan:PNum:BNum
							clientRequest = "ModifyBan";
							this.modifyBan(requestTokens[1], requestTokens[2], requestTokens[3]);
						}else if(requestTokens[0].equals(Request.MODIFY_STATE.getRequest())) { //ModifyState:BMNum:State
							clientRequest = "ModifyState";
							this.modifyState(requestTokens[1], requestTokens[2]);
						}
						else if(requestTokens[0].equals(Request.MODIFY_WORKBOOK.getRequest())) { //ModifyWorkbook:WNum:newName
							clientRequest = "ModifyWorkbook";
							this.modifyWorkbook(requestTokens[1], requestTokens[2]);
						}else if(requestTokens[0].equals(Request.MODIFY_PROBLEM.getRequest())) { //ModifyProblem:PNum:newQuestion
							clientRequest = "ModifyProblem";
							this.modifyProblem(requestTokens[1], requestTokens[2]);
						}else if(requestTokens[0].equals(Request.GET_PROFESSOR.getRequest())) { //GetProfessor:Id
							clientRequest = "GetProfessor";
							this.getProfessor(requestTokens[1]);
						}else if(requestTokens[0].equals(Request.GET_BANMANGER.getRequest())) { //GetBanManager:code
							clientRequest = "GetBanManager";
							this.getBanManager(requestTokens[1]);
						}else if(requestTokens[0].equals(Request.GET_CURRENTBANMANAGER.getRequest())) { //GetCurrentBanManager:PNum:BNum
							clientRequest = "GetCurrentBanManager";
							this.getCurrentBanManager(requestTokens[1], requestTokens[2]);
						}else if(requestTokens[0].equals(Request.GET_ALLBAN.getRequest())) { //GetAllBan:PNum
							clientRequest = "GetAllBan";
							this.getAllBan(requestTokens[1]);
						}else if(requestTokens[0].equals(Request.GET_ALLBANMANGER.getRequest())) { //GetAllBanManager:PNum:BNum
							clientRequest = "GetAllBanManager";
							this.getAllBanManager(requestTokens[1], requestTokens[2]);
						}else if(requestTokens[0].equals(Request.GET_BAN.getRequest())) { //GetBan:PNum:BNum
							clientRequest = "GetBan";
							this.getBan(requestTokens[1], requestTokens[2]);
						}else if(requestTokens[0].equals(Request.GET_ALLBAN.getRequest())) { //GetAllBan:PNum
							clientRequest = "GetAllBan";
							this.getAllBan(requestTokens[1]);
						}
						else if(requestTokens[0].equals(Request.GET_WORKBOOK.getRequest())) { //GetWorkbook
							clientRequest = "GetWorkbook";
							this.getWorkbook(); 
						}else if(requestTokens[0].equals(Request.GET_ALLWORKBOOK.getRequest())) { //GetAllWorkbook:PNum
							clientRequest = "GetAllWorkbook";
							this.getAllWorkbook(requestTokens[1]); 
						}else if(requestTokens[0].equals(Request.GET_CURRENTWORKBOOK.getRequest())) { //GetCurrentWorkbook:WNum
							clientRequest = "GetCurrentWorkbook";
							this.getCurrentWorkbook(requestTokens[1]);
						}
						else if(requestTokens[0].equals(Request.GET_PROBLEM.getRequest())) { //GetProblem:WNum:Index
							clientRequest = "GetProblem";
							this.getProblem(requestTokens[1],requestTokens[2]);
						}else if(requestTokens[0].equals(Request.GET_WORKBOOK_PROBLEM.getRequest())) {
							clientRequest = "GetWorkbookProblem";
							this.getWorkbookProblem(requestTokens[1]);
						}else if(requestTokens[0].equals(Request.GET_ANSWERLIST.getRequest())) { //GetAnswerList:WNum
							clientRequest = "GetAnswerList";
							this.getAnswerList(requestTokens[1]);
						}else if(requestTokens[0].equals(Request.GET_TYPELIST.getRequest())) { //GetTypeList:WNum
							clientRequest = "GetTypeList";
							this.getTypeList(requestTokens[1]);
						}
					}
				} catch(MyException e) {
					pw.println(clientRequest + ":" + e.getMessage());
					pw.flush();
				}
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally {

		}
	}
	private void Login(String Id,String password) throws MyException {
		if(LogInContext.logIn(Id, password)) {
			DB_USER.userLogIn(Id);
			System.out.println(Id + "님이 입장하셨습니다.");
			pw.println("LogIn:Success:" + DB_USER.getUser(Id).tokenString());
			pw.flush();
			this.dataModel.getClient_id_ip().put(Id,socket.getInetAddress().toString());
			this.id=Id;
			this.dataModel.getListClient().put(this.id,pw);
		}
	}

	private void Logout(String Id) {
		if(DB_USER.userLogOut(Id)) {
			System.out.println(Id + "님이 로그아웃합니다.");
			pw.println("LogOut:Success");
			pw.flush();
			this.dataModel.getClient_id_ip().remove(Id);
			this.dataModel.getListClient().remove(Id);
		}
	}

	private void overLap(String Id) throws MyException {
		if(LogInContext.overLap(Id)) {
			pw.println("OverLap:Success");
			pw.flush();
		}
	}

	private void signUp(String ID, String PassWord, String Email) {
		if(DB_USER.insertUser(ID,PassWord, Email, "true")) {
			pw.println("SignUp:Success");
			pw.flush();
		}
	}

	private void join(String code) throws MyException {
		if(RoomCode.join(code)) {
			pw.println("Join:Success");
			pw.flush();
		}
	}
	private void addBan(String PNum) {
		int pNum = Integer.parseInt(PNum);
		if(DB_Ban.insertBan(pNum, "new"))
			pw.println("AddBan:Success");
		else pw.println(">>FAIL [AddBan]<<");

		pw.flush();
	}
	private void addBanManager(String PNum, String BNum, String name, String code, String WNum) {
		int pNum = Integer.parseInt(PNum);
		int bNum = Integer.parseInt(BNum);
		int wNum = Integer.parseInt(WNum);

		if(DB_BanManager.insertBanManager(pNum, bNum, name, code, wNum))
			pw.println("AddBan:Success");
		else pw.println(">>FAIL [AddBan]<<");

		pw.flush();
	}
	
	private void addWorkbook(String PNum, String Name, String Size) {
		if(DB_Workbook.insertWorkbook(PNum,Name,Size)) {
			int WNum = DB_Workbook.getWNumOf(PNum, Name);
			if(WNum < 0) {
				System.out.println("AddWorkbook:Fail");
				pw.println("AddWorkbook:Fail");
			}
			else {
				pw.println("AddWorkbook:Success:" + WNum);
			}
		}
			
		else pw.println("AddWorkbook:Fail");
		pw.flush();
	}

	private void addProblem(String problems) {
		String[] problemList = problems.split("_");
		for(int i = 0; i < problemList.length; i++) {
			//WNum`question`answer`type`answerContents
			String[] problemInfo = problemList[i].split("`");
			if(!DB_Problem.insertProblem(problemInfo[0], problemInfo[1], problemInfo[2], problemInfo[3],problemInfo[4])) {
				pw.println("AddProblem:Fail");
				pw.flush();
				break;
			}
		}
		pw.println("AddProblem:Success");
		pw.flush();
	
	}
	
	private void addStudent(String BNum, String BMNum, String WNum, String Name,String Answer,String Result) {
		if(DB_Student.insertStudent(BNum, BMNum, WNum, Name, Answer, Result)) 
			pw.println("AddStudent:Success");
		
		else 
			pw.println("AddStudent:Fail");
		pw.flush();
	}
	private void deleteBan(String PNum, String BNum) {
		int pNum = Integer.parseInt(PNum);
		int bNum = Integer.parseInt(BNum);

		if(DB_Ban.deleteBan(pNum, bNum))
			pw.println(">>SUCCESS [DeleteBan]<<");
		else pw.println(">>FAIL [DeleteBan[<<");

		pw.flush();
	}
	private void deleteBanManager(String PNum, String BNum, String BMNum) {
		int pNum = Integer.parseInt(PNum);
		int bNum = Integer.parseInt(BNum);
		int bmNum = Integer.parseInt(BMNum);

		if(DB_BanManager.deleteBanManager(pNum, bNum, bmNum))
			pw.println("DeleteBanManager:Success");
		else pw.println("DeleteBanManager:Fail");

		pw.flush();
	}
	private void deleteWorkbook(String WNum) {
		if(DB_Workbook.deleteWorkbook(WNum))
			pw.println(">>SUCCESS [DeleteWorkbook]<<");
		else pw.println(">>FAIL [DeleteWorkbook[<<");

		pw.flush();
	}

	private void deleteProblem(String PNum) {
		if(DB_Problem.deleteProblem(PNum)) 
			pw.println(">>SUCCESS [DeleteProblem]<<");
		else pw.println(">>FAIL [DeleteProblem]<<");

		pw.flush();
	}
	
	private void modifyProfessor(String id, String newEmail, String newPassword) {
		if(DB_USER.modifyProfessor(id, newEmail, newPassword)) 
			pw.println("ModifyProfessor:Success");
		else 
			pw.println("ModifyProfessor:Fail");
		pw.flush();
	}
	private void modifyBan(String PNum, String BNum, String newName) {
		int pNum = Integer.parseInt(PNum);
		int bNum = Integer.parseInt(BNum);
		
		if(DB_Ban.modifyBanName(pNum, bNum, newName))
			pw.println(">>SUCCESS [ModifyBan]<<");
		else pw.println(">>FAIL [ModifyBan]<<");

		pw.flush();
	}
	private void modifyState(String BMNum, String newState) {
		int bmNum = Integer.parseInt(BMNum);
		
		if(DB_BanManager.modifyState(bmNum, newState))
			pw.println("ModifyState:Success");
		else pw.println("ModifyState:Fail");

		pw.flush();
	}
	private void modifyWorkbook(String WNum, String newName) {
		if(DB_Workbook.modifyWorkbookName(WNum, newName))
			pw.println(">>SUCCESS [ModifyWorkbook]<<");
		else pw.println(">>FAIL [DeleteWorkbook]<<");

		pw.flush();
	}

	private void modifyProblem(String PNum, String newQuestion){ 
		if(DB_Problem.modifyProblemName(PNum, newQuestion))
			pw.println(">>SUCCESS [ModifyProblem]<<");
		else pw.println(">>FAIL [ModifyProblem]<<");

		pw.flush();
	}
	private void getProfessor(String Id) {
		Professor professor = DB_USER.getUser(Id);
		if(professor != null) {
			pw.println(">>SUCCESS [GetProfessor]<<");
			pw.flush();
			pw.println(professor.toString());
			pw.flush();	
		}
		else {
			pw.println(">>FAIL [GetProfessor]<<");
			pw.flush();
		}
	}
	private void getBan(String PNum, String BNum) {

		Ban ban = DB_Ban.getBan(Integer.parseInt(PNum), Integer.parseInt(BNum));

		if (ban == null) {
			pw.println("GetBan:Fail " + PNum);
			pw.flush();
		}
		else {
			String result = "GetBan:Success";
			result = result + ":" + ban.ban_num() + ":" + ban.ban_name() + ":" + ban.banManager_Size();
			
			pw.println(result);
			pw.flush();
		}
	}
	private void getAllBan(String PNum) { //GetAllBan:Success:BNum:Name:BM_Size

		List<Ban> listBan = DB_Ban.getAllBan(Integer.parseInt(PNum));

		if (listBan == null) {
			pw.println("GetAllBan:Fail " + PNum);
			pw.flush();
		}
		else {
			String result = "GetAllBan:Success";

			Iterator<Ban> iterator = listBan.iterator();
			while(iterator.hasNext()) {
				Ban ban = iterator.next();
				result = result + ":" + ban.ban_num() + ":" + ban.ban_name() + ":" + ban.banManager_Size();
			}
			pw.println(result);
			pw.flush();
		}
	}
	private void getAllBanManager(String PNum, String BNum) { //GetAllBanManager:Success:Name, State, Code, WorkBook

		List<BanManager> listBanManager = DB_BanManager.getAllBanManager(Integer.parseInt(PNum), Integer.parseInt(BNum));

		if (listBanManager == null) {
			pw.println("GetAllBan:Fail " + PNum);
			pw.flush();
		}
		else {
			String result = "GetAllBanManager:Success";

			Iterator<BanManager> iterator = listBanManager.iterator();
			while(iterator.hasNext()) {
				BanManager banManager = iterator.next();
				result = result + ":" + banManager.BM_num() + ":" + 
						banManager.BM_name() + ":" + 
						banManager.stateToString(banManager.BM_sate()) + ":" +
						banManager.BM_roomcode() + ":" + 
						banManager.workbook() + ":" + 
						banManager.student_size();
			}
			pw.println(result);
			pw.flush();
		}
	}
	private void getBanManager(String code) {
		BanManager banManager = DB_BanManager.getBanManagerOfCode(code);
		if(banManager != null) {
			pw.println("GetBanManager:Success:" + banManager.tokenString());
			pw.flush();
		}

	}
	private void getCurrentBanManager(String PNum, String BNum) { //GetCurrentBanManager:Success:Name, State, Code, WorkBook

		BanManager banManager = DB_BanManager.getCurrentBanManager(Integer.parseInt(PNum), Integer.parseInt(BNum));

		if (banManager == null) {
			pw.println("GetCurrentBan:Fail " + PNum);
			pw.flush();
		}
		else {
			String result = "GetCurrentBanManager:Success";

			result = result + ":" + banManager.BM_num() + ":" + 
					banManager.BM_name() + ":" + 
					banManager.stateToString(banManager.BM_sate()) + ":" +
					banManager.BM_roomcode() + ":" + 
					banManager.workbook() + ":" + 
					banManager.student_size();

			pw.println(result);
			pw.flush();
		}
	}
	private void getWorkbook() {
		List<Workbook> listWorkbook = DB_Workbook.getAllWorkbook();
		if(listWorkbook == null) {
			pw.println(">>FAIL [GetWorkbok]<<");
			pw.flush();
		}
		else {
			Iterator<Workbook> iterator = listWorkbook.iterator();
			pw.println(">>SUCCESS [GetWorkbook]");
			pw.flush();
			while(iterator.hasNext()) {
				Workbook workbook = iterator.next();
				pw.println(workbook.toString());
				pw.flush();
			}
		}
	}
	private void getAllWorkbook(String PNum) { //GetAllWorkbook:PNum

		int pNum = Integer.parseInt(PNum);
		
		List<Workbook> listWorkbook = DB_Workbook.getWorkbookList(pNum);

		if (listWorkbook == null) {
			pw.println("GetAllWorkbook:Fail");
			pw.flush();
		}
		else {
			String result = "GetAllWorkbook:Success";

			Iterator<Workbook> iterator = listWorkbook.iterator();
			while(iterator.hasNext()) {
				Workbook workbook = iterator.next();
				result = result + ":" + workbook.getWBNum() + ":" + workbook.getName() + ":" + workbook.getSize();
			}
			pw.println(result);
			pw.flush();
		}
	}
	private void getCurrentWorkbook(String WNum) { 

		Workbook workbook = DB_Workbook.getCurrentWorkbook(Integer.parseInt(WNum));

		if (workbook == null) {
			pw.println("GetCurrentWorkbook:Fail");
			pw.flush();
		}
		else {
			String result = "GetCurrentWorkbook:Success";
			result = result + ":" + workbook.getName() + ":" + workbook.getSize();

			pw.println(result);
			pw.flush();
		}
	}
	private void getProblem(String WNum, String index) {
		Problem problem = DB_Problem.getProblemOf(Integer.parseInt(WNum), Integer.parseInt(index));
		if(problem == null) 
			pw.println("GetProblem:Fail");
		else pw.println("GetProblem:Success:" + problem.tokenString());
		pw.flush();
	}
	
	private void getWorkbookProblem(String BMNum) {
		int num = Integer.parseInt(BMNum);
		Workbook workbook = DB_Workbook.getWorkbookOf(num);
		StringBuilder sb = new StringBuilder("");
		if(workbook == null) {
			pw.println("GetWorkbook:Fail");
			pw.flush();
		}
		else {
			sb.append("GetWorkbook:Success:");
			sb.append(workbook.tokenString()+ ":");
			Problem problem = DB_Problem.getProblemOf(workbook.getWBNum(), 0);
			if(problem == null) {
				sb.append("GetProblem:Fail");
				pw.println(new String(sb));
				pw.flush();
			}
			else {
				sb.append("GetProblem:Success:");
				sb.append(problem.tokenString());
				pw.println(new String(sb));
				pw.flush();
			}
		}
	}
	
	private void getAnswerList(String WNum) {
		int num = Integer.parseInt(WNum);
		List<String> answerList = DB_Problem.getAnswerList(num);
		
		if(answerList != null) {
			StringBuilder sb = new StringBuilder("GetAnswerList:Success:");
			
			Iterator<String> iterator = answerList.iterator();
			while(iterator.hasNext()){
				sb.append(iterator.next());
				sb.append("`");
			}
			sb.deleteCharAt(sb.length()-1);
			
			pw.println(new String(sb));
			pw.flush();
		}
		else {
			pw.println("GetAnswerList:Fail");
			pw.flush();
		}
	}
	
	private void getTypeList(String WNum) {
		int num = Integer.parseInt(WNum);
		List<String> typeList = DB_Problem.getTypeList(num);
		
		if(typeList != null) {
			StringBuilder sb = new StringBuilder("GetTypeList:Success:");
			
			Iterator<String> iterator = typeList.iterator();
			while(iterator.hasNext()){
				sb.append(iterator.next());
				sb.append("-");
			}
			sb.deleteCharAt(sb.length()-1);

			pw.println(new String(sb));
			pw.flush();
		}
		else {
			pw.println("GetTypeList:Fail");
			pw.flush();
		}
	}
}

