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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.ProfessorDataModel;
import model.StudentDataModel;

public class WorkBook_SubjectiveController implements Initializable {

	@FXML
	private HBox stage;
	@FXML
	private Button btn_DeleteWorkBook, btn_Cancel, btn_SaveWorkBook, btn_num1, btn_num2, btn_num3, btn_num4, btn_num5,
			btn_num6, btn_num7, btn_num8, btn_num9, btn_num10, btn_num11, btn_num12, btn_num13, btn_num14, btn_num15,
			btn_Logo, btn_MyInfo;
	@FXML
	private TextField tf_ChangeName;
	@FXML
	private TextArea ta_Question, ta_Answer;
	@FXML
	private Pane Pane_Answer;

	private Socket socket;
	private Workbook workBook;
	private Problem[] problemList;
	private Problem problem;
	private Button[] btn;
	private int PB_num;
	private int workBookSize;
	private boolean[] hasQValue;
	private boolean[] hasAValue;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		this.socket = ProfessorDataModel.socket;
		this.workBook = ProfessorDataModel.workbook;
		this.problemList = ProfessorDataModel.problemList;
		this.problem = ProfessorDataModel.problem;
		this.workBookSize = this.workBook.WorkBooksize();
		this.PB_num = ProfessorDataModel.currentPB;

		// setting
		btn = new Button[] { btn_num1, btn_num2, btn_num3, btn_num4, btn_num5, btn_num6, btn_num7, btn_num8, btn_num9,
				btn_num10, btn_num11, btn_num12, btn_num13, btn_num14, btn_num15 };

		for (int i = 0; i < workBookSize; i++) {
			btn[i].setStyle("-fx-background-color: #5ad18f;");
			btn[i].setDisable(false);
		}
		for (int i = workBookSize; i < 15; i++) {
			btn[i].setStyle("-fx-background-color: #dcdcdc;");
			btn[i].setDisable(true);
		}
		btn[PB_num].setStyle("-fx-background-color: #22941C;");
		btn[PB_num].setDisable(false);
		// setting

		ta_Question.setText(problem.question());
		ta_Answer.setText(problem.answer());
		tf_ChangeName.setText(workBook.W_name());

		btn = new Button[] { btn_num1, btn_num2, btn_num3, btn_num4, btn_num5, btn_num6, btn_num7, btn_num8, btn_num9,
				btn_num10, btn_num11, btn_num12, btn_num13, btn_num14, btn_num15 };

		for (int i = 0; i < workBookSize; i++) {
			btn[i].setStyle("-fx-background-color: #5ad18f;");
			btn[i].setDisable(false);
		}
		for (int i = workBookSize; i < 15; i++) {
			btn[i].setStyle("-fx-background-color: #dcdcdc;");
			btn[i].setDisable(true);
		}
		btn[PB_num].setStyle("-fx-background-color: #22941C;");
		btn[PB_num].setDisable(false);
		// setting
	}

	private void changeName() {
		String temp = workBook.W_name();
		String name = tf_ChangeName.getText();
		if (! name.equals(""))
			workBook.setName(name);
		else
			workBook.setName(temp);
	}

	private void savePro() {

		this.changeName();
		String S_question = ta_Question.getText();
		String S_answer = ta_Answer.getText();

		if (!S_question.equals(null) || !S_question.equals(""))
			problem.setQuestion(S_question);

		if (!S_question.equals(null) || !S_question.equals(""))
			problem.setAnswer(S_answer);

	}

	private String tokenProblemList(Problem[] problem, int wnum) {
		StringBuilder sb = new StringBuilder("");
		int n = 0;
		while (problem[n] != null) {
			sb.append(problem[n].tokenString(wnum));
			sb.append("_");
			n++;
		}
		sb.deleteCharAt(sb.length() - 1);

		return new String(sb);

	}

	public void btn_DeleteWorkBook_Action() {
		Alert alert = new Alert(AlertType.WARNING, "(Workbook) " + workBook.W_name() + "을(를) 정말로 삭제하시겠습니까?",
				ButtonType.YES, ButtonType.NO);
		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == ButtonType.YES) {

			// ㄹㅇ삭제
			String responseMessage = null;
			try {
				String requestMessage = "DeleteWorkbook:" + this.workBook.W_Num();
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
			String[] responseTokens = responseMessage.split(":");

			if (responseTokens[0].equals("DeleteWorkbook")) {
				if (!responseTokens[1].equals("Success")) {
					System.out.println("Fail : DeleteWorkbook");
				} else {
					System.out.println("  [Delete] " + this.workBook.W_name());
				}
			}

			ProfessorDataModel.workbook = null;
			ProfessorDataModel.problem = null;

			try {
				Stage primaryStage = (Stage) btn_DeleteWorkBook.getScene().getWindow();
				Parent main = FXMLLoader.load(getClass().getResource("/gui/WorkBookList.fxml"));
				Scene scene = new Scene(main);
				primaryStage.setTitle("GuessWhat/WorkBookList");
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void btn_SaveWorkBook_Action() {
		this.savePro();
		String modifiedProblem = this.problem.PB_Num() + ":" + this.problem.question() + ":" + this.problem.answer() + ":" + this.problem.getAnswerContent();
		String responseMessage = null;
		try {
			String requestTokens = "ModifyProblem:" + modifiedProblem;
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
		if (responseTokens[0].equals("ModifyProblem")) {
			if (!responseTokens[1].equals("Success")) {
				System.out.println("ModifyProblem:Fail");
			} else {
				System.out.println("  [Modify] Problem");
			}
		}
		
		String responseMessage2 = null;
		try {
			String requestTokens2 = "ModifyWorkbook:" + this.workBook.W_Num() + ":" + this.workBook.W_name();
			BufferedReader br = new BufferedReader(
					new InputStreamReader(this.socket.getInputStream(), StandardCharsets.UTF_8));
			PrintWriter pw = new PrintWriter(
					new OutputStreamWriter(this.socket.getOutputStream(), StandardCharsets.UTF_8));
			pw.println(requestTokens2);
			pw.flush();
			responseMessage2 = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] responseTokens2 = responseMessage2.split(":");
		if (responseTokens2[0].equals("ModifyWorkbook")) {
			if (!responseTokens2[1].equals("Success")) {
				System.out.println("ModifyWorkbook:Fail");
			} else {
				System.out.println("  [Modify] Workbook Name");
			}
		}
		Alert alert = new Alert(AlertType.INFORMATION,"수정되었습니다");
		alert.show();
	}

	public void btn_Cancel_Action() {
		Alert alert = new Alert(AlertType.WARNING, "해당 문제를 수정하시겠습니까?", ButtonType.YES, ButtonType.NO);
		Optional<ButtonType> result = alert.showAndWait();

		if (result.equals(ButtonType.YES))
			btn_SaveWorkBook_Action();

		try {
			Stage primaryStage = (Stage) btn_Cancel.getScene().getWindow();
			Parent main = FXMLLoader.load(getClass().getResource("/gui/WorkBookList.fxml"));
			Scene scene = new Scene(main);
			primaryStage.setTitle("GuessWhat/WorkBook");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void changeProblem() {
		int index = ProfessorDataModel.currentPB;
		ProfessorDataModel.problem = problemList[index];
		if (ProfessorDataModel.problem.getType().equals(ProblemType.MultipleChoice)) {
			try {
				Stage primaryStage = (Stage) stage.getScene().getWindow();
				Parent main = FXMLLoader.load(getClass().getResource("/gui/WorkBook_MultipleChoice.fxml"));
				Scene scene = new Scene(main);
				primaryStage.setTitle("GuessWhat/WorkBook");
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (ProfessorDataModel.problem.getType().equals(ProblemType.ShortAnswer)) {
			try {
				Stage primaryStage = (Stage) stage.getScene().getWindow();
				Parent main = FXMLLoader.load(getClass().getResource("/gui/WorkBook_ShortAnswer.fxml"));
				Scene scene = new Scene(main);
				primaryStage.setTitle("GuessWhat/WorkBook");
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			initialize(null, null);
		}
	}

	public void btn_num1_Action() {
		savePro();
		ProfessorDataModel.currentPB = 0;
		changeProblem();
	}

	public void btn_num2_Action() {
		savePro();
		ProfessorDataModel.currentPB = 1;
		changeProblem();
	}

	public void btn_num3_Action() {
		savePro();
		ProfessorDataModel.currentPB = 2;
		changeProblem();
	}

	public void btn_num4_Action() {
		savePro();
		ProfessorDataModel.currentPB = 3;
		changeProblem();
	}

	public void btn_num5_Action() {
		savePro();
		ProfessorDataModel.currentPB = 4;
		changeProblem();
	}

	public void btn_num6_Action() {
		savePro();
		ProfessorDataModel.currentPB = 5;
		changeProblem();
	}

	public void btn_num7_Action() {
		savePro();
		ProfessorDataModel.currentPB = 6;
		changeProblem();
	}

	public void btn_num8_Action() {
		savePro();
		ProfessorDataModel.currentPB = 7;
		changeProblem();
	}

	public void btn_num9_Action() {
		savePro();
		ProfessorDataModel.currentPB = 8;
		changeProblem();
	}

	public void btn_num10_Action() {
		savePro();
		ProfessorDataModel.currentPB = 9;
		changeProblem();
	}

	public void btn_num11_Action() {
		savePro();
		ProfessorDataModel.currentPB = 10;
		changeProblem();
	}

	public void btn_num12_Action() {
		savePro();
		ProfessorDataModel.currentPB = 11;
		changeProblem();
	}

	public void btn_num13_Action() {
		savePro();
		ProfessorDataModel.currentPB = 12;
		changeProblem();
	}

	public void btn_num14_Action() {
		savePro();
		ProfessorDataModel.currentPB = 13;
		changeProblem();
	}

	public void btn_num15_Action() {
		savePro();
		ProfessorDataModel.currentPB = 14;
		changeProblem();
	}

	public void btn_Logo_Action() {
		Alert alert = new Alert(AlertType.WARNING, "MainPage로 이동하시겠습니까? 진행중이던 작업이 날아갈 수 있습니다.", ButtonType.YES,
				ButtonType.NO);
		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == ButtonType.YES) {
			try {
				Stage primaryStage = (Stage) btn_Logo.getScene().getWindow();
				Parent main = FXMLLoader.load(getClass().getResource("/gui/MainPage.fxml"));
				Scene scene = new Scene(main);
				primaryStage.setTitle("GuessWhat/MainPage");
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void btn_MyInfo_Action() {
		Alert alert = new Alert(AlertType.WARNING, "MyInfo로 이동하시겠습니까? 진행중이던 작업이 날아갈 수 있습니다.", ButtonType.YES,
				ButtonType.NO);
		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == ButtonType.YES) {
			try {
				Stage primaryStage = (Stage) btn_MyInfo.getScene().getWindow();
				Parent main = FXMLLoader.load(getClass().getResource("/gui/MyInfo.fxml"));
				Scene scene = new Scene(main);
				primaryStage.setTitle("GuessWhat/MyInfo");
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}