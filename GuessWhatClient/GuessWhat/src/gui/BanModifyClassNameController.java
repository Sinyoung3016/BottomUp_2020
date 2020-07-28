package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class BanModifyClassNameController extends BaseController implements Initializable {

	@FXML
	private Button btn_CreateNewBanManager,btn_SaveClassName;
	@FXML
	private TextField tf_ChangeClassName;

	public void btn_CreateNewBanManager_Action() {
		try {
			Stage primaryStage = (Stage) btn_CreateNewBanManager.getScene().getWindow();
			Parent main = FXMLLoader.load(getClass().getResource("/gui/CreateNewBanManager.fxml"));
			Scene scene = new Scene(main);
			primaryStage.setTitle("GuessWhat/CreateNewRoom");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void btn_SaveClassName_Action() {
		try {
			Stage primaryStage = (Stage) btn_CreateNewBanManager.getScene().getWindow();
			Parent main = FXMLLoader.load(getClass().getResource("/gui/Ban.fxml"));
			Scene scene = new Scene(main);
			primaryStage.setTitle("GuessWhat/Class");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}
}
