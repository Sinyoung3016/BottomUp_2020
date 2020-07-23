package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;

import database.DB_Problem;
import database.DB_Workbook;
import exam.Problem;
import exam.Workbook;
import exception.MyException;
import server.Request;
public class ServerThread extends Thread{
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
				String[] requestTokens = message.split(":");
				
				if(requestTokens[0].equals(Request.ADD_WORKBOOK.getRequest())) { //AddWorkbook:BMNum:PNum:Name:Size
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
				}
			} 
			finally {
				
			}
		}
		}catch(IOException e) {
			
		}finally {
			
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
}

