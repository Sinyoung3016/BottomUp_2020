package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import exam.ProblemType;
import exam.Result;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.HBoxModel;
import model.ProfessorDataModel;
import room.Ban;
import room.BanManager;

public class BanManagerSecondDoneMultiChoiceController<R> extends BaseController implements Initializable {

	@FXML
	private Button btn_Delete, btn_Close, btn_Previous;
	@FXML
	private Button btn_num1, btn_num2, btn_num3, btn_num4, btn_num5, btn_num6, btn_num7, btn_num8, btn_num9, btn_num10,
			btn_num11, btn_num12, btn_num13, btn_num14, btn_num15, btn_num16, btn_num17, btn_num18, btn_num19,
			btn_num20;
	@FXML
	private PieChart pc_Result;
	@FXML
	private Label lb_BanManagerName, lb_WorkBook, lb_answerNum;
	@FXML
	private ObservableList<Result> PieDataList;

	private ObservableList<Data> Pie;

	private Button[] btn;

	private Ban ban;
	private BanManager banManager;
	private Workbook workbook;

	private String className;

	private int WorkBookSize;
	private int StudentSize;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		this.ban = ProfessorDataModel.ban;
		this.banManager = ProfessorDataModel.banManager;
		this.workbook = ProfessorDataModel.workbook;
		this.PieDataList = ProfessorDataModel.ItemList_Results1;
		this.WorkBookSize = workbook.size();
		this.StudentSize = PieDataList.size();

		this.btn_Main.setText(ban.ban_name());
		this.lb_BanManagerName.setText(banManager.BM_name());
		this.lb_WorkBook.setText(workbook.name());

		btn = new Button[] { btn_num1, btn_num2, btn_num3, btn_num4, btn_num5, btn_num6, btn_num7, btn_num8, btn_num9,
				btn_num10, btn_num11, btn_num12, btn_num13, btn_num14, btn_num15, btn_num16, btn_num17, btn_num18,
				btn_num19, btn_num20 };

		className = btn_Main.getText();

		for (int i = 0; i < WorkBookSize; i++) {
			btn[i].setStyle("-fx-background-color: #5ad18f;");
			btn[i].setDisable(false);
		}
		for (int i = WorkBookSize; i < 20; i++) {
			btn[i].setStyle("-fx-background-color: #f0fff0;");
			btn[i].setDisable(true);
		}

		// start
		btn_num1_Action();

	}

	private void settingPie(int[] value) {
		this.Pie = FXCollections.observableArrayList();
		for (int i = 0; i < 5; i++)
			Pie.add(new PieChart.Data((i + 1) + "번", value[i]));

		pc_Result.setData(Pie);
	}

	private int[] pieValue() {
		int[] value = new int[5];
		Iterator<Result> e = PieDataList.iterator();
		Result answer = null;
		for (int i = 0; (i < 5) && e.hasNext(); i++) {
			answer = e.next();
			if ((answer.result()).equals((i + 1) + ""))
				value[i]++;
		}
		for (int i = 0; i < 5; i++) {
			value[i] = value[i] * 100 / StudentSize;
		}
		return value;
	}

	@Override
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

	public void btn_Previous_Action() {
		try {
			Stage primaryStage = (Stage) btn_Previous.getScene().getWindow();
			Parent main = FXMLLoader.load(getClass().getResource("/gui/BanManagerSecondDone.fxml"));
			Scene scene = new Scene(main);
			primaryStage.setTitle("GuessWhat/" + className);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean checkProblemType() {

		Result r = PieDataList.iterator().next();

		if ((r.problemType()).equals("Subjective") || (r.problemType()).equals("ShortAnswer")) {
			try {
				Stage primaryStage = (Stage) btn[0].getScene().getWindow();
				Parent main = FXMLLoader.load(getClass().getResource("/gui/BanManagerSecondDone.fxml"));
				Scene scene = new Scene(main);
				primaryStage.setTitle("GuessWhat/"+ className);
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (Exception c) {
				c.printStackTrace();
			}
		}
		return true;
	}

	public void btn_num1_Action() {
		this.PieDataList = ProfessorDataModel.ItemList_Results1;
		if (checkProblemType())
			settingPie(pieValue());
	}

	public void btn_num2_Action() {
		this.PieDataList = ProfessorDataModel.ItemList_Results2;
		if (checkProblemType())
			settingPie(pieValue());
	}
/*
	public void btn_num3_Action() {
		if (3 <= this.WorkBookSize) {
			checkProblemType();
			settingPie(pieValue());
		}
	}

	public void btn_num4_Action() {
		if (4 <= this.WorkBookSize) {
			checkProblemType(4);
			settingPie(pieValue());
		}
	}

	public void btn_num5_Action() {
		if (5 <= this.WorkBookSize) {
			checkProblemType(5);
			settingPie(pieValue());
		}
	}

	public void btn_num6_Action() {
		if (6 <= this.WorkBookSize) {
			checkProblemType(6);
			settingPie(pieValue());
		}
	}

	public void btn_num7_Action() {
		if (7 <= this.WorkBookSize) {
			checkProblemType(7);
			settingPie(pieValue());
		}
	}

	public void btn_num8_Action() {
		if (8 <= this.WorkBookSize) {
			checkProblemType(8);
			settingPie(pieValue());
		}
	}

	public void btn_num9_Action() {
		if (9 <= this.WorkBookSize) {
			checkProblemType(9);
			settingPie(pieValue());
		}
	}

	public void btn_num10_Action() {
		if (10 <= this.WorkBookSize) {
			checkProblemType(10);
			settingPie(pieValue());
		}
	}

	public void btn_num11_Action() {
		if (11 <= this.WorkBookSize) {
			checkProblemType(11);
			settingPie(pieValue());
		}
	}

	public void btn_num12_Action() {
		if (12 <= this.WorkBookSize) {
			checkProblemType(12);
			settingPie(pieValue());
		}
	}

	public void btn_num13_Action() {
		if (13 <= this.WorkBookSize) {
			checkProblemType(13);
			settingPie(pieValue());
		}
	}

	public void btn_num14_Action() {
		if (14 <= this.WorkBookSize) {
			checkProblemType(14);
			settingPie(pieValue());
		}
	}

	public void btn_num15_Action() {
		if (15 <= this.WorkBookSize) {
			checkProblemType(15);
			settingPie(pieValue());
		}
	}

	public void btn_num16_Action() {
		if (16 <= this.WorkBookSize) {
			checkProblemType(16);
			settingPie(pieValue());
		}
	}

	public void btn_num17_Action() {
		if (17 <= this.WorkBookSize) {
			checkProblemType(17);
			settingPie(pieValue());
		}
	}

	public void btn_num18_Action() {
		if (18 <= this.WorkBookSize) {
			checkProblemType(18);
			settingPie(pieValue());
		}
	}

	public void btn_num19_Action() {
		if (19 <= this.WorkBookSize) {
			checkProblemType(19);
			settingPie(pieValue());
		}
	}

	public void btn_num20_Action() {
		if (20 <= this.WorkBookSize) {
			checkProblemType(20);
			settingPie(pieValue());
		}
	}
	*/
}
