package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class StuResultController implements Initializable {
	@FXML
	private Pane Pane_Answer;
	@FXML
	private Button btn_Detail, btn_num1, btn_num2, btn_num3, btn_num4, btn_num5,
	btn_num6, btn_num7, btn_num8, btn_num9, btn_num10, btn_num11, btn_num12, btn_num13, btn_num14, btn_num15,
	btn_num16, btn_num17, btn_num18, btn_num19, btn_num20;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public void btn_Detail_Action() {
		try {
			Stage primaryStage = (Stage) btn_Detail.getScene().getWindow();
			Parent main = FXMLLoader.load(getClass().getResource("/gui/StuResultDetail.fxml"));
			Scene scene = new Scene(main);
			primaryStage.setTitle("GuessWhat/ResultDetail");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
