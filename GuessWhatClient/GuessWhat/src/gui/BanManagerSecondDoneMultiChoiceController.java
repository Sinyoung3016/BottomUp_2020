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
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;

import exam.Problem;
import exam.ProblemType;
import exam.Workbook;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.HBoxModel;
import model.ProfessorDataModel;
import model.StudentDataModel;
import room.Ban;
import room.BanManager;
import user.Student;

public class BanManagerSecondDoneMultiChoiceController implements Initializable {

	@FXML
	private Button btn_Delete, btn_Close, btn_Previous, btn_Main, btn_Logo, btn_MyInfo;
	@FXML
	private Button btn_num1, btn_num2, btn_num3, btn_num4, btn_num5, btn_num6, btn_num7, btn_num8, btn_num9, btn_num10,
			btn_num11, btn_num12, btn_num13, btn_num14, btn_num15, btn_num16, btn_num17, btn_num18, btn_num19,
			btn_num20;
	@FXML
	private PieChart pc_Result;
	@FXML
	private Label lb_BanManagerName, lb_WorkBook, lb_answerNum;

	private ArrayList<StuNum> list;

	private ObservableList<Data> Pie;
	private Map<String, Student> ip_student;
	private Socket socket;
	private Ban ban;
	private BanManager banManager;
	private Workbook workbook;
	private String className;
	private int PB_num;
	private Problem[] problemList;
	private Button[] btn;
	private int WorkBookSize;
	private int StudentSize;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		this.socket = ProfessorDataModel.socket;
		this.problemList = ProfessorDataModel.problemList;
		this.ban = ProfessorDataModel.ban;
		this.banManager = ProfessorDataModel.banManager;
		this.workbook = ProfessorDataModel.workbook;
		this.ip_student = ProfessorDataModel.ip_student;

		this.btn_Main.setText(ban.ban_name());
		this.lb_BanManagerName.setText(banManager.BM_name());
		this.lb_WorkBook.setText(workbook.W_name());

		btn = new Button[] { btn_num1, btn_num2, btn_num3, btn_num4, btn_num5, btn_num6, btn_num7, btn_num8, btn_num9,
				btn_num10, btn_num11, btn_num12, btn_num13, btn_num14, btn_num15 };

		className = btn_Main.getText();

		for (int i = 0; i < WorkBookSize; i++) {
			btn[i].setStyle("-fx-background-color: #5ad18f;");
			btn[i].setDisable(false);
		}
		btn[PB_num].setStyle("-fx-background-color: #22941C;");
		for (int i = WorkBookSize; i < 15; i++) {
			btn[i].setStyle("-fx-background-color: #cdcdcd;");
			btn[i].setDisable(true);
		}

		changeNum();
		settingPie(pieValue());
	}

	private void changeNum() {
		this.PB_num = ProfessorDataModel.currentPB;

		list = new ArrayList<>();
		Iterator<Student> e = ip_student.values().iterator();
		while (e.hasNext()) {
			Student stu = e.next();
			list.add(new StuNum(stu.name(), stu.answer()[PB_num], stu.result()[PB_num]));
		}

		if (!problemList[PB_num].getType().equals(ProblemType.MultipleChoice)) {
			try {
				Stage primaryStage = (Stage) btn_Main.getScene().getWindow();
				Parent main = FXMLLoader.load(getClass().getResource("/gui/BanManagerSecondDone.fxml"));
				Scene scene = new Scene(main);
				primaryStage.setTitle("GuessWhat/Workbook");
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (Exception a) {
				a.printStackTrace();
			}
		}
	}

	private void settingPie(int[] value) {
		this.Pie = FXCollections.observableArrayList();
		for (int i = 0; i < 5; i++)
			Pie.add(new PieChart.Data((i + 1) + "번", value[i]));

		pc_Result.setData(Pie);
	}

	private int[] pieValue() {
		int[] value = new int[5];
		int correct = 0;
		String answer = "";
		Iterator<StuNum> e = list.iterator();
		while (e.hasNext()) {
			StuNum stu = e.next();
			for (int i = 0; i < stu.answer().length(); i++) {
				int a = stu.answer().charAt(i) - '0';
				value[a]++;
			}
			if (stu.result().equals("O")) {
				correct++;
				answer = stu.answer();
			}
		}
		for (int i = 0; i < 5; i++)
			value[i] = value[i] * 100 / StudentSize;

		lb_answerNum.setText(answer + (correct / StudentSize));

		return value;
	}

	public void btn_Main_Action() {
		try {
			Stage primaryStage = (Stage) btn_Main.getScene().getWindow();
			Parent main = FXMLLoader.load(getClass().getResource("/gui/Ban.fxml"));
			Scene scene = new Scene(main);
			primaryStage.setTitle("GuessWhat/" + className);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void btn_Close_Action() {
		try {
			Stage primaryStage = (Stage) btn_Close.getScene().getWindow();
			Parent main = FXMLLoader.load(getClass().getResource("/gui/Ban.fxml"));
			Scene scene = new Scene(main);
			primaryStage.setTitle("GuessWhat/" + className);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void btn_Delete_Action() {
		Alert alert = new Alert(AlertType.WARNING, "(TestRoom) " + banManager.BM_name() + "을(를) 정말로 삭제하시겠습니까?",
				ButtonType.YES, ButtonType.NO);
		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == ButtonType.YES) {

			String responseMessage = null;
			try {
				String requestMessage = "DeleteBanManager:" + this.banManager.P_num() + ":" + this.banManager.ban_num()
						+ ":" + this.banManager.BM_num();
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

			if (responseTokens[0].equals("DeleteBanManager")) {
				if (!responseTokens[1].equals("Success")) {
					System.out.println("Fail : DeleteBanManager");
				} else {
					System.out.println("[Delete] BM: " + this.banManager.BM_name());

					try {
						Stage primaryStage = (Stage) btn_Close.getScene().getWindow();
						Parent main = FXMLLoader.load(getClass().getResource("/gui/Ban.fxml"));
						Scene scene = new Scene(main);
						primaryStage.setTitle("GuessWhat/" + className);
						primaryStage.setScene(scene);
						primaryStage.show();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		}
	}

	public void btn_Previous_Action() {
		try {
			Stage primaryStage = (Stage) btn_Previous.getScene().getWindow();
			Parent main = FXMLLoader.load(getClass().getResource("/gui/BanManagerFirstDone.fxml"));
			Scene scene = new Scene(main);
			primaryStage.setTitle("GuessWhat/" + className);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void btn_num1_Action() {
		StudentDataModel.currentPB = 0;
		changeNum();
	}

	public void btn_num2_Action() {
		StudentDataModel.currentPB = 1;
		changeNum();
	}

	public void btn_num3_Action() {
		StudentDataModel.currentPB = 2;
		changeNum();
	}

	public void btn_num4_Action() {
		StudentDataModel.currentPB = 3;
		changeNum();
	}

	public void btn_num5_Action() {
		StudentDataModel.currentPB = 4;
		changeNum();
	}

	public void btn_num6_Action() {
		StudentDataModel.currentPB = 5;
		changeNum();
	}

	public void btn_num7_Action() {
		StudentDataModel.currentPB = 6;
		changeNum();
	}

	public void btn_num8_Action() {
		StudentDataModel.currentPB = 7;
		changeNum();
	}

	public void btn_num9_Action() {
		StudentDataModel.currentPB = 8;
		changeNum();
	}

	public void btn_num10_Action() {
		StudentDataModel.currentPB = 9;
		changeNum();
	}

	public void btn_num11_Action() {
		StudentDataModel.currentPB = 10;
		changeNum();
	}

	public void btn_num12_Action() {
		StudentDataModel.currentPB = 11;
		changeNum();
	}

	public void btn_num13_Action() {
		StudentDataModel.currentPB = 12;
		changeNum();
	}

	public void btn_num14_Action() {
		StudentDataModel.currentPB = 13;
		changeNum();
	}

	public void btn_num15_Action() {
		StudentDataModel.currentPB = 14;
		changeNum();
	}

	public void btn_Logo_Action() {
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

	public void btn_MyInfo_Action() {
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

	private class StuNum {
		private String name;
		private String answer;
		private String result;

		private StuNum(String name, String answer, String result) {
			this.name = name;
			this.answer = answer;
			this.result = result;
		}

		public String name() {
			return this.name;
		}

		public String answer() {
			return this.answer;
		}

		public String result() {
			return this.result;
		}
	}

}
