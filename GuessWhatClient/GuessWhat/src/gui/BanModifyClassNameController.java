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
import model.HBoxModel;
import model.ProfessorDataModel;
import room.Ban;

public class BanModifyClassNameController extends BaseController implements Initializable {

	@FXML
	private Button btn_CancelChangeName, btn_SaveClassName;
	@FXML
	private TextField tf_ChangeClassName;
	@FXML
	private ListView<HBoxModel> lv_BanManagerList;

	private Ban ban;
	private String className;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		lv_BanManagerList.setItems(ProfessorDataModel.ItemList_MyBanManager);
		
		ban = ProfessorDataModel.ban;
		className = ban.ban_name();
	
	}

	public void btn_CancelChangeName_Action() {
		
		//이름 수정
		
		try {
			Stage primaryStage = (Stage) btn_CancelChangeName.getScene().getWindow();
			Parent main = FXMLLoader.load(getClass().getResource("/gui/Ban.fxml"));
			Scene scene = new Scene(main);
			primaryStage.setTitle("GuessWhat/" + className);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void btn_SaveClassName_Action() {

		try {
			Stage primaryStage = (Stage) btn_SaveClassName.getScene().getWindow();
			Parent main = FXMLLoader.load(getClass().getResource("/gui/Ban.fxml"));
			Scene scene = new Scene(main);
			primaryStage.setTitle("GuessWhat/" + className);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
