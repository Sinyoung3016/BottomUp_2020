package thread;

import java.net.Socket;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.HBox;
import model.DataModel;

public class ClientThread extends Thread {
	Socket socket = null;

	public ClientThread(Socket socket) {
		this.socket = socket;
		
		DataModel.ItemList_MyClass = FXCollections.observableArrayList();
		DataModel.ItemList_MyWorkBook = FXCollections.observableArrayList();
		DataModel.ItemList_MyBanManager = FXCollections.observableArrayList();
	}

	@Override
	public void run() {

	}
}
