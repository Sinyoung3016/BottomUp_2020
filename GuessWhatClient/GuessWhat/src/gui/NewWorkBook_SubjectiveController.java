package gui;

import java.net.URL;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.ProfessorDataModel;

public class NewWorkBook_SubjectiveController extends BaseController implements Initializable {

	@FXML
	private Button btn_DeleteWorkBook, btn_CreateProblem, btn_SaveWorkBook, btn_num1, btn_num2, btn_num3, btn_num4, btn_num5,
			btn_num6, btn_num7, btn_num8, btn_num9, btn_num10, btn_num11, btn_num12, btn_num13, btn_num14, btn_num15,
			btn_num16, btn_num17, btn_num18, btn_num19, btn_num20;
	@FXML
	private RadioButton rbtn_MultipleChoice, rbtn_ShortAnswer, rbtn_Subjective;
	@FXML
	private TextField tf_ChangeName;
	@FXML
	private TextArea ta_Question, ta_Answer;
	@FXML
	private Pane Pane_Answer;

	private Workbook workbook;

	private Button[] btn;

	private int WorkBookSize;

	private int inputNum;
	private boolean isNewProblem = true;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		// setting
		// 이전에 서버로 번호 보내고 번호 받아오기
		inputNum = 1;
		if (inputNum <= WorkBookSize)
			isNewProblem = false;

		if (!isNewProblem) {

			// workbook의 input번호를 가진 problem 가지고 오기
			Problem problem = new Problem(1, 1, 4, ProblemType.Subjective, "질문", "답");
			
			//아래 if문 안에 다시 inputNum 서버로 보내기
			if (problem.getType().equals(ProblemType.ShortAnswer)) {
				try {
					Stage primaryStage = (Stage) btn_Main.getScene().getWindow();
					Parent main = FXMLLoader.load(getClass().getResource("/gui/NewWorkBook_ShortAnswer.fxml"));
					Scene scene = new Scene(main);
					primaryStage.setTitle("GuessWhat/WorkBook");
					primaryStage.setScene(scene);
					primaryStage.show();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (problem.getType().equals(ProblemType.MultipleChoice)) {
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

			ta_Question.setText(problem.question());
			ta_Answer.setText(problem.answer());
			tf_ChangeName.setText(workbook.W_name());
		}

		// setting

		// data
		btn = new Button[] { btn_num1, btn_num2, btn_num3, btn_num4, btn_num5, btn_num6, btn_num7, btn_num8, btn_num9,
				btn_num10, btn_num11, btn_num12, btn_num13, btn_num14, btn_num15, btn_num16, btn_num17, btn_num18,
				btn_num19, btn_num20 };

		WorkBookSize = workbook.WorkBooksize();

		for (int i = 0; i < WorkBookSize; i++) {
			btn[i].setStyle("-fx-background-color: #5ad18f;");
			btn[i].setDisable(false);
		}
		btn[WorkBookSize].setStyle("-fx-background-color: #54bd54;");
		for (int i = WorkBookSize + 1; i < 20; i++) {
			btn[i].setStyle("-fx-background-color: #f0fff0;");
			btn[i].setDisable(true);
		}

		// data

		// radiobtn
		ToggleGroup group = new ToggleGroup();
		rbtn_MultipleChoice.setToggleGroup(group);
		rbtn_ShortAnswer.setToggleGroup(group);
		rbtn_Subjective.setToggleGroup(group);

		rbtn_MultipleChoice.setSelected(false);
		rbtn_ShortAnswer.setSelected(false);
		rbtn_Subjective.setSelected(true);

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
		// radiobtn

	}
	
	private boolean changeName() {
		String name = tf_ChangeName.getText();
		if(!name.equals(null)) {
			workbook.setName(name);
			return true;
		}
		return false;
	}

	public void btn_DeleteWorkBook_Action() {
		
		//워크북 지우기
		
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
		
		this.changeName();
			
		try {
			Stage primaryStage = (Stage) btn_SaveWorkBook.getScene().getWindow();
			Parent main = FXMLLoader.load(getClass().getResource("/gui/WorkBookList.fxml"));
			Scene scene = new Scene(main);
			primaryStage.setTitle("GuessWhat/WorkBookList");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void btn_CreateProblem_Action() {

		int P_num = workbook.P_Num();
		int W_num = workbook.W_Num();
		int PB_num = workbook.WorkBooksize();
		ProblemType type = ProblemType.ShortAnswer;
		String question = ta_Question.getText();
		String answer = ta_Answer.getText();

		Problem newProblem = new Problem(P_num, W_num, PB_num, type, question, answer);
		
		//if(isNewProblem) //새로운 저장장소 만들기
				//Problem newProblem = new Problem(P_num, W_num, PB_num, type, question, answer);
			//else 원래의 문제의 디비를 가지고 와서 수정하기

	}

	public void btn_num1_Action() {
		// 서버에 번호 보내기
	}

	public void btn_num2_Action() {

	}

	public void btn_num3_Action() {

	}

	public void btn_num4_Action() {

	}

	public void btn_num5_Action() {

	}

	public void btn_num6_Action() {

	}

	public void btn_num7_Action() {

	}

	public void btn_num8_Action() {

	}

	public void btn_num9_Action() {

	}

	public void btn_num10_Action() {

	}

	public void btn_num11_Action() {

	}

	public void btn_num12_Action() {

	}

	public void btn_num13_Action() {

	}

	public void btn_num14_Action() {

	}

	public void btn_num15_Action() {

	}

	public void btn_num16_Action() {

	}

	public void btn_num17_Action() {

	}

	public void btn_num18_Action() {

	}

	public void btn_num19_Action() {

	}

	public void btn_num20_Action() {

	}

}
