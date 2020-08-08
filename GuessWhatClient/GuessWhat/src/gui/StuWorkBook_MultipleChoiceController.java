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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.StudentDataModel;
import user.Student;

public class StuWorkBook_MultipleChoiceController extends BaseController implements Initializable {

	@FXML
	private Button btn_Submit, btn_Previous, btn_Next, btn_num1, btn_num2, btn_num3, btn_num4, btn_num5, btn_num6,
			btn_num7, btn_num8, btn_num9, btn_num10, btn_num11, btn_num12, btn_num13, btn_num14, btn_num15;
	@FXML
	private Label lb_Question;
	@FXML
	private CheckBox cb_1, cb_2, cb_3, cb_4, cb_5;

	private Socket socket;
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
		this.socket = StudentDataModel.socket;
		this.problem = StudentDataModel.problem;
		this.hasAnswer = StudentDataModel.hasAnswer;
		this.student = StudentDataModel.student;

		if (!problem.getType().equals(ProblemType.MultipleChoice)) {
			try {
				Stage primaryStage = (Stage) lb_Question.getScene().getWindow();
				Parent main = FXMLLoader.load(getClass().getResource("/gui/StuWorkBook.fxml"));
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

		btn[PB_num].setStyle("-fx-background-color: #22941C;");
		lb_Question.setText(problem.question());
		cb = new CheckBox[] { cb_1, cb_2, cb_3, cb_4, cb_5 };
		for (int i = 0; i < 5; i++)
			cb[i].setText(problem.getAnswerContentList()[i]);

		if (hasAnswer[PB_num]) {
			String S_answer = this.student.answer()[PB_num];
			for (int j = 0; j < S_answer.length(); j++)
				cb[S_answer.charAt(j)].setSelected(true);
		}

		for (int i = workBookSize; i < 15; i++) {
			btn[i].setStyle("-fx-background-color: #dcdcdc;");
			btn[i].setDisable(true);
		}
		// setting

	}

	private void changeProblem() {
		String responseMessage = null;
		try {
			String requestTokens = "GetProblem:" + StudentDataModel.workbook.W_Num() + ":" + StudentDataModel.currentPB;
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
		if (responseTokens[0].equals("GetProblem")) {
			if (!responseTokens[1].equals("Success")) {
				System.out.println(responseTokens[1]);
			} else {
				// Success GetProblem
				Problem problem = new Problem(responseTokens[2]);
				StudentDataModel.setProblem(problem);
				System.out.println(StudentDataModel.problem.toString());

			}
		}

		this.initialize(null, null);
	}

	private void savePro() {

		String S_answer = "";
		for (int i = 0; i < 5; i++)
			if (cb[i].isSelected())
				S_answer = S_answer + (i + 1);

		if (S_answer.equals(""))
			return;
		else {
			this.student.answer()[StudentDataModel.currentPB] = S_answer;
			StudentDataModel.hasAnswer[StudentDataModel.currentPB] = true;
		}

	}

	public void btn_Next_Action() {
		savePro();

		if (workBookSize == StudentDataModel.currentPB + 1)
			btn_Submit_Action();
		else {
			StudentDataModel.currentPB = StudentDataModel.currentPB + 1;
			changeProblem();
		}
	}

	public void btn_Previous_Action() {
		savePro();

		if (0 == StudentDataModel.currentPB)
			btn_num1_Action();
		else {
			StudentDataModel.currentPB = StudentDataModel.currentPB - 1;
			changeProblem();
		}
	}

	public void btn_Submit_Action() {

		this.savePro();
		this.markAnswer();

		// 서버에 student정보 넘기기 구현할것!

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


	private void markAnswer() {

		String[] studentAnswer = this.student.answer;
		String[] professorAnswer = this.getAnswerList();
		String[] typeList = this.getTypeList();
		StringBuilder sb = new StringBuilder("");
		if (studentAnswer != null) {
			for (int i = 0; i < studentAnswer.length; i++) {
				if(studentAnswer[i] != null) {
					if (typeList[i].equals("Subjective")) {
						sb.append("N");
					} else { 
						if (studentAnswer[i].equals(professorAnswer[i])) {
							sb.append("O");
						} else {
							sb.append("X");
						}
					}
				}
				else {
					sb.append("X");
				}
				
			}
			this.student.setResult(new String(sb));
		}

	}

	private String[] getAnswerList() {
		String responseMessage = null;
		try {
			String requestTokens = "GetAnswerList:" + StudentDataModel.workbook.W_Num();
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
		// GetAnswer:Success:Answer(A1`A2`A3 ...)
		String[] answerList = null;
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

	private String[] getTypeList() {
		String responseMessage = null;
		try {
			String requestTokens = "GetTypeList:" + StudentDataModel.workbook.W_Num();
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

}
