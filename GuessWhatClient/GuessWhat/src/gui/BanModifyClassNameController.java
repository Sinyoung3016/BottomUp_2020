package gui;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.HBoxModel;
import model.ProfessorDataModel;
import room.Ban;
import room.BanManager;
import user.Professor;

public class BanModifyClassNameController implements Initializable {

	@FXML
	private Button btn_CancelChangeName, btn_SaveClassName, btn_Main, btn_Logo, btn_MyInfo;
	@FXML
	private TextField tf_ChangeClassName;
	@FXML
	private ListView<HBoxModel> lv_BanManagerList;

	public Socket socket;
	public Professor professor;
	public Ban ban;

	private String className;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		try {
			this.socket = ProfessorDataModel.socket;
			this.professor = ProfessorDataModel.professor;
			this.ban = ProfessorDataModel.ban;

			this.showBanManagerList(professor.P_Num(), ban.ban_num());

			lv_BanManagerList.setItems(ProfessorDataModel.ItemList_MyBanManager);
			this.className = ban.ban_name();

		} catch (Exception e) {
			System.out.println("BanModify : " + e.getMessage());
			new Alert(AlertType.WARNING, "서버와 연결이 끊겼습니다.", ButtonType.CLOSE).showAndWait();
			Platform.exit();
		}

	}

	private void showBanManagerList(int PNum, int BNum) throws Exception {

		ProfessorDataModel.ItemList_MyBanManager.clear();
		String responseMessage = null;

		String requestMessage = "GetAllBanManager:" + PNum + ":" + BNum;
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
		writer.println(requestMessage);
		writer.flush();
		responseMessage = reader.readLine();

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
					int wNum = Integer.parseInt(responseTokens[i + 4]);
					int student_size = Integer.parseInt(responseTokens[i + 5]);
					BanManager newBanManager = new BanManager(PNum, BNum, BMNum, name, state, code, wNum, student_size);
					ProfessorDataModel.addBanManager(n, newBanManager);
					i = i + 5;
					n++;
				}
				lv_BanManagerList.setItems(ProfessorDataModel.ItemList_MyBanManager);
			}
		}
	}

	private void modifyClassName(int PNum, int BNum, String newName) throws Exception {

		String responseMessage = null;

		String requestMessage = "ModifyBan:" + this.professor.P_Num() + ":" + this.ban.ban_num() + ":" + newName;
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
		writer.println(requestMessage);
		writer.flush();
		responseMessage = reader.readLine();

		String[] responseTokens = responseMessage.split(":");

		if (responseTokens[0].equals("ModifyBan")) {
			if (!responseTokens[1].equals("Success")) {
				System.out.println("Fail : ModifyBan");
			} else {
				System.out.println("  [Modify] " + this.ban.ban_name() + " -> " + newName);
				new Alert(AlertType.CONFIRMATION, "반이름이 성공적으로 변경되였습니다.", ButtonType.OK).showAndWait();
			}
		}
	}

	public void btn_CancelChangeName_Action() {
		Alert alert = new Alert(AlertType.WARNING, "입력하신 이름을 저장할까요?", ButtonType.YES, ButtonType.NO);
		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == ButtonType.YES)
			btn_SaveClassName_Action();
		else {
			try {
				Stage primaryStage = (Stage) btn_SaveClassName.getScene().getWindow();
				Parent main = FXMLLoader.load(getClass().getResource("/gui/Ban.fxml"));
				Scene scene = new Scene(main);
				primaryStage.setTitle("GuessWhat/" + className);
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (Exception e) {
				System.out.println("BanModify : " + e.getMessage());
				new Alert(AlertType.WARNING, "서버와 연결이 끊겼습니다.", ButtonType.CLOSE).showAndWait();
			}
		}
	}

	public void btn_SaveClassName_Action() {
		try {
			this.className = this.tf_ChangeClassName.getText();
			if (className.equals("")) {
				Alert alert = new Alert(AlertType.WARNING, "반 이름을 채워주세요!", ButtonType.YES);
				alert.showAndWait();
			} else {
				this.modifyClassName(professor.P_Num(), ban.ban_num(), this.className);
				ProfessorDataModel.ban.setName(this.className);

				Stage primaryStage = (Stage) btn_SaveClassName.getScene().getWindow();
				Parent main = FXMLLoader.load(getClass().getResource("/gui/Ban.fxml"));
				Scene scene = new Scene(main);
				primaryStage.setTitle("GuessWhat/" + className);
				primaryStage.setScene(scene);
				primaryStage.show();
			}
		} catch (Exception e) {
			System.out.println("BanModify : " + e.getMessage());
			new Alert(AlertType.WARNING, "서버와 연결이 끊겼습니다.", ButtonType.CLOSE).showAndWait();
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
			System.out.println("BanModify : " + e.getMessage());
			new Alert(AlertType.WARNING, "서버와 연결이 끊겼습니다.", ButtonType.CLOSE).showAndWait();
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
			System.out.println("BanModify : " + e.getMessage());
			new Alert(AlertType.WARNING, "서버와 연결이 끊겼습니다.", ButtonType.CLOSE).showAndWait();
		}
	}
}
