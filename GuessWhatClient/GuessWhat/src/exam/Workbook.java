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
	private int WNum;
	private String BMNum;
	private String PNum;
	private String name;
	private int size;
	private Problem [] problemSet;
	
	//Constructor start
	public Workbook() {
		problemSet = new Problem[10];
	}
	
	public Workbook(String[] workbookInfo) {
		this.name = workbookInfo[0];
		this.size = Integer.parseInt(workbookInfo[1]);
	}
	//Constructor end
	
	//Getter start
	public int getWBNum() { return WNum; }
	public String getBMnum() { return this.BMNum;}
	public String getPNum() { return this.PNum;}
	public String getName() { return name;}
	public int getSize() { return size;	}
	public Problem[] problemSet() { return problemSet; }
	//Getter end
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("");
		sb.append("Name:"+ this.name + ", Size:" + this.size);
		return new String(sb);
	}
	
	public HBoxCell getWorkbook() {
		return new HBoxCell(this.WNum, this.name, this.problemSet.length);
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

