package gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.ResourceBundle;

import exam.Problem;
import exam.ProblemType;
import exam.Workbook;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.ProfessorDataModel;
import model.StudentDataModel;
import room.BanManager;
import user.Student;

public class StuInfoToStuWBController implements Initializable {

	@FXML
	private BorderPane parent;

	private Socket socket;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.socket = StudentDataModel.socket;

		new LoadingIngThread().start();
	}

	class LoadingIngThread extends Thread {

		private boolean isMultipleChoice = false;
		private boolean threadClose = false;

		@Override
		public void run() {
			try {
				String responseMessage = null;
				BufferedReader br = new BufferedReader(
						new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
				PrintWriter pw = new PrintWriter(
						new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
				while (!threadClose) {
					// GetBanManagerState:BMNum
					String requestMessage = "GetBanManagerState:" + StudentDataModel.banManager.BM_num();
					pw.println(requestMessage);
					pw.flush();
					responseMessage = br.readLine();
					String[] responseTokens = responseMessage.split(":");
					// GetBanMnagerState:Success:BMState
					if (responseTokens[0].equals("GetBanManagerState")) {
						if (!responseTokens[1].equals("Success")) {
							System.out.println(responseMessage);
						} else {
							if (responseTokens[2].equals("ING")) {
								// >>>>ING<<<
								StudentDataModel.banManager.setBM_state_ING();

								// 화면전환
								if (!threadClose) {
									Platform.runLater(() -> {
										try {
											if (this.getWorkbook()) {
												if (this.getAllProblem()) {
													if (this.isMultipleChoice) {
														Stage primaryStage = (Stage) parent.getScene().getWindow();
														Parent main = FXMLLoader.load(getClass()
																.getResource("/gui/StuWorkBook_MultipleChoice.fxml"));
														Scene scene = new Scene(main);
														primaryStage.setTitle("GuessWhat/Test");
														primaryStage.setScene(scene);
														primaryStage.show();
														threadClose = true;
													} else {

														Stage primaryStage = (Stage) parent.getScene().getWindow();
														Parent main = FXMLLoader
																.load(getClass().getResource("/gui/StuWorkBook.fxml"));
														Scene scene = new Scene(main);
														primaryStage.setTitle("GuessWhat/Test");
														primaryStage.setScene(scene);
														primaryStage.show();
														threadClose = true;
													}
												} else {
													System.out.println("Fail getAllProblem");
												}
											} else {
												System.out.println("Fail getWorkbook");
											}
										} catch (Exception e) {
											System.out.println("StuInfoToWB : " + e.getMessage());
											new Alert(AlertType.WARNING, "서버와 연결이 끊겼습니다.", ButtonType.CLOSE)
													.showAndWait();
											Platform.exit();
										}
									});
								}
								if (threadClose) {
									break;
								}
							} else {
								// >>>>>CLOSE || OPEN <<<<<
							}
						}
					}
					Thread.sleep(7000);
				}
			} catch (Exception e) {
				System.out.println("StuInfoToWB : " + e.getMessage());
				new Alert(AlertType.WARNING, "서버와 연결이 끊겼습니다.", ButtonType.CLOSE).showAndWait();
				Platform.exit();
			}
		}

		private boolean getWorkbook() throws Exception {

			String responseMessage = null;
			String requestTokens = "GetWorkbookProblem:" + StudentDataModel.banManager.W_num();
			BufferedReader br = new BufferedReader(
					new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
			pw.println(requestTokens);
			pw.flush();
			responseMessage = br.readLine();
			String[] responseTokens = responseMessage.split(":");
			if (responseTokens[0].equals("GetWorkbook")) {
				if (!responseTokens[1].equals("Success")) {
					System.out.println(responseTokens[1]);
					return false;
				} else {
					// GetWorkbook:Success:WorkbookInfo

					Workbook workbook = new Workbook(responseTokens[2]);
					StudentDataModel.setWorkbook(workbook);
					StudentDataModel.hasAnswer = new boolean[workbook.WorkBooksize()];
					StudentDataModel.student = new Student();
					StudentDataModel.student.setAnswer(new String[workbook.WorkBooksize()]);
					StudentDataModel.student.setResultWithList(new String[workbook.WorkBooksize()]);
					return true;
				}
			} else
				return false;
		}

		private boolean getAllProblem() throws Exception {
			String responseMessage = null;
			String requestMessage = "GetAllProblem:" + StudentDataModel.banManager.W_num();
			BufferedReader br = new BufferedReader(
					new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
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
					StudentDataModel.problemList = problemList;
					if (problemList[0].getType().equals(ProblemType.MultipleChoice)) {
						this.isMultipleChoice = true;
					}
					StudentDataModel.setProblem(problemList[0]);

				}
			}
			return true;
		}
	}
}