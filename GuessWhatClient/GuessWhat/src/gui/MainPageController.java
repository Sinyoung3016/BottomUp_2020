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
import java.util.ResourceBundle;

import exception.MyException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.DataModel;
import model.HBoxModel;
import room.Ban.HBoxCell;
import user.Professor;

public class MainPageController implements Initializable{
	@FXML
	private Button btn_WorkBookList, btn_CreateNewClass, btn_MyInfo;
	@FXML
	private ListView<HBoxModel> lv_ClassList;
	
	public Socket socket;
	public Professor professor; 

	ObservableList<HBoxModel> list;

	private void showBanList(String PNum) {
		String responseMessage = null;
		try {
			String requestMessage = "GetBan:" + PNum;
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
			writer.println(requestMessage);
			writer.flush();
			responseMessage = reader.readLine();
			System.out.println("(test) " + responseMessage);
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
				
				DataModel.ItemList_MyClass = FXCollections.observableArrayList();
				this.list = DataModel.ItemList_MyClass;
				
				for(int i = 2 ; i < responseTokens.length ; i++) {
					this.list.add(new HBoxCell(i - 2, responseTokens[i], 0));
				}
				
				lv_ClassList.setItems(list);
			}
		}  
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		//DataModel.ItemList_MyClass = FXCollections.observableArrayList();
		this.socket = DataModel.socket;
		this.professor = DataModel.professor;
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

	public void btn_CreateNewClass_Action() {
		String pNum = this.professor.getPNum();
		try {
			this.createNewClass(pNum);
		} catch (Exception e) {
			System.out.println("Failed : Create New Class");
			e.printStackTrace();
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
				new Alert(Alert.AlertType.WARNING, responseTokens[2], ButtonType.CLOSE).show();
			}
			else {
				this.showBanList(PNum);
			//	Ban ban = new Ban("new");
			//	DataModel.addClass(ban);
			}
		} 
	}
	
}
