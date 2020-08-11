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
import java.util.LinkedList;
import java.util.Optional;
import java.util.ResourceBundle;

import exam.Workbook;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.ProfessorDataModel;
import room.Ban;
import room.BanManager;
import room.BanManager.State;
import user.Student;

public class BanManagerProgressController implements Initializable {

	@FXML
	private Button btn_End, btn_Main, btn_Logo, btn_MyInfo;
	@FXML
	private TextField tf_NewBanManagerName, tf_NewBanManagerCode;
	@FXML
	private Label lb_BanManagerName, lb_WorkBook;
	@FXML
	private TableView<Student> tv_Answer;

	public Socket socket;
	private Ban ban;
	private BanManager banManager;
	private Workbook workbook;

	private String className;

	private int WorkBookSize;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		this.socket = ProfessorDataModel.socket;
		this.ban = ProfessorDataModel.ban;
		this.banManager = ProfessorDataModel.banManager;
		this.workbook = ProfessorDataModel.workbook;
		this.WorkBookSize = workbook.WorkBooksize();

		className = btn_Main.getText();

		this.btn_Main.setText(ban.ban_name());
		this.lb_BanManagerName.setText(banManager.BM_name());
		this.lb_WorkBook.setText(workbook.W_name());

		Thread thread = new Thread() {
			@Override
			public void run() {
				while ((banManager.stringOfState()).equals("ING")) {
					Platform.runLater(() -> {
						upload();
					});
					try {
						Thread.sleep(1000);
					} catch (Exception e) {
					}
				}
			}
		};
		thread.start();
	}

	private void upload() {
		tv_Answer.getColumns().setAll(this.getColumns());
		tv_Answer.getItems().setAll(ProfessorDataModel.Students);
	}

	private void changeBMState(int bmNum, String newState) {
		String responseMessage = null;
		try {
			String requestMessage = "ModifyState:" + bmNum + ":" + newState;
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
		System.out.println(responseMessage);
		String[] responseTokens = responseMessage.split(":");

		if (responseTokens[0].equals("ModifyState")) {
			if (!responseTokens[1].equals("Success")) {
				System.out.println("Fail : ModifyState");
			} else {
				System.out.println("Success: ModifyState");
			}
		}
	}

	private TableColumn<Student, String>[] getColumns() {

		TableColumn<Student, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().name()));
		nameColumn.setPrefWidth(50);

		TableColumn<Student, String> scoreColumn[] = new TableColumn[WorkBookSize];
		for (int i = 0; i < WorkBookSize; i++) {
			final int j = i;
			scoreColumn[i] = new TableColumn<Student, String>("" + (i + 1));
			scoreColumn[i].setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().answer()[j]));
		}

		TableColumn<Student, String>[] returnTable = new TableColumn[WorkBookSize + 1];
		returnTable[0] = nameColumn;
		for (int i = 1; i < WorkBookSize + 1; i++) {
			returnTable[i] = scoreColumn[i - 1];
		}
		return returnTable;
	}

	public void btn_End_Action() {
		Alert alert = new Alert(AlertType.WARNING, "Test를 종료하시겠습니까?", ButtonType.YES, ButtonType.NO);
		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == ButtonType.YES) {
			if (banManager.BM_state().equals(State.ING))
				this.changeBMState(this.banManager.BM_num(), "CLOSE");
			this.banManager.setBM_state_CLOSE();

			try {
				Stage primaryStage = (Stage) btn_End.getScene().getWindow();
				Parent main = FXMLLoader.load(getClass().getResource("/gui/BanManagerFirstDone.fxml"));
				Scene scene = new Scene(main);
				primaryStage.setTitle("GuessWhat/" + className);
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void btn_Main_Action() {
		new Alert(AlertType.WARNING, "Test 중에는 화면 전환이 불가합니다.").show();
	}

	public void btn_Logo_Action() {
		new Alert(AlertType.WARNING, "Test 중에는 화면 전환이 불가합니다.").show();
	}

	public void btn_MyInfo_Action() {
		new Alert(AlertType.WARNING, "Test 중에는 화면 전환이 불가합니다.").show();
	}

}
