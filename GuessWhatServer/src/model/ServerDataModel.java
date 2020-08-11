package model;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class ServerDataModel {
	private ServerSocket serverSocket;
	private List<Socket> socketList;
	public static Map<String, String> client_id_ip;
	private Map<String, PrintWriter> listClient;
	
	
	
	public ServerDataModel() {
		
		client_id_ip = new HashMap<>();
		listClient = new HashMap<>();
		socketList = new Vector<>();
		
		try {
			serverSocket = new ServerSocket();
		} catch(IOException e) {
			System.out.println("Error : " + e.getMessage() + "FROM serveOpen");
		}
		
	}
	
	public ServerSocket getServerSocket() {
		return serverSocket;
	}

	public void setServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	public List<Socket> getSocketList() {
		return socketList;
	}

	public void setSocketList(List<Socket> socketList) {
		this.socketList = socketList;
	}

	public Map<String, String> getClient_id_ip() {
		return client_id_ip;
	}

	public void setClient_id_ip(Map<String, String> client_id_ip) {
		this.client_id_ip = client_id_ip;
	}

	public Map<String, PrintWriter> getListClient() {
		return listClient;
	}

	public void setListClient(Map<String, PrintWriter> listClient) {
		this.listClient = listClient;
	}
}
	

	
	

