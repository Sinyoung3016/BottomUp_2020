package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import exam.ProblemType;
import exam.Result;
import exam.Workbook;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.ProfessorDataModel;
import room.Ban;
import room.BanManager;

public class BanManagerSecondDoneController extends BaseController implements Initializable {

	@FXML
	private Button btn_Delete, btn_Close, btn_Previous;
	@FXML
	private Button btn_num1, btn_num2, btn_num3, btn_num4, btn_num5, btn_num6, btn_num7, btn_num8, btn_num9, btn_num10,
			btn_num11, btn_num12, btn_num13, btn_num14, btn_num15, btn_num16, btn_num17, btn_num18, btn_num19,
			btn_num20;
	@FXML
	private TableView<Result> tv_Result;
	@FXML
	private Label lb_BanManagerName, lb_WorkBook;
	@FXML
	private ObservableList<Result> tableDataList;

	private Button[] btn;

	private Ban ban;
	private BanManager banManager;
	private Workbook workbook;

	private String className;

	private int WorkBookSize;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		this.ban = ProfessorDataModel.ban;
		this.banManager = ProfessorDataModel.banManager;
		this.workbook = ProfessorDataModel.workbook;
		this.tableDataList = ProfessorDataModel.ItemList_Results2;
		this.WorkBookSize = workbook.size();

		this.btn_Main.setText(ban.ban_name());
		this.lb_BanManagerName.setText(banManager.BM_name());
		this.lb_WorkBook.setText(workbook.name());

		btn = new Button[] { btn_num1, btn_num2, btn_num3, btn_num4, btn_num5, btn_num6, btn_num7, btn_num8, btn_num9,
				btn_num10, btn_num11, btn_num12, btn_num13, btn_num14, btn_num15, btn_num16, btn_num17, btn_num18,
				btn_num19, btn_num20 };

		className = btn_Main.getText();

		for (int i = 0; i < WorkBookSize; i++)
			btn[i].setStyle("-fx-background-color: #5ad18f;");

		// start
		btn_num1_Action();
	}

	private void settingColumn() {
		tv_Result.getColumns().setAll(this.getColumns());
		tv_Result.getItems().setAll(this.tableDataList);
	}

	private TableColumn<Result, String>[] getColumns() {

		TableColumn<Result, String> numColumn = new TableColumn<>("No");
		numColumn.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().num()));
		numColumn.setPrefWidth(30);

		TableColumn<Result, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().name()));
		nameColumn.setPrefWidth(50);

		TableColumn<Result, String> resultColumn = new TableColumn<>("Result");
		resultColumn.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().result()));

		TableColumn<Result, String>[] returnTable = new TableColumn[] { numColumn, nameColumn, resultColumn };

		return returnTable;
	}

	@Override
	public void btn_Main_Action() {
		try {
			Stage primaryStage = (Stage) btn_Main.getScene().getWindow();
			Parent main = FXMLLoader.load(getClass().getResource("/gui/Ban.fxml"));
			Scene scene = new Scene(main);
			primaryStage.setTitle("GuessWhat/Class");
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

	private boolean checkProblemType(int n) {

		Result t = tableDataList.iterator().next();

		if ((t.problemType()).equals("MultipleChoice")) {
			try {
				Stage primaryStage = (Stage) btn[n].getScene().getWindow();
				Parent main = FXMLLoader.load(getClass().getResource("/gui/BanManagerSecondDoneMultiChoice.fxml"));
				Scene scene = new Scene(main);
				primaryStage.setTitle("GuessWhat/" + n);
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (Exception c) {
				c.printStackTrace();
			}
		}
		return true;
	}

	public void btn_num1_Action() {
		this.tableDataList = ProfessorDataModel.ItemList_Results2;
		if (checkProblemType(1))
			settingColumn();
	}

	public void btn_num2_Action() {
		this.tableDataList = ProfessorDataModel.ItemList_Results1;
		if (checkProblemType(2))
			settingColumn();
	}

	/*
	public void btn_num3_Action() {
		if (3 <= this.WorkBookSize) {
			checkProblemType(3);
			settingColumn();
		}
	}

	public void btn_num4_Action() {
		if (4 <= this.WorkBookSize) {
			checkProblemType(4);
			settingColumn();
		}
	}

	public void btn_num5_Action() {
		if (5 <= this.WorkBookSize) {
			checkProblemType(5);
			settingColumn();
		}
	}

	public void btn_num6_Action() {
		if (6 <= this.WorkBookSize) {
			checkProblemType(6);
			settingColumn();
		}
	}

	public void btn_num7_Action() {
		if (7 <= this.WorkBookSize) {
			checkProblemType(7);
			settingColumn();
		}
	}

	public void btn_num8_Action() {
		if (8 <= this.WorkBookSize) {
			checkProblemType(8);
			settingColumn();
		}
	}

	public void btn_num9_Action() {
		if (9 <= this.WorkBookSize) {
			checkProblemType(9);
			settingColumn();
		}
	}

	public void btn_num10_Action() {
		if (10 <= this.WorkBookSize) {
			checkProblemType(10);
			settingColumn();
		}
	}

	public void btn_num11_Action() {
		if (11 <= this.WorkBookSize) {
			checkProblemType(11);
			settingColumn();
		}
	}

	public void btn_num12_Action() {
		if (12 <= this.WorkBookSize) {
			checkProblemType(12);
			settingColumn();
		}
	}

	public void btn_num13_Action() {
		if (13 <= this.WorkBookSize) {

			checkProblemType(13);
			settingColumn();
		}
	}

	public void btn_num14_Action() {
		if (14 <= this.WorkBookSize) {
			checkProblemType(14);
			settingColumn();
		}
	}

	public void btn_num15_Action() {
		if (15 <= this.WorkBookSize) {
			checkProblemType(15);
			settingColumn();
		}
	}

	public void btn_num16_Action() {
		if (16 <= this.WorkBookSize) {
			checkProblemType(16);
			settingColumn();
		}
	}

	public void btn_num17_Action() {
		if (17 <= this.WorkBookSize) {
			checkProblemType(17);
			settingColumn();
		}
	}

	public void btn_num18_Action() {
		if (18 <= this.WorkBookSize) {
			checkProblemType(18);
			settingColumn();
		}
	}

	public void btn_num19_Action() {
		if (19 <= this.WorkBookSize) {
			checkProblemType(19);
			settingColumn();
		}
	}

	public void btn_num20_Action() {
		if (20 <= this.WorkBookSize) {
			checkProblemType(20);
			settingColumn();
		}
	}
	*/
}
