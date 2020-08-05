package gui;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
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
import javafx.stage.Stage;
import model.ProfessorDataModel;

public class MyInfoController implements Initializable {

	@FXML
	private TextField tf_Email;
	@FXML
	private Label lb_warning_CheckPW;
	@FXML
	private PasswordField pf_PassWord, pf_CheckPW;
	@FXML
	private Button btn_Update, btn_Close, btn_LogOut;

	private boolean check_checkPW = false;
	private Socket socket;
	
	public void btn_Close_Action() {//MainPage로 이동
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
		//로그아웃
		
		String responseMessage = null;
		try {
			String requestTokens = "LogOut:" + ProfessorDataModel.ID;
			BufferedReader br = new BufferedReader(new InputStreamReader(this.socket.getInputStream(), StandardCharsets.UTF_8));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream(), StandardCharsets.UTF_8));
			pw.println(requestTokens);
			pw.flush();
			responseMessage = br.readLine();
			System.out.println(responseMessage);
		} catch(Exception e) {
			System.out.println("Error : " + e.getMessage() + "FROM btn_LogOut_Action");
		}
		String[] responseTokens = responseMessage.split(":");
		if(responseTokens[0].equals("LogOut")) {
			if(!responseTokens[1].equals("Success")) {
				new Alert(Alert.AlertType.WARNING, responseTokens[1], ButtonType.CLOSE).show();
			}
			else {
				System.out.println(ProfessorDataModel.ID + "님이 로그아웃하셨습니다.");
			}
		}
		
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
	
	
	public void btn_Update_Action() { 
		
		//DB에서 정보 수정 
		
		pf_PassWord.getText();
		pf_CheckPW.getText();

		if (!check_checkPW) {
			new Alert(Alert.AlertType.WARNING, "비밀번호를 확인해주세요.", ButtonType.CLOSE).show();
			return ;
		}

		

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		//정보 가져와서 출력
		
		// TODO Auto-generated method stub
		this.socket = ProfessorDataModel.socket;
		
		System.out.println("Id:"+ProfessorDataModel.ID );
		pf_CheckPW.focusedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (!pf_CheckPW.isFocused()) {
					if (!pf_PassWord.getText().equals(pf_CheckPW.getText())) {
						lb_warning_CheckPW.setText("비밀번호를 다시 확인해 주세요.");
					}
					else {
						check_checkPW = true;
						lb_warning_CheckPW.setText("");
					}
				}
			}
		});

	}
}
