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
import exam.Workbook;
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
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.StudentDataModel;
import user.Student;

public class StuResultDetail_MultipleChoiceController implements Initializable {

	@FXML
	private Button btn_Close, btn_Previous, btn_Next, btn_num1, btn_num2, btn_num3, btn_num4,
			btn_num5, btn_num6, btn_num7, btn_num8, btn_num9, btn_num10, btn_num11, btn_num12, btn_num13, btn_num14,
			btn_num15;
	@FXML
	private Label lb_Question;
	@FXML
	private CheckBox cb_1, cb_2, cb_3, cb_4, cb_5;

	private Socket socket;
	private Problem[] problemList;
	private Problem problem;
	private Student student;
	private Button[] btn;
	private int workBookSize;
	private int PB_num;
	private CheckBox[] cb;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		this.socket = StudentDataModel.socket;
		this.PB_num = StudentDataModel.currentPB;
		this.problemList = StudentDataModel.problemList;
		this.student = StudentDataModel.student;
		this.problem = problemList[PB_num];
		this.workBookSize = StudentDataModel.workbook.WorkBooksize();

		if (!problem.getType().equals(ProblemType.MultipleChoice)) {
			try {
				Stage primaryStage = (Stage) btn_Close.getScene().getWindow();
				Parent main = FXMLLoader.load(getClass().getResource("/gui/StuResultDetail.fxml"));
				Scene scene = new Scene(main);
				primaryStage.setTitle("GuessWhat/Workbook");
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// setting
		btn = new Button[] {btn_num1, btn_num2, btn_num3, btn_num4, btn_num5, btn_num6, btn_num7, btn_num8,
				btn_num9, btn_num10, btn_num11, btn_num12, btn_num13, btn_num14, btn_num15 };

		String[] result = this.student.result();
		int[] value = new int[3];

		for (int i = 0; i < workBookSize; i++) {
			if (result.equals("O"))
				btn[i].setStyle("-fx-background-color: #5ad18f;");
			else if (result.equals("X"))
				btn[i].setStyle("-fx-background-color: #ff848f;");
			else if (result.equals("N"))
				btn[i].setStyle("-fx-background-color: #5ad18f;");

			btn[i].setDisable(false);
		}
		for (int i = workBookSize; i < 15; i++) {
			btn[i].setStyle("-fx-background-color: #dcdcdc;");
			btn[i].setDisable(true);
		}

		// setting

		btn[PB_num].setStyle("-fx-background-color: #22941C;");
		lb_Question.setText(this.problem.question());
		String T_answer = this.problem.answer();
		String S_answer = this.student.answer()[PB_num];

		cb = new CheckBox[] { cb_1, cb_2, cb_3, cb_4, cb_5 };
		for (int i = 0; i < 5; i++)
			cb[i].setText(problem.getAnswerContentList()[i]);

		for (int i = 0; i < S_answer.length(); i++) {
			int a = S_answer.charAt(i) - '0';
			cb[a].setSelected(true);
			cb[a].setStyle("-fx-background-color: #ff848f;");
		}

		for (int i = 0; i < T_answer.length(); i++) {
			int a = T_answer.charAt(i) - '0';
			cb[a].setStyle("-fx-background-color: #64d6ff;");
		}
	}

	private void changeProblem() {
		if (PB_num < workBookSize) {
			PB_num = StudentDataModel.currentPB;
			 StudentDataModel.problem = problemList[PB_num];
			if (problem.getType().equals(ProblemType.MultipleChoice)) {
				try {
					Stage primaryStage = (Stage) btn_Close.getScene().getWindow();
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
					Stage primaryStage = (Stage) btn_Close.getScene().getWindow();
					Parent main = FXMLLoader.load(getClass().getResource("/gui/StuResultDetail.fxml"));
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

	public void btn_Close_Action() {
		try {
			Stage primaryStage = (Stage) btn_Close.getScene().getWindow();
			Parent main = FXMLLoader.load(getClass().getResource("/gui/Home.fxml"));
			Scene scene = new Scene(main);
			primaryStage.setTitle("GuessWhat/Home");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void btn_Next_Action() {
		if (workBookSize == StudentDataModel.currentPB + 1)
			new Alert(AlertType.CONFIRMATION, "마지막 문제입니다.", ButtonType.CLOSE).showAndWait();
		else {
			StudentDataModel.currentPB = StudentDataModel.currentPB + 1;
			changeProblem();
		}
	}

	public void btn_Previous_Action() {
		if (0 == StudentDataModel.currentPB)
			new Alert(AlertType.CONFIRMATION, "첫 문제입니다.", ButtonType.CLOSE).showAndWait();
		else {
			StudentDataModel.currentPB = StudentDataModel.currentPB - 1;
			changeProblem();
		}
	}

	public void btn_num1_Action() {
		StudentDataModel.currentPB = 0;
		changeProblem();
	}

	public void btn_num2_Action() {
		StudentDataModel.currentPB = 1;
		changeProblem();
	}

	public void btn_num3_Action() {
		StudentDataModel.currentPB = 2;
		changeProblem();
	}

	public void btn_num4_Action() {
		StudentDataModel.currentPB = 3;
		changeProblem();
	}

	public void btn_num5_Action() {
		StudentDataModel.currentPB = 4;
		changeProblem();
	}

	public void btn_num6_Action() {
		StudentDataModel.currentPB = 5;
		changeProblem();
	}

	public void btn_num7_Action() {
		StudentDataModel.currentPB = 6;
		changeProblem();
	}

	public void btn_num8_Action() {
		StudentDataModel.currentPB = 7;
		changeProblem();
	}

	public void btn_num9_Action() {
		StudentDataModel.currentPB = 8;
		changeProblem();
	}

	public void btn_num10_Action() {
		StudentDataModel.currentPB = 9;
		changeProblem();
	}

	public void btn_num11_Action() {
		StudentDataModel.currentPB = 10;
		changeProblem();
	}

	public void btn_num12_Action() {
		StudentDataModel.currentPB = 11;
		changeProblem();
	}

	public void btn_num13_Action() {
		StudentDataModel.currentPB = 12;
		changeProblem();
	}

	public void btn_num14_Action() {
		StudentDataModel.currentPB = 13;
		changeProblem();
	}

	public void btn_num15_Action() {
		StudentDataModel.currentPB = 14;
		changeProblem();
	}
}
