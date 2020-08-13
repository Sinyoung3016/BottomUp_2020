package gui;

import java.net.URL;
import java.util.ResourceBundle;

import application.SampleController.Data;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.StudentDataModel;
import thread.LoadingThread;

public class StuInfoToStuWBController implements Initializable {

	@FXML
	private BorderPane parent;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		Thread thread = new Thread() {
			@Override
			public void run() {
				while (((StudentDataModel.banManager.stringOfState()).equals("OPEN"))) {

					if (((StudentDataModel.banManager.stringOfState()).equals("ING")))
						Platform.runLater(() -> {
							try {
								Stage primaryStage = (Stage) parent.getScene().getWindow();
								Parent main = FXMLLoader.load(getClass().getResource("/gui/StuWorkBook.fxml"));
								Scene scene = new Scene(main);
								primaryStage.setTitle("GuessWhat/Test");
								primaryStage.setScene(scene);
								primaryStage.show();
							} catch (Exception e) {
								e.printStackTrace();
							}
						});
				}
			}
		};
		thread.start();
	}
}