package gui;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import exam.ProblemType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.ProfessorDataModel;

public class NewWorkBook_ShortAnswerController extends newWorkBook_Base implements Initializable {

	@FXML
	private TextField tf_Answer;

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
			if (problem.getType().equals(ProblemType.ShortAnswer))
				tf_Answer.setText(problem.answer());
			else
				tf_Answer.setText("");
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

	@Override
	public boolean isValueChange() {
		if (problem.getType().equals(ProblemType.ShortAnswer) && problem.question().equals(ta_Question.getText())
				&& problem.answer().equals(tf_Answer.getText())) {
			return false; // 변화가 없는 문제
		}
		else if (!problem.getType().equals(ProblemType.ShortAnswer) || !problem.question().equals(ta_Question.getText())
				|| !problem.answer().equals(tf_Answer.getText())) {
			Alert alert = new Alert(AlertType.WARNING, "문제를 수정하시겠습니까?", ButtonType.YES, ButtonType.NO);
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == (ButtonType.YES))
				return true;
		}//button == false
		return false;
	}

	@Override
	public boolean IsNotEmpty() {

		String S_question = ta_Question.getText();
		String S_answer = tf_Answer.getText();
		if (S_question.equals("") && S_answer.equals("")) {
			empty = false;
			return false; //문제 안 만들어
		} else if (S_question.equals("") || S_answer.equals("")) {
			empty = true;
			new Alert(AlertType.WARNING, "문제를 입력해주세요.", ButtonType.CLOSE).showAndWait();
			return false;
		} else {
			empty = false;
			return true; //모든칸에 답이 있어용
		}
	}

	public boolean savePro() {

		if (IsNotEmpty()) { 

			if (PB_num < workBookSize) {
				if (!isValueChange()) //수정안함
					return false;
				else {
					String S_question = ta_Question.getText();
					String S_answer = tf_Answer.getText();

					ProfessorDataModel.problem.setAnswer(S_answer);
					ProfessorDataModel.problem.setQuestion(S_question);
					ProfessorDataModel.problem.setType(ProblemType.ShortAnswer);
					ProfessorDataModel.problem.setAnswerContent("0~0~0~0~0");

					ProfessorDataModel.problemList[PB_num] = ProfessorDataModel.problem;

					return true;
				}
			}
			// PB_num == workBookSize 저장
			String S_question = ta_Question.getText();
			String S_answer = tf_Answer.getText();

			ProfessorDataModel.problem.setPB_num(PB_num);
			ProfessorDataModel.problem.setAnswer(S_answer);
			ProfessorDataModel.problem.setQuestion(S_question);
			ProfessorDataModel.problem.setType(ProblemType.ShortAnswer);
			ProfessorDataModel.problem.setAnswerContent("0~0~0~0~0");

			ProfessorDataModel.problemList[PB_num] = ProfessorDataModel.problem;

			return true;
		}
		return false;
	}

}