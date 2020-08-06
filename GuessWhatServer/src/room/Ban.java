package room;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import room.Ban.HBoxCell;

//BanManager의 모음
public class Ban {
	
	private int P_num;
	
	private int ban_num;
	private String ban_name;
	
	private int banManager_size;
	private BanManager[] banManagerSet;
	
	//Constructor start
	public Ban(int num, String name) {
		this.ban_num = num;
		this.ban_name = name;
		banManagerSet = new BanManager[10];
	}
	public Ban(String name) {
		this.ban_name = name;
	}
	public Ban(int pnum, int bnum, String name, int bmsize) {
		this.P_num = pnum;
		this.ban_num = bnum;
		this.ban_name = name;
		this.banManager_size = bmsize;
	}
	//Constructor end
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("");
		sb.append("Name:"+ this.ban_name);
		return new String(sb);
	}
	
	//Getter start
	public int P_num() { return this.P_num; }
	
	public int ban_num() { return this.ban_num;}
	public String ban_name() { return this.ban_name; }
	
	public int banManager_Size() { return this.banManager_size; }
	public BanManager[] banManagerSet() { return this.banManagerSet; }
	//Getter end
	
	public HBoxCell getBan() {
		return new HBoxCell(this.ban_num, this.ban_name, this.banManagerSet.length);
	}
	
	public static class HBoxCell extends HBox {
		
		private Label num = new Label();
		private Label name = new Label();
		private Label size = new Label();
		private Button enter = new Button();
		
		public HBoxCell(int ban_num, String ban_name, int banManagerSize) {
			super();
			this.setSpacing(10);
			
			num.setText(ban_num + "");
			num.setStyle("-fx-font-family: Dubai Medium; -fx-alignment: center; -fx-text-fill: #ffffff; -fx-font-size: 20; -fx-background-color: #5ad18f;");
			num.setPrefWidth(40);
			num.setPrefHeight(40);

			name.setText("  " + ban_name);
			name.setStyle("-fx-font-family: Dubai Medium; -fx-text-fill: #5ad18f; -fx-font-size: 20; -fx-background-color: #f0fff0;");
			name.setPrefWidth(350);
			name.setPrefHeight(40);
			
			size.setText("" + banManagerSize);
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
					try {
						Stage primaryStage = (Stage) name.getScene().getWindow();
						Parent search = FXMLLoader.load(getClass().getResource("/gui/Ban.fxml"));
						Scene scene = new Scene(search);
						primaryStage.setTitle("HelloBooks/" + name.getText());
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
