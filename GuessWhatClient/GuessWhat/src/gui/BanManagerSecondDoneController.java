package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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

	private Ban ban;
	private BanManager banManager;
	private Workbook workbook;

	private String className;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		this.ban = ProfessorDataModel.ban;
		this.banManager = ProfessorDataModel.banManager;
		this.workbook = ProfessorDataModel.workbook;

		className = btn_Main.getText();

		this.btn_Main.setText(ban.ban_name());
		this.lb_BanManagerName.setText(banManager.BM_name());
		this.lb_WorkBook.setText(workbook.name());

		this.btn_num1_Action();

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

	private void settingColumn() {
		tv_Result.getColumns().setAll(this.getColumns());
		tv_Result.getItems().setAll(this.getTableData());
	}

	private ObservableList<Result> getTableData() {
		ObservableList<Result> tableDataList = ProfessorDataModel.ItemList_Results;
		return tableDataList;
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
