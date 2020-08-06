package gui;

import javafx.application.Application;
import javafx.stage.Stage;
import server.Server;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	public void start(Stage primaryStage) throws Exception {
		try {
			//for test
			//BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("/gui/MainPage.fxml"));
			BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("/gui/WorkBookList.fxml"));
			//BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("/gui/Home.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setTitle("QuessWhat/Home");
			primaryStage.setOnCloseRequest(ActionEvent -> BaseController.CloseButtonActione());
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}