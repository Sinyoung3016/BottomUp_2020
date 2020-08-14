package gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import exam.Problem;
import exam.Workbook;
import exam.Workbook.HBoxCell;
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
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.ProfessorDataModel;
import model.StudentDataModel;
import user.Professor;
import model.HBoxModel;

public class WorkBookListController implements Initializable {

	@FXML
	private Button btn_CreateNewWorkBook, btn_Main, btn_Logo, btn_MyInfo;
	@FXML
	private ListView<HBoxModel> lv_WorkBookList;

	public Socket socket;
	public Professor professor;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		this.socket = ProfessorDataModel.socket;
		this.professor = ProfessorDataModel.professor;

		this.showWorkbookList(this.professor.P_Num());

		lv_WorkBookList.setItems(ProfessorDataModel.ItemList_MyWorkBook);
	}

	private void showWorkbookList(int PNum) {
		ProfessorDataModel.ItemList_MyWorkBook.clear();
		String responseMessage = null;
		try {
			String requestMessage = "GetAllWorkbook:" + PNum; // -> GetAllWorkbook:PNum
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			PrintWriter writer = new PrintWriter(
					new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
			writer.println(requestMessage);
			writer.flush();
			responseMessage = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] responseTokens = responseMessage.split(":");
		if (responseTokens[0].equals("GetAllWorkbook")) {
			if (!responseTokens[1].equals("Success")) {
				System.out.println("Fail : GetAllWorkbook");
			} else {
				int n = 1;
				for (int i = 2; i < responseTokens.length; i++) { // <- GetAllWorkbook:Success:WNum:Name:Size
					int WBNum = Integer.parseInt(responseTokens[i]);
					String name = responseTokens[i + 1];
					int size = Integer.parseInt(responseTokens[i + 2]);

					Workbook newWorkbook = new Workbook(PNum, WBNum, name, size);
					ProfessorDataModel.addWorkBook(n, newWorkbook);
					i = i + 2;
					n++;
				}
				lv_WorkBookList.setItems(ProfessorDataModel.ItemList_MyWorkBook);
			}
		}
	}

	public void btn_CreateNewWorkBook_Action() {

		ProfessorDataModel.workbook = new Workbook();
		ProfessorDataModel.workbook.setName("NewWorkbook " + ProfessorDataModel.ItemList_MyWorkBook.size());
		ProfessorDataModel.problem = new Problem(0);
		ProfessorDataModel.problemList = new Problem[20];
		ProfessorDataModel.currentPB = 0;

		try {
			Stage primaryStage = (Stage) btn_CreateNewWorkBook.getScene().getWindow();
			Parent main = FXMLLoader.load(getClass().getResource("/gui/NewWorkBook_MultipleChoice.fxml"));
			Scene scene = new Scene(main);
			primaryStage.setTitle("GuessWhat/wWorkBook");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void btn_Main_Action() {
		try {
			Stage primaryStage = (Stage) btn_Main.getScene().getWindow();
			Parent main = FXMLLoader.load(getClass().getResource("/gui/MainPage.fxml"));
			Scene scene = new Scene(main);
			primaryStage.setTitle("GuessWhat/MainPage");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void btn_Logo_Action() {
		try {
			Stage primaryStage = (Stage) btn_Logo.getScene().getWindow();
			Parent main = FXMLLoader.load(getClass().getResource("/gui/MainPage.fxml"));
			Scene scene = new Scene(main);
			primaryStage.setTitle("GuessWhat/MainPage");
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

}
