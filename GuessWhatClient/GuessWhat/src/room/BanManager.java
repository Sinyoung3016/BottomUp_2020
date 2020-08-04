package room;

import exam.Workbook;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.HBoxModel;
import room.BanManager.State;

//workbook을 풀이하고 결과를 관리
public class BanManager {
	private int BM_num;
	private String BM_name;
	public enum State { OPEN, ING, CLOSE };
	private State BM_state;
	private String BM_password;
	private int BNum;
	private int size; 
	private Workbook workbook;
	private AnswerSet answerSet;


	//Constructor start
	public BanManager(int num, String name, String password, Workbook workbook) {
		this.BM_name = name;
		this.BM_num = num;
		this.BM_password = password;
		this.BM_state = State.OPEN;
		this.workbook = workbook;
		this.answerSet = new AnswerSet();
	}
	public BanManager(String responseTokens) {
		String[] banManagerInfo = responseTokens.split("-");
		this.BM_num = Integer.parseInt(banManagerInfo[0]);
		this.BM_name = banManagerInfo[1];
		this.BM_state = this.stateOf(banManagerInfo[2]);
		this.BM_password = banManagerInfo[3];
		this.BM_num = Integer.parseInt(banManagerInfo[4]);
		this.size = Integer.parseInt(banManagerInfo[5]);
	}
	
	public BanManager() {
		
	}
	//Constructor end
	

	//Getter,Setter start

	//Setter start
	public void setBM_state_OPEN() {
		this.BM_state = State.OPEN;
	}
	public void setBM_state_ING() {
		this.BM_state = State.ING;
	}
	public void setBM_state_CLOSE() {
		this.BM_state = State.CLOSE;
	}
	public void setSize(int n) {
		this.size = n;
	}
	//Setter end
	
	//Getter start
	public int BM_num() { return BM_num; } 
	public String BM_name() { return BM_name; }
	public String BM_password() { return BM_password; }
	public Workbook workbook() { return workbook; }
	public State BM_state() { return BM_state; }
	public AnswerSet answerSet() { return answerSet; }
	public int size() { return size; } 
	//Getter end

	
	
	public void setBM_num(int bM_num) {
		BM_num = bM_num;
	}

	public void setBM_state(State bM_state) {
		BM_state = bM_state;
	}

	public void setBM_name(String bM_name) {
		BM_name = bM_name;
	}

	public void setBM_password(String bM_password) {
		BM_password = bM_password;
	}

	public void setWorkbook(Workbook workbook) {
		this.workbook = workbook;
	}

	public void setAnswerSet(AnswerSet answerSet) {
		this.answerSet = answerSet;
	}

	//Getter,Setter end
	
	//Private Method
	private State stateOf(String state) {
		switch(state) {
		case "Open":
			return State.OPEN;
		case "Ing":
			return State.ING;
		default:
			return State.CLOSE;
		}
	}
		
	//Public Method
	public HBoxCell getBanManager() {
		return new HBoxCell(this.BM_num, this.BM_name, this.BM_state);
	}
	
	public String stringOfState() {
		return this.BM_state.toString();
	}
	public static class HBoxCell extends HBoxModel {
		
		private Label state = new Label();
		
		public HBoxCell(int BM_num, String BM_name, State BM_state) {
			super();
			this.setSpacing(10);
			
			num.setText(BM_num + "");
			num.setStyle("-fx-font-family: Dubai Medium; -fx-alignment: center; -fx-text-fill: #ffffff; -fx-font-size: 20; -fx-background-color: #5ad18f;");
			num.setPrefWidth(40);
			num.setPrefHeight(40);

			name.setText("  " + BM_name);
			name.setStyle("-fx-font-family: Dubai Medium; -fx-text-fill: #5ad18f; -fx-font-size: 20; -fx-background-color: #f0fff0;");
			name.setPrefWidth(330);
			name.setPrefHeight(40);
			
			state.setText("" + BM_state.toString());
			state.setStyle("-fx-font-family: Dubai Medium; -fx-alignment: center; -fx-text-fill: #5ad18f; -fx-font-size: 20;");
			state.setPrefWidth(60);
			state.setPrefHeight(40);
			
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
						primaryStage.setTitle("HelloBooks/"+ name.getText());
						primaryStage.setScene(scene);
						primaryStage.show();
					} catch (Exception a) {
						a.printStackTrace();
					}
				}
			});
			
			this.getChildren().addAll(num, name, state, enter);
		}
	}
}

