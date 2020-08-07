package room;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.HBoxModel;
import model.ProfessorDataModel;

//workbook을 풀이하고 결과를 관리
public class BanManager {
	
	public enum State {
		OPEN, ING, CLOSE
	};
	
	private int P_num;
	private int ban_num;
	private int BM_num;
	
	private State BM_state;
	private String BM_name;
	private String BM_roomcode;
	private int Student_size;
	
	private String workBook;

	// Constructor start
	public BanManager(int P_num, int ban_num, int BM_num, String name, String roomcode, String workBook ) {
		this.P_num = P_num;
		this.ban_num = ban_num;
		this.BM_num = BM_num;
		this.BM_name = name;
		this.BM_roomcode = roomcode; //gui 내부에서 초기화
		this.workBook = workBook;
		
		this.BM_state = State.OPEN;
		this.Student_size = 0;
	}	
	
	public BanManager(int P_num, int ban_num, int BM_num, String name, String roomcode, String workbok, int student_size) {
		this.P_num = P_num;
		this.ban_num = ban_num;
		this.BM_num = BM_num;
		this.BM_name = name;
		this.BM_roomcode = roomcode;
		this.workBook = workbok;
		this.Student_size = student_size;
	}
	public BanManager(String responseTokens) {
		String[] banManagerInfo = responseTokens.split("-");	
		this.P_num = Integer.parseInt(banManagerInfo[0]);
		this.ban_num = Integer.parseInt(banManagerInfo[1]);
		this.BM_num = Integer.parseInt(banManagerInfo[2]);
		this.BM_name = banManagerInfo[3];
		this.BM_roomcode = banManagerInfo[4];
		//this.workBook = banManagerInfo[5];
		this.BM_state = this.stateOf(banManagerInfo[5]);
		this.Student_size = Integer.parseInt(banManagerInfo[6]);
	}
	// Constructor end

	// Setter start
	public void setBM_state_OPEN() { this.BM_state = State.OPEN;}
	public void setBM_state_ING() { this.BM_state = State.ING;}
	public void setBM_state_CLOSE() { this.BM_state = State.CLOSE;}
	public void setSize(int size) { this.Student_size = size; }
	// Setter end

	// Getter start
	public int P_num() { return this.P_num; }
	public int ban_num() { return this.ban_num; }
	public int BM_num() { return this.BM_num; }
	public String BM_name() { return this.BM_name; }
	public String BM_roomcode() { return this.BM_roomcode; }
	public String workbook() { return this.workBook; }
	public State BM_state() { return this.BM_state; }
	public int Student_size() {	return this.Student_size; }
	// Getter end

	private State stateOf(String state) {
		switch (state) {
		case "OPEN":
			return State.OPEN;
		case "ING":
			return State.ING;
		default:
			return State.CLOSE;
		}
	}
	
	public String stringOfState() { return BM_state.toString();}

	// Public Method
	public HBoxCell getBanManager(int n) {
		return new HBoxCell(n, this.P_num, this.ban_num, this.BM_num, this.BM_name, this.Student_size, this.BM_state);
	}

	public static class HBoxCell extends HBoxModel {

		private Label size = new Label();
		private Label state = new Label();

		public HBoxCell(int n, int P_num, int ban_Num, int BM_num, String BM_name, int Student_size, State BM_state) {
			super();
			String ban_Name =  ProfessorDataModel.ban.ban_name();
			
			this.setSpacing(10);

			num.setText(n + "");
			num.setStyle(
					"-fx-font-family: Dubai Medium; -fx-alignment: center; -fx-text-fill: #ffffff; -fx-font-size: 20; -fx-background-color: #5ad18f;");
			num.setPrefWidth(40);
			num.setPrefHeight(40);

			name.setText("  " + BM_name);
			name.setStyle(
					"-fx-font-family: Dubai Medium; -fx-text-fill: #5ad18f; -fx-font-size: 20; -fx-background-color: #f0fff0;");
			name.setPrefWidth(290);
			name.setPrefHeight(40);
			
			size.setText("" + Student_size);
			size.setStyle(
					"-fx-font-family: Dubai Medium; -fx-alignment: center; -fx-text-fill: #5ad18f; -fx-font-size: 20; -fx-background-color: #f0fff0;");
			size.setPrefWidth(40);
			size.setPrefHeight(40);

			state.setText("" + BM_state.toString());
			state.setStyle(
					"-fx-font-family: Dubai Medium; -fx-alignment: center; -fx-text-fill: #5ad18f; -fx-font-size: 20;");
			state.setPrefWidth(60);
			state.setPrefHeight(40);

			enter.setText("ENTER");
			enter.setStyle(
					"-fx-font-family: Dubai Medium; -fx-text-fill: #ffffff; -fx-font-size: 15; -fx-background-color: #5ad18f;");
			enter.setPrefWidth(100);
			enter.setPrefHeight(30);
			enter.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					if (BM_state.equals(State.OPEN)) {

						try {
							Stage primaryStage = (Stage) name.getScene().getWindow();
							Parent search = FXMLLoader.load(getClass().getResource("/gui/BanManagerSoon.fxml"));
							Scene scene = new Scene(search);
							primaryStage.setTitle("GuessWhat/" + ban_Name);
							primaryStage.setScene(scene);
							primaryStage.show();
						} catch (Exception a) {
							a.printStackTrace();
						}
					} else if (BM_state.equals(State.ING)) {

						try {
							Stage primaryStage = (Stage) name.getScene().getWindow();
							Parent search = FXMLLoader.load(getClass().getResource("/gui/BanMAnagerProgress.fxml"));
							Scene scene = new Scene(search);
							primaryStage.setTitle("GuessWhat/" + ban_Name);
							primaryStage.setScene(scene);
							primaryStage.show();
						} catch (Exception a) {
							a.printStackTrace();
						}
					} else if (BM_state.equals(State.CLOSE)) {

						try {
							Stage primaryStage = (Stage) name.getScene().getWindow();
							Parent search = FXMLLoader.load(getClass().getResource("/gui/BanManagerDoneFirst.fxml"));
							Scene scene = new Scene(search);
							primaryStage.setTitle("GuessWhat/" + ban_Name);
							primaryStage.setScene(scene);
							primaryStage.show();
						} catch (Exception a) {
							a.printStackTrace();
						}
					}

				}

			});

			this.getChildren().addAll(num, name, size, state, enter);
		}
	}
}
