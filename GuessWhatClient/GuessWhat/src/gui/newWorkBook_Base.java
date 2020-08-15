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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.ProfessorDataModel;

public class newWorkBook_Base {

	@FXML
	public HBox stage;
	@FXML
	public Button btn_DeleteWorkBook, btn_CreateProblem, btn_SaveWorkBook, btn_num1, btn_num2, btn_num3, btn_num4,
			btn_num5, btn_num6, btn_num7, btn_num8, btn_num9, btn_num10, btn_num11, btn_num12, btn_num13, btn_num14,
			btn_num15, btn_Logo, btn_MyInfo;
	@FXML
	public RadioButton rbtn_MultipleChoice, rbtn_ShortAnswer, rbtn_Subjective;
	@FXML
	public TextArea ta_Question;

	public TextField tf_ChangeName;
	public Socket socket;
	public Workbook workBook;
	public Problem[] problemList;
	public Problem problem;
	public Button[] btn;
	public int PB_num;
	public int workBookSize;
	public boolean empty;

	public String tokenProblemList(Problem[] problem, int wnum) {
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
		boolean emptyWorkbook = false;
		if (workBookSize < 1) {
			emptyWorkbook = true;
		}
		if (!canMakeWB || emptyWorkbook) {
			System.out.println("workbook 저장 못함");
			Alert alert = new Alert(AlertType.INFORMATION, "만들어진 문제가 없습니다.");
			alert.setTitle("Check your problem");
			alert.setHeaderText("Empty Workbook!");
			alert.showAndWait();
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

	public boolean IsNotEmpty() {
		// 문제가 비어있는 지 확인
		return false;
	}

	public boolean savePro() {
		// 저장
		return false;
	}

	public boolean isValueChange() {
		// 원래 문제와 차이가 있는 문제인지 확인
		return false;
	}

	public void initialize(URL arg0, ResourceBundle arg1) {
	};

	public boolean btn_CreateProblem_Action() {

		this.changeName();

		boolean save = savePro();
		if (save && (PB_num < workBookSize)) { // 문제 수정 isValueChange == true
			new Alert(AlertType.CONFIRMATION, "Problem 수정.", ButtonType.CLOSE).showAndWait();
			return false;
		} else if (!save && (PB_num < workBookSize)) {
			if (empty) // 제대로 수정 안함 isValueChange == true
				return false;
			else {
				new Alert(AlertType.CONFIRMATION, "수정사항이 없습니다.", ButtonType.CLOSE).showAndWait();
				return true;
			}
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
			return true;
		}
		return true;
	}

	public boolean numberBtnSave() {
		this.changeName();

		if (PB_num == workBookSize) { // 새로운 문제 저장
			if (IsNotEmpty()) {
				Alert alert = new Alert(AlertType.WARNING, "해당 작업을 중단하시겠습니까?", ButtonType.YES, ButtonType.NO);
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.YES)
					return true;
				else if (result.get() == ButtonType.NO)
					return false;
			} else
				return true;
		}

		boolean save = savePro();
		if (save && (PB_num < workBookSize)) { // 문제 수정 isValueChange == true
			new Alert(AlertType.CONFIRMATION, "Problem 수정.", ButtonType.CLOSE).showAndWait();
			return false;
		} else if (!save && (PB_num < workBookSize)) {
			if (empty) // 제대로 수정 안함 isValueChange == true
				return false;
			else // 수정안하고 번호 이동 isValueChange == false
				return true;
		}
		return true;

	}

	public void changeProblem() {
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
			if (ProfessorDataModel.problem.getType().equals(ProblemType.ShortAnswer)) {
				try {
					Stage primaryStage = (Stage) stage.getScene().getWindow();
					Parent main = FXMLLoader.load(getClass().getResource("/gui/NewWorkBook_ShortAnswer.fxml"));
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
			} else if (ProfessorDataModel.problem.getType().equals(ProblemType.MultipleChoice)) {
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
			}
		}
	}

	public void changeName() {
		String name = tf_ChangeName.getText();
		if (!name.equals("") || !name.equals(ProfessorDataModel.workbook.W_name()))
			ProfessorDataModel.workbook.setName(name);
	}

	public void btn_num1_Action() {
		if (!numberBtnSave())
			return;
		ProfessorDataModel.currentPB = 0;
		changeProblem();
	}

	public void btn_num2_Action() {
		if (!numberBtnSave())
			return;
		ProfessorDataModel.currentPB = 1;
		changeProblem();

	}

	public void btn_num3_Action() {
		if (!numberBtnSave())
			return;
		ProfessorDataModel.currentPB = 2;
		changeProblem();
	}

	public void btn_num4_Action() {
		if (!numberBtnSave())
			return;
		ProfessorDataModel.currentPB = 3;
		changeProblem();
	}

	public void btn_num5_Action() {
		if (!numberBtnSave())
			return;
		ProfessorDataModel.currentPB = 4;
		changeProblem();
	}

	public void btn_num6_Action() {
		if (!numberBtnSave())
			return;
		ProfessorDataModel.currentPB = 5;
		changeProblem();
	}

	public void btn_num7_Action() {
		if (!numberBtnSave())
			return;
		ProfessorDataModel.currentPB = 6;
		changeProblem();
	}

	public void btn_num8_Action() {
		if (!numberBtnSave())
			return;
		ProfessorDataModel.currentPB = 7;
		changeProblem();
	}

	public void btn_num9_Action() {
		if (!numberBtnSave())
			return;
		ProfessorDataModel.currentPB = 8;
		changeProblem();
	}

	public void btn_num10_Action() {
		if (!numberBtnSave())
			return;
		ProfessorDataModel.currentPB = 9;
		changeProblem();
	}

	public void btn_num11_Action() {
		if (!numberBtnSave())
			return;
		ProfessorDataModel.currentPB = 10;
		changeProblem();
	}

	public void btn_num12_Action() {
		if (!numberBtnSave())
			return;
		ProfessorDataModel.currentPB = 11;
		changeProblem();
	}

	public void btn_num13_Action() {
		if (!numberBtnSave())
			return;
		ProfessorDataModel.currentPB = 12;
		changeProblem();
	}

	public void btn_num14_Action() {
		if (!numberBtnSave())
			return;
		ProfessorDataModel.currentPB = 13;
		changeProblem();
	}

	public void btn_num15_Action() {
		if (!numberBtnSave())
			return;
		ProfessorDataModel.currentPB = 14;
		changeProblem();
	}

	public void btn_Logo_Action() {
		Alert alert = new Alert(AlertType.WARNING, "MainPage로 이동하시겠습니까? 진행중이던 작업이 날아갈 수 있습니다.", ButtonType.YES,
				ButtonType.NO);
		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == ButtonType.YES) {

			ProfessorDataModel.workbook = null;
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
