package gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

import exam.Problem;
import exam.ProblemType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.ProfessorDataModel;
import model.StudentDataModel;
import user.Student;

public class StuWorkBookController extends BaseController implements Initializable {

	@FXML
	private Button btn_Submit, btn_Previous, btn_Next, btn_num0, btn_num1, btn_num2, btn_num3, btn_num4, btn_num5,
			btn_num6, btn_num7, btn_num8, btn_num9, btn_num10, btn_num11, btn_num12, btn_num13, btn_num14, btn_num15;
	@FXML
	private Label lb_Question;
	@FXML
	private TextArea ta_Answer;

	private Socket socket;
	private Problem[] problemList;
	private Problem problem;
	private Student student;
	private Button[] btn;
	private int PB_num;
	private int workBookSize;
	private boolean[] hasAnswer;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		this.socket = StudentDataModel.socket;
		this.problem = StudentDataModel.problem;
		this.student = StudentDataModel.student;
		this.hasAnswer = StudentDataModel.hasAnswer;
		this.problemList = StudentDataModel.problemList;
		this.workBookSize = StudentDataModel.workbook.WorkBooksize();
		this.PB_num = StudentDataModel.currentPB;
		
		// setting
		btn = new Button[] { btn_num1, btn_num2, btn_num3, btn_num4, btn_num5, btn_num6, btn_num7, btn_num8, btn_num9,
				btn_num10, btn_num11, btn_num12, btn_num13, btn_num14, btn_num15 };

		for (int i = 0; i < workBookSize; i++) {
			if (hasAnswer[i])
				btn[i].setStyle("-fx-background-color: #54bd54;");
			else
				btn[i].setStyle("-fx-background-color: #5ad18f;");

			btn[i].setDisable(false);
		}

		btn[PB_num].setStyle("-fx-background-color: #22941C ;");
		lb_Question.setText(problem.question());
		if (hasAnswer[PB_num])
			ta_Answer.setText(student.answer()[PB_num]);
		else
			ta_Answer.setText("");

		for (int i = workBookSize; i < 15; i++) {
			btn[i].setStyle("-fx-background-color: #dcdcdc;");
			btn[i].setDisable(true);
		}
		// setting

	}

	private void savePro() {

		String S_answer = ta_Answer.getText();
		if (S_answer.equals(null))
			StudentDataModel.hasAnswer[StudentDataModel.currentPB] = false;
		else {
			this.student.answer()[StudentDataModel.currentPB] = S_answer;
			StudentDataModel.hasAnswer[StudentDataModel.currentPB] = true;
		}

	}

	public void btn_Next_Action() {
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

	}

	public void btn_Previous_Action() {
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

	}

	public void btn_Submit_Action() {
		this.savePro();
		this.markAnswer(); // 체점하기
		String responseMessage = null;
		try {
			String requestTokens = "AddStudent:" + StudentDataModel.tokenStudentData() + ":"
					+ this.student.tokenAnswer() + ":" + this.student.tokenResult();
			BufferedReader br = new BufferedReader(
					new InputStreamReader(this.socket.getInputStream(), StandardCharsets.UTF_8));
			PrintWriter pw = new PrintWriter(
					new OutputStreamWriter(this.socket.getOutputStream(), StandardCharsets.UTF_8));
			pw.println(requestTokens);
			pw.flush();
			responseMessage = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] responseTokens = responseMessage.split(":");
		if (responseTokens[0].equals("AddStudent")) {
			if (!responseTokens[1].equals("Success")) {
				System.out.println(responseTokens[1]);
			} else {
				try {
					Stage primaryStage = (Stage) btn_Submit.getScene().getWindow();
					Parent main = FXMLLoader.load(getClass().getResource("/gui/StuResult.fxml"));
					Scene scene = new Scene(main);
					primaryStage.setTitle("GuessWhat/Result");
					primaryStage.setScene(scene);
					primaryStage.show();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
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

	private void changeProblem() {
		PB_num = StudentDataModel.currentPB;
		StudentDataModel.problem = problemList[PB_num];
		ProblemType p = StudentDataModel.problem.getType();
		if (p.equals(ProblemType.MultipleChoice)) {
			try {
				Stage primaryStage = (Stage) btn_Submit.getScene().getWindow();
				Parent main = FXMLLoader.load(getClass().getResource("/gui/StuWorkBook_MultipleChoice.fxml"));
				Scene scene = new Scene(main);
				primaryStage.setTitle("GuessWhat/Workbook");
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (!p.equals(ProblemType.MultipleChoice)) {
			try {
				Stage primaryStage = (Stage) btn_Submit.getScene().getWindow();
				Parent main = FXMLLoader.load(getClass().getResource("/gui/StuWorkBook.fxml"));
				Scene scene = new Scene(main);
				primaryStage.setTitle("GuessWhat/Workbook");
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void markAnswer() {

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
					sb.append("X");
				}

			}
			this.student.setResult(new String(sb));
		}

	}

	private String[] getAnswerList() {
		Problem[] problemList = StudentDataModel.problemList;
		String[] answerList = new String[problemList.length];

		for (int i = 0; i < answerList.length; i++) {
			answerList[i] = problemList[i].answer();
		}

		return answerList;

	}

	private String[] getTypeList() {
		Problem[] problemList = StudentDataModel.problemList;
		String[] typeList = new String[problemList.length];

		for (int i = 0; i < typeList.length; i++) {
			typeList[i] = problemList[i].getType().toString();
		}

		return typeList;

	}

	private boolean isIng() {
		String responseMessage = null;
		try {
			BufferedReader br = new BufferedReader(
					new InputStreamReader(this.socket.getInputStream(), StandardCharsets.UTF_8));
			PrintWriter pw = new PrintWriter(
					new OutputStreamWriter(this.socket.getOutputStream(), StandardCharsets.UTF_8));
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
		} catch (Exception e) {
			return false;
		}
		return false;

	}

	private void pressButton(int currentPB) {
		if (!this.isIng()) {
			new Alert(AlertType.CONFIRMATION, "제출되었습니다.", ButtonType.CLOSE).showAndWait();
			this.btn_Submit_Action();
		} else {
			savePro();
			StudentDataModel.currentPB = currentPB;
			changeProblem();
		}
	}

}