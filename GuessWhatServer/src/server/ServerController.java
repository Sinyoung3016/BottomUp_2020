package server;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.ServerDataModel;
import thread.ClientAcceptThread;
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
import database.DB_Ban;
import database.DB_Problem;
import database.DB_USER;
import database.DB_Workbook;
import exam.Problem;
import exam.Workbook;
import exception.MyException;

public class ServerController {
	@FXML
	private Button btn_Open, btn_Close;

	static ServerDataModel dataModel;
	ServerSocket serverSocket;
	int i = 1;

	public void btn_Open_Action() throws IOException {
		// ServerOpen
		dataModel = new ServerDataModel();
		this.serverSocket = dataModel.getServerSocket();
		new ClientAcceptThread(this.dataModel).start();
	}

	public void btn_Close_Action() {
		// 서버종료
		try {
			Iterator<Socket> iterator = dataModel.getSocketList().iterator();

			while (iterator.hasNext()) {
				Socket socket = iterator.next();
				PrintWriter pw = new PrintWriter(
						new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
				pw.println("ShutDown:");
				pw.flush();
				socket.close();
				iterator.remove();
			}

			if (this.serverSocket != null && !this.serverSocket.isClosed()) {
				System.out.println("---ServerClose---");
				this.serverSocket.close();
			}
		} catch (Exception e) {
			System.out.println("Error : " + e.getMessage() + "FROM serverClose");
		}
	}

	public Object CloseButtonActione() {
		btn_Close_Action();
		return null;
	}

}

// Here is MoonDD's Test Code! Please don't touch!
/*
 * ----------------ChangeState Test------------------- try { Iterator<Socket>
 * iterator = dataModel.getSocketList().iterator();
 * System.out.println("btn_Empty_Action"); while(iterator.hasNext()) { Socket
 * socket = iterator.next(); PrintWriter pw=new PrintWriter(new
 * OutputStreamWriter(socket.getOutputStream(),StandardCharsets.UTF_8));
 * pw.println("ChangeState:"); pw.flush(); } } catch(IOException e) {
 * System.out.println("Error: " +e.getMessage() + "FROM btn_Empty_Action"); }
 * 
 * ----------------------SizeUpBanManager-------------------- try {
 * Iterator<Socket> iterator = dataModel.getSocketList().iterator();
 * while(iterator.hasNext()) { Socket socket = iterator.next(); PrintWriter
 * pw=new PrintWriter(new
 * OutputStreamWriter(socket.getOutputStream(),StandardCharsets.UTF_8));
 * pw.println("SizeUpBanManager:Moondd:1:3"); pw.flush(); } } catch(IOException
 * e) { System.out.println("Error: " +e.getMessage() + "FROM btn_Empty_Action");
 * }
 */
