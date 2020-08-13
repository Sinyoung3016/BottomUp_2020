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
import user.Student;

public class NewWorkBook_ShortAnswerController implements Initializable {
	@FXML
	private HBox stage;
	@FXML
	private Button btn_DeleteWorkBook, btn_CreateProblem, btn_SaveWorkBook, btn_num1, btn_num2, btn_num3, btn_num4,
			btn_num5, btn_num6, btn_num7, btn_num8, btn_num9, btn_num10, btn_num11, btn_num12, btn_num13, btn_num14,
			btn_num15, btn_Logo, btn_MyInfo;
	@FXML
	private TextField tf_Answer, tf_ChangeName;
	@FXML
	private RadioButton rbtn_MultipleChoice, rbtn_ShortAnswer, rbtn_Subjective;
	@FXML
	private TextArea ta_Question;

	private Socket socket;
	private Workbook workBook;
	private Problem[] problemList;
	private Problem problem;
	private Button[] btn;
	private int PB_num;
	private int workBookSize;

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
		if (ProfessorDataModel.problemList[PB_num] != null) {
			ta_Question.setText(problem.question());
			tf_Answer.setText(problem.answer());
		} else {
			ta_Question.setText("");
			tf_Answer.setText("");
		}

		tf_ChangeName.setText(workBook.W_name());

		btn = new Button[] { btn_num1, btn_num2, btn_num3, btn_num4, btn_num5, btn_num6, btn_num7, btn_num8, btn_num9,
				btn_num10, btn_num11, btn_num12, btn_num13, btn_num14, btn_num15 };

		if (PB_num == workBookSize) {
			for (int i = 0; i < workBookSize; i++) {
				btn[i].setStyle("-fx-background-color: #5ad18f;");
				btn[i].setDisable(false);
			}
			for (int i = workBookSize; i < 15; i++) {
				btn[i].setStyle("-fx-background-color: #f0fff0;");
				btn[i].setDisable(true);
			}
			btn[PB_num].setStyle("-fx-background-color: #22941C;");
			btn[PB_num].setDisable(false);
		} else {
			for (int i = 0; i < workBookSize + 1; i++) {
				btn[i].setStyle("-fx-background-color: #5ad18f;");
				btn[i].setDisable(false);
			}
			for (int i = workBookSize + 1; i < 15; i++) {
				btn[i].setStyle("-fx-background-color: #f0fff0;");
				btn[i].setDisable(true);
			}
			btn[PB_num].setStyle("-fx-background-color: #22941C;");
			btn[PB_num].setDisable(false);
		
		}
		// setting

		// radiobtn
		ToggleGroup group = new ToggleGroup();
		rbtn_MultipleChoice.setToggleGroup(group);
		rbtn_ShortAnswer.setToggleGroup(group);
		rbtn_Subjective.setToggleGroup(group);

		rbtn_MultipleChoice.setSelected(false);
		rbtn_ShortAnswer.setSelected(true);
		rbtn_Subjective.setSelected(false);

		rbtn_MultipleChoice.setOnAction((ActionEvent) -> {
			try {
				Stage primaryStage = (Stage) rbtn_MultipleChoice.getScene().getWindow();
				Parent main = FXMLLoader.load(getClass().getResource("/gui/NewWorkBook_MultipleChoice.fxml"));
				Scene scene = new Scene(main);
				primaryStage.setTitle("GuessWhat/WorkBook");
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		rbtn_Subjective.setOnAction((ActionEvent) -> {
			try {
				Stage primaryStage = (Stage) rbtn_Subjective.getScene().getWindow();
				Parent main = FXMLLoader.load(getClass().getResource("/gui/NewWorkBook_Subjective.fxml"));
				Scene scene = new Scene(main);
				primaryStage.setTitle("GuessWhat/WorkBook");
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		// radiobtn
	}

	private void changeName() {
		String name = tf_ChangeName.getText();
		if (!name.equals(null) || !name.equals(""))
			ProfessorDataModel.workbook.setName(name);
	}

	private boolean savePro() {

		this.changeName();

		String S_question = ta_Question.getText();
		String S_answer = tf_Answer.getText();
		if ((S_question.equals(null) || S_question.equals("")) && (S_answer.equals(null) || S_answer.equals(""))) {
			return false;
		} else if (S_question.equals(null) || S_question.equals("")) {
			new Alert(AlertType.WARNING, "문제를 입력해주세요.", ButtonType.CLOSE).showAndWait();
			return false;
		} else if (S_answer.equals(null) || S_answer.equals("")) {
			new Alert(AlertType.WARNING, "정답을 입력해주세요.", ButtonType.CLOSE).showAndWait();
			return false;
		} else {

			ProfessorDataModel.problem.setPB_num(PB_num);
			ProfessorDataModel.problem.setAnswer(S_answer);
			ProfessorDataModel.problem.setQuestion(S_question);
			ProfessorDataModel.problem.setType(ProblemType.ShortAnswer);

			ProfessorDataModel.problemList[PB_num] = ProfessorDataModel.problem;
			return true;
		}
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

			ProfessorDataModel.workbook = null;
			ProfessorDataModel.problemList = null;
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

		boolean canMakeWB = true;
		for (int i = 0; i < workBookSize; i++) {
			if (problemList[i] == null)
				canMakeWB = false;
		}

		if (!canMakeWB)
			System.out.println("workbook 저장 못함");
		else {

			// problemList db에 저장
			// workbook db에 저장

			this.workBook.setP_Num(ProfessorDataModel.professor.P_Num());
			String responseMessage = null;
			try {
				// AddWorkbook:PNum:Name:Size
				String requestTokens = "AddWorkbook:" + this.workBook.tokenString() + ":" + this.workBookSize;
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
			if (responseTokens[0].equals("AddWorkbook")) {
				if (!responseTokens[1].equals("Success")) {
					System.out.println("AddWorkbook:Fail");
				} else {
					this.workBook.setW_Num(Integer.parseInt(responseTokens[2]));
					try {
						// AddProblem:problem1_problem2(WNum`question`answer`type`answerContents)...
						String requestMessage = "AddProblem:"
								+ this.tokenProblemList(this.problemList, this.workBook.W_Num());
						BufferedReader br = new BufferedReader(
								new InputStreamReader(this.socket.getInputStream(), StandardCharsets.UTF_8));
						PrintWriter pw = new PrintWriter(
								new OutputStreamWriter(this.socket.getOutputStream(), StandardCharsets.UTF_8));
						pw.println(requestMessage);
						pw.flush();
						responseMessage = br.readLine();
					} catch (IOException e) {
						e.printStackTrace();
					}
					responseTokens = responseMessage.split(":");
					if (responseTokens[0].equals("AddProblem")) {
						if (!responseTokens[1].equals("Success"))
							System.out.println("AddProblem:Fail");
					}
					ProfessorDataModel.currentPB = 0;
				}
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
	}

	public void btn_CreateProblem_Action() {
		boolean save = savePro();

		if (save && (PB_num < workBookSize)) { // 문제 수정
			new Alert(AlertType.CONFIRMATION, "Problem 수정.", ButtonType.CLOSE).showAndWait();

			if (15 == PB_num + 1) {
				return;
			} else {
				StudentDataModel.currentPB = StudentDataModel.currentPB + 1;
				changeProblem();
			}

		} else if (!save && (PB_num < workBookSize)) {
			new Alert(AlertType.WARNING, "해당 문제를 작성해주세요.", ButtonType.CLOSE).showAndWait();
			return;
		} else if (save && (PB_num == workBookSize)) { // 새로운 문제 저장
			new Alert(AlertType.CONFIRMATION, "Problem 저장.", ButtonType.CLOSE).showAndWait();

			ProfessorDataModel.workbook.setSize(workBookSize + 1);
			ProfessorDataModel.currentPB = PB_num + 1;
			ProfessorDataModel.problem = new Problem(ProfessorDataModel.currentPB);

			try {
				Stage primaryStage = (Stage) btn_CreateProblem.getScene().getWindow();
				Parent main = FXMLLoader.load(getClass().getResource("/gui/NewWorkBook_MultipleChoice.fxml"));
				Scene scene = new Scene(main);
				primaryStage.setTitle("GuessWhat/WorkBook");
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private void changeProblem() {
		int index = ProfessorDataModel.currentPB;
		if (index == workBookSize) {
			ProfessorDataModel.problem = new Problem(index);
			try {
				Stage primaryStage = (Stage) stage.getScene().getWindow();
				Parent main = FXMLLoader.load(getClass().getResource("/gui/NewWorkBook_MultipleChoice.fxml"));
				Scene scene = new Scene(main);
				primaryStage.setTitle("GuessWhat/WorkBook");
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			ProfessorDataModel.problem = problemList[index];

			if (ProfessorDataModel.problem.getType().equals(ProblemType.MultipleChoice)) {
				try {
					Stage primaryStage = (Stage) stage.getScene().getWindow();
					Parent main = FXMLLoader.load(getClass().getResource("/gui/NewWorkBook_MultipleChoice.fxml"));
					Scene scene = new Scene(main);
					primaryStage.setTitle("GuessWhat/WorkBook");
					primaryStage.setScene(scene);
					primaryStage.show();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (ProfessorDataModel.problem.getType().equals(ProblemType.Subjective)) {
				try {
					Stage primaryStage = (Stage) stage.getScene().getWindow();
					Parent main = FXMLLoader.load(getClass().getResource("/gui/NewWorkBook_Subjective.fxml"));
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

			ProfessorDataModel.workbook = null;
			ProfessorDataModel.problemList = null;
			ProfessorDataModel.problem = null;

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

			ProfessorDataModel.workbook = null;
			ProfessorDataModel.problemList = null;
			ProfessorDataModel.problem = null;

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
