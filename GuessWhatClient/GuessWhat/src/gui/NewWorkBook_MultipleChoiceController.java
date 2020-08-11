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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.ProfessorDataModel;
import model.StudentDataModel;

public class NewWorkBook_MultipleChoiceController extends BaseController implements Initializable {

	@FXML
	private Button btn_DeleteWorkBook, btn_CreateProblem, btn_SaveWorkBook, btn_num1, btn_num2, btn_num3, btn_num4,
			btn_num5, btn_num6, btn_num7, btn_num8, btn_num9, btn_num10, btn_num11, btn_num12, btn_num13, btn_num14,
			btn_num15;
	@FXML
	private TextField tf_Answer1, tf_Answer2, tf_Answer3, tf_Answer4, tf_Answer5, tf_ChangeName;
	@FXML
	private RadioButton rbtn_MultipleChoice, rbtn_ShortAnswer, rbtn_Subjective;
	@FXML
	private CheckBox cb_1, cb_2, cb_3, cb_4, cb_5;
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
		this.workBookSize = this.workBook.WorkBooksize();
		this.PB_num = ProfessorDataModel.currentPB;

		// setting
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

		if (hasQValue[PB_num])
			ta_Question.setText(problem.question());
		else
			ta_Question.setText("");

		if (hasAValue[PB_num]) {
			String[] answerContent = problem.getAnswerContent().split("_");
			tf_Answer1.setText(answerContent[0]);
			tf_Answer2.setText(answerContent[1]);
			tf_Answer3.setText(answerContent[2]);
			tf_Answer4.setText(answerContent[3]);
			tf_Answer5.setText(answerContent[4]);

			String answer = problem.answer();
			for (int i = 0; i < answer.length(); i++) {
				char num = answer.charAt(i);
				if (num == '1')
					cb_1.setSelected(true);
				if (num == '2')
					cb_2.setSelected(true);
				if (num == '3')
					cb_3.setSelected(true);
				if (num == '4')
					cb_4.setSelected(true);
				if (num == '5')
					cb_5.setSelected(true);
			}
		} else {

			tf_Answer1.setText("");
			tf_Answer2.setText("");
			tf_Answer3.setText("");
			tf_Answer4.setText("");
			tf_Answer5.setText("");

			cb_1.setSelected(false);
			cb_2.setSelected(false);
			cb_3.setSelected(false);
			cb_4.setSelected(false);
			cb_5.setSelected(false);
		}

		tf_ChangeName.setText(workBook.W_name());

		btn = new Button[] { btn_num1, btn_num2, btn_num3, btn_num4, btn_num5, btn_num6, btn_num7, btn_num8, btn_num9,
				btn_num10, btn_num11, btn_num12, btn_num13, btn_num14, btn_num15 };

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
		// setting

		// radiobtn
		ToggleGroup group = new ToggleGroup();
		rbtn_MultipleChoice.setToggleGroup(group);
		rbtn_ShortAnswer.setToggleGroup(group);
		rbtn_Subjective.setToggleGroup(group);

		rbtn_MultipleChoice.setSelected(true);
		rbtn_ShortAnswer.setSelected(false);
		rbtn_Subjective.setSelected(false);

		rbtn_ShortAnswer.setOnAction((ActionEvent) -> {
			try {
				Stage primaryStage = (Stage) rbtn_ShortAnswer.getScene().getWindow();
				Parent main = FXMLLoader.load(getClass().getResource("/gui/NewWorkBook_ShortAnswer.fxml"));
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
				primaryStage.setTitle("GuessWhat/WorkBookList");
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

		String answerContent = tf_Answer1.getText() + "_" + tf_Answer2.getText() + "_" + tf_Answer3.getText() + "_"
				+ tf_Answer4.getText() + "_" + tf_Answer5.getText();
		String answer = new String();
		if (cb_1.isSelected())
			answer = answer + "1";
		if (cb_2.isSelected())
			answer = answer + "2";
		if (cb_3.isSelected())
			answer = answer + "3";
		if (cb_4.isSelected())
			answer = answer + "4";
		if (cb_5.isSelected())
			answer = answer + "5";

		this.changeName();

		String S_question = ta_Question.getText();
		String S_answer = answerContent;
		String S_answerContent = answer; // content가 있으면 answer도 저장
		if (S_question.equals("") && S_answerContent.equals("")) {
			hasQValue[PB_num] = false;
			hasAValue[PB_num] = false;
		} else if ((S_question.equals(null) || S_question.equals(""))
				&& (!S_answerContent.equals(null) || !S_answerContent.equals(""))) {
			if (!S_answer.equals(null) || !S_answer.equals(""))
				problem.setAnswer(S_answer);
			else {
				new Alert(AlertType.WARNING, "답을 체크해주세요.", ButtonType.CLOSE);
				return;
			}
			problem.setAnswer(S_answer);
			problem.setAnswerContent(answerContent);
			hasQValue[PB_num] = false;
			hasAValue[PB_num] = true;
		} else if ((!S_question.equals(null) || !S_question.equals(""))
				&& (S_answerContent.equals(null) || S_answerContent.equals(""))) {
			problem.setQuestion(S_question);
			hasQValue[PB_num] = true;
			hasAValue[PB_num] = false;
		} else {
			if (!S_answer.equals(null) || !S_answer.equals(""))
				problem.setAnswer(S_answer);
			else {
				new Alert(AlertType.WARNING, "답을 체크해주세요.", ButtonType.CLOSE);
				return;
			}
			problem.setAnswer(S_answer);
			problem.setQuestion(S_question);
			problem.setAnswerContent(answerContent);
			hasAValue[PB_num] = true;
			hasQValue[PB_num] = true;
		}

		problem.setPB_num(PB_num);
		problem.setType(ProblemType.MultipleChoice);

		problemList[PB_num] = problem;

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
	}

	public void btn_SaveWorkBook_Action() {

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
				Stage primaryStage = (Stage) btn_CreateProblem.getScene().getWindow();
				Parent main = FXMLLoader.load(getClass().getResource("/gui/NewWorkBook_ShortAnswer.fxml"));
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
