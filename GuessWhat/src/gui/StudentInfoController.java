package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class StudentInfoController {
	@FXML
	private Button btn_Join, btn_Close;
	@FXML
	private TextField tf_StudentName;
	@FXML
	private Label lb_ClassRoomName;

	public void btn_Close_Action(){
		try {
			Stage primaryStage = (Stage) btn_Close.getScene().getWindow();
			Parent main = FXMLLoader.load(getClass().getResource("/gui/Home.fxml"));
			Scene scene = new Scene(main);
			primaryStage.setTitle("GuessWhat/Home");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void btn_Join_Action(){

	}

	public static Object CloseButtonActione() {
		// TODO Auto-generated method stub
		return null;
	}

}
