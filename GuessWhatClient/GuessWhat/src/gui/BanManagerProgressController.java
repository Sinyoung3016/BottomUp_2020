package gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Optional;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.ProfessorDataModel;
import room.Ban;
import room.BanManager;
import room.BanManager.State;

public class BanManagerProgressController implements Initializable {

	@FXML
	private Button btn_End, btn_Main, btn_Logo, btn_MyInfo;
	@FXML
	private TextField tf_NewBanManagerName, tf_NewBanManagerCode;
	@FXML
	private Label lb_BanManagerName, lb_WorkBook, lb_Timer, timer;

	private Ban ban;
	private BanManager banManager;
	private Workbook workbook;

	private String className;
	private Socket socket;
	private long start;
	private boolean stop;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		try {

			this.socket = ProfessorDataModel.socket;
			this.ban = ProfessorDataModel.ban;
			this.banManager = ProfessorDataModel.banManager;
			this.workbook = ProfessorDataModel.workbook;
			this.start = ProfessorDataModel.startTime;

			className = btn_Main.getText();

			this.btn_Main.setText(ban.ban_name());
			this.lb_BanManagerName.setText(banManager.BM_name());
			this.lb_WorkBook.setText(workbook.W_name());

			setTimer();

		} catch (Exception e) {
			System.out.println("BanManagerProgress : " + e.getMessage());
			new Alert(AlertType.WARNING, "서버와 연결이 끊겼습니다.", ButtonType.CLOSE).showAndWait();
			Platform.exit();
		}

	}

	private void setTimer() throws Exception {
		boolean stop = false;
		Thread thread = new Thread() {

			@Override
			public void run() {
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				while (!stop) {
					long end = System.currentTimeMillis() - 32400000;
					String strTime = sdf.format(end - start);
					Platform.runLater(() -> {
						lb_Timer.setText(strTime);
					});
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
					}
				}
			};
		};
		thread.setDaemon(true);
		thread.start();
	}

	private void changeBMState(int bmNum, String newState) throws Exception {
		
		String responseMessage = null;
		String requestMessage = "ModifyState:" + bmNum + ":" + newState;
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
		writer.println(requestMessage);
		writer.flush();
		responseMessage = reader.readLine();

		String[] responseTokens = responseMessage.split(":");

		if (responseTokens[0].equals("ModifyState")) {
			if (!responseTokens[1].equals("Success")) {
				System.out.println("Fail : ModifyState");
			} else {
				System.out.println("            State: ING --> CLOSE");
			}
		}
	}

	public void btn_End_Action() {
		try {
			Alert alert = new Alert(AlertType.WARNING, "Test를 종료하시겠습니까?", ButtonType.YES, ButtonType.NO);
			Optional<ButtonType> result = alert.showAndWait();

			if (result.get() == ButtonType.YES) {
				stop = true;
				if (banManager.BM_state().equals(State.CLOSE))
					banManager.setBM_state_CLOSE();
				this.changeBMState(this.banManager.BM_num(), "CLOSE");
				try {
					Stage primaryStage = (Stage) btn_End.getScene().getWindow();
					Parent main = FXMLLoader.load(getClass().getResource("/gui/BanManagerFirstDone.fxml"));
					Scene scene = new Scene(main);
					primaryStage.setTitle("GuessWhat/" + className);
					primaryStage.setScene(scene);
					primaryStage.show();
				} catch (IOException i) {
					System.out.println("BanManagerProgress : " + i.getMessage());
				}
			}
		} catch (Exception e) {
			System.out.println("BanManagerProgress : " + e.getMessage());
			new Alert(AlertType.WARNING, "서버와 연결이 끊겼습니다.", ButtonType.CLOSE).showAndWait();
			Platform.exit();
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
			System.out.println("BanManagerProgress : " + e.getMessage());
			new Alert(AlertType.WARNING, "서버와 연결이 끊겼습니다.", ButtonType.CLOSE).showAndWait();
			Platform.exit();
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
			System.out.println("BanManagerProgress : " + e.getMessage());
			new Alert(AlertType.WARNING, "서버와 연결이 끊겼습니다.", ButtonType.CLOSE).showAndWait();
			Platform.exit();
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
			System.out.println("BanManagerProgress : " + e.getMessage());
			new Alert(AlertType.WARNING, "서버와 연결이 끊겼습니다.", ButtonType.CLOSE).showAndWait();
			Platform.exit();
		}
	}

}
