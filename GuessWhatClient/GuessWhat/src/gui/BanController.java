package gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.ResourceBundle;
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
import model.HBoxModel;
import user.Professor;
import room.Ban;
import room.BanManager;

public class BanController implements Initializable {

	@FXML
	private Button btn_CreateNewBanManager, btn_ModifyClassName, btn_DeleteBan, btn_Main, btn_Logo, btn_MyInfo;;
	@FXML
	private ListView<HBoxModel> lv_BanManagerList;

	public Socket socket;
	public Professor professor;
	public Ban ban;

	private String className;

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

	private void showBanManagerList(int PNum, int BNum) {
		ProfessorDataModel.ItemList_MyBanManager.clear();
		String responseMessage = null;
		try {
			String requestMessage = "GetAllBanManager:" + PNum + ":" + BNum;
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
		if (responseTokens[0].equals("GetAllBanManager")) {
			if (!responseTokens[1].equals("Success")) {
				System.out.println("Fail : GetAllBanManager");
			} else {
				int n = 1;
				for (int i = 2; i < responseTokens.length; i++) {
					int BMNum = Integer.parseInt(responseTokens[i]);
					String name = responseTokens[i + 1];
					String state = responseTokens[i + 2];
					String code = responseTokens[i + 3];
					int WNum = Integer.parseInt(responseTokens[i + 4]);
					int student_size = Integer.parseInt(responseTokens[i + 5]);
					BanManager newBanManager = new BanManager(PNum, BNum, BMNum, name, state, code, WNum, student_size);
					ProfessorDataModel.addBanManager(n, newBanManager);
					i = i + 5;
					n++;
				}
				lv_BanManagerList.setItems(ProfessorDataModel.ItemList_MyBanManager);
			}
		}
	}

	public void btn_DeleteBan_Action() {

		Alert alert = new Alert(AlertType.WARNING, "(Class) " + className + "을(를) 정말로 삭제하시겠습니까?", ButtonType.YES,
				ButtonType.NO);
		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == ButtonType.YES) {
			// delete
			String responseMessage = null;
			try {
				String requestMessage = "DeleteBan:" + this.professor.P_Num() + ":" + this.ban.ban_num();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
				PrintWriter writer = new PrintWriter(
						new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
				writer.println(requestMessage);
				writer.flush();
				responseMessage = reader.readLine();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			String[] responseTokens = responseMessage.split(":");

			if (responseTokens[0].equals("DeleteBan")) {
				if (!responseTokens[1].equals("Success")) {
					System.out.println("Fail : DeleteBan");
				} else {
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
	}

	public void btn_CreateNewBanManager_Action() {
		try {
			if (ProfessorDataModel.ChoiceList_MyWorkBook.isEmpty()) {
				new Alert(AlertType.CONFIRMATION, "생성된 WorkBook이 없습니다. MainPage의 WorkBookList를 눌러 WorkBook을 먼저 만드세요.",
						ButtonType.CLOSE).showAndWait();
			}
			else {
				Stage primaryStage = (Stage) btn_CreateNewBanManager.getScene().getWindow();
				Parent main = FXMLLoader.load(getClass().getResource("/gui/CreateNewBanManager.fxml"));
				Scene scene = new Scene(main);
				primaryStage.setTitle("GuessWhat/" + className);
				primaryStage.setScene(scene);
				primaryStage.show();
			}
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
