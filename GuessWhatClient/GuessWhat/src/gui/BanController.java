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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.ProfessorDataModel;
import model.HBoxModel;
import room.BanManager.HBoxCell;
import user.Professor;
import room.Ban;
import room.BanManager;

public class BanController extends BaseController implements Initializable {

	@FXML
	private Button btn_CreateNewBanManager, btn_ModifyClassName, btn_DeleteBan;
	@FXML
	private ListView<HBoxModel> lv_BanManagerList;
	
	public Socket socket;
	public Professor professor; 
	public Ban ban;
	
	private String className;
	
	private void showBanManagerList(int PNum, int BNum) {
		ProfessorDataModel.ItemList_MyBanManager.clear();
		String responseMessage = null;
		try {
			String requestMessage = "GetAllBanManager:" + PNum + ":" + BNum;
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
			writer.println(requestMessage);
			writer.flush();
			responseMessage = reader.readLine();
		} catch(IOException e) {
			e.printStackTrace();
		}
		String[] responseTokens = responseMessage.split(":");
		if(responseTokens[0].equals("GetAllBanManager")) {
			if(! responseTokens[1].equals("Success")) {
				System.out.println("Fail : GetAllBanManager");
			}
			else {
				System.out.println("Success: GetAllBanManager");
				int n = 1;
				for(int i = 2 ; i < responseTokens.length ; i++) {	
					// [2]BMNum:[3]Name:[4]State:[5]Code:[6]Workbook:[7]Student_size
					int BMNum = Integer.parseInt(responseTokens[i]);
					String name = responseTokens[i+1];
					String state = responseTokens[i+2];
					String code = responseTokens[i+3];
					String workbook = responseTokens[i+4];
					int student_size = Integer.parseInt(responseTokens[i+5]);
					BanManager newBanManager = new BanManager(PNum, BNum, BMNum, name, state, code, workbook, student_size);
					ProfessorDataModel.addBanManager(n, newBanManager);					
					i = i+5;
					n++;
				}
				lv_BanManagerList.setItems(ProfessorDataModel.ItemList_MyBanManager);
			}
		}  
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		this.socket = ProfessorDataModel.socket;
		this.professor = ProfessorDataModel.professor;
		this.ban = ProfessorDataModel.ban;
		
		this.showBanManagerList(professor.P_Num(), ban.ban_num());
		
		className = this.ban.ban_name();
		this.btn_Main.setText(className);

	}
	
	public void btn_DeleteBan_Action() {
		
		//delete
		String responseMessage = null;
		try {
			String requestMessage = "DeleteBan:" + this.professor.P_Num() + ":" + this.ban.ban_num();
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
		
		if(responseTokens[0].equals("DeleteBan")) {
			if(! responseTokens[1].equals("Success")) {
				System.out.println("Fail : DeleteBan");
			}
			else {
				System.out.println("Success: DeleteBan");
			}
		}
		
		try {
			Stage primaryStage = (Stage) btn_CreateNewBanManager.getScene().getWindow();
			Parent main = FXMLLoader.load(getClass().getResource("/gui/MainPage.fxml"));
			Scene scene = new Scene(main);
			primaryStage.setTitle("GuessWhat/MainPage");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void btn_CreateNewBanManager_Action() {
		try {
			Stage primaryStage = (Stage) btn_CreateNewBanManager.getScene().getWindow();
			Parent main = FXMLLoader.load(getClass().getResource("/gui/CreateNewBanManager.fxml"));
			Scene scene = new Scene(main);
			primaryStage.setTitle("GuessWhat/" + className);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void btn_ModifyClassName_Action() {
		try {
			Stage primaryStage = (Stage) btn_ModifyClassName.getScene().getWindow();
			Parent main = FXMLLoader.load(getClass().getResource("/gui/BanModifyClassName.fxml"));
			Scene scene = new Scene(main);
			primaryStage.setTitle("GuessWhat/" + className);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
