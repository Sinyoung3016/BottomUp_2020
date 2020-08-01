package gui;

import java.net.URL;
import java.util.ResourceBundle;
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
import room.Ban.HBoxCell;

public class MainPageController implements Initializable{
	@FXML
	private Button btn_WorkBookList, btn_CreateNewClass, btn_MyInfo;
	@FXML
	private ListView<HBoxModel> lv_ClassList;

	public void btn_WorkBookList_Action() {
		try {
			Stage primaryStage = (Stage) btn_WorkBookList.getScene().getWindow();
			Parent main = FXMLLoader.load(getClass().getResource("/gui/WorkBookList.fxml"));
			Scene scene = new Scene(main);
			primaryStage.setTitle("GuessWhat/WorkBookList");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void btn_CreateNewClass_Action() {
		try {
			Stage primaryStage = (Stage) btn_CreateNewClass.getScene().getWindow();
			Parent main = FXMLLoader.load(getClass().getResource("/gui/Ban.fxml"));
			Scene scene = new Scene(main);
			primaryStage.setTitle("GuessWhat/Class");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void btn_MyInfo_Action() {
		try {
			Stage primaryStage = (Stage) btn_MyInfo.getScene().getWindow();
			Parent main = FXMLLoader.load(getClass().getResource("/gui/MyInfo.fxml"));
			Scene scene = new Scene(main);
			primaryStage.setTitle("GuessWhat/MyInfo");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		DataModel.ItemList_MyClass = FXCollections.observableArrayList();
		ObservableList<HBoxModel> list = DataModel.ItemList_MyClass;
		
		// 서버에서 가지고 오기
		list.add(new HBoxCell(1, "class1", 3));
		list.add(new HBoxCell(2, "class2", 4));
		list.add(new HBoxCell(3, "class3", 5));
		// 서버에서 가지고 오기
		
		lv_ClassList.setItems(list);
		
		
	}
}
