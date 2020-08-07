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
import exam.Workbook;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.ProfessorDataModel;
import model.StudentDataModel;
import user.Student;

public class StuWorkBookController extends BaseController implements Initializable {

	@FXML
	private Button btn_Submit, btn_Previous, btn_Next, btn_num1, btn_num2, btn_num3, btn_num4, btn_num5, btn_num6,
			btn_num7, btn_num8, btn_num9, btn_num10, btn_num11, btn_num12, btn_num13, btn_num14, btn_num15, btn_num16,
			btn_num17, btn_num18, btn_num19, btn_num20;
	@FXML
	private Label lb_Question;
	@FXML
	private TextArea ta_Answer;

	private Button[] btn;
	private int workBookSize;

	private Socket socket;
	private Problem problem;
	private Student student;
	private boolean [] answerIsFull;
	private int PB_num;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		this.socket = StudentDataModel.socket;
		this.problem = StudentDataModel.problem;
		this.answerIsFull = StudentDataModel.answerIsEmpty;
		
		if (problem.getType().equals(ProblemType.MultipleChoice)) {
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
		}

		this.workBookSize = StudentDataModel.workbook.WorkBooksize();
		this.PB_num = problem.PB_Num();

		// setting
		btn = new Button[] { btn_num1, btn_num2, btn_num3, btn_num4, btn_num5, btn_num6, btn_num7, btn_num8, btn_num9,
				btn_num10, btn_num11, btn_num12, btn_num13, btn_num14, btn_num15, btn_num16, btn_num17, btn_num18,
				btn_num19, btn_num20 };

		for (int i = 0; i < workBookSize; i++) {
			if (answerIsFull[i])
				btn[i].setStyle("-fx-background-color: #dcdcdc;");
			else
				btn[i].setStyle("-fx-background-color: #5ad18f;");
			
			btn[i].setDisable(false);
		}

		btn[PB_num].setStyle("-fx-background-color: #54bd54;");
		if(answerIsFull[PB_num]) ta_Answer.setText(student.answer()[PB_num]);

		for (int i = workBookSize + 1; i < 20; i++) {
			btn[i].setStyle("-fx-background-color: #f0fff0;");
			btn[i].setDisable(true);
		}

		lb_Question.setText(problem.question());
		// setting

	}

	public void btn_Next_Action() {

		String anwer = ta_Answer.getText();
		// if(answer != null) 저장

		StudentDataModel.currentPB = StudentDataModel.currentPB + 1;

	}

	public void btn_Previous_Action() {

		String anwer = ta_Answer.getText();
		// if(answer != null) 저장

		StudentDataModel.currentPB = StudentDataModel.currentPB - 1;

	}

	public void btn_Submit_Action() {

		// workbook 전체 저장

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

	private void changeProblem() {
		// 여기가 만들어주세용 정현
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
		} catch(IOException e) {
			e.printStackTrace();
		}
		String[] responseTokens = responseMessage.split(":");
		if(responseTokens[0].equals("GetProblem")){
			if(!responseTokens[1].equals("Success")) {
				System.out.println(responseTokens[1]);
			}
			else {
				//Success GetProblem
				Problem problem = new Problem(responseTokens[2]);
				StudentDataModel.setProblem(problem);
				System.out.println(StudentDataModel.problem.toString());
				
			}
		}
	}

	public void btn_num1_Action() {
		StudentDataModel.currentPB = 1;
		changeProblem();
	}

	public void btn_num2_Action() {
		StudentDataModel.currentPB = 2;
		changeProblem();

	}

	public void btn_num3_Action() {
		StudentDataModel.currentPB = 3;
		changeProblem();

	}

	public void btn_num4_Action() {
		StudentDataModel.currentPB = 4;
		changeProblem();

	}

	public void btn_num5_Action() {
		StudentDataModel.currentPB = 5;
		changeProblem();
	}

	public void btn_num6_Action() {
		StudentDataModel.currentPB = 6;
		changeProblem();
	}

	public void btn_num7_Action() {
		StudentDataModel.currentPB = 7;
		changeProblem();
	}

	public void btn_num8_Action() {
		StudentDataModel.currentPB = 8;
		changeProblem();
	}

	public void btn_num9_Action() {
		StudentDataModel.currentPB = 9;
		changeProblem();
	}

	public void btn_num10_Action() {
		StudentDataModel.currentPB = 10;
		changeProblem();
	}

	public void btn_num11_Action() {
		StudentDataModel.currentPB = 11;
		changeProblem();
	}

	public void btn_num12_Action() {
		StudentDataModel.currentPB = 12;
		changeProblem();
	}

	public void btn_num13_Action() {
		StudentDataModel.currentPB = 13;
		changeProblem();
	}

	public void btn_num14_Action() {
		StudentDataModel.currentPB = 14;
		changeProblem();
	}

	public void btn_num15_Action() {
		StudentDataModel.currentPB = 15;
		changeProblem();
	}

	public void btn_num16_Action() {
		StudentDataModel.currentPB = 16;
		changeProblem();
	}

	public void btn_num17_Action() {
		StudentDataModel.currentPB = 17;
		changeProblem();
	}

	public void btn_num18_Action() {
		StudentDataModel.currentPB = 18;
		changeProblem();
	}

	public void btn_num19_Action() {
		StudentDataModel.currentPB = 19;
		changeProblem();
	}

	public void btn_num20_Action() {
		StudentDataModel.currentPB = 20;
		changeProblem();
	}

}
