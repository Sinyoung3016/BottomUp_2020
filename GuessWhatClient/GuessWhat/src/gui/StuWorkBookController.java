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

public class StuWorkBookController implements Initializable {

	@FXML
	private Button btn_Submit, btn_Previous, btn_Next, btn_num1, btn_num2, btn_num3, btn_num4, btn_num5, btn_num6,
			btn_num7, btn_num8, btn_num9, btn_num10, btn_num11, btn_num12, btn_num13, btn_num14, btn_num15;
	@FXML
	private Label lb_Question;
	@FXML
	private TextArea ta_Answer;

	private Socket socket;
	private Problem problem;
	private Student student;
	private Problem[] problemList;
	private Button[] btn;
	private int PB_num;
	private int workBookSize;
	private boolean[] hasAnswer;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		this.socket = StudentDataModel.socket;
		this.workBookSize = StudentDataModel.workbook.WorkBooksize();
		this.PB_num = StudentDataModel.currentPB;
		this.problemList = StudentDataModel.problemList;
		this.problem = problemList[PB_num];
		this.student = StudentDataModel.student;
		this.hasAnswer = StudentDataModel.hasAnswer;

		if (problem.getType().equals(ProblemType.MultipleChoice)) {
			try {
				Stage primaryStage = (Stage) lb_Question.getScene().getWindow();
				Parent main = FXMLLoader.load(getClass().getResource("/gui/StuWorkBook_MultipleChoice.fxml"));
				Scene scene = new Scene(main);
				primaryStage.setTitle("GuessWhat/Workbook");
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

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
		if (S_answer.equals(""))
			StudentDataModel.hasAnswer[StudentDataModel.currentPB] = false;
		else {
			StudentDataModel.student.setAnswerNum(S_answer, PB_num);
			StudentDataModel.hasAnswer[StudentDataModel.currentPB] = true;
		}

	}

	public void btn_Next_Action() {
		if (workBookSize == StudentDataModel.currentPB + 1) {
			Alert alert = new Alert(AlertType.CONFIRMATION, "마지막 문제입니다. 제출하시겠습니까?", ButtonType.OK, ButtonType.NO);
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.YES) {
				btn_Submit_Action();
			}
		}
		else {
			savePro();
			StudentDataModel.currentPB = StudentDataModel.currentPB + 1;
			changeProblem();
		}
	}

	public void btn_Previous_Action() {
		if (0 == StudentDataModel.currentPB)
			new Alert(AlertType.CONFIRMATION, "첫번째 문제입니다.", ButtonType.CLOSE).showAndWait();
		else {
			savePro();
			StudentDataModel.currentPB = StudentDataModel.currentPB - 1;
			changeProblem();
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
		savePro();
		StudentDataModel.currentPB = 0;
		changeProblem();
	}

	public void btn_num2_Action() {
		savePro();
		StudentDataModel.currentPB = 1;
		changeProblem();
	}

	public void btn_num3_Action() {
		savePro();
		StudentDataModel.currentPB = 2;
		changeProblem();
	}

	public void btn_num4_Action() {
		savePro();
		StudentDataModel.currentPB = 3;
		changeProblem();
	}

	public void btn_num5_Action() {
		savePro();
		StudentDataModel.currentPB = 4;
		changeProblem();
	}

	public void btn_num6_Action() {
		savePro();
		StudentDataModel.currentPB = 5;
		changeProblem();
	}

	public void btn_num7_Action() {
		savePro();
		StudentDataModel.currentPB = 6;
		changeProblem();
	}

	public void btn_num8_Action() {
		savePro();
		StudentDataModel.currentPB = 7;
		changeProblem();
	}

	public void btn_num9_Action() {
		savePro();
		StudentDataModel.currentPB = 8;
		changeProblem();
	}

	public void btn_num10_Action() {
		savePro();
		StudentDataModel.currentPB = 9;
		changeProblem();
	}

	public void btn_num11_Action() {
		savePro();
		StudentDataModel.currentPB = 10;
		changeProblem();
	}

	public void btn_num12_Action() {
		savePro();
		StudentDataModel.currentPB = 11;
		changeProblem();
	}

	public void btn_num13_Action() {
		savePro();
		StudentDataModel.currentPB = 12;
		changeProblem();
	}

	public void btn_num14_Action() {
		savePro();
		StudentDataModel.currentPB = 13;
		changeProblem();
	}

	public void btn_num15_Action() {
		savePro();
		StudentDataModel.currentPB = 14;
		changeProblem();
	}

	private void changeProblem() {
		if (PB_num < workBookSize) {
			PB_num = StudentDataModel.currentPB;
			 StudentDataModel.problem = problemList[PB_num];
			if (problem.getType().equals(ProblemType.MultipleChoice)) {
				try {
					Stage primaryStage = (Stage) btn_Submit.getScene().getWindow();
					Parent main = FXMLLoader.load(getClass().getResource("/gui/StuResultDetail_MutlipleChoice.fxml"));
					Scene scene = new Scene(main);
					primaryStage.setTitle("GuessWhat/Workbook");
					primaryStage.setScene(scene);
					primaryStage.show();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (!problem.getType().equals(ProblemType.MultipleChoice)) {
				try {
					Stage primaryStage = (Stage) btn_Submit.getScene().getWindow();
					Parent main = FXMLLoader.load(getClass().getResource("/gui/StuResultDetail_MutlipleChoice.fxml"));
					Scene scene = new Scene(main);
					primaryStage.setTitle("GuessWhat/Workbook");
					primaryStage.setScene(scene);
					primaryStage.show();
				} catch (Exception e) {
					e.printStackTrace();
				}
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

}
