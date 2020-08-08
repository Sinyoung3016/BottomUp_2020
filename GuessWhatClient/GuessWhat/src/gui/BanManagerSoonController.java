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

import exam.Workbook;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.ProfessorDataModel;
import room.Ban;
import room.BanManager;
import room.BanManager.State;
import user.Professor;

public class BanManagerSoonController extends BaseController implements Initializable{

	@FXML
	private Button btn_Start, btn_Close, btn_Delete;
	@FXML
	private Label lb_BanManagerName, lb_WorkBook, lb_RoomCode;
	
	public Socket socket;
	public Professor professor; 
	public Ban ban;
	public BanManager banManager;
	public Workbook workbook;

	private String className;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		this.socket = ProfessorDataModel.socket;
		this.professor = ProfessorDataModel.professor;
		this.ban = ProfessorDataModel.ban;
		this.banManager = ProfessorDataModel.banManager;
		
		this.workbook = ProfessorDataModel.workbook;
		
		this.btn_Main.setText(ban.ban_name());
		this.lb_BanManagerName.setText(banManager.BM_name());
		this.lb_WorkBook.setText(workbook.W_name());
		this.lb_RoomCode.setText(banManager.BM_roomcode());
		
		
		className = btn_Main.getText();
		
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

	public void btn_Close_Action() {
		try {
			Stage primaryStage = (Stage) btn_Close.getScene().getWindow();
			Parent main = FXMLLoader.load(getClass().getResource("/gui/Ban.fxml"));
			Scene scene = new Scene(main);
			primaryStage.setTitle("GuessWhat/" + className);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void btn_Delete_Action() {
		
		String responseMessage = null;
		try {
			String requestMessage = "DeleteBanManager:" + this.banManager.P_num() + ":" + this.banManager.ban_num() + ":" + this.banManager.BM_num();
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
			writer.println(requestMessage);
			writer.flush();
			responseMessage = reader.readLine();
		} catch(IOException e1) {
			e1.printStackTrace();
		}
		System.out.println(responseMessage);
		String[] responseTokens = responseMessage.split(":");
		
		if(responseTokens[0].equals("DeleteBanManager")) {
			if(! responseTokens[1].equals("Success")) {
				System.out.println("Fail : DeleteBanManager");
			}
			else {
				System.out.println("Success: DeleteBanManager");
			}
		}
		
		try {
			Stage primaryStage = (Stage) btn_Close.getScene().getWindow();
			Parent main = FXMLLoader.load(getClass().getResource("/gui/Ban.fxml"));
			Scene scene = new Scene(main);
			primaryStage.setTitle("GuessWhat/" + className);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void btn_Start_Action() {
		
		if(banManager.BM_state().equals(State.OPEN))
			banManager.setBM_state_ING(); 
		
		try {
			Stage primaryStage = (Stage) btn_Start.getScene().getWindow();
			Parent main = FXMLLoader.load(getClass().getResource("/gui/BanManagerProgress.fxml"));
			Scene scene = new Scene(main);
			primaryStage.setTitle("GuessWhat/" + className);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
