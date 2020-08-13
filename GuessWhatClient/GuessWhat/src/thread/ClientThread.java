package thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.ProfessorDataModel;
import model.StudentDataModel;
import room.BanManager;
import user.Professor;

public class ClientThread extends Thread { //for student
	Socket socket = null;

	public ClientThread(Socket socket) {
		this.socket = socket;
		
	}

	@Override
	public void run() {
	
	
		
	}

}