package gui;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
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

	private static Socket socket = ProfessorDataModel.socket;

	public static Object CloseButtonActione() {
		// TODO Auto-generated method stub
		if (ProfessorDataModel.isUser) {
			// 사용자 = professor
			String responseMessage = null;
			try {
				String requestTokens = "LogOut:" + ProfessorDataModel.ID;
				BufferedReader br = new BufferedReader(
						new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
				PrintWriter pw = new PrintWriter(
						new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
				pw.println(requestTokens);
				pw.flush();
				responseMessage = br.readLine();
			} catch (Exception e) {
				System.out.println("Error : " + e.getMessage() + "FROM btn_LogOut_Action");
			}
			String[] responseTokens = responseMessage.split(":");
			if (responseTokens[0].equals("LogOut")) {
				if (!responseTokens[1].equals("Success")) {
					new Alert(Alert.AlertType.WARNING, responseTokens[1], ButtonType.CLOSE).showAndWait();
				} else {
					ProfessorDataModel.isUser = false;
					System.out.println(ProfessorDataModel.ID + "님이 로그아웃하셨습니다.");
				}
			}
		} else {
			// 사용자 = student;
		}
		return null;

	}
}