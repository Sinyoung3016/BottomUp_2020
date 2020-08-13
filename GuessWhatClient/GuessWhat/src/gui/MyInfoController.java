package gui;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.ProfessorDataModel;

public class MyInfoController implements Initializable {

	@FXML
	private TextField tf_Email;
	@FXML
	private Label lb_ID;
	@FXML
	private PasswordField pf_PassWord, pf_CheckPW;
	@FXML
	private Button btn_Update, btn_Close, btn_LogOut;

	private Socket socket;
	private String Email;
	private String NewPassword;
	private String ID;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		this.socket = ProfessorDataModel.socket;
		this.Email = ProfessorDataModel.professor.email();
		this.ID = ProfessorDataModel.professor.Id();

		lb_ID.setText(ID);
		tf_Email.setPromptText(Email);
	}

	public void btn_Close_Action() {// MainPage로 이동
		try {
			Stage primaryStage = (Stage) btn_Close.getScene().getWindow();
			Parent main = FXMLLoader.load(getClass().getResource("/gui/MainPage.fxml"));
			Scene scene = new Scene(main);
			primaryStage.setTitle("GuessWhat/Main");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void btn_LogOut_Action() {

		String responseMessage = null;
		try {
			String requestTokens = "LogOut:" + ProfessorDataModel.ID;
			BufferedReader br = new BufferedReader(
					new InputStreamReader(this.socket.getInputStream(), StandardCharsets.UTF_8));
			PrintWriter pw = new PrintWriter(
					new OutputStreamWriter(this.socket.getOutputStream(), StandardCharsets.UTF_8));
			pw.println(requestTokens);
			pw.flush();
			responseMessage = br.readLine();
		} catch (Exception e) {
			System.out.println("Error : " + e.getMessage() + "FROM btn_LogOut_Action");
		}
		String[] responseTokens = responseMessage.split(":");
		if (responseTokens[0].equals("LogOut")) {
			if (!responseTokens[1].equals("Success")) {
				new Alert(Alert.AlertType.WARNING, responseTokens[1], ButtonType.CLOSE).show();
			} else {
				System.out.println(ProfessorDataModel.ID + "님이 로그아웃하셨습니다.");
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
		}
	}

	public void btn_Update_Action() {

		// ModifyProfessor:ID:NewEmail:NewPassWord
		boolean canRequest = true; // 요청을 보낼 수 있는 상태인가

		if (pf_PassWord.getLength() != 0) {// password 수정 O
			if (!this.checkNewPassword(pf_PassWord.getText(), pf_CheckPW.getText())) {
				canRequest = false;
				new Alert(AlertType.WARNING, "비밀번호를 확인해주세요.", ButtonType.CLOSE).show();
				return;
			} else {// 비번 맞으면
				NewPassword = pf_PassWord.getText();
				if (tf_Email.getLength() != 0) // email 수정
					Email = tf_Email.getText();
			}
		} else {// password 수정 X
			if (tf_Email.getLength() != 0) // email 수정
				Email = tf_Email.getText();
		}

		if (canRequest) {
			String responseMessage = null;
			try {
				String requestTokens = "ModifyProfessor:" + ProfessorDataModel.ID + ":" + Email + ":" + NewPassword;
				BufferedReader br = new BufferedReader(
						new InputStreamReader(this.socket.getInputStream(), StandardCharsets.UTF_8));
				PrintWriter pw = new PrintWriter(
						new OutputStreamWriter(this.socket.getOutputStream(), StandardCharsets.UTF_8));
				pw.println(requestTokens);
				pw.flush();
				responseMessage = br.readLine();
			} catch (Exception e) {
				System.out.println("Error : " + e.getMessage() + " FROM btn_Update_Action");
			}
			String[] responseTokens = responseMessage.split(":");
			if (responseTokens[0].equals("ModifyProfessor")) {
				if (!responseTokens[1].equals("Success")) {
					System.out.println(responseTokens[1]);
					new Alert(AlertType.WARNING, "MyInfo 수정을 실패했습니다. 잠시후 다시 시도해주세요.", ButtonType.CLOSE).show();
				} else {
					ProfessorDataModel.professor.setEmail(Email);
					initialize(null, null);
				}
			}
		} else
			System.out.println("CanRequest : false");
	}

	private boolean checkNewPassword(String password, String checkPW) {
		if (password.equals(checkPW))
			return true;
		else
			return false;
	}
}
