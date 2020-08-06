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
		try {
			BufferedReader br= new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			
			while(true) {
				String responseMessage = br.readLine();
				String[] tokens = responseMessage.split(":");
				
				if(tokens[0].equals("ChangeState")) {
					StudentDataModel.banManager.setBM_state_ING();				
				}
				else if(tokens[0].equals("SizeUpBanManger")) {
					if(tokens[1].equals(ProfessorDataModel.ID)) {
						Iterator iterator = ProfessorDataModel.ItemList_BanManager.iterator();
						
						while(iterator.hasNext()) {
							BanManager banManager = iterator.next();
							if(banManager.BM_num().equals(Integer.parseInt(tokens[2])))
						}
						
					}
				}
			}
		} catch(IOException e) {
			if(e.getMessage().equals("Connection reset")) {
        		Platform.runLater(() -> {new Alert(Alert.AlertType.INFORMATION, "서버가 닫혔습니다.강제종료됩니다.", ButtonType.CLOSE).show();});
        		try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
				}
				System.exit(0);
        	}
        	System.out.println("Error : "  + e.getMessage() + " FROM ClientThread Run");
		}
		
	}
}
