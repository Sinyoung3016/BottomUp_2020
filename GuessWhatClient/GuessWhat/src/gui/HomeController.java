package gui;

import java.io.BufferedReader;
import java.io.IOException;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.DataModel;

public class HomeController implements Initializable{
	@FXML
	private Button btn_CreateProblem, btn_Enter;
	@FXML
	private TextField tf_RoomCode;
	
	public Socket socket;
		
	private static final String SERVER_IP ="172.30.1.32";
	private static final int SERVER_PORT =6000;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			this.socket = new Socket(SERVER_IP, SERVER_PORT);
			DataModel.socket = this.socket;
		} catch(Exception e) {
			System.out.println("Error :" +e.getMessage() + " FROM initialize in HomeController");
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
			e.printStackTrace();
		}
	}

	public void btn_Enter_Action() { // 만약 반이 열리지 않으면 HomeToStuInfo페이지
				
		//MoonDD's code start
		if(tf_RoomCode.getText().length() != 0) { //!isEmpty(tf_RoomCode)
			String responseMessage = null;
			try {
				String requestTokens = "Join:" + tf_RoomCode;
				BufferedReader br = new BufferedReader(
						new InputStreamReader(this.socket.getInputStream(), StandardCharsets.UTF_8));
				PrintWriter pw = new PrintWriter(
						new OutputStreamWriter(this.socket.getOutputStream(), StandardCharsets.UTF_8));
				pw.println(requestTokens);
				pw.flush();
				responseMessage = br.readLine();
			} catch(IOException e) {
				e.printStackTrace();
			}
			String[] responseTokens = responseMessage.split(":");
			if(responseTokens[0].equals("Join")){
				if(!responseTokens[1].equals("Success"))
					new Alert(Alert.AlertType.WARNING, "RoomCode가 올바르지 않습니다.", ButtonType.CLOSE).show();
				else {
					//Success Join 
					//Fortune's code start
					try {
						Stage primaryStage = (Stage) btn_Enter.getScene().getWindow();
						Parent main = FXMLLoader.load(getClass().getResource("/gui/StudentInfo.fxml"));
						Scene scene = new Scene(main);
						primaryStage.setTitle("GuessWhat/StudentInfo");
						primaryStage.setScene(scene);
						primaryStage.show();
					} catch (Exception e) {
						e.printStackTrace();
					}
					//Fortune's code end
				}
			}
		}
		else {//isEmpty(tf_RoomCode)
			new Alert(Alert.AlertType.WARNING, "RoomCode를 입력해주세요.", ButtonType.CLOSE).show();
		}
		//MoonDD's code end

		
	}

	public static Object CloseButtonActione() {
		// TODO Auto-generated method stub
		return null;
	}

}
