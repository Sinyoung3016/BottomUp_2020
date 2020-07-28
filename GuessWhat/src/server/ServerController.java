package server;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ServerController {
	@FXML
	private Button btn_Open, btn_Close, btn_Empty, btn_Enter;
	@FXML
	private TextField tf_input;

	public void btn_Open_Action() {
		// 서버 열기

	}

	public void btn_Close_Action() {
		// 서버 닫기
	}

	//MoonDD's PlayGround start
	public void btn_Enter_Action() {
		//텍스트 입력받고, 값이 있으면 버튼누르면 실행
		String input = tf_input.getText(); //input에 입력받은 텍스트
		while(input == null) {
			input = tf_input.getText();
			
			
			
		}
	}

	public void btn_Empty_Action() {
		//그냥 버튼 누르면 실행
	}
	//MoonDD's PlayGround end

	public Object CloseButtonActione() {
		// 닫기 버튼 눌렀을 때, 서버 종료하고 닫음.
		btn_Close_Action();
		return null;
	}

}
