package server;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import user.Professor;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import authentication.LogInContext;
import database.DB_Problem;
import database.DB_USER;
import database.DB_Workbook;
import exam.Problem;
import exam.Workbook;
import exception.MyException;

public class ServerController {
	@FXML
	private Button btn_Open, btn_Close, btn_Empty, btn_Enter;
	@FXML
	private TextField tf_input;
	
	ServerSocket serverSocket;
	List<Socket> socketList;
	Map<String, String> client_id_ip;
	Map<String, PrintWriter> listClient;
	
	public void btn_Open_Action() {
		// ServerOpen
		client_id_ip = new HashMap<>();
		listClient = new HashMap<>();
		socketList = new Vector<>();
		try {
			serverSocket = new ServerSocket();
		}catch(IOException e) {
			System.out.println("Error : " + e.getMessage() + "FROM serveOpen");
		}
		new AcceptThread().start();
	}


	public void btn_Close_Action() {
		// 서버종료
		try {
			Iterator<Socket> iterator = socketList.iterator();
			
			while(iterator.hasNext()) {
				Socket socket = iterator.next();
				PrintWriter pw=new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),StandardCharsets.UTF_8));
				pw.println("ShutDown:");
				pw.flush();
				socket.close();
				iterator.remove();
			}
			
			if(serverSocket != null && !serverSocket.isClosed()) {
				serverSocket.close();
			}
		} catch(Exception e) {
			System.out.println("Error : " +e.getMessage() + "FROM serverClose");
		}
	}

	//MoonDD's PlayGround start
	public void btn_Enter_Action() {
		//�뀓�뒪�듃 �엯�젰諛쏄퀬, 媛믪씠 �엳�쑝硫� 踰꾪듉�늻瑜대㈃ �떎�뻾
		String input = tf_input.getText(); //input�뿉 �엯�젰諛쏆� �뀓�뒪�듃
		while(input == null) {
			input = tf_input.getText();
			
			
			
		}
	}

	public void btn_Empty_Action() {
		//洹몃깷 踰꾪듉 �늻瑜대㈃ �떎�뻾
	}
	//MoonDD's PlayGround end

	public Object CloseButtonActione() {
		// �떕湲� 踰꾪듉 �닃���쓣 �븣, �꽌踰� 醫낅즺�븯怨� �떕�쓬.
		btn_Close_Action();
		return null;
	}
	
	class AcceptThread extends Thread{
		private final static int SERVER_PORT = 6000;
		
		@Override
		public void run() {
			
			try {
				String localHostAddress = InetAddress.getLocalHost().getHostAddress();
				serverSocket.bind(new InetSocketAddress(localHostAddress, SERVER_PORT));
				System.out.println("[server] binding \naddress: " + localHostAddress + ", port: " + SERVER_PORT);
				
				
				while(true) {
					Socket socket = serverSocket.accept();
					socketList.add(socket);
					
					InetSocketAddress remoteSocketAddress = (InetSocketAddress)socket.getRemoteSocketAddress();
					String remoteHostName = remoteSocketAddress.getAddress().getHostAddress();
					int remoteHostPort = remoteSocketAddress.getPort();
					System.out.println("[server] connected! \nconnected socket address:" + remoteHostName+ ", port: " +remoteHostPort);

					new ServerThread(socket).start();
				}
				
			} catch (IOException e ) {
				e.printStackTrace();
			} finally {
				try {
					if(serverSocket != null && !serverSocket.isClosed()) {
						serverSocket.close();
					}
				}
				catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	class ServerThread extends Thread{
		private Socket socket;
		private BufferedReader br = null;
		private PrintWriter pw = null;
		private String id = "";
		private String clientRequest = null;
		
		public ServerThread(Socket socket) {
			this.socket = socket;
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
							clientRequest = "Logout";
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
				System.out.println(Id + "님이 로그인하셨습니다.");
				pw.println("LogIn:Success:" + DB_USER.getUser(Id).tokenString());
				pw.flush();
				client_id_ip.put(Id,socket.getInetAddress().toString());
				listClient.put(id,pw);
				this.id=Id;
			}
		}
		
		private void Logout(String Id) {
			if(DB_USER.userLogOut(Id))
				pw.println(">>SUCCESS [Logout]<<");
			else pw.println("FAIL [Logout]<<");
			
			pw.flush();
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
}
