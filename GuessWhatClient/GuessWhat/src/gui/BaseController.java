package gui;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.ProfessorDataModel;

public class BaseController {

	@FXML
	protected Button btn_Main, btn_Logo, btn_MyInfo;

	public void btn_Main_Action() {

		Alert alert = new Alert(AlertType.WARNING, "MainPage로 이동하시겠습니까? 진행중이던 작업이 날아갈 수 있습니다.", ButtonType.YES,
				ButtonType.NO);
		alert.show();
		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == ButtonType.YES) {
			try {
				Stage primaryStage = (Stage) btn_Main.getScene().getWindow();
				Parent main = FXMLLoader.load(getClass().getResource("/gui/MainPage.fxml"));
				Scene scene = new Scene(main);
				primaryStage.setTitle("GuessWhat/MainPage");
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void btn_Logo_Action() {
		Alert alert = new Alert(AlertType.WARNING, "MainPage로 이동하시겠습니까? 진행중이던 작업이 날아갈 수 있습니다.", ButtonType.YES,
				ButtonType.NO);
		alert.show();
		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == ButtonType.YES) {
			try {
				Stage primaryStage = (Stage) btn_Logo.getScene().getWindow();
				Parent main = FXMLLoader.load(getClass().getResource("/gui/MainPage.fxml"));
				Scene scene = new Scene(main);
				primaryStage.setTitle("GuessWhat/MainPage");
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void btn_MyInfo_Action() {
		Alert alert = new Alert(AlertType.WARNING, "MyInfo로 이동하시겠습니까? 진행중이던 작업이 날아갈 수 있습니다.", ButtonType.YES,
				ButtonType.NO);
		alert.show();
		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == ButtonType.YES) {
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
	}

	public static Object CloseButtonActione() {
		// TODO Auto-generated method stub
		return null;
	}

}
