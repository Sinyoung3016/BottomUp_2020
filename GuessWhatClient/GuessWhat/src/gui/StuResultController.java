package gui;

import java.net.Socket;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;

import exam.Problem;
import exam.ProblemType;
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
import javafx.stage.Stage;
import model.ProfessorDataModel;
import model.StudentDataModel;
import user.Student;

public class StuResultController implements Initializable {

	@FXML
	private Button btn_Detail, btn_num1, btn_num2, btn_num3, btn_num4, btn_num5, btn_num6, btn_num7, btn_num8, btn_num9,
			btn_num10, btn_num11, btn_num12, btn_num13, btn_num14, btn_num15;
	@FXML
	private PieChart pc_result;

	private ObservableList<Data> Pie;

	private Button[] btn;

	private Socket socket;
	private Student student;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		this.socket = StudentDataModel.socket;
		this.student = StudentDataModel.student;

		btn = new Button[] { btn_num1, btn_num2, btn_num3, btn_num4, btn_num5, btn_num6, btn_num7, btn_num8, btn_num9,
				btn_num10, btn_num11, btn_num12, btn_num13, btn_num14, btn_num15 };

		String[] result = this.student.result();
		int WorkBookSize = result.length;
		int[] value = new int[3];

		for (int i = 0; i < WorkBookSize; i++) {
			if (result[i].equals("O")) {
				value[2]++;
				btn[i].setStyle("-fx-background-color: #5ad18f;");
			} else if (result[i].equals("X")) {
				value[0]++;
				btn[i].setStyle("-fx-background-color: #ff848f;");
			} else if (result[i].equals("N")) {
				value[1]++;
				btn[i].setStyle("-fx-background-color: #5ad18f;");
			}
		}
		for (int i = WorkBookSize; i < 15; i++) {
			btn[i].setStyle("-fx-background-color: #dcdcdc;");
			btn[i].setDisable(true);
		}

		// pie
		for (int i = 0; i < 3; i++) {
			value[i] = value[i] * 100 / WorkBookSize;
		}

		this.Pie = FXCollections.observableArrayList();

		Pie.add(new PieChart.Data("Wrong", value[0]));
		Pie.add(new PieChart.Data("Non", value[1]));
		Pie.add(new PieChart.Data("Correct", value[2]));

		pc_result.setData(Pie);
		// pie

	}

	public void btn_Detail_Action() {

		StudentDataModel.currentPB = 0;
		ProblemType p = ProfessorDataModel.problemList[0].getType();
		
		if (!p.equals(ProblemType.MultipleChoice)) {
			try {
				Stage primaryStage = (Stage) btn_Detail.getScene().getWindow();
				Parent main = FXMLLoader.load(getClass().getResource("/gui/StuResultDetail.fxml"));
				Scene scene = new Scene(main);
				primaryStage.setTitle("GuessWhat/WorkBook");
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (p.equals(ProblemType.MultipleChoice)) {
			try {
				Stage primaryStage = (Stage) btn_Detail.getScene().getWindow();
				Parent main = FXMLLoader.load(getClass().getResource("/gui/StuResultDetail_MultipleChoice.fxml"));
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
