package gui;

import java.sql.SQLException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import authentication.Authentication;
import database.DB_USER;
import exception.MyException;

public class LoginController {

	@FXML
	private Button btn_SignUp, btn_Login;
	@FXML
	private TextField tf_Id;
	@FXML
	private PasswordField pf_Password;

	public void btn_SignUp_Action() throws Exception {
		try {
			Stage primaryStage = (Stage) btn_SignUp.getScene().getWindow();
			Parent main = FXMLLoader.load(getClass().getResource("/gui/SignUp.fxml"));
			Scene scene = new Scene(main);
			primaryStage.setTitle("GuessWhat/SignIn");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void btn_Login_Action() {

		if (tf_Id.getText().length() != 0 && pf_Password.getText().length() != 0) {
			try {
				this.logIn(tf_Id.getText(), pf_Password.getText());
			} catch (Exception e) {
				new Alert(Alert.AlertType.WARNING, e.getMessage(), ButtonType.CLOSE).show();
				return ;
			}

			Stage primaryStage = (Stage) btn_Login.getScene().getWindow();
			Platform.runLater(() -> {
				Parent login;
				try {
					login = FXMLLoader.load(getClass().getResource("/gui/MainPage.fxml"));
					Scene scene = new Scene(login);
					primaryStage.setTitle("GuessWhat/Main");
					primaryStage.setScene(scene);
					primaryStage.show();

				} catch (Exception e) {
					e.printStackTrace();
				}
			});

			btn_Login.setDisable(true);
			btn_SignUp.setDisable(true);
			
		} else
			new Alert(Alert.AlertType.WARNING, "빈칸을 전부 채워주세요.", ButtonType.CLOSE).show();
	}

	private void logIn(String id, String password) throws MyException, SQLException {
		if (Authentication.LogIn(id, password)) {
			DB_USER.userLogIn(id);
			System.out.println("LogIn:성공" + DB_USER.getUser(id).toString());
		}else
			System.out.println("LogIn:실패");
	}
}
