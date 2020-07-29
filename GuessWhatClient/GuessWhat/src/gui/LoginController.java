package gui;

import java.sql.SQLException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

import authentication.Authentication;
import database.DB_USER;
import exception.MyException;

public class LoginController implements Initializable{

	@FXML
	private Button btn_GuessWhat, btn_SignUp, btn_Login;
	@FXML
	private TextField tf_Id;
	@FXML
	private PasswordField pf_Password;
	
	public Socket socket;
	
	private static final String SERVER_IP ="192.168.35.24";
	private static final int SERVER_PORT =6000;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			this.socket = new Socket(SERVER_IP, SERVER_PORT);
		} catch(Exception e) {
			System.out.println("Error :" +e.getMessage() + "FROM initialize in LoginController");
		}
	}
	public void btn_GuessWhat_Action() {//Home으로 이동
		try {
			Stage primaryStage = (Stage) btn_GuessWhat.getScene().getWindow();
			Parent main = FXMLLoader.load(getClass().getResource("/gui/Home.fxml"));
			Scene scene = new Scene(main);
			primaryStage.setTitle("GuessWhat/Home");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void btn_SignUp_Action() {
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
			String message = null;
			try {
				//this.logIn(tf_Id.getText(), pf_Password.getText());
				String requestTokens = "Login:" + tf_Id.getText()+ ":" +pf_Password.getText();
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
				PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
				pw.println(requestTokens);
				pw.flush();
				message = br.readLine();
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

	/*private void logIn(String id, String password) throws MyException, SQLException {
		if (Authentication.LogIn(id, password)) {
			DB_USER.userLogIn(id);
			System.out.println("LogIn:성공" + DB_USER.getUser(id).toString());
		}else
			System.out.println("LogIn:실패");
	}*/
}
