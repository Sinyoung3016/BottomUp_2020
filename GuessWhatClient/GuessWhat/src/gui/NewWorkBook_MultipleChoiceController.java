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
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.ProfessorDataModel;

public class NewWorkBook_MultipleChoiceController extends newWorkBook_Base implements Initializable {

	@FXML
	private TextField tf_Answer1, tf_Answer2, tf_Answer3, tf_Answer4, tf_Answer5;
	@FXML
	private CheckBox cb_1, cb_2, cb_3, cb_4, cb_5;

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

			if (problem.getType().equals(ProblemType.MultipleChoice)) {

				String[] answerContent = problem.getAnswerContent().split("~");//수정필요할듯 ㅇㅇ
				if (answerContent[0].equals(" "))
					tf_Answer1.setText("");
				else
					tf_Answer1.setText(answerContent[0]);
				if (answerContent[1].equals(" "))
					tf_Answer2.setText("");
				else
					tf_Answer2.setText(answerContent[1]);
				if (answerContent[2].equals(" "))
					tf_Answer3.setText("");
				else
					tf_Answer3.setText(answerContent[2]);
				if (answerContent[3].equals(" "))
					tf_Answer4.setText("");
				else
					tf_Answer4.setText(answerContent[3]);
				if (answerContent[4].equals(" "))
					tf_Answer5.setText("");
				else
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

		} else {
			ta_Question.setText("");

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

	private String S_answerContent;
	private String[] answerContent;
	private String S_question;
	private String S_answer;

	@Override
	public boolean isValueChange() {
		if (problem.getType().equals(ProblemType.MultipleChoice) && problem.question().equals(ta_Question.getText())
				&& problem.answer().equals(S_answer) && problem.getAnswerContent().equals(S_answerContent)) {
			return false;
		} else if (!problem.getType().equals(ProblemType.MultipleChoice)
				|| !problem.question().equals(ta_Question.getText()) || !problem.answer().equals(S_answer)
				|| !problem.getAnswerContent().equals(S_answerContent)) {
			Alert alert = new Alert(AlertType.WARNING, "문제를 수정하시겠습니까?", ButtonType.YES, ButtonType.NO);
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.YES)
				return true;
		}
		return false;
	}

	@Override
	public boolean IsNotEmpty() {

		S_question = ta_Question.getText();
		S_answerContent = "";
		answerContent = new String[] { tf_Answer1.getText(), tf_Answer2.getText(), tf_Answer3.getText(),
				tf_Answer4.getText(), tf_Answer5.getText() };
		for (int i = 0; i < 5; i++) {
			if (!answerContent[i].equals(""))
				S_answerContent += answerContent[i];
			else
				S_answerContent += " ";

			if (i != 4)
				S_answerContent += "~";
		}

		S_answer = new String();
		if (cb_1.isSelected())
			S_answer = S_answer + "1";
		if (cb_2.isSelected())
			S_answer = S_answer + "2";
		if (cb_3.isSelected())
			S_answer = S_answer + "3";
		if (cb_4.isSelected())
			S_answer = S_answer + "4";
		if (cb_5.isSelected())
			S_answer = S_answer + "5";

		if (S_question.equals("") && S_answer.equals("") && S_answerContent.equals(" ~ ~ ~ ~ ")) {
			empty = false;
			return false;
		} else if (S_question.equals("") || S_answer.equals("") || S_answerContent.equals(" ~ ~ ~ ~ ")) {
			empty = true;
			new Alert(AlertType.WARNING, "문제를 입력해주세요.", ButtonType.CLOSE).showAndWait();
			return false;
		} else {
			empty = false;
			return true;
		}
	}

	public boolean savePro() {

		if (IsNotEmpty()) {

			if (PB_num < workBookSize) { // 수정
				if (!isValueChange())
					return false;
				else { // 변화가 있음

					ProfessorDataModel.problem.setType(ProblemType.MultipleChoice);
					ProfessorDataModel.problem.setAnswer(S_answer);
					ProfessorDataModel.problem.setQuestion(S_question);
					ProfessorDataModel.problem.setAnswerContent(S_answerContent);

					ProfessorDataModel.problemList[PB_num] = ProfessorDataModel.problem;

					return true;
				}
			}
			// PB_num == workBookSize 저장
			ProfessorDataModel.problem.setPB_num(PB_num);
			ProfessorDataModel.problem.setType(ProblemType.MultipleChoice);
			ProfessorDataModel.problem.setAnswer(S_answer);
			ProfessorDataModel.problem.setQuestion(S_question);
			ProfessorDataModel.problem.setAnswerContent(S_answerContent);

			ProfessorDataModel.problemList[PB_num] = ProfessorDataModel.problem;

			return true;
		}
		return false;
	}

}
