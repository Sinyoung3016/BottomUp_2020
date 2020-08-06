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
						}
						else if(requestTokens[0].equals(Request.ADD_WORKBOOK.getRequest())) { //AddWorkbook:BMNum:PNum:Name:Size
							clientRequest = "AddWorkbook";
							this.addWorkbook(requestTokens[1], requestTokens[2], requestTokens[3], requestTokens[4]);
						}
						else if(requestTokens[0].equals(Request.ADD_PROBLEM.getRequest())) { //AddProblem:WNum:Question:Answer:Type:AnswerContents
							clientRequest = "AddProblem";
							this.addProblem(requestTokens[1], requestTokens[2], requestTokens[3], requestTokens[4], requestTokens[5]);
						}
						else if(requestTokens[0].equals(Request.DELETE_WORKBOOK.getRequest())) { //DeleteWorkbook:WNum
							clientRequest = "DeleteWorkbook";
							this.deleteWorkbook(requestTokens[1]);
						}
						else if(requestTokens[0].equals(Request.DELETE_PROBLEM.getRequest())) { //DeleteProblem:PNum
							clientRequest = "DeleteProblem";
							this.deleteProblem(requestTokens[1]);
						}else if(requestTokens[0].equals(Request.MODIFY_WORKBOOK.getRequest())) { //ModifyWorkbook:WNum:newName
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
						}else if(requestTokens[0].equals(Request.GET_BAN.getRequest())) { //GetBan:PNum
							clientRequest = "GetBan";
							this.getBan(requestTokens[1]);
						}
						else if(requestTokens[0].equals(Request.GET_WORKBOOK.getRequest())) { //GetWorkbook
							clientRequest = "GetWorkbook";
							this.getWorkbook();
						}else if(requestTokens[0].equals(Request.GET_PROBLEM.getRequest())) { //GetProblem:WNum
							clientRequest = "GetProblem";
							this.getProblem(requestTokens[1]);
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

	private void addWorkbook(String BMNum, String PNum, String Name, String Size) {
		if(DB_Workbook.insertWorkbook(BMNum,PNum,Name,Size)) 
			pw.println(">>SUCCESS [AddWorkbook]<<");
		else pw.println(">>FAIL [AddWorkbook]<<");

		pw.flush();
	}

	private void addProblem(String WNum, String Question, String Answer, String Type, String AnswerContents) {
		if(DB_Problem.insertProblem(WNum, Question, Question, Type, AnswerContents))
			pw.println(">>SUCCESS [AddProblem]<<");
		else pw.println(">>FAIL [AddProblem]");

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
	private void getBan(String PNum) {

		List<Ban> listBan = DB_Ban.getAllBan(Integer.parseInt(PNum));

		if (listBan == null) {
			pw.println("GetBan:Fail " + PNum);
			pw.flush();
		}
		else {
			String result = "GetBan:Success";

			Iterator<Ban> iterator = listBan.iterator();
			while(iterator.hasNext()) {
				Ban ban = iterator.next();
				result = result + ":" + ban.ban_num() + ":" + ban.ban_name() + ":" + ban.banManager_Size();
			}
			pw.println(result);
			pw.flush();
		}
	}
	private void getBanManager(String code) {
		BanManager banManager = DB_BanManager.getBanManagerOfCode(code);
		if(banManager != null) {
			System.out.println(banManager.tokenString());
			pw.println("GetBanManager:Success:" + banManager.tokenString());
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

	private void getProblem(String PNum) {
		List<Problem> listProblem = DB_Problem.getProblemOf(PNum);
		if(listProblem == null) {
			pw.println(">>FAIL [GetProblem]<<");
			pw.flush();
		}	
		else {
			Iterator<Problem> iterator = listProblem.iterator();
			pw.println(">>SUCCESS [GetProblem]<<");
			pw.flush();
			while(iterator.hasNext()) {
				Problem problem = iterator.next();
				pw.println(problem.toString());
				pw.flush();
			}
		}
	}

}

