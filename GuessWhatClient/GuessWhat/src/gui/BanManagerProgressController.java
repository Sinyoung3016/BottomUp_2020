package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import exam.Student;
import javafx.beans.property.ReadOnlyStringWrapper;
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

public class BanManagerProgressController extends BaseController implements Initializable {

	@FXML
	private Button btn_Close, btn_End;
	@FXML
	private TextField tf_NewBanManagerName, tf_NewBanManagerCode;
	@FXML
	private Label lb_BanManagerName, lb_WorkBook;
	@FXML
	private TableView<Student> tv_Answer;

	private int WorkBookSize;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		this.WorkBookSize = 5;
		tv_Answer.getColumns().setAll(this.getColumns());
		tv_Answer.getItems().setAll(this.getTableData());

	}

	private ArrayList<Student> getTableData() {
		//값 받아서 가져오기
		ArrayList<Student> tableDataList = new ArrayList<>();

		tableDataList.add(new Student("1", "문정현", "1:2:3:4:4vdssdvsdvsdvsdvdvsdv"));
		tableDataList.add(new Student("2", "복신영", "1:2:3:4:4"));
		tableDataList.add(new Student("3", "성장하는문정현","1:2:3:4:4"));
		tableDataList.add(new Student("4", "성장한문정현", "1:2:3:4:4"));
		tableDataList.add(new Student("5", "선넘는문정현", "1:2:3:4:4"));

		return tableDataList;
	}

	private TableColumn<Student, String>[] getColumns() {

		TableColumn<Student, String> numColumn = new TableColumn<>("No");
		numColumn.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().num()));
		numColumn.setPrefWidth(30);

		TableColumn<Student, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().name()));
		nameColumn.setPrefWidth(50);

		TableColumn<Student, String> scoreColumn[] = new TableColumn[WorkBookSize];
		for (int i = 0; i < WorkBookSize; i++) {
			final int j = i;
			scoreColumn[i] = new TableColumn<Student, String>("" + (i+1));
			scoreColumn[i].setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().answer()[j]));
		}

		TableColumn<Student, String>[] returnTable = new TableColumn[WorkBookSize + 2];
		returnTable[0] = numColumn;	returnTable[1] = nameColumn;
		for (int i = 2; i < WorkBookSize+2; i++) {
			returnTable[i] = scoreColumn[i-2];
		}
		
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
			primaryStage.setTitle("GuessWhat/Class");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void btn_End_Action() {
		try {
			Stage primaryStage = (Stage) btn_End.getScene().getWindow();
			Parent main = FXMLLoader.load(getClass().getResource("/gui/Ban.fxml"));
			Scene scene = new Scene(main);
			primaryStage.setTitle("GuessWhat/Class");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
