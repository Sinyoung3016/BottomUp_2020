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
import java.util.Random;
import java.util.ResourceBundle;

import exception.MyException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.ProfessorDataModel;
import room.Ban;
import user.Professor;

public class CreateNewBanManagerController extends BaseController implements Initializable{

	@FXML
	private Button btn_Cancel, btn_CreateNewBanManager;
	@FXML
	private TextField tf_NewBanManagerName;
	@FXML
	private ChoiceBox cb_NewBanManagerWorkBook;
	@FXML
	private Label lv_roomcode;
	
	public Socket socket;
	public Professor professor;
	public Ban ban;
	
	private String className;
	
	private void createNewBanManager(int PNum, int BNum, String name, String code, String workbook) throws MyException, SQLException {
		String responseMessage = null;
		try {
			String requestMessage = "AddBanManager:" + PNum + ":" + BNum + ":" + name + ":" + code + ":" + workbook;
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
			writer.println(requestMessage);
			writer.flush();
			responseMessage = reader.readLine();
		} catch(IOException e) {
			e.printStackTrace();
		}
		String[] responseTokens = responseMessage.split(":");
		if(responseTokens[0].equals("AddBanManager")) {
			if(!responseTokens[1].equals("Success")) {
				System.out.println("Fail : AddBanManager");
			}
			else {
				System.out.println("Success : AddBanManager");
			}
		} 
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		this.makeRoomCode();
		
		this.socket = ProfessorDataModel.socket;
		this.professor = ProfessorDataModel.professor;
		this.ban = ProfessorDataModel.ban;
		
		className = ban.ban_name();
		this.btn_Main.setText(className);		
	}
	
	private void makeRoomCode() { //값 만들기
		StringBuffer temp = new StringBuffer();
		Random random = new Random();
		for (int i = 0 ; i < 10 ; i++) {
			int index = random.nextInt(3);
			switch(index) {
			case 0:
				temp.append((char) ((int) (random.nextInt(26)) + 97));
				break;
			case 1:
				temp.append((char) ((int) random.nextInt(26) + 65));
				break;
			case 2:
				temp.append(random.nextInt(10));
				break;
			}
		}
		String roomcode = temp.toString();
		this.lv_roomcode.setText(roomcode);
	}

	@Override
	public void btn_Main_Action() {
		try {
			Stage primaryStage = (Stage) btn_Main.getScene().getWindow();
			Parent main = FXMLLoader.load(getClass().getResource("/gui/Ban.fxml"));
			Scene scene = new Scene(main);
			primaryStage.setTitle("GuessWhat/" + className);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void btn_Cancel_Action() {
		try {
			Stage primaryStage = (Stage) btn_Cancel.getScene().getWindow();
			Parent main = FXMLLoader.load(getClass().getResource("/gui/Ban.fxml"));
			Scene scene = new Scene(main);
			primaryStage.setTitle("GuessWhat/" + className);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void btn_CreateNewBanManager_Action() {
		try {
			int pNum = this.professor.P_Num();
			int bNum = this.ban.ban_num();
			
			this.createNewBanManager(pNum, bNum, this.tf_NewBanManagerName.getText(), this.lv_roomcode.getText(), "workbook");
			
			Stage primaryStage = (Stage) btn_CreateNewBanManager.getScene().getWindow();
			Parent main = FXMLLoader.load(getClass().getResource("/gui/Ban.fxml"));
			Scene scene = new Scene(main);
			primaryStage.setTitle("GuessWhat/" + className);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
