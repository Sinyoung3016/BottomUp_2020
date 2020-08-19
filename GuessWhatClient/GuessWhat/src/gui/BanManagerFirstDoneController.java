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
import exam.ProblemType;
import exam.Workbook;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.ProfessorDataModel;
import room.Ban;
import room.BanManager;
import user.Student;

public class BanManagerFirstDoneController implements Initializable {

	@FXML
	private Button btn_Delete, btn_Close, btn_Next, btn_Main, btn_Logo, btn_MyInfo;
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
	private Problem[] problemList;

	private String className;
	private boolean no;
	private int WorkBookSize;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		try {
			this.socket = ProfessorDataModel.socket;
			this.ban = ProfessorDataModel.ban;
			this.banManager = ProfessorDataModel.banManager;
			this.workbook = ProfessorDataModel.workbook;
			this.WorkBookSize = workbook.WorkBooksize();

			this.getAllProblem();
			no = this.getStudent();
			this.problemList = ProfessorDataModel.problemList;

			className = btn_Main.getText();

			this.btn_Main.setText(ban.ban_name());
			this.lb_BanManagerName.setText(banManager.BM_name());
			this.lb_WorkBook.setText(workbook.W_name());

			tv_Answer.getColumns().setAll(this.getColumns());

			ArrayList<Student> students = ProfessorDataModel.ip_student;
			if (students.size() == 0)
				throw new NullPointerException();

			tv_Answer.getItems().setAll(students);

		} catch (NullPointerException n) {
			new Alert(AlertType.WARNING, "해당 시험을 본 학생이 없습니다.", ButtonType.CLOSE).showAndWait();
			try {
				Stage primaryStage = (Stage) btn_Main.getScene().getWindow();
				Parent main;
				main = FXMLLoader.load(getClass().getResource("/gui/Ban.fxml"));

				Scene scene = new Scene(main);
				primaryStage.setTitle("GuessWhat/" + className);
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (IOException i) {
				System.out.println("BanManagerFirstDone : " + i.getMessage());
			}
		} catch (Exception e) {
			System.out.println("BanManagerFirstDone : " + e.getMessage());
			new Alert(AlertType.WARNING, "서버와 연결이 끊겼습니다.", ButtonType.CLOSE).showAndWait();
			Platform.exit();
		}
	}

	private TableColumn<Student, String>[] getColumns() throws Exception {

		TableColumn<Student, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().name()));
		nameColumn.setPrefWidth(50);

		TableColumn<Student, String> scoreColumn[] = new TableColumn[WorkBookSize];
		for (int i = 0; i < WorkBookSize; i++) {
			final int j = i;
			scoreColumn[i] = new TableColumn<Student, String>("" + (i + 1));

			scoreColumn[i].setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().answer()[j]));
			scoreColumn[i].setCellFactory(col -> new TableCell<Student, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					if (empty || item == null) {
						setText(null);
						setGraphic(null);
					} else {
						setText(item);
						if (problemList[j].getType().equals(ProblemType.ShortAnswer)) {
							if (item.equals(problemList[j].answer())) {
								setStyle("-fx-background-color: #5ad18f;");// 맞음
							} else if (!item.equals(problemList[j].answer())) {
								setStyle("-fx-background-color: #ff848f;");// 틀림
							}
						} else if (problemList[j].getType().equals(ProblemType.MultipleChoice)) {
							if (item.equals(problemList[j].answer())) {
								setStyle("-fx-background-color: #5ad18f;");// 맞음
							} else if (!item.equals(problemList[j].answer())) {
								setStyle("-fx-background-color: #ff848f;");// 틀림
							}

							String answer = "";
							for (int i = 0; i < item.length(); i++)
								answer += (item.charAt(i) + "/");

							setText(answer.substring(0, answer.length() - 1));

						} else if (problemList[j].getType().equals(ProblemType.Subjective))
							setStyle("-fx-background-color: #ffcd28;");// subjective
					}
				}
			});

		}

		TableColumn<Student, String>[] returnTable = new TableColumn[WorkBookSize + 1];
		returnTable[0] = nameColumn;
		for (int i = 1; i < WorkBookSize + 1; i++) {
			returnTable[i] = scoreColumn[i - 1];
		}
		return returnTable;

	}

	public void btn_Main_Action() {
		try {
			Stage primaryStage = (Stage) btn_Main.getScene().getWindow();
			Parent main = FXMLLoader.load(getClass().getResource("/gui/Ban.fxml"));
			Scene scene = new Scene(main);
			primaryStage.setTitle("GuessWhat/" + className);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			System.out.println("BanManagerFirstDone : " + e.getMessage());
			new Alert(AlertType.WARNING, "서버와 연결이 끊겼습니다.", ButtonType.CLOSE).showAndWait();
			Platform.exit();
		}
	}

	public void btn_Close_Action() {
		try {
			Stage primaryStage = (Stage) btn_Close.getScene().getWindow();
			Parent main = FXMLLoader.load(getClass().getResource("/gui/Ban.fxml"));
			Scene scene = new Scene(main);
			primaryStage.setTitle("GuessWhat/" + className);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			System.out.println("BanManagerFirstDone : " + e.getMessage());
			new Alert(AlertType.WARNING, "서버와 연결이 끊겼습니다.", ButtonType.CLOSE).showAndWait();
			Platform.exit();
		}
	}

	public void btn_Delete_Action() {

		try {
			Alert alert = new Alert(AlertType.WARNING, "(TestRoom) " + banManager.BM_name() + "을(를) 정말로 삭제하시겠습니까?",
					ButtonType.YES, ButtonType.NO);
			Optional<ButtonType> result = alert.showAndWait();

			if (result.get() == ButtonType.YES) {

				String responseMessage = null;

				String requestMessage = "DeleteBanManager:" + this.banManager.P_num() + ":" + this.banManager.ban_num()
						+ ":" + this.banManager.BM_num();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
				PrintWriter writer = new PrintWriter(
						new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
				writer.println(requestMessage);
				writer.flush();
				responseMessage = reader.readLine();

				String[] responseTokens = responseMessage.split(":");

				if (responseTokens[0].equals("DeleteBanManager")) {
					if (!responseTokens[1].equals("Success")) {
						System.out.println("Fail : DeleteBanManager");
						throw new Exception();
					} else {
						System.out.println("[Delete] BM: " + this.banManager.BM_name());

						try {
							Stage primaryStage = (Stage) btn_Close.getScene().getWindow();
							Parent main = FXMLLoader.load(getClass().getResource("/gui/Ban.fxml"));
							Scene scene = new Scene(main);
							primaryStage.setTitle("GuessWhat/" + className);
							primaryStage.setScene(scene);
							primaryStage.show();
						} catch (IOException i) {
							System.out.println("BanManagerFirstDone : " + i.getMessage());
						}
					}
				}

			}
		} catch (Exception e) {
			System.out.println("BanManagerFirstDone : " + e.getMessage());
			new Alert(AlertType.WARNING, "서버와 연결이 끊겼습니다.", ButtonType.CLOSE).showAndWait();
			Platform.exit();
		}
	}

	public void btn_Next_Action() {
		try {
			if (no) {
				ProfessorDataModel.currentPB = 0;

				ProblemType p = problemList[ProfessorDataModel.currentPB].getType();
				if (!p.equals(ProblemType.MultipleChoice)) {
					Stage primaryStage = (Stage) btn_Main.getScene().getWindow();
					Parent main = FXMLLoader.load(getClass().getResource("/gui/BanManagerSecondDone.fxml"));
					Scene scene = new Scene(main);
					primaryStage.setTitle("GuessWhat/Workbook");
					primaryStage.setScene(scene);
					primaryStage.show();
				} else if (p.equals(ProblemType.MultipleChoice)) {
					Stage primaryStage = (Stage) btn_Main.getScene().getWindow();
					Parent main = FXMLLoader.load(getClass().getResource("/gui/BanManagerSecondDoneMultiChoice.fxml"));
					Scene scene = new Scene(main);
					primaryStage.setTitle("GuessWhat/Workbook");
					primaryStage.setScene(scene);
					primaryStage.show();
				}
			} else {
				new Alert(AlertType.WARNING, "시험에 참가한 학생이 없습니다.", ButtonType.CLOSE).showAndWait();
				Stage primaryStage = (Stage) btn_Main.getScene().getWindow();
				Parent main = FXMLLoader.load(getClass().getResource("/gui/Ban.fxml"));
				Scene scene = new Scene(main);
				primaryStage.setTitle("GuessWhat/Workbook");
				primaryStage.setScene(scene);
				primaryStage.show();
			}
		} catch (Exception e) {
			System.out.println("BanManagerFirstDone : " + e.getMessage());
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
			System.out.println("BanManagerFirstDone : " + e.getMessage());
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
			System.out.println("BanManagerFirstDone : " + e.getMessage());
			new Alert(AlertType.WARNING, "서버와 연결이 끊겼습니다.", ButtonType.CLOSE).showAndWait();
			Platform.exit();
		}
	}

	private boolean getStudent() throws Exception {
		ProfessorDataModel.ip_student.clear();

		String responseMessage = null;

		String requestMessage = "GetStudent:" + this.banManager.BM_num();

		System.out.println("requestMessage : " + requestMessage);
		BufferedReader br = new BufferedReader(
				new InputStreamReader(this.socket.getInputStream(), StandardCharsets.UTF_8));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream(), StandardCharsets.UTF_8));
		pw.println(requestMessage);
		pw.flush();
		responseMessage = br.readLine();

		// GetStudent:Success:Student1_Student2(SNum~BNum~BMNum~Name~N1~N2~...~N15~Result)
		String[] responseTokens = responseMessage.split(":");
		System.out.println("responseMessage : " + responseMessage);
		if (responseTokens[0].equals("GetStudent")) {
			if (!responseTokens[1].equals("Success")) {
				System.out.println("GetStudent:Fail");
				return false;
			} else {
				String[] studentList = responseTokens[2].split("_");
				for (int i = 0; i < studentList.length; i++) {
					ProfessorDataModel.ip_student.add(new Student(this.workbook.WorkBooksize(), studentList[i]));
				}
				if (studentList.length > 0) {
					String requestMessage2 = "ModifyStudentSize:" + this.banManager.BM_num() + ":" + studentList.length;
					PrintWriter pw2 = new PrintWriter(
							new OutputStreamWriter(this.socket.getOutputStream(), StandardCharsets.UTF_8));
					pw2.println(requestMessage2);
					pw2.flush();
				}
			}
		}
		return true;
	}

	private boolean getAllProblem() throws Exception {

		String responseMessage = null;

		String requestMessage = "GetAllProblem:" + this.workbook.W_Num();
		BufferedReader br = new BufferedReader(
				new InputStreamReader(this.socket.getInputStream(), StandardCharsets.UTF_8));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream(), StandardCharsets.UTF_8));
		pw.println(requestMessage);
		pw.flush();
		responseMessage = br.readLine();
		String[] responseTokens = responseMessage.split(":");
		if (responseTokens[0].equals("GetAllProblem")) {
			if (!responseTokens[1].equals("Success")) {
				System.out.println("GetAllProblem:Fail");
				return false;
			} else {
				// Success GetAllProblem
				// GetAllProblem:Success:Problem1_Problem2
				String[] problemInfo = responseTokens[2].split("_");
				Problem[] problemList = new Problem[problemInfo.length];
				for (int i = 0; i < problemList.length; i++) {
					Problem problem = new Problem(problemInfo[i]);
					problemList[i] = problem;
				}
				ProfessorDataModel.problemList = problemList;
			}
		}
		return true;
	}
}
