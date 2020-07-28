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

//BanManager의 모음
public class Ban {
	private int ban_num;
	private String ban_name;
	private BanManager[] banManagerSet;
	
	//Constructor start
	public Ban() {
		banManagerSet = new BanManager[10];
	}
	//Constructor end
	
	//Getter start
	public int ban_num() { return this.ban_num;}
	public String ban_name() { return this.ban_name; }
	public BanManager[] banManagerSet() { return this.banManagerSet; }
	//Getter end
	
	public HBoxCell getBook() {
		return new HBoxCell(this.ban_num, this.ban_name, this.banManagerSet.length);
	}
	
	public static HBoxCell getBook(String no_message) {
		return new HBoxCell(no_message);
	}
	
	public static class HBoxCell extends HBox {
		
		private Label num = new Label();
		private Button name = new Button();
		private Label size = new Label();
		
		HBoxCell(int ban_num, String ban_name, int banManagerSize) {
			super();
			num.setText(ban_num + "");
			num.setStyle("-fx-text-fill: #5ad18f; -fx-font-size: 14; ");
			num.setPrefWidth(20);
			num.setPrefHeight(20);

			name.setText(ban_name);
			name.setStyle("-fx-text-fill: #5ad18f; -fx-font-size: 14; -fx-background-color: #5ad18f;");
			name.setPrefWidth(200);
			name.setPrefHeight(20);
			name.setOnAction(new EventHandler<ActionEvent>() {
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

			size.setText(banManagerSize + "");
			size.setStyle("-fx-text-fill: #5ad18f; -fx-font-size: 14;");
			size.setPrefWidth(130);
			size.setPrefHeight(20);

			this.getChildren().addAll(num, name, size);
		}

		HBoxCell(String no_book_message) {
			super();
			num.setText(no_book_message);
			this.getChildren().addAll(num);
		}

	}

}
