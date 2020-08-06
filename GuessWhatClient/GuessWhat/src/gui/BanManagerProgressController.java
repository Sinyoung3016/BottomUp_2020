package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.ProfessorDataModel;
import room.Ban;
import room.BanManager;
import room.BanManager.State;
import user.Student;

public class BanManagerProgressController extends BaseController implements Initializable {

	@FXML
	private Button btn_Close, btn_End;
	@FXML
	private TextField tf_NewBanManagerName, tf_NewBanManagerCode;
	@FXML
	private Label lb_BanManagerName, lb_WorkBook;
	@FXML
	private TableView<Student> tv_Answer;

	private Ban ban;
	private BanManager banManager;
	private Workbook workbook;

	private String className;

	private int WorkBookSize;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		this.ban = ProfessorDataModel.ban;
		this.banManager = ProfessorDataModel.banManager;
		this.workbook = ProfessorDataModel.workbook;
		
		this.WorkBookSize = workbook.WorkBooksize();
		className = btn_Main.getText();
		
		this.btn_Main.setText(ban.ban_name());
		this.lb_BanManagerName.setText(banManager.BM_name());
		this.lb_WorkBook.setText(workbook.W_name());
		
		tv_Answer.getColumns().setAll(this.getColumns());
		tv_Answer.getItems().setAll(this.getTableData());
		
	}

	private ObservableList<Student> getTableData() {
		ObservableList<Student> tableDataList = ProfessorDataModel.ItemList_Students;
		return tableDataList;
	}

	private TableColumn<Student, String>[] getColumns() {

		TableColumn<Student, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().name()));
		nameColumn.setPrefWidth(50);

		TableColumn<Student, String> scoreColumn[] = new TableColumn[WorkBookSize];
		for (int i = 0; i < WorkBookSize; i++) {
			final int j = i;
			scoreColumn[i] = new TableColumn<Student, String>("" + (i + 1));
			scoreColumn[i].setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().answer()[j]));
		}

		TableColumn<Student, String>[] returnTable = new TableColumn[WorkBookSize + 1];
		returnTable[0] = nameColumn;
		for (int i = 1; i < WorkBookSize + 1; i++) {
			returnTable[i] = scoreColumn[i - 1];
		}

		return returnTable;
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

	public void btn_End_Action() {
		
		if(banManager.BM_state().equals(State.CLOSE))
			banManager.setBM_state_CLOSE(); 
		
		try {
			Stage primaryStage = (Stage) btn_End.getScene().getWindow();
			Parent main = FXMLLoader.load(getClass().getResource("/gui/BanManagerSecondDone.fxml"));
			Scene scene = new Scene(main);
			primaryStage.setTitle("GuessWhat/" + className);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
