package gui;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

public class Main extends Application {

	public void start(Stage primaryStage) throws Exception {
		
		try {
			BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("/server/Server.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setTitle("GuessWhat/Server");
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