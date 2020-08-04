package thread;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.StudentDataModel;

public class LoadingThread extends Thread{
	
	@FXML
	private BorderPane parent;
	
	@Override
	public void run() {
		
		while(true) {
			if((StudentDataModel.banManager.stringOfState()).equals("ING")) {
					try {
					Stage primaryStage = (Stage) parent.getScene().getWindow();
					Parent main = FXMLLoader.load(getClass().getResource("/gui/StuWorkBook.fxml"));
					Scene scene = new Scene(main);
					primaryStage.setTitle("GuessWhat/Test");
					primaryStage.setScene(scene);
					primaryStage.show();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}
	}

}
