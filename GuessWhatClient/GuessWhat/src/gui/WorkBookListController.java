package gui;

import java.net.URL;
import java.util.ResourceBundle;
import exam.Workbook.HBoxCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.DataModel;
import model.HBoxModel;

public class WorkBookListController extends BaseController implements Initializable {

	@FXML
	private Button btn_CreateNewWorkBook;
	@FXML
	private ListView<HBoxModel> lv_WorkBookList;

	public void btn_CreateNewWorkBook_Action() {
		try {
			Stage primaryStage = (Stage) btn_CreateNewWorkBook.getScene().getWindow();
			Parent main = FXMLLoader.load(getClass().getResource("/gui/NewWorkBook.fxml"));
			Scene scene = new Scene(main);
			primaryStage.setTitle("GuessWhat/CreateNewWorkBook");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		DataModel.ItemList_MyWorkBook = FXCollections.observableArrayList();
		ObservableList<HBoxModel> list = DataModel.ItemList_MyWorkBook;

		// 서버에서 가지고 오기
		list.add(new HBoxCell(1, "workbook1", 3));
		list.add(new HBoxCell(2, "workbook2", 4));
		list.add(new HBoxCell(3, "workbook3", 5));
		// 서버에서 가지고 오기

		lv_WorkBookList.setItems(list);

	}

}
