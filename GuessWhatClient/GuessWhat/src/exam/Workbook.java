package exam;

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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.HBoxModel;
import model.ProfessorDataModel;

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

		public Socket socket = ProfessorDataModel.socket;
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
					String responseMessage = null;
					try {
						String requestMessage = "GetCurrentWorkbook:" + W_num;
						BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
						PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
						writer.println(requestMessage);
						writer.flush();
						responseMessage = reader.readLine();
					} catch(IOException e1) {
						e1.printStackTrace();
					}
					String[] responseTokens = responseMessage.split(":");
					
					if(responseTokens[0].equals("GetCurrentWorkbook")) {
						if(! responseTokens[1].equals("Success")) {
							System.out.println("Fail : GetCurrentWorkbook");
						}
						else {
							System.out.println("  [Enter] Workbook: " + responseTokens[2]);
							//GetAllBan:Success:BNum:Name:BM_Size
							
							String name = responseTokens[2];
							int wbSize = Integer.parseInt(responseTokens[3]);
							
							Workbook wb = new Workbook(P_num, W_num, name, wbSize);
							ProfessorDataModel.workbook = wb;
							
							ProfessorDataModel.problemList = new Problem[wbSize];
							String responseMessage2 = null;
							try {
								String requestMessage2 = "GetAllProblem:" + W_num;
								BufferedReader reader2 = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
								PrintWriter writer2 = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
								writer2.println(requestMessage2);
								writer2.flush();
								responseMessage2 = reader2.readLine();
							} catch(IOException e1) {
								e1.printStackTrace();
							}
							// GetAllProblem:Success:PPNum`WNum`Question`Answer`type`answercontent_...
							String[] responseTokens2 = responseMessage2.split(":");
							if(responseTokens2[0].equals("GetAllProblem")) {
								if(! responseTokens2[1].equals("Success")) {
									System.out.println("Fail : GetAllProblem");
								}
								else {
									String[] pbList = responseTokens2[2].split("_");
									for(int i = 0 ; i < pbList.length ; i++) {
										String[] problemInfo = pbList[i].split("`");
										
										int pbNum = Integer.parseInt(problemInfo[0]);
										String q = problemInfo[2];
										String s = problemInfo[3];
										String type = problemInfo[4];
										String content = problemInfo[5];
										
										Problem newPB = new Problem(P_num, W_num, pbNum, type, q, s, content);
 										ProfessorDataModel.problemList[i] = newPB;
									}
									ProfessorDataModel.problem=ProfessorDataModel.problemList[0];
									ProfessorDataModel.currentPB=0;
								}
							}
						}
					}
					
					if(ProfessorDataModel.problemList[0].getType() == ProblemType.MultipleChoice) {
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
					}else if (ProfessorDataModel.problemList[0].getType() == ProblemType.ShortAnswer) {
						try {
							Stage primaryStage = (Stage) name.getScene().getWindow();
							Parent search = FXMLLoader.load(getClass().getResource("/gui/WorkBook_ShortAnswer.fxml"));
							Scene scene = new Scene(search);
							primaryStage.setTitle("GuessWhat/" + name.getText());
							primaryStage.setScene(scene);
							primaryStage.show();
						} catch (Exception a) {
							a.printStackTrace();
						}
					}else {
						try {
							Stage primaryStage = (Stage) name.getScene().getWindow();
							Parent search = FXMLLoader.load(getClass().getResource("/gui/WorkBook_Subjective.fxml"));
							Scene scene = new Scene(search);
							primaryStage.setTitle("GuessWhat/" + name.getText());
							primaryStage.setScene(scene);
							primaryStage.show();
						} catch (Exception a) {
							a.printStackTrace();
						}
					}
				}
			});

			this.getChildren().addAll(num, name, size, enter);

		}

	}
}
