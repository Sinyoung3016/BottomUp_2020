package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class StudentInfoController {
	@FXML
	private Button btn_Join, btn_Close;
	@FXML
	private TextField tf_StudentName;
	@FXML
	private Label lb_ClassRoomName;
	
	private boolean IsTestStarted = true;

	public void btn_Close_Action(){
		try {
			Stage primaryStage = (Stage) btn_Close.getScene().getWindow();
			Parent main = FXMLLoader.load(getClass().getResource("/gui/Home.fxml"));
			Scene scene = new Scene(main);
			primaryStage.setTitle("GuessWhat/Home");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void btn_Join_Action(){//While문으로 계속해서 받다가 열리면 입장
		
		if(IsTestStarted) {
			try {
				Stage primaryStage = (Stage) btn_Close.getScene().getWindow();
				Parent main = FXMLLoader.load(getClass().getResource("/gui/StuWorkBook.fxml"));
				Scene scene = new Scene(main);
				primaryStage.setTitle("GuessWhat/Test");
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			try {
				Stage primaryStage = (Stage) btn_Close.getScene().getWindow();
				Parent main = FXMLLoader.load(getClass().getResource("/gui/StuInfoToStuWB.fxml"));
				Scene scene = new Scene(main);
				primaryStage.setTitle("GuessWhat/Loading");
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
