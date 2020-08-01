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

public class WorkBookListController extends BaseController implements Initializable {

	@FXML
	private Button btn_CreateNewWorkBook;
	@FXML
	private ListView<HBoxCell> lv_WorkBookList;
	
	public void btn_CreateNewWorkBook_Action() {
		try {
			Stage primaryStage = (Stage)  btn_CreateNewWorkBook.getScene().getWindow();
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
		
		ObservableList<HBoxCell> listMyClass = FXCollections.observableArrayList();
		listMyClass.add(new HBoxCell(1, "class1", 3));
		listMyClass.add(new HBoxCell(2, "class2", 4));
		listMyClass.add(new HBoxCell(3, "class3", 5));
		
		lv_WorkBookList.setItems(listMyClass);
	}

}
