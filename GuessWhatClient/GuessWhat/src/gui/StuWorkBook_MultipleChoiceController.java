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

import exam.Problem;
import exam.ProblemType;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.StudentDataModel;
import user.Student;

public class StuWorkBook_MultipleChoiceController implements Initializable {

	@FXML
	private Button btn_Submit, btn_Previous, btn_Next, btn_num1, btn_num2, btn_num3, btn_num4, btn_num5, btn_num6,
			btn_num7, btn_num8, btn_num9, btn_num10, btn_num11, btn_num12, btn_num13, btn_num14, btn_num15;
	@FXML
	private Label lb_Question;
	@FXML
	private CheckBox cb_1, cb_2, cb_3, cb_4, cb_5;

	private Socket socket;
	private Problem[] problemList;
	private Problem problem;
	private Student student;
	private Button[] btn;
	private CheckBox[] cb;
	private int PB_num;
	private int workBookSize;
	private boolean[] hasAnswer;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		try {
			this.socket = StudentDataModel.socket;
			this.problem = StudentDataModel.problem;
			this.hasAnswer = StudentDataModel.hasAnswer;
			this.student = StudentDataModel.student;
			this.problemList = StudentDataModel.problemList;
			this.workBookSize = StudentDataModel.workbook.WorkBooksize();
			this.PB_num = StudentDataModel.currentPB;

			// setting
			btn = new Button[] { btn_num1, btn_num2, btn_num3, btn_num4, btn_num5, btn_num6, btn_num7, btn_num8,
					btn_num9, btn_num10, btn_num11, btn_num12, btn_num13, btn_num14, btn_num15 };

			for (int i = 0; i < workBookSize; i++) {
				if (hasAnswer[i])
					btn[i].setStyle("-fx-background-color: #54bd54;");
				else
					btn[i].setStyle("-fx-background-color: #5ad18f;");

				btn[i].setDisable(false);
			}

			btn[PB_num].setStyle("-fx-background-color: #22941C;");
			lb_Question.setText(problem.question());
			cb = new CheckBox[] { cb_1, cb_2, cb_3, cb_4, cb_5 };
			for (int i = 0; i < 5; i++) {
				cb[i].setText(problem.getAnswerContentList()[i]);
			}

			if (hasAnswer[PB_num]) {
				String S_answer = this.student.answer()[PB_num];
				for (int j = 0; j < S_answer.length(); j++) {
					int num = S_answer.charAt(j) - '0';
					cb[num - 1].setSelected(true);
				}
			} else {
				for (int j = 0; j < 5; j++)
					cb[j].setSelected(false);
			}

			for (int i = workBookSize; i < 15; i++) {
				btn[i].setStyle("-fx-background-color: #dcdcdc;");
				btn[i].setDisable(true);
			}
			// setting
		} catch (Exception e) {
			System.out.println("StuResultDetail : " + e.getMessage());
			new Alert(AlertType.WARNING, "서버와 연결이 끊겼습니다.", ButtonType.CLOSE).showAndWait();
			Platform.exit();
		}

	}

	private void changeProblem() {
		try {
			PB_num = StudentDataModel.currentPB;
			StudentDataModel.problem = problemList[PB_num];
			ProblemType p = StudentDataModel.problem.getType();
			if (p.equals(ProblemType.MultipleChoice)) {
				Stage primaryStage = (Stage) btn_Submit.getScene().getWindow();
				Parent main = FXMLLoader.load(getClass().getResource("/gui/StuWorkBook_MultipleChoice.fxml"));
				Scene scene = new Scene(main);
				primaryStage.setTitle("GuessWhat/Workbook");
				primaryStage.setScene(scene);
				primaryStage.show();

			} else if (!p.equals(ProblemType.MultipleChoice)) {
				Stage primaryStage = (Stage) btn_Submit.getScene().getWindow();
				Parent main = FXMLLoader.load(getClass().getResource("/gui/StuWorkBook.fxml"));
				Scene scene = new Scene(main);
				primaryStage.setTitle("GuessWhat/Workbook");
				primaryStage.setScene(scene);
				primaryStage.show();
			}
		} catch (Exception e) {
			System.out.println("StuResultDetail : " + e.getMessage());
			new Alert(AlertType.WARNING, "서버와 연결이 끊겼습니다.", ButtonType.CLOSE).showAndWait();
			Platform.exit();
		}
	}

	private void savePro() throws Exception {

		String S_answer = "";
		for (int i = 0; i < 5; i++)
			if (cb[i].isSelected())
				S_answer = S_answer + (i + 1);

		if (S_answer.equals("") || S_answer.equals(" ") || S_answer.equals(null) || S_answer == null) {
			StudentDataModel.hasAnswer[StudentDataModel.currentPB] = false;
		} else {
			this.student.answer()[StudentDataModel.currentPB] = S_answer;
			StudentDataModel.hasAnswer[StudentDataModel.currentPB] = true;
		}

	}

	public void btn_Next_Action() {
		try {

			if (!this.isIng()) {
				btn_Submit_Action();
			} else {
				savePro();

				if (workBookSize == StudentDataModel.currentPB + 1)
					btn_Submit_Action();
				else {
					StudentDataModel.currentPB = StudentDataModel.currentPB + 1;
					changeProblem();
				}
			}
		} catch (Exception e) {
			System.out.println("StuResultDetail : " + e.getMessage());
			new Alert(AlertType.WARNING, "서버와 연결이 끊겼습니다.", ButtonType.CLOSE).showAndWait();
			Platform.exit();
		}

	}

	public void btn_Previous_Action() {
		try {
			if (!this.isIng()) {
				btn_Submit_Action();
			} else {
				savePro();

				if (0 == StudentDataModel.currentPB)
					btn_num1_Action();
				else {
					StudentDataModel.currentPB = StudentDataModel.currentPB - 1;
					changeProblem();
				}
			}
		} catch (Exception e) {
			System.out.println("StuResultDetail : " + e.getMessage());
			new Alert(AlertType.WARNING, "서버와 연결이 끊겼습니다.", ButtonType.CLOSE).showAndWait();
			Platform.exit();
		}

	}

	public void btn_Submit_Action() {
		try {
			Alert alert = new Alert(AlertType.CONFIRMATION, "제출하시겠습니까?", ButtonType.YES, ButtonType.NO);
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == (ButtonType.YES)) {

				this.savePro();
				this.markAnswer(); // 체점하기

				String responseMessage = null;

				String requestTokens = "AddStudent:" + StudentDataModel.tokenStudentData() + ":"
						+ this.student.tokenAnswer() + ":" + this.student.tokenResult();
				BufferedReader br = new BufferedReader(
						new InputStreamReader(this.socket.getInputStream(), StandardCharsets.UTF_8));
				PrintWriter pw = new PrintWriter(
						new OutputStreamWriter(this.socket.getOutputStream(), StandardCharsets.UTF_8));
				pw.println(requestTokens);
				pw.flush();
				responseMessage = br.readLine();

				String[] responseTokens = responseMessage.split(":");
				if (responseTokens[0].equals("AddStudent")) {
					if (!responseTokens[1].equals("Success")) {
						System.out.println(responseTokens[1]);
					} else {

						Stage primaryStage = (Stage) btn_Submit.getScene().getWindow();
						Parent main = FXMLLoader.load(getClass().getResource("/gui/StuResult.fxml"));
						Scene scene = new Scene(main);
						primaryStage.setTitle("GuessWhat/Result");
						primaryStage.setScene(scene);
						primaryStage.show();
					}
				}
			}
		} catch (Exception e) {
			System.out.println("StuResultDetail : " + e.getMessage());
			new Alert(AlertType.WARNING, "서버와 연결이 끊겼습니다.", ButtonType.CLOSE).showAndWait();
			Platform.exit();
		}
	}

	public void btn_num1_Action() {
		this.pressButton(0);
	}

	public void btn_num2_Action() {
		this.pressButton(1);
	}

	public void btn_num3_Action() {
		this.pressButton(2);
	}

	public void btn_num4_Action() {
		this.pressButton(3);
	}

	public void btn_num5_Action() {
		this.pressButton(4);
	}

	public void btn_num6_Action() {
		this.pressButton(5);
	}

	public void btn_num7_Action() {
		this.pressButton(6);
	}

	public void btn_num8_Action() {
		this.pressButton(7);
	}

	public void btn_num9_Action() {
		this.pressButton(8);
	}

	public void btn_num10_Action() {
		this.pressButton(9);
	}

	public void btn_num11_Action() {
		this.pressButton(10);
	}

	public void btn_num12_Action() {
		this.pressButton(11);
	}

	public void btn_num13_Action() {
		this.pressButton(12);
	}

	public void btn_num14_Action() {
		this.pressButton(13);
	}

	public void btn_num15_Action() {
		this.pressButton(14);
	}

	private void markAnswer() throws Exception {

		String[] studentAnswer = this.student.answer;
		String[] professorAnswer = this.getAnswerList();
		String[] typeList = this.getTypeList();
		StringBuilder sb = new StringBuilder("");
		if (studentAnswer != null) {
			for (int i = 0; i < studentAnswer.length; i++) {
				if (studentAnswer[i] != null) {
					if (typeList[i].equals("Subjective")) {
						sb.append("N");
					} else {
						if (studentAnswer[i].equals(professorAnswer[i])) {
							sb.append("O");
						} else {
							sb.append("X");
						}
					}
				} else {
					if (typeList[i].equals("Subjective"))
						sb.append("N");
					else
						sb.append("X");
				}
			}
			this.student.setResult(new String(sb));
		}

	}

	private String[] getAnswerList() throws Exception {
		String responseMessage = null;

		String requestTokens = "GetAnswerList:" + StudentDataModel.workbook.W_Num();
		BufferedReader br = new BufferedReader(
				new InputStreamReader(this.socket.getInputStream(), StandardCharsets.UTF_8));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream(), StandardCharsets.UTF_8));
		pw.println(requestTokens);
		pw.flush();
		responseMessage = br.readLine();

		// GetAnswer:Success:Answer(A1`A2`A3 ...)
		String[] answerList = null;
		System.out.println(responseMessage);
		String[] responseTokens = responseMessage.split(":");
		if (responseTokens[0].equals("GetAnswerList")) {
			if (!responseTokens[1].equals("Success")) {
				System.out.println(responseTokens[1]);
			} else {
				// Success GetAnswer
				answerList = responseTokens[2].split("`");
			}
		}
		return answerList;
	}

	private String[] getTypeList() throws Exception {
		String responseMessage = null;

		String requestTokens = "GetTypeList:" + StudentDataModel.workbook.W_Num();
		BufferedReader br = new BufferedReader(
				new InputStreamReader(this.socket.getInputStream(), StandardCharsets.UTF_8));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream(), StandardCharsets.UTF_8));
		pw.println(requestTokens);
		pw.flush();
		responseMessage = br.readLine();

		// GetAnswer:Success:Type
		String[] typeList = null;
		String[] responseTokens = responseMessage.split(":");
		if (responseTokens[0].equals("GetTypeList")) {
			if (!responseTokens[1].equals("Success")) {
				System.out.println(responseTokens[1]);
			} else {
				// Success GetTypeList
				typeList = responseTokens[2].split("-");
			}
		}
		return typeList;
	}

	private boolean isIng() throws Exception {
		String responseMessage = null;

		BufferedReader br = new BufferedReader(
				new InputStreamReader(this.socket.getInputStream(), StandardCharsets.UTF_8));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream(), StandardCharsets.UTF_8));
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
					return true;
				}
			}
		}

		return false;
	}

	private void pressButton(int currentPB) {
		try {
			if (!this.isIng()) {
				new Alert(AlertType.CONFIRMATION, "제출되었습니다.", ButtonType.CLOSE).showAndWait();
				this.btn_Submit_Action();
			} else {
				savePro();
				StudentDataModel.currentPB = currentPB;
				changeProblem();
			}
		} catch (Exception e) {
			System.out.println("StuResultDetail : " + e.getMessage());
			new Alert(AlertType.WARNING, "서버와 연결이 끊겼습니다.", ButtonType.CLOSE).showAndWait();
			Platform.exit();
		}
	}

}