package Gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

	@FXML
	private Button btn_SignUp, btn_Login;
	@FXML
	private TextField tf_Id;
	@FXML
	private PasswordField pf_Password;
	
	public void btn_SignUp_Action() throws Exception{
		try {
			Stage primaryStage = (Stage) btn_SignUp.getScene().getWindow();
			Parent main = FXMLLoader.load(getClass().getResource("/Gui/SignUp.fxml"));
			Scene scene = new Scene(main);
			primaryStage.setTitle("GuessWhat/SignIn");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void btn_Login_Action() {
		
	}
}
