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

import javafx.scene.control.Alert;
import exam.Problem;
import exam.ProblemType;
import exam.Workbook;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.StudentDataModel;
import user.Student;

public class StuResultDetailController implements Initializable {

	@FXML
	private Button btn_Close, btn_Previous, btn_Next, btn_num1, btn_num2, btn_num3, btn_num4, btn_num5, btn_num6,
			btn_num7, btn_num8, btn_num9, btn_num10, btn_num11, btn_num12, btn_num13, btn_num14, btn_num15;
	@FXML
	private Label lb_Question, lb_MyAnswer, lb_TeacherAnswer;

	private Socket socket;
	private Problem[] problemList;
	private Problem problem;
	private Student student;

	private int PB_num;
	private Button[] btn;
	private int workBookSize;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		this.socket = StudentDataModel.socket;
		this.PB_num = StudentDataModel.currentPB;
		this.problemList = StudentDataModel.problemList;
		this.problem = problemList[PB_num];
		this.student = StudentDataModel.student;
		this.workBookSize = StudentDataModel.workbook.WorkBooksize();

		// setting
		btn = new Button[] { btn_num1, btn_num2, btn_num3, btn_num4, btn_num5, btn_num6, btn_num7, btn_num8, btn_num9,
				btn_num10, btn_num11, btn_num12, btn_num13, btn_num14, btn_num15 };

		String[] result = this.student.result();

		for (int i = 0; i < workBookSize; i++) {
			if (result[i].equals("O"))
				btn[i].setStyle("-fx-background-color: #5ad18f;");
			else if (result[i].equals("X"))
				btn[i].setStyle("-fx-background-color: #ff848f;");
			else if (result[i].equals("N"))
				btn[i].setStyle("-fx-background-color: #f0fff0;");

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
		lb_MyAnswer.setText(S_answer);
		lb_TeacherAnswer.setText(T_answer);

		if (problem.getType() == ProblemType.ShortAnswer) {
			if (S_answer.equals(T_answer))
				lb_MyAnswer.setStyle("-fx-background-color: #5ad18f;");
			else
				lb_MyAnswer.setStyle("-fx-background-color: #ff848f;");
		}
	}

	private void changeProblem() {

		PB_num = StudentDataModel.currentPB;
		StudentDataModel.problem = problemList[PB_num];
		ProblemType p = StudentDataModel.problem.getType();
		if (p.equals(ProblemType.MultipleChoice)) {
			try {
				Stage primaryStage = (Stage) btn_Close.getScene().getWindow();
				Parent main = FXMLLoader.load(getClass().getResource("/gui/StuResultDetail_MultipleChoice.fxml"));
				Scene scene = new Scene(main);
				primaryStage.setTitle("GuessWhat/Workbook");
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (!p.equals(ProblemType.MultipleChoice)) {
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
		if (workBookSize == StudentDataModel.currentPB + 1) {
			new Alert(AlertType.CONFIRMATION, "마지막 문제입니다.", ButtonType.CLOSE).showAndWait();
		} else {
			StudentDataModel.currentPB = StudentDataModel.currentPB + 1;
			changeProblem();
		}
	}

	public void btn_Previous_Action() {
		if (0 == StudentDataModel.currentPB) {
			new Alert(AlertType.CONFIRMATION, "첫 문제입니다.", ButtonType.CLOSE).showAndWait();
		} else {
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
