package gui;

import java.net.URL;
import java.util.ResourceBundle;

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
import javafx.stage.Stage;
import model.ProfessorDataModel;
import room.Ban;
import room.BanManager;

public class BanManagerSecondDoneMultiChoiceController extends BaseController implements Initializable {

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

	private ObservableList<Data> list;

	private Ban ban;
	private BanManager banManager;
	private Workbook workbook;

	private String className;

	private int StudentSize;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		this.ban = ProfessorDataModel.ban;
		this.banManager = ProfessorDataModel.banManager;
		this.workbook = ProfessorDataModel.workbook;

		this.StudentSize = banManager.size();
		className = btn_Main.getText();

		this.btn_Main.setText(ban.ban_name());
		this.lb_BanManagerName.setText(banManager.BM_name());
		this.lb_WorkBook.setText(workbook.name());
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
			Parent main = FXMLLoader.load(getClass().getResource("/gui/BanManagerSecondDoneController.fxml"));
			Scene scene = new Scene(main);
			primaryStage.setTitle("GuessWhat/" + className);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void settingPie(int[] value) {
		this.list = FXCollections.observableArrayList();

		int n = value.length;
		
		for (int i = 0; i < n; i++)
			list.add(new PieChart.Data((i+1)+"번", value[i]));

		pc_Result.setData(list);
	}

	private int[]  pieValue(int n) {
		int [] value = new int[n];
		
		//계산 total = studentsize
		
		return value; 
	}

	public void btn_num1_Action() {

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
