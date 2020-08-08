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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.ProfessorDataModel;
import model.StudentDataModel;
import user.Student;

public class NewWorkBook_ShortAnswerController extends BaseController implements Initializable {

	@FXML
	private Button btn_DeleteWorkBook, btn_CreateProblem, btn_SaveWorkBook, btn_num1, btn_num2, btn_num3, btn_num4,
			btn_num5, btn_num6, btn_num7, btn_num8, btn_num9, btn_num10, btn_num11, btn_num12, btn_num13, btn_num14,
			btn_num15;
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
	private boolean[] hasQValue;
	private boolean[] hasAValue;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		this.socket = ProfessorDataModel.socket;
		this.workBook = ProfessorDataModel.workbook;
		this.problemList = ProfessorDataModel.problemList;
		this.problem = ProfessorDataModel.problem;
		this.hasQValue = ProfessorDataModel.hasQValue;
		this.hasAValue = ProfessorDataModel.hasAValue;

		this.workBookSize = workBook.WorkBooksize();
		this.PB_num = ProfessorDataModel.currentPB;

		// setting
		if (hasQValue[PB_num] || hasAValue[PB_num]) {
			if (problem.getType().equals(ProblemType.MultipleChoice)) {
				try {
					Stage primaryStage = (Stage) btn_Main.getScene().getWindow();
					Parent main = FXMLLoader.load(getClass().getResource("/gui/NewWorkBook_MultipleChoice.fxml"));
					Scene scene = new Scene(main);
					primaryStage.setTitle("GuessWhat/WorkBook");
					primaryStage.setScene(scene);
					primaryStage.show();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (problem.getType().equals(ProblemType.Subjective)) {
				try {
					Stage primaryStage = (Stage) btn_Main.getScene().getWindow();
					Parent main = FXMLLoader.load(getClass().getResource("/gui/NewWorkBook_Subjective.fxml"));
					Scene scene = new Scene(main);
					primaryStage.setTitle("GuessWhat/WorkBook");
					primaryStage.setScene(scene);
					primaryStage.show();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			ta_Question.setText(problem.question());
			tf_Answer.setText(problem.answer());
			tf_ChangeName.setText(workBook.W_name());
		} else {
			ta_Question.setText("");
			tf_Answer.setText("");
			tf_ChangeName.setText("NewWorkBook");
		}

		btn = new Button[] { btn_num1, btn_num2, btn_num3, btn_num4, btn_num5, btn_num6, btn_num7, btn_num8, btn_num9,
				btn_num10, btn_num11, btn_num12, btn_num13, btn_num14, btn_num15 };

		for (int i = 0; i < workBookSize; i++) {
			btn[i].setStyle("-fx-background-color: #5ad18f;");
			btn[i].setDisable(false);
		}
		btn[PB_num].setStyle("-fx-background-color: #22941C;");
		for (int i = workBookSize; i < 15; i++) {
			btn[i].setStyle("-fx-background-color: #dcdcdc;");
			btn[i].setDisable(true);
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
			workBook.setName(name);
		else
			workBook.setName("NewWorkBook");
	}

	private void savePro() {

		this.changeName();

		String S_question = ta_Question.getText();
		String S_answer = tf_Answer.getText();
		if (S_question.equals("") && S_answer.equals("")) {
			hasQValue[PB_num] = false;
			hasAValue[PB_num] = false;
		} else if ((S_question.equals(null) || S_question.equals(""))
				&& (!S_answer.equals(null) || !S_answer.equals(""))) {
			problem.setAnswer(S_answer);
			hasQValue[PB_num] = false;
			hasAValue[PB_num] = true;
		} else if ((!S_question.equals(null) || !S_question.equals(""))
				&& (S_answer.equals(null) || S_answer.equals(""))) {
			problem.setQuestion(S_question);
			hasQValue[PB_num] = true;
			hasAValue[PB_num] = false;
		} else {
			problem.setAnswer(S_answer);
			problem.setQuestion(S_question);
			hasAValue[PB_num] = true;
			hasQValue[PB_num] = true;
		}

		problem.setPB_num(PB_num);
		problem.setType(ProblemType.ShortAnswer);

		problemList[PB_num] = problem;

	}

	public void btn_DeleteWorkBook_Action() {

		ProfessorDataModel.workbook = null;
		ProfessorDataModel.problem = null;
		ProfessorDataModel.hasAValue = null;
		ProfessorDataModel.hasQValue = null;

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

	public void btn_SaveWorkBook_Action() {

		this.savePro();

		boolean canMakeWB = true;
		for (int i = 0; i < workBookSize; i++) {
			if (problemList[i] == null)
				canMakeWB = false;
		}

		if (!canMakeWB)
			new Alert(AlertType.CONFIRMATION, "저장못함.", ButtonType.CLOSE).show();
		else {

			// problemList db에 저장
			// workbook db에 저장

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

	public void btn_CreateProblem_Action() {

		this.savePro();

		if (PB_num < workBookSize) {
			new Alert(AlertType.CONFIRMATION, "저장함.", ButtonType.CLOSE).show();

			if (15 == PB_num + 1) {
				return;
			} else {
				StudentDataModel.currentPB = StudentDataModel.currentPB + 1;
				changeProblem();
			}

		} else if (PB_num == workBookSize) {
			workBook.setSize(workBookSize + 1);
			ProfessorDataModel.currentPB = ProfessorDataModel.currentPB + 1;

			this.problem = new Problem(ProfessorDataModel.currentPB);
			try {
				Stage primaryStage = (Stage) btn_Main.getScene().getWindow();
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

		initialize(null, null);
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
}
