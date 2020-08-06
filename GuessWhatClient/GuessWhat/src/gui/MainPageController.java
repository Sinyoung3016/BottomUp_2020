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
import java.util.List;
import java.util.ResourceBundle;

import exception.MyException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.HBoxModel;
import model.ProfessorDataModel;
import room.Ban;
import user.Professor;

public class MainPageController implements Initializable{
	@FXML
	private Button btn_WorkBookList, btn_CreateNewClass, btn_MyInfo;
	@FXML
	private ListView<HBoxModel> lv_ClassList;
	
	public Socket socket;
	public Professor professor; 

	private void showBanList(String PNum) {
		
		ProfessorDataModel.ItemList_MyClass.clear();
		
		String responseMessage = null;
		try {
			String requestMessage = "GetBan:" + PNum;
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
			writer.println(requestMessage);
			writer.flush();
			responseMessage = reader.readLine();
		} catch(IOException e) {
			e.printStackTrace();
		}
		String[] responseTokens = responseMessage.split(":");
		
		if(responseTokens[0].equals("GetBan")) {
			if(! responseTokens[1].equals("Success")) {
				System.out.println("Fail : GetBan");
			}
			else {
				System.out.println("Success: GetBan");
				
				for(int i = 2 ; i < responseTokens.length ; i++) {	//[0]GetBan:[1]Success:[2]BNum:[3]Name:[4]BM_Size
					
					int pNum = Integer.parseInt(PNum);
					int BNum = Integer.parseInt(responseTokens[i]);
					String name = responseTokens[i+1];
					int bmSize = Integer.parseInt(responseTokens[i+2]);
					
					Ban newBan = new Ban(pNum, BNum, name, bmSize);
					ProfessorDataModel.addClass(newBan);					
					i = i + 2;
				}
			lv_ClassList.setItems(ProfessorDataModel.ItemList_MyClass);
			
			}
		}  
	}
	private void createNewClass(String PNum) throws MyException, SQLException {
		String responseMessage = null;
		try {
			String requestMessage = "AddBan:" + PNum;
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
			writer.println(requestMessage);
			writer.flush();
			responseMessage = reader.readLine();
		} catch(IOException e) {
			e.printStackTrace();
		}
		String[] responseTokens = responseMessage.split(":");
		if(responseTokens[0].equals("AddBan")) {
			if(!responseTokens[1].equals("Success")) {
				System.out.println("Fail : AddBan");
			}
			else {
				this.showBanList(PNum);
			}
		} 
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		this.socket = ProfessorDataModel.socket;
		this.professor = ProfessorDataModel.professor;
		this.showBanList(professor.getPNum());
			
	}

	public void btn_WorkBookList_Action() {
		try {
			Stage primaryStage = (Stage) btn_WorkBookList.getScene().getWindow();
			Parent main = FXMLLoader.load(getClass().getResource("/gui/WorkBookList.fxml"));
			Scene scene = new Scene(main);
			primaryStage.setTitle("GuessWhat/WorkBookList");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void btn_CreateNewClass_Action() {
		String pNum = this.professor.getPNum();
		try {
			this.createNewClass(pNum);
		} catch (Exception e) {
			System.out.println("Failed : Create New Class");
			e.printStackTrace();
		}
	}

	public void btn_MyInfo_Action() {
		try {
			Stage primaryStage = (Stage) btn_MyInfo.getScene().getWindow();
			Parent main = FXMLLoader.load(getClass().getResource("/gui/MyInfo.fxml"));
			Scene scene = new Scene(main);
			primaryStage.setTitle("GuessWhat/MyInfo");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
