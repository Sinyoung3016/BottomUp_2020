package exam;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.HBoxModel;

//Problem의 모음
public class Workbook {

	private int P_Num;
	
	private int W_Num;
	private String W_name;
	
	private int WorkBook_size;
	
	//Constructor start
	public Workbook(String[] workbookInfo) {
		this.P_Num =  Integer.parseInt(workbookInfo[0]);
		this.W_Num =  Integer.parseInt(workbookInfo[1]);
		this.W_name = workbookInfo[2];
		this.WorkBook_size = Integer.parseInt(workbookInfo[3]);
	}
	//Constructor end
	
	//Getter start
	public int P_Num() { return this.P_Num;}
	public int W_Num() { return this.W_Num; }
	public String W_name() { return this.W_name;}
	public int WorkBooksize() { return this.WorkBook_size; }
	//Getter end
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("");
		sb.append("Name:"+ this.W_name + ", Size:" + this.WorkBook_size);
		return new String(sb);
	}
	
	public Workbook(String responseToken) {
		String[] tokens = responseToken.split("-");
		this.P_Num = Integer.parseInt(tokens[0]);
		this.W_Num = Integer.parseInt(tokens[1]);
		this.W_name = tokens[2];
		this.WorkBook_size = Integer.parseInt(tokens[3]);
	}
	
	public HBoxCell getWorkbook(int num) {
		return new HBoxCell(num, this.W_name, this.WorkBook_size);
	}
		
	public static class HBoxCell extends HBoxModel {
			
	private Label size = new Label();
	
	public HBoxCell(int W_num, String W_name, int W_size) {
		super();
		this.setSpacing(10);
		
		num.setText(W_num + "");
		num.setStyle("-fx-font-family: Dubai Medium; -fx-alignment: center; -fx-text-fill: #ffffff; -fx-font-size: 20; -fx-background-color: #5ad18f;");
		num.setPrefWidth(40);
		num.setPrefHeight(40);
			name.setText("  " + W_name);
		name.setStyle("-fx-font-family: Dubai Medium; -fx-text-fill: #5ad18f; -fx-font-size: 20; -fx-background-color: #f0fff0;");
		name.setPrefWidth(350);
		name.setPrefHeight(40);
		
		size.setText("" + W_size);
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

