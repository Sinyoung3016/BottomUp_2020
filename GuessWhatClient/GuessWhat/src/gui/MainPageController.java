package gui;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import database.DB_Ban;
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
import room.Ban.HBoxCell;

public class MainPageController implements Initializable{
	@FXML
	private Button btn_WorkBookList, btn_CreateNewClass, btn_MyInfo;
	@FXML
	private ListView<HBoxCell> lv_ClassList;
	
	private ArrayList<String> list;

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
		
		ObservableList<HBoxCell> listMyClass = FXCollections.observableArrayList();
		listMyClass.add(new HBoxCell(1, "class1", 3));
		listMyClass.add(new HBoxCell(2, "class2", 4));
		listMyClass.add(new HBoxCell(3, "class3", 5));
		
		lv_ClassList.setItems(listMyClass);
		/*
		 * try {

			int pNum = -1; 		// <-- Login user's pNum (Input DB : Professor's PNum to Test)

			this.list = DB_Ban.getAllBanList(pNum);
			Iterator<String> itr = list.iterator();
			int num = 1;
			
			ObservableList<HBoxCell> listMyClass = FXCollections.observableArrayList();
			
			while (itr.hasNext()) {
				
				listMyClass.add(new HBoxCell(num, itr.next(), 3));
				num++;
			}
					
			lv_ClassList.setItems(listMyClass);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		*/
		
		
	}
}
