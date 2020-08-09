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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

import exam.Workbook;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.ProfessorDataModel;
import room.Ban;
import user.Professor;

public class CreateNewBanManagerController implements Initializable {

	@FXML
	private Button btn_Cancel, btn_CreateNewBanManager, btn_Main, btn_Logo, btn_MyInfo;
	@FXML
	private TextField tf_NewBanManagerName;
	@FXML
	private ChoiceBox<String> cb_NewBanManagerWorkBook;
	@FXML
	private Label lv_roomcode;

	public Socket socket;
	public Professor professor;
	public Ban ban;
	public Workbook[] workbookList;

	private String className;

	private void createNewBanManager(int PNum, int BNum, String name, String code, int wNum)
			throws MyException, SQLException {
		String responseMessage = null;
		try {
			String requestMessage = "AddBanManager:" + PNum + ":" + BNum + ":" + name + ":" + code + ":" + wNum;
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
		if (responseTokens[0].equals("AddBanManager")) {
			if (!responseTokens[1].equals("Success")) {
				System.out.println("Fail : AddBanManager");
			} else {
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
		this.workbookList = ProfessorDataModel.WorkbookList;

		className = ban.ban_name();
		this.btn_Main.setText(className);

		if (workbookList.length == 0) {
			new Alert(AlertType.CONFIRMATION, "생성된 WorkBook이 없습니다. MainPage의 WorkBookList를 눌러 WorkBook을 먼저 만드세요.",
					ButtonType.CLOSE).show();

			try {
				Stage primaryStage = (Stage) btn_Cancel.getScene().getWindow();
				Parent main = FXMLLoader.load(getClass().getResource("/gui/MainPage.fxml"));
				Scene scene = new Scene(main);
				primaryStage.setTitle("GuessWhat/" + className);
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}


		
		
	}

	private void makeRoomCode() { // 값 만들기
		StringBuffer temp = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			int index = random.nextInt(3);
			switch (index) {
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

	public void btn_Main_Action() {
		Alert alert = new Alert(AlertType.WARNING, "(Class) " + className + "(으)로 이동하시겠습니까? 진행중이던 작업이 날아갈 수 있습니다.",
				ButtonType.YES, ButtonType.NO);
		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == ButtonType.YES) {
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
	}

	public void btn_Cancel_Action() {
		Alert alert = new Alert(AlertType.WARNING, "해당 작업을 그만두시겠습니까?", ButtonType.YES, ButtonType.NO);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.YES) {
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
	}

	public void btn_CreateNewBanManager_Action() {
		try {
			int pNum = this.professor.P_Num();
			int bNum = this.ban.ban_num();

			this.createNewBanManager(pNum, bNum, this.tf_NewBanManagerName.getText(), this.lv_roomcode.getText(), -1);

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

	public void btn_Logo_Action() {
		Alert alert = new Alert(AlertType.WARNING, "MainPage로 이동하시겠습니까? 진행중이던 작업이 날아갈 수 있습니다.", ButtonType.YES,
				ButtonType.NO);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.YES) {
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
	}

	public void btn_MyInfo_Action() {
		Alert alert = new Alert(AlertType.WARNING, "MyInfo로 이동하시겠습니까? 진행중이던 작업이 날아갈 수 있습니다.", ButtonType.YES,
				ButtonType.NO);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.YES) {
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

}
