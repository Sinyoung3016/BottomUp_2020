package thread;

import java.net.Socket;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.HBox;
import model.ProfessorDataModel;

public class ClientThread extends Thread { //for student
	Socket socket = null;

	public ClientThread(Socket socket) {
		this.socket = socket;
		
	}

	@Override
	public void run() {

	}
}
