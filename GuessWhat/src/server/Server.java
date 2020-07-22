package server;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;

import database.DB_Problem;
import database.DB_Workbook;
import exam.Workbook;

public class Server {
	public String token;
	/*public PrintWriter pw;
	public BufferedReader br;
	public Socket socket;
	public ServerSocket server; */
	
	//Getter,Setter
	private void setToken(String token) {
		this.token = token;
	}
	
	public String getToken() {
		return this.token;
	}
	
	
	public void run(){
		PrintWriter pw = null;
		BufferedReader br = null;
		Socket socket = null;
		
		try {
			//8000번을 포트로 설정해 서버를 생성
			ServerSocket server = new ServerSocket(9000);
			
			//클라이언트 접속 accept
			System.out.println("서버가 요청을 기다립니다.");
			socket = server.accept();
			
			//클라이언트 접속
			InetAddress addr = socket.getInetAddress();
			String ip = addr.getHostAddress();
			System.out.println(ip + "의 클라이언트가 접속했습니다.");
			
			//InputStream 생성
			InputStream is = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			br = new BufferedReader(isr);
			
			//OutputStream 생성
			OutputStream os = socket.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os);
			pw = new PrintWriter(osw);
			
			//클라이언트가 보낸 메세지 출력
			String message = null;
			
			while((message = br.readLine()) != null) {
				if(message.equals("quit")) break;
				if(message.equals("give Workbook")) {
					List<Workbook> workbookList = this.getAllWorkbook();
					Iterator<Workbook> iterator = workbookList.iterator();
					while(iterator.hasNext()) {
						Workbook workbook = iterator.next();
						pw.println(workbook.toString());
						pw.flush();
					}
				}
				else {
				insertWorkbook(message);
				System.out.println(message);
				}
			}
			server.close();
			return;
		} catch(IOException e) {
			System.out.println("서버가 준비되지 않았습니다." + e.getMessage());
		} finally {
			try {
                if (pw != null) pw.close();
                if (br != null) br.close();
                if (socket != null) {
                    socket.close();
                }
            } catch (Exception e) {}
		}
	}
	
	//Workbook
	public void insertWorkbook(String requestToken) {
		String [] requestTokens=requestToken.split(":"); //WNum:BMNum:PNum:Name:Size
		DB_Workbook.insertWorkbook(requestTokens[0],requestTokens[1],requestTokens[2], requestTokens[3]);
	}
	
	public void deleteWorkbook(String WNum) {
		DB_Workbook.deleteWorkbook(WNum);
	}
	
	public void modifyWorkbook (String requestToken) {
		String [] requestTokens = requestToken.split(":"); //WNum:Name;
		DB_Workbook.modifyName(requestTokens[0], requestTokens[1]);
	}
	
	public List<Workbook> getAllWorkbook() {
		List<Workbook> workbookList = DB_Workbook.getAllWorkbook();
		return workbookList;
	}
	
	//Problem
	public void insertProblem(String requestToken) {
		String[] requestTokens = requestToken.split(":"); //WNum:Question:Answer:Type:AnswerContents
		DB_Problem.insertProblem(requestTokens[0], requestTokens[1], requestTokens[2], requestTokens[3], requestTokens[4]);
	}
	
	
	
	/*public void serverOpen() { //서버생성 (version.Local)
		try{
			this.server= new ServerSocket(8000);
			System.out.println("서버가요청을 기다립니다.");
	        socket = server.accept();
	
	        InetAddress addr = socket.getInetAddress();
	        String ip = addr.getHostAddress();
	        System.out.println(ip + "의 클라이언트가 접속했습니다.");
	
		}catch (IOException e) {
	        System.out.println("서버가 준비되지 않았습니다." + e.getMessage());
	    } 
	}

	public void serverClose() {
		try {
			socket.close();
			br.close();
			pw.close();
			
			if(server != null && !server.isClosed()) {
				server.close();
			}
		}catch(IOException e) {
			System.out.println("Error: " +e.getMessage() + "IN serverClose");
		}
		
		
	}
	
	
	
	public String outputClientData() {
		//Stream 생성
		String message = null;
		try {
		InputStream is = this.socket.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		this.br = new BufferedReader(isr);
		
		//클라이언트 데이터 출력
		while((message = this.br.readLine()) != null) {
			if(message.equals("quit")) {
				this.serverClose();
				break;
			}
			
			setToken(message);
			this.insertWorkbook(message);
			System.out.println(message);
		}
		
		}catch(IOException e) {
			System.out.println("Error: " + e.getMessage() + " IN outputClientData");
		}
		return message;
	}
	
	public void run() {
		this.serverOpen();
		String message = this.outputClientData();
		this.insertWorkbook(message);
	} */
}
