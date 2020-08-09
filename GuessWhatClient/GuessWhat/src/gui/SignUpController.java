package gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import authentication.Authentication;
import exception.MyException;
import database.DB_USER;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

public class SignUpController implements Initializable {

	@FXML
	private TextField tf_ID, tf_Email;
	@FXML
	private Label lb_warning_CheckPW;
	@FXML
	private PasswordField pf_PassWord, pf_CheckPW;
	@FXML
	private Button btn_GuessWhat, btn_SignUp, btn_Overlap;

	private boolean check_Overlap_Id = false, check_checkPW = false;

	public Socket socket;

	public void btn_GuessWhat_Action() {// Login으로 이동
		Alert alert = new Alert(AlertType.WARNING, "로그인 창으로 이동하시겠습니까? 진행중이던 작업이 날아갈 수 있습니다.", ButtonType.YES,
				ButtonType.NO);
		alert.show();
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.YES) {
			try {
				Stage primaryStage = (Stage) btn_GuessWhat.getScene().getWindow();
				Parent main = FXMLLoader.load(getClass().getResource("/gui/Login.fxml"));
				Scene scene = new Scene(main);
				primaryStage.setTitle("GuessWhat/LogIn");
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void btn_Overlap_Action() {
		if (tf_ID.getText().length() != 0) { // !isEmpty(tf_ID);
			String responseMessage = null;
			try {
				String requestTokens = "OverLap:" + tf_ID.getText();
				BufferedReader br = new BufferedReader(
						new InputStreamReader(this.socket.getInputStream(), StandardCharsets.UTF_8));
				PrintWriter pw = new PrintWriter(
						new OutputStreamWriter(this.socket.getOutputStream(), StandardCharsets.UTF_8));
				pw.println(requestTokens);
				pw.flush();
				responseMessage = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			String[] responseTokens = responseMessage.split(":");
			if (responseTokens[0].equals("OverLap")) {
				if (!responseTokens[1].equals("Success")) {
					new Alert(Alert.AlertType.WARNING, responseTokens[1], ButtonType.OK).show();
				} else {
					check_Overlap_Id = true;
					new Alert(Alert.AlertType.CONFIRMATION, "가능한 ID입니다.", ButtonType.OK).show();
				}
			}
		} else { // isEmpty(tf_ID);
			new Alert(Alert.AlertType.WARNING, "빈칸을 전부 채워주세요.", ButtonType.OK).show();
		}

	}

	public void btn_SignUp_Action() {

		if (!check_checkPW) {
			new Alert(Alert.AlertType.WARNING, "비밀번호가 정확하지 않습니다.", ButtonType.OK).show();
			return;
		}

		if (!check_Overlap_Id) {
			new Alert(Alert.AlertType.WARNING, "ID 중복 체크를 해주세요.", ButtonType.OK).show();
			return;
		}

		if (tf_ID.getText().length() != 0 && pf_PassWord.getText().length() != 0 && tf_Email.getText().length() != 0
				&& check_Overlap_Id && check_checkPW) {
			try {
				this.signUp(tf_ID.getText(), pf_PassWord.getText(), tf_Email.getText());
			} catch (Exception e) {
				System.out.println("회원가입 : 실패");
			}

			Stage primaryStage = (Stage) btn_SignUp.getScene().getWindow();
			Platform.runLater(() -> {
				Parent login;
				try {
					login = FXMLLoader.load(getClass().getResource("/gui/Login.fxml"));
					Scene scene = new Scene(login);
					primaryStage.setTitle("GuessWhat/Login");
					primaryStage.setScene(scene);
					primaryStage.show();

				} catch (Exception e) {
					e.printStackTrace();
				}
			});

			btn_SignUp.setDisable(true);
		} else
			new Alert(Alert.AlertType.WARNING, "빈칸을 전부 채워주세요.", ButtonType.OK).show();
	}

	private void signUp(String id, String password, String Email) throws MyException, SQLException {
		String responseMessage = null;
		try {
			String requestMessage = "SignUp:" + id + ":" + password + ":" + Email;
			BufferedReader br = new BufferedReader(
					new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
			pw.println(requestMessage);
			pw.flush();
			responseMessage = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] responseTokens = responseMessage.split(":");
		if (responseTokens[0].equals("SignUp")) {
			if (!responseTokens[1].equals("Success")) {
				new Alert(Alert.AlertType.WARNING, "회원가입에 실패했습니다. 잠시후 다시 진행해주세요.", ButtonType.OK).show();
				System.out.println("id :" + responseTokens[2]);
			} else {
				new Alert(Alert.AlertType.CONFIRMATION, "회원가입이 성공적으로 진행되었습니다.", ButtonType.OK).show();
			}
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		this.socket = ProfessorDataModel.socket;
		pf_CheckPW.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (!pf_CheckPW.isFocused()) {
					if (!pf_PassWord.getText().equals(pf_CheckPW.getText())) {
						lb_warning_CheckPW.setText("비밀번호가 정확하지 않습니다.");
					} else {
						check_checkPW = true;
						lb_warning_CheckPW.setText("");
					}
				}
			}
		});

	}
}
