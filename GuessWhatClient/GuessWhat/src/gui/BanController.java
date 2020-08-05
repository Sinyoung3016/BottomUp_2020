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
import model.ProfessorDataModel;
import model.HBoxModel;
import room.BanManager.HBoxCell;
import room.Ban;
import room.BanManager;

public class BanController extends BaseController implements Initializable {

	@FXML
	private Button btn_CreateNewBanManager, btn_ModifyClassName;
	@FXML
	private ListView<HBoxModel> lv_BanManagerList;
	
	private String className;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		className = ProfessorDataModel.ban.ban_name();
		lv_BanManagerList.setItems(ProfessorDataModel.ItemList_MyBanManager);

	}

	public void btn_CreateNewBanManager_Action() {
		try {
			Stage primaryStage = (Stage) btn_CreateNewBanManager.getScene().getWindow();
			Parent main = FXMLLoader.load(getClass().getResource("/gui/CreateNewBanManager.fxml"));
			Scene scene = new Scene(main);
			primaryStage.setTitle("GuessWhat/" + className);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void btn_ModifyClassName_Action() {
		try {
			Stage primaryStage = (Stage) btn_ModifyClassName.getScene().getWindow();
			Parent main = FXMLLoader.load(getClass().getResource("/gui/BanModifyClassName.fxml"));
			Scene scene = new Scene(main);
			primaryStage.setTitle("GuessWhat/" + className);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
