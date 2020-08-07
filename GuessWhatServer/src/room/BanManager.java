package room;

import exam.Workbook;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import room.BanManager.HBoxCell;
import room.BanManager.State;

//workbook을 풀이하고 결과를 관리
public class BanManager {
	private int P_num;
	private int BM_num;
	private String BM_name;
	public enum State { OPEN, ING, CLOSE };
	private State BM_state;
	private String BM_roomcode;
	private int B_Num;
	private int student_size; 
	private String workbook;
	private AnswerSet answerSet;
	
	//Constructor start
	public BanManager(int num, String name, String password, String workbook) {
		this.BM_name = name;
		this.BM_num = num;
		this.BM_roomcode = password;
		this.BM_state = State.OPEN;
		this.workbook = workbook;
		this.answerSet = new AnswerSet();
	}
	
	public BanManager(int PNum, int bNum, int BMNum, String name, String state, String code, String workbookName, int studentSize) {
		this.P_num = PNum;
		this.B_Num = bNum;
		this.BM_num = BMNum;
		this.BM_name = name;
		this.BM_state = stateOf(state);
		this.BM_roomcode = code;
		this.workbook = workbookName;
		this.student_size = studentSize;
	}
	
	public BanManager(String[] banManagerInfo) {
		this.BM_num = Integer.parseInt(banManagerInfo[0]);
		this.BM_name = banManagerInfo[1];
		this.BM_state = this.stateOf(banManagerInfo[2]);
		this.BM_roomcode = banManagerInfo[3];
		this.B_Num = Integer.parseInt(banManagerInfo[4]);
		this.student_size = Integer.parseInt(banManagerInfo[5]);
	}
	
	//Constructor end
	
	//Getter start
	public int BM_num() { return BM_num; } 
	public String BM_name() { return BM_name; }
	public State BM_sate() { return BM_state; }
	public String BM_roomcode() { return BM_roomcode; }
	public String workbook() { return workbook; }
	public AnswerSet answerSet() { return answerSet; }
	public int BNum() {return this.B_Num;}
	public int student_size() { return student_size; }
	//Getter end
	
	
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
	
	public String stateToString(State state) {
		switch(state) {
		case OPEN:
			return "Open";
		case ING:
			return "Ing";
		default:
			return "Close";
		}
	}
	
	//Public Method
	public HBoxCell getBanManager() {
		return new HBoxCell(this.BM_num, this.BM_name, this.BM_state);
	}
	
	public String tokenString() {
		StringBuilder sb = new StringBuilder("");
		sb.append(this.P_num + "-" + this.BNum() + "-" + this.BM_num + "-" + this.BM_name + "-" + this.BM_roomcode + "-" + this.BM_state.toString() + "-" + this.student_size   );
		return new String(sb);
	}
	
	public static class HBoxCell extends HBox {
		
		private Label num = new Label();
		private Label name = new Label();
		private Label state = new Label();
		private Button enter = new Button();
		
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

