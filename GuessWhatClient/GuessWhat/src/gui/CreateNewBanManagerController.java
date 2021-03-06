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
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

import exam.Workbook;
import javafx.application.Platform;
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
	private ChoiceBox<Workbook> cb_NewBanManagerWorkBook;
	@FXML
	private Label lv_roomcode;

	public Socket socket;
	public Professor professor;
	public Ban ban;
	public Workbook[] workbookList;

	private String className;

	private void createNewBanManager(int PNum, int BNum, String name, String code, int wNum)
			throws SQLException, Exception {
		String responseMessage = null;
		String requestMessage = "AddBanManager:" + PNum + ":" + BNum + ":" + name + ":" + code + ":" + wNum;
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
		writer.println(requestMessage);
		writer.flush();
		responseMessage = reader.readLine();

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
		try {
			this.makeRoomCode();

			this.socket = ProfessorDataModel.socket;
			this.professor = ProfessorDataModel.professor;
			this.ban = ProfessorDataModel.ban;
			this.workbookList = ProfessorDataModel.WorkbookList;

			this.cb_NewBanManagerWorkBook.setItems(ProfessorDataModel.ChoiceList_MyWorkBook);

			className = ban.ban_name();
			this.btn_Main.setText(className);

		} catch (Exception e) {
			System.out.println("CreateNewBanManager : " + e.getMessage());
			new Alert(AlertType.WARNING, "서버와 연결이 끊겼습니다.", ButtonType.CLOSE).showAndWait();
			Platform.exit();
		}
	}

	private void makeRoomCode() throws Exception { // 값 만들기
		StringBuffer temp = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < 5; i++) {
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

		try {
			if (result.get() == ButtonType.YES) {
				Stage primaryStage = (Stage) btn_Main.getScene().getWindow();
				Parent main = FXMLLoader.load(getClass().getResource("/gui/Ban.fxml"));
				Scene scene = new Scene(main);
				primaryStage.setTitle("GuessWhat/" + className);
				primaryStage.setScene(scene);
				primaryStage.show();
			}
		} catch (Exception e) {
			System.out.println("CreateNewBanManager : " + e.getMessage());
			new Alert(AlertType.WARNING, "서버와 연결이 끊겼습니다.", ButtonType.CLOSE).showAndWait();
			Platform.exit();
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
				System.out.println("CreateNewBanManager : " + e.getMessage());
				new Alert(AlertType.WARNING, "서버와 연결이 끊겼습니다.", ButtonType.CLOSE).showAndWait();
				Platform.exit();
			}
		}
	}

	private boolean validName() throws Exception {
		String bmName = this.tf_NewBanManagerName.getText();
		if (bmName.equals("")) return false;
		return true;
	}

	private boolean validWorkbook() throws Exception {
		Workbook wb = this.cb_NewBanManagerWorkBook.getSelectionModel().getSelectedItem();
		if (wb == null)	return false;
		return true;
	}

	public void btn_CreateNewBanManager_Action() {
		try {
			int pNum = this.professor.P_Num();
			int bNum = this.ban.ban_num();
			if (!this.validName() && !this.validWorkbook()) {
				new Alert(AlertType.WARNING, "이름과 Workbook을 선택해주세요.", ButtonType.YES).showAndWait();
			} else if (!this.validName()) {
				new Alert(AlertType.WARNING, "이름을 채워주세요", ButtonType.YES).showAndWait();
			} else if (!this.validWorkbook()) {
				new Alert(AlertType.WARNING, "Workbook을 선택해주세요.", ButtonType.YES).showAndWait();
			} else {
				String bmName = this.tf_NewBanManagerName.getText();
				int wNum = this.cb_NewBanManagerWorkBook.getSelectionModel().getSelectedItem().W_Num();
				this.createNewBanManager(pNum, bNum, bmName, this.lv_roomcode.getText(), wNum);

				Stage primaryStage = (Stage) btn_CreateNewBanManager.getScene().getWindow();
				Parent main = FXMLLoader.load(getClass().getResource("/gui/Ban.fxml"));
				Scene scene = new Scene(main);
				primaryStage.setTitle("GuessWhat/" + className);
				primaryStage.setScene(scene);
				primaryStage.show();
			}
		} catch (Exception e) {
			System.out.println("CreateNewBanManager : " + e.getMessage());
			new Alert(AlertType.WARNING, "서버와 연결이 끊겼습니다.", ButtonType.CLOSE).showAndWait();
			Platform.exit();
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
				System.out.println("CreateNewBanManager : " + e.getMessage());
				new Alert(AlertType.WARNING, "서버와 연결이 끊겼습니다.", ButtonType.CLOSE).showAndWait();
				Platform.exit();
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
				System.out.println("CreateNewBanManager : " + e.getMessage());
				new Alert(AlertType.WARNING, "서버와 연결이 끊겼습니다.", ButtonType.CLOSE).showAndWait();
				Platform.exit();
			}
		}
	}

}
