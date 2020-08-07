package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class StuWorkBook_MultipleChoiceController extends BaseController {

	@FXML
	private Button btn_Submit, btn_Previous, btn_Next, btn_num1, btn_num2, btn_num3, btn_num4, btn_num5,
			btn_num6, btn_num7, btn_num8, btn_num9, btn_num10, btn_num11, btn_num12, btn_num13, btn_num14, btn_num15,
			btn_num16, btn_num17, btn_num18, btn_num19, btn_num20;
	@FXML
	private ListView lv_AnswerList;
	@FXML
	private Label lb_Question;
	@FXML
	private CheckBox cb_1, cb_2, cb_3, cb_4, cb_5;

	public void btn_Next_Action() {

	}

	public void btn_Previous_Action() {

	}

	public void btn_Submit_Action() {

		try {
			Stage primaryStage = (Stage) btn_Submit.getScene().getWindow();
			Parent main = FXMLLoader.load(getClass().getResource("/gui/StuResult.fxml"));
			Scene scene = new Scene(main);
			primaryStage.setTitle("GuessWhat/Result");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void btn_num1_Action() {

	}

	public void btn_num2_Action() {

	}

	public void btn_num3_Action() {

	}

	public void btn_num4_Action() {

	}

	public void btn_num5_Action() {

	}

	public void btn_num6_Action() {

	}

	public void btn_num7_Action() {

	}

	public void btn_num8_Action() {

	}

	public void btn_num9_Action() {

	}

	public void btn_num10_Action() {

	}

	public void btn_num11_Action() {

	}

	public void btn_num12_Action() {

	}

	public void btn_num13_Action() {

	}

	public void btn_num14_Action() {

	}

	public void btn_num15_Action() {

	}

	public void btn_num16_Action() {

	}

	public void btn_num17_Action() {

	}

	public void btn_num18_Action() {

	}

	public void btn_num19_Action() {

	}

	public void btn_num20_Action() {

	}

}
