package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.StudentDataModel;
import thread.LoadingThread;

public class StuInfoToStuWBController implements Initializable{
	
	@FXML
	private BorderPane parent;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		new LoadingThread().start();
	
		
		
	}

}
