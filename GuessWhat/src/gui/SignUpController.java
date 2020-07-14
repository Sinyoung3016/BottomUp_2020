package gui;

import java.sql.SQLException;

import authentication.Authentication;
import exception.MyException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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

	public void btn_SignUp_Action() throws Exception {
		if (tf_ID.getText().length() != 0 && pf_PassWord.getText().length() != 0 && tf_Email.getText().length() != 0) {
			try {
				this.signUp(tf_ID.getText(), pf_PassWord.getText(), tf_Email.getText());
			} catch (Exception e) {
				e.printStackTrace();
			}

			Stage primaryStage = (Stage) btn_SignUp.getScene().getWindow();
			Platform.runLater(() -> {
				Parent login;
				try {
					login = FXMLLoader.load(getClass().getResource("/gui/SignUp.fxml"));
					Scene scene = new Scene(login);
					primaryStage.setTitle("GuessWhat/SignUp");
					primaryStage.setScene(scene);
					primaryStage.show();

				} catch (Exception e) {
					e.printStackTrace();
				}
			});

			btn_SignUp.setDisable(true);
		} else
			new Alert(Alert.AlertType.WARNING, "빈칸을 전부 채워주세요.", ButtonType.CLOSE).show();
	}

	private void signUp(String id, String password, String Email) throws MyException, SQLException {
		if (Authentication.SignUp(id, password, Email))
			System.out.println("SignUp:성공");
		else
			System.out.println("SignUp:실패");
	}
}
