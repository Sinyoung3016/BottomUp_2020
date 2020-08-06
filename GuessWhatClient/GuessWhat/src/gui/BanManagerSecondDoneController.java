package gui;

import java.net.URL;
import java.util.ResourceBundle;
import exam.StuNumResult;
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
	private TableView<StuNumResult> tv_Result;
	@FXML
	private Label lb_BanManagerName, lb_WorkBook;
	@FXML
	private ObservableList<StuNumResult> tableDataList;

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
		this.tableDataList = ProfessorDataModel.ItemList_Results;
		this.WorkBookSize = workbook.WorkBooksize();

		this.btn_Main.setText(ban.ban_name());
		this.lb_BanManagerName.setText(banManager.BM_name());
		this.lb_WorkBook.setText(workbook.W_name());

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

	private TableColumn<StuNumResult, String>[] getColumns() {

		TableColumn<StuNumResult, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().S_name()));
		nameColumn.setPrefWidth(50);

		TableColumn<StuNumResult, String> resultColumn = new TableColumn<>("Result");
		resultColumn.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().S_result()));

		TableColumn<StuNumResult, String>[] returnTable = new TableColumn[] { nameColumn, resultColumn };

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

	private boolean checkProblemType() {

		StuNumResult t = tableDataList.iterator().next();

		if ((t.problemType()).equals("MultipleChoice")) {
			try {
				Stage primaryStage = (Stage) btn[0].getScene().getWindow();
				Parent main = FXMLLoader.load(getClass().getResource("/gui/BanManagerSecondDoneMultiChoice.fxml"));
				Scene scene = new Scene(main);
				primaryStage.setTitle("GuessWhat/" + className);
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (Exception c) {
				c.printStackTrace();
			}
		}
		return true;
	}

	public void btn_Action() {
		this.tableDataList = ProfessorDataModel.ItemList_Results;
		if (checkProblemType())
			settingColumn();
	}

	public void btn_CreateProblem_Action() {
		 this.btn_Action();
	}

	public void btn_num1_Action() {
		 this.btn_Action();
	}

	public void btn_num2_Action() {
		 this.btn_Action();
	}

	public void btn_num3_Action() {
		 this.btn_Action();
	}

	public void btn_num4_Action() {
		 this.btn_Action();
	}

	public void btn_num5_Action() {
		 this.btn_Action();
	}

	public void btn_num6_Action() {
		 this.btn_Action();
	}

	public void btn_num7_Action() {
		 this.btn_Action();
	}

	public void btn_num8_Action() {
		 this.btn_Action();
	}

	public void btn_num9_Action() {
		 this.btn_Action();
	}

	public void btn_num10_Action() {
		 this.btn_Action();
	}

	public void btn_num11_Action() {
		 this.btn_Action();
	}

	public void btn_num12_Action() {
		 this.btn_Action();
	}

	public void btn_num13_Action() {
		 this.btn_Action();
	}

	public void btn_num14_Action() {
		 this.btn_Action();
	}

	public void btn_num15_Action() {
		 this.btn_Action();
	}

	public void btn_num16_Action() {
		 this.btn_Action();
	}

	public void btn_num17_Action() {
		 this.btn_Action();
	}

	public void btn_num18_Action() {
		 this.btn_Action();
	}

	public void btn_num19_Action() {
		 this.btn_Action();
	}

	public void btn_num20_Action() {
		 this.btn_Action();
	}

}

