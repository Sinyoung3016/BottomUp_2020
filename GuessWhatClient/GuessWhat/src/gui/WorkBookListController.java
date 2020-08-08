package gui;

import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import exam.Problem;
import exam.Workbook;
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
import model.StudentDataModel;
import model.HBoxModel;

public class WorkBookListController extends BaseController implements Initializable {

	@FXML
	private Button btn_CreateNewWorkBook;
	@FXML
	private ListView<HBoxModel> lv_WorkBookList;

	private Socket socket;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		
		lv_WorkBookList.setItems(ProfessorDataModel.ItemList_MyWorkBook);
		
		

	}
	
	public void btn_CreateNewWorkBook_Action() {
		
		ProfessorDataModel.workbook = new Workbook();
		ProfessorDataModel.problem = new Problem(0);
		ProfessorDataModel.hasAValue = new boolean[20];
		ProfessorDataModel.hasQValue = new boolean[20];
		ProfessorDataModel.problemList = new Problem[20];
		
		try {
			Stage primaryStage = (Stage) btn_CreateNewWorkBook.getScene().getWindow();
			Parent main = FXMLLoader.load(getClass().getResource("/gui/NewWorkBook_ShortAnswer.fxml"));
			Scene scene = new Scene(main);
			primaryStage.setTitle("GuessWhat/wWorkBook");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
