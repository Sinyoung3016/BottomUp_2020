package gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import model.ProfessorDataModel;
import model.StudentDataModel;

public class HomeController implements Initializable {
	@FXML
	private Button btn_CreateProblem, btn_Enter;
	@FXML
	private TextField tf_RoomCode;

	public Socket socket;

	private static final String SERVER_IP = "127.0.0.1";
	private static final int SERVER_PORT = 8000;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			this.socket = new Socket(SERVER_IP, SERVER_PORT);

			ProfessorDataModel.socket = this.socket;
			StudentDataModel.socket = this.socket;
			StudentDataModel.studentIp = InetAddress.getLocalHost().getHostAddress();
			btn_CreateProblem.setDisable(false);
			btn_Enter.setDisable(false);
			tf_RoomCode.setDisable(false);

		} catch (SocketException s) {
			new Alert(AlertType.WARNING, "서버 연결을 다시 확인해주세요.", ButtonType.CLOSE).showAndWait();
			Platform.exit();
		} catch (Exception e) {
			System.out.println("Error :" + e.getMessage() + " FROM initialize in HomeController");
		}
	}

	public void btn_CreateProblem_Action() {
		try {
			Stage primaryStage = (Stage) btn_CreateProblem.getScene().getWindow();
			Parent main = FXMLLoader.load(getClass().getResource("/gui/Login.fxml"));
			Scene scene = new Scene(main);
			primaryStage.setTitle("GuessWhat/Login");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			System.out.println("Home : " + e.getMessage());
			new Alert(AlertType.WARNING, "서버와 연결이 끊겼습니다.", ButtonType.CLOSE).showAndWait();
			Platform.exit();
		}
	}

	public void btn_Enter_Action() { // 만약 반이 열리지 않으면 HomeToStuInfo페이지
		try {
			if (tf_RoomCode.getText().length() != 0) { // !isEmpty(tf_RoomCode)
				String responseMessage = null;

				String requestTokens = "Join:" + tf_RoomCode.getText();
				BufferedReader br = new BufferedReader(
						new InputStreamReader(this.socket.getInputStream(), StandardCharsets.UTF_8));
				PrintWriter pw = new PrintWriter(
						new OutputStreamWriter(this.socket.getOutputStream(), StandardCharsets.UTF_8));
				pw.println(requestTokens);
				pw.flush();
				responseMessage = br.readLine();

				String[] responseTokens = responseMessage.split(":");
				if (responseTokens[0].equals("Join")) {
					if (!responseTokens[1].equals("Success"))
						new Alert(AlertType.WARNING, responseTokens[1], ButtonType.CLOSE).showAndWait();
					else {
						// Success Join
						StudentDataModel.code = tf_RoomCode.getText();

						Stage primaryStage = (Stage) btn_Enter.getScene().getWindow();
						Parent main = FXMLLoader.load(getClass().getResource("/gui/StudentInfo.fxml"));
						Scene scene = new Scene(main);
						primaryStage.setTitle("GuessWhat/StudentInfo");
						primaryStage.setScene(scene);
						primaryStage.show();
					}
				}
			} else {// isEmpty(tf_RoomCode)
				new Alert(AlertType.WARNING, "RoomCode를 입력해주세요.", ButtonType.CLOSE).showAndWait();
			}
		} catch (Exception e) {
			System.out.println("Home : " + e.getMessage());
			new Alert(AlertType.WARNING, "서버와 연결이 끊겼습니다.", ButtonType.CLOSE).showAndWait();
			Platform.exit();
		}
	}

}
