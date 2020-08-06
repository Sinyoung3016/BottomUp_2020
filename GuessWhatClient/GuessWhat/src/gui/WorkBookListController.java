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
import model.ProfessorDataModel;
import model.HBoxModel;

public class WorkBookListController extends BaseController implements Initializable {

	@FXML
	private Button btn_CreateNewWorkBook;
	@FXML
	private ListView<HBoxModel> lv_WorkBookList;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		lv_WorkBookList.setItems(ProfessorDataModel.ItemList_MyWorkBook);

	}
	
	public void btn_CreateNewWorkBook_Action() {
		try {
			Stage primaryStage = (Stage) btn_CreateNewWorkBook.getScene().getWindow();
			Parent main = FXMLLoader.load(getClass().getResource("/gui/NewWorkBook_MultipleChoice.fxml"));
			Scene scene = new Scene(main);
			primaryStage.setTitle("GuessWhat/CreateNewWorkBook");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
