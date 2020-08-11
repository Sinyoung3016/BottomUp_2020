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
	private int BM_Num;
	private int W_Num;
	private String W_name = "NewWorkBook";

	private int WorkBook_size = 0;

	// Constructor start
	public Workbook(String[] workbookInfo) {
		this.P_Num = Integer.parseInt(workbookInfo[0]);
		this.W_Num = Integer.parseInt(workbookInfo[1]);
		this.W_name = workbookInfo[2];
		this.WorkBook_size = Integer.parseInt(workbookInfo[3]);
	}

	public Workbook(String responseToken) {
		String[] tokens = responseToken.split("-");
		this.P_Num = Integer.parseInt(tokens[0]);
		this.BM_Num = Integer.parseInt(tokens[1]);
		this.W_Num = Integer.parseInt(tokens[2]);
		this.W_name = tokens[3];
		this.WorkBook_size = Integer.parseInt(tokens[4]);
	}

	public Workbook(int PNum, int WNum, String name, int size) {
		this.P_Num = PNum;
		this.BM_Num = 0;
		this.W_Num = WNum;
		this.W_name = name;
		this.WorkBook_size = size;
	}

	public Workbook() {
	}
	// Constructor end

	public void setName(String name) {
		this.W_name = name;
	}

	public void setSize(int n) {
		this.WorkBook_size = n;
	}

	public void setP_Num(int num) {
		this.P_Num = num;
	}

	public void setW_Num(int num) {
		this.W_Num = num;
	}

	// Getter start
	public int P_Num() {
		return this.P_Num;
	}

	public int W_Num() {
		return this.W_Num;
	}

	public String W_name() {
		return this.W_name;
	}

	public int WorkBooksize() {
		return this.WorkBook_size;
	}
	// Getter end

	@Override
	public String toString() {
		return this.W_name;
	}

	public String tokenString() {
		StringBuilder sb = new StringBuilder("");
		sb.append(this.P_Num() + ":" + this.W_name());
		return new String(sb);
	}

	public HBoxCell getWorkbook(int n) {
		return new HBoxCell(n, this.P_Num, this.W_Num, this.W_name, this.WorkBook_size);
	}

	public static class HBoxCell extends HBoxModel {

		private Label size = new Label();

		public HBoxCell(int n, int P_num, int W_num, String W_name, int W_size) {
			super();
			this.setSpacing(10);

			num.setText(n + "");
			num.setStyle(
					"-fx-font-family: Dubai Medium; -fx-alignment: center; -fx-text-fill: #ffffff; -fx-font-size: 20; -fx-background-color: #5ad18f;");
			num.setPrefWidth(40);
			num.setPrefHeight(40);
			name.setText("  " + W_name);
			name.setStyle(
					"-fx-font-family: Dubai Medium; -fx-text-fill: #5ad18f; -fx-font-size: 20; -fx-background-color: #f0fff0;");
			name.setPrefWidth(350);
			name.setPrefHeight(40);

			size.setText("" + W_size);
			size.setStyle(
					"-fx-font-family: Dubai Medium; -fx-alignment: center; -fx-text-fill: #5ad18f; -fx-font-size: 20;");
			size.setPrefWidth(50);
			size.setPrefHeight(40);

			enter.setText("ENTER");
			enter.setStyle(
					"-fx-font-family: Dubai Medium; -fx-text-fill: #ffffff; -fx-font-size: 15; -fx-background-color: #5ad18f;");
			enter.setPrefWidth(100);
			enter.setPrefHeight(30);
			enter.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent e) {
					try {
						Stage primaryStage = (Stage) name.getScene().getWindow();
						Parent search = FXMLLoader.load(getClass().getResource("/gui/WorkBook_MultipleChoice.fxml"));
						Scene scene = new Scene(search);
						primaryStage.setTitle("GuessWhat/" + name.getText());
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
