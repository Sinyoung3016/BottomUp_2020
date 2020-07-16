package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class HomeController {
	@FXML
	private Button btn_CreateProblem, btn_Enter;
	private TextField tf_RoomCode;

	public void btn_CreateProblem_Action(){
		try {
			Stage primaryStage = (Stage) btn_CreateProblem.getScene().getWindow();
			Parent main = FXMLLoader.load(getClass().getResource("/gui/Login.fxml"));
			Scene scene = new Scene(main);
			primaryStage.setTitle("GuessWhat/Login");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void btn_Enter_Action(){

	}

	public static Object CloseButtonActione() {
		// TODO Auto-generated method stub
		return null;
	}

}
