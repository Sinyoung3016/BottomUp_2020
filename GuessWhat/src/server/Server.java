package server;
import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;

import database.DB_Problem;
import database.DB_Workbook;
import exam.Problem;
import exam.Workbook;

public class Server {
	final int SERVER_PORT = 7000;
	public void run() {
		ServerSocket serverSocket = null;
		
		try {
			serverSocket = new ServerSocket();
			
			String localHostAddress = InetAddress.getLocalHost().getHostAddress();
			serverSocket.bind(new InetSocketAddress(localHostAddress, SERVER_PORT));
			System.out.println("[server] binding \naddress: " + localHostAddress + ", port: " + SERVER_PORT);
			
			Socket socket = serverSocket.accept();
			
			InetSocketAddress remoteSocketAddress = (InetSocketAddress)socket.getRemoteSocketAddress();
			String remoteHostName = remoteSocketAddress.getAddress().getHostAddress();
			int remoteHostPort = remoteSocketAddress.getPort();
			System.out.println("[server] connected! \nconnected socket address:" + remoteHostName+ ", port: " +remoteHostPort);
			
			new ServerThread(socket).start();
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

