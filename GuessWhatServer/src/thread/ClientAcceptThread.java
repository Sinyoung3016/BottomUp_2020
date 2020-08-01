package thread;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import model.ServerDataModel;



public class ClientAcceptThread extends Thread{
	private final static int SERVER_PORT = 6000;
	
	ServerDataModel dataModel;
	ServerSocket serverSocket;
	public List<Socket> socketList;
	
	public ClientAcceptThread(ServerDataModel dataModel) {
		this.dataModel = dataModel;
		this.serverSocket = this.dataModel.getServerSocket();
		this.socketList = this.dataModel.getSocketList();
	}
	
	@Override
	public void run() {
		try {
			String localHostAddress = InetAddress.getLocalHost().getHostAddress();
			serverSocket.bind(new InetSocketAddress(localHostAddress, SERVER_PORT));
			System.out.println("[server] binding \naddress: " + localHostAddress + ", port: " + SERVER_PORT);
			
			
			while(true) {
				Socket socket = this.serverSocket.accept();
				this.socketList.add(socket);
				
				InetSocketAddress remoteSocketAddress = (InetSocketAddress)socket.getRemoteSocketAddress();
				String remoteHostName = remoteSocketAddress.getAddress().getHostAddress();
				int remoteHostPort = remoteSocketAddress.getPort();
				System.out.println("[server] connected! \nconnected socket address:" + remoteHostName+ ", port: " +remoteHostPort);

				new ServerThread(socket, this.dataModel).start();
			}
			
		} catch (IOException e ) {
			e.printStackTrace();
		} finally {
			try {
				if(this.serverSocket != null && !this.serverSocket.isClosed()) {
					this.serverSocket.close();
				}
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
}
