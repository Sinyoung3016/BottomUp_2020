package room;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.HBoxModel;
import model.ProfessorDataModel;

//BanManager의 모음
public class Ban {

	private int P_num;
	private int ban_num;
	
	private String ban_name;
	private int banManager_size;

	//Constructor start
	public Ban(int P_num, int num, String name, int bmsize) {
		this.P_num = P_num;
		this.ban_num = num;
		this.ban_name = name;
		this.banManager_size = bmsize;
	}
	//Constructor end
	
	//Setter start
	public void setSize(int banManager_size) {
		this.banManager_size = banManager_size;
	}
	public void setName(String name) {
		this.ban_name = name;
	}
	//Setter end
	
	//Getter start
	public int P_num() { return this.P_num; }
	public int ban_num() { return this.ban_num;}
	public String ban_name() { return this.ban_name; }
	public int banManager_Size() { return this.banManager_size; }
	//Getter end
	
	
	public HBoxCell getBan(int n) {
		return new HBoxCell(n, this.P_num(), this.ban_num, this.ban_name, this.banManager_size);
	}
	
	public static class HBoxCell extends HBoxModel {
		
		public Socket socket = ProfessorDataModel.socket;
		private Label size = new Label();

		
		public HBoxCell(int n, int P_num, int ban_num, String ban_name, int banManager_size) {
			super();
			this.setSpacing(10);
			
			num.setText(n + "");
			num.setStyle("-fx-font-family: Dubai Medium; -fx-alignment: center; -fx-text-fill: #ffffff; -fx-font-size: 20; -fx-background-color: #5ad18f;");
			num.setPrefWidth(40);
			num.setPrefHeight(40);

			name.setText("  " + ban_name);
			name.setStyle("-fx-font-family: Dubai Medium; -fx-text-fill: #5ad18f; -fx-font-size: 20; -fx-background-color: #f0fff0;");
			name.setPrefWidth(350);
			name.setPrefHeight(40);
			
			size.setText("" + banManager_size);
			size.setStyle("-fx-font-family: Dubai Medium; -fx-alignment: center; -fx-text-fill: #5ad18f; -fx-font-size: 20;");
			size.setPrefWidth(50);
			size.setPrefHeight(40);
			
			enter.setText("ENTER");
			enter.setStyle("-fx-font-family: Dubai Medium; -fx-text-fill: #ffffff; -fx-font-size: 15; -fx-background-color: #5ad18f;");
			enter.setPrefWidth(100);
			enter.setPrefHeight(30);
			enter.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					
					String responseMessage = null;
					try {
						String requestMessage = "GetBan:" + P_num + ":" + ban_num;
						BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
						PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
						writer.println(requestMessage);
						writer.flush();
						responseMessage = reader.readLine();
					} catch(IOException e1) {
						e1.printStackTrace();
					}
					String[] responseTokens = responseMessage.split(":");
					
					if(responseTokens[0].equals("GetBan")) {
						if(! responseTokens[1].equals("Success")) {
							System.out.println("Fail : GetBan");
						}
						else {
							System.out.println("  [Enter] Ban: " + responseTokens[3]);
							//GetAllBan:Success:BNum:Name:BM_Size
							
							int BNum = Integer.parseInt(responseTokens[2]);
							String name = responseTokens[3];
							int bmSize = Integer.parseInt(responseTokens[4]);
							
							Ban newBan = new Ban(P_num, BNum, name, bmSize);
							ProfessorDataModel.ban = newBan;
						}
					}
					try {
						Stage primaryStage = (Stage) name.getScene().getWindow();
						Parent search = FXMLLoader.load(getClass().getResource("/gui/Ban.fxml"));
						Scene scene = new Scene(search);
						primaryStage.setTitle("GuessWhat/" + ban_name);
						primaryStage.setScene(scene);
						primaryStage.show();
					} catch (Exception a) {
						a.printStackTrace();
					}
				}
			});

			this.getChildren().addAll(num, name, size, enter);
	
		}
	}
}
