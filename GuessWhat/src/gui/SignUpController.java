package Gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignUpController {

	@FXML
	private TextField tf_ID, tf_Email;
	@FXML
	private Label lb_warning_ID, lb_warning_CheckPW;
	@FXML
	private PasswordField pf_PassWord, pf_CheckPW;
	@FXML
	private Button btn_SignUp;

	public void btn_SignUp_Action() throws Exception{
		try {
			Stage primaryStage = (Stage) btn_SignUp.getScene().getWindow();
			Parent main = FXMLLoader.load(getClass().getResource("/Gui/Login.fxml"));
			Scene scene = new Scene(main);
			primaryStage.setTitle("GuessWhat/Login");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
