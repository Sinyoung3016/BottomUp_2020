package server;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import server.Server.AcceptThread;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import database.DB_Problem;
import database.DB_Workbook;
import exam.Problem;
import exam.Workbook;

public class ServerController {
	@FXML
	private Button btn_Open, btn_Close, btn_Empty, btn_Enter;
	@FXML
	private TextField tf_input;
	
	ServerSocket serverSocket;
	List<Socket> socketList;
	
	public void btn_Open_Action() {
		// 서버 열기
		socketList = new Vector<>();
		try {
			serverSocket = new ServerSocket();
		}catch(IOException e) {
			System.out.println("Error : " + e.getMessage() + "FROM serveOpen");
		}
		new AcceptThread().start();
	}


	public void btn_Close_Action() {
		// 서버 닫기
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
		//텍스트 입력받고, 값이 있으면 버튼누르면 실행
		String input = tf_input.getText(); //input에 입력받은 텍스트
		while(input == null) {
			input = tf_input.getText();
			
			
			
		}
	}

	public void btn_Empty_Action() {
		//그냥 버튼 누르면 실행
	}
	//MoonDD's PlayGround end

	public Object CloseButtonActione() {
		// 닫기 버튼 눌렀을 때, 서버 종료하고 닫음.
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
}
